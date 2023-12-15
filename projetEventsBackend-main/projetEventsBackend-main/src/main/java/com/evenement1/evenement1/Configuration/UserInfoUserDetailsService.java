package com.evenement1.evenement1.Configuration;
import com.evenement1.evenement1.Models.Organisateur;
import com.evenement1.evenement1.Repositories.OrganisateurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoUserDetailsService implements  UserDetailsService {

    @Autowired
    private  OrganisateurRepo organisateurRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Organisateur> organisateur = organisateurRepo.findByEmail(email);
        return organisateur.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + email));
    }
}
