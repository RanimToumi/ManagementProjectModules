package com.evenement1.evenement1.Configuration;

import com.evenement1.evenement1.Models.Organisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoUserDetails implements UserDetails {
    private String username;
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    private PasswordEncoder passwordEncoder;
    private GrantedAuthority authority;
    private Long id;
    private String role;

    public UserInfoUserDetails(Organisateur organisateur) {
//        this.username=employeeInfo.getFirstName()+employeeInfo.getLastName();
        this.email=organisateur.getEmail();

        this.authorities= Arrays.stream(organisateur.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        this.password = organisateur.getPassword();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email ;
    }




    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
