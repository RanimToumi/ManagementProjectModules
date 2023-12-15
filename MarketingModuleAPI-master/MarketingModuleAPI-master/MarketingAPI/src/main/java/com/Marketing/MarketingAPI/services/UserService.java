package com.Marketing.MarketingAPI.services;

import com.Marketing.MarketingAPI.DTO.ProductDto;
import com.Marketing.MarketingAPI.DTO.UserDTO;
import com.Marketing.MarketingAPI.models.Product;
import com.Marketing.MarketingAPI.models.Token;
import com.Marketing.MarketingAPI.models.User;
import com.Marketing.MarketingAPI.repositories.TokenRepository;
import com.Marketing.MarketingAPI.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper ;
    // get all employees
    public List<UserDTO> findAllUsers(){
        List<User> users= userRepository.findAll() ;
        return users.stream().map(u->modelMapper.map(u,UserDTO.class)).collect(Collectors.toList()) ;
    }
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

}
