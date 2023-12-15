package com.projectTBS.employeesmicroservice.controller;

import com.projectTBS.employeesmicroservice.dto.AuthRequest;
import com.projectTBS.employeesmicroservice.dto.AuthResponse;
import com.projectTBS.employeesmicroservice.models.AuthenticationResponse;
import com.projectTBS.employeesmicroservice.models.LoginRequest;
import com.projectTBS.employeesmicroservice.service.Implementation.JwtService;
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
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtService jwtService;





    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Étape 1: Création de l'objet UsernamePasswordAuthenticationToken
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Étape 2: Tentative d'authentification
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("Utilisateur authentifié: " + userDetails.getUsername());

            // Étape 3: Mise à jour du contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Étape 4: Mise à jour du contexte de sécurité
            UserDetails userDetailsAfterUpdate = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("Utilisateur après la mise à jour du contexte: " + userDetailsAfterUpdate.getUsername());

            // Étape 5: Réponse à la requête

            // Supposons que getAuthorities() retourne une collection d'objets GrantedAuthority
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            // Supposons que getAuthorities() retourne une seule chaîne avec des rôles séparés par des virgules
            String rolesString = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            // Utilisez la chaîne directement comme votre rôle
            String role = rolesString;

            String email=userDetails.getUsername();

            return ResponseEntity.ok(new AuthenticationResponse("Utilisateur authentifié",email, role));



        } catch (AuthenticationException e) {
            // Gestion des erreurs d'authentification
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'authentification");
        }
    }




//    @PostMapping("/authenticate")
//    public String authenticationAndGetToken (@RequestBody AuthRequest authRequest){
//        Authentication authentication =authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())));
//        if (authentication.isAuthenticated()){
//            return jwtService.generateToken(authRequest.getEmail());
//        }else {
//            throw new UsernameNotFoundException("invalid user request !!");
//        }
//    }


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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }



}
