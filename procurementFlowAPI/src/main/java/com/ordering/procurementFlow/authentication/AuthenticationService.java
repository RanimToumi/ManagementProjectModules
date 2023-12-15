package com.ordering.procurementFlow.authentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordering.procurementFlow.DTO.UserDTO;
import com.ordering.procurementFlow.Models.*;
import com.ordering.procurementFlow.config.JwtService;
import com.ordering.procurementFlow.repositories.UserRepository;
import com.ordering.procurementFlow.repositories.TokenRepository;
import com.ordering.procurementFlow.tfa.TwoFactorAuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TwoFactorAuthenticationService tfaService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponse register(UserDTO request) {
        //String generatedPassword = RandomStringUtils.randomAlphanumeric(8);
        Role role = Role.valueOf(request.getRole().name().toUpperCase());
        logger.info("Role value: {}", role);
        User user;
        switch (role) {
            case ADMIN:
                user = new Admin();
                break;
            case PURCHASE_MANAGER:
                user = new PurchaseManager();
                break;
            case EMPLOYEE:
                user = new Employee();
                break;
            default:
                // Handle unsupported roles
                throw new IllegalArgumentException("Invalid role specified.");
        }
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setCreatedAt(LocalDateTime.now());
        user.setPosition(request.getPosition());
        user.setImageURL(request.getImageURL());
        user.setRole(request.getRole());
        user.setTfaEnabled(request.isTfaEnbled());
        //////
        /*
         User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .position(request.getPosition())
                .imageURL(request.getImageURL())
                .createdAt(LocalDateTime.now())
                .role(request.getRole())
                .tfaEnabled(request.isTfaEnbled())
                .build();*/
        // if tfa is enabled -> generate a secret key for the user
        if(user.isTfaEnabled()){
            user.setSecret(tfaService.generateNewSecret());
        }
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .secretImageUri(tfaService.generateQRCodeImageUri(savedUser.getSecret()))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .tfaEnabled(user.isTfaEnabled())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        if(user.isTfaEnabled()){
            return AuthenticationResponse.builder()
                    .accessToken("")
                    .refreshToken("")
                    .tfaEnabled(true)
                    .build();
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .tfaEnabled(false)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .tfaEnabled(false)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
    public AuthenticationResponse verifyCode(VerificationRequest verificationRequest) {
        User user = userRepository.findByEmail(verificationRequest.getEmail())
                .orElseThrow(()->new EntityNotFoundException(String.format("No user found %s",verificationRequest.getEmail())));
        if(tfaService.isOtpNotValid(user.getSecret(),verificationRequest.getCode())){
            throw  new BadCredentialsException("code is not correct");
        }
        var jwtToken =jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .tfaEnabled(user.isTfaEnabled())
                .accessToken(jwtToken)
                .build();
    }
}
