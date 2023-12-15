package com.ordering.procurementFlow.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.ordering.procurementFlow.Models.Permission.*;
import static com.ordering.procurementFlow.Models.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enable method-level security

public class SecurityConfiguration extends WebSecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/requisition/**").permitAll()
                .requestMatchers("/api/provider/**").permitAll()
                .requestMatchers("/api/category/**").permitAll()
                .requestMatchers("/api/user/**").permitAll()
                .requestMatchers("/api/product/**").permitAll()
                .requestMatchers("/api/email/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole(ADMIN.name())
                .requestMatchers(OPTIONS,"/api/admin/**").permitAll()
                .requestMatchers(POST,"/api/admin/**").hasAuthority(ADMIN_CREATE.name())
                .requestMatchers(GET,"/api/admin/**").hasAuthority(ADMIN_READ.name())
                .requestMatchers(PUT,"/api/admin/**").hasAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE,"/api/admin/**").hasAuthority(ADMIN_DELETE.name())
                .requestMatchers("/api/procurement/**").hasRole(PURCHASE_MANAGER.name())
                .requestMatchers(GET,"/api/procurement/**").hasAuthority(PURCHASE_MANAGER_READ.name())
                .requestMatchers(POST,"/api/procurement/**").hasAuthority(PURCHASE_MANAGER_CREATE.name())
                .requestMatchers(PUT,"/api/procurement/**").hasAuthority(PURCHASE_MANAGER_UPDATE.name())
                .requestMatchers(DELETE,"/api/procurement/**").hasAuthority(PURCHASE_MANAGER_DELETE.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(withDefaults());


        return http.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS","PUT","DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Specify allowed headers here
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
