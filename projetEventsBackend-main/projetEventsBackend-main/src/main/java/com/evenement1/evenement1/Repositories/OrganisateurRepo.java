package com.evenement1.evenement1.Repositories;

import com.evenement1.evenement1.Models.Organisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OrganisateurRepo extends JpaRepository<Organisateur,Long> {

    Optional<Organisateur> findByEmail(String email);
}
