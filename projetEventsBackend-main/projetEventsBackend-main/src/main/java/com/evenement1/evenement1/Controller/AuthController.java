package com.evenement1.evenement1.Controller;

import com.evenement1.evenement1.DTO.AuthRequest;
import com.evenement1.evenement1.DTO.AuthResponse;
import com.evenement1.evenement1.ModelsAuth.AuthenticationResponse;
import com.evenement1.evenement1.ModelsAuth.LoginRequest;
import com.evenement1.evenement1.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticationAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getEmail());

                return ResponseEntity.ok(new AuthResponse("Utilisateur authentifié", token));

            } else {
                throw new UsernameNotFoundException("Invalid user request !!");
            }
        } catch (AuthenticationException e) {
            System.out.println("Authentication exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

        }
    }
}

