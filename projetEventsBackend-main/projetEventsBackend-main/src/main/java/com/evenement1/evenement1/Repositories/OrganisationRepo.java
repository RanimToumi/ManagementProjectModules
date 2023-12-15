package com.evenement1.evenement1.Repositories;

import com.evenement1.evenement1.Models.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepo extends JpaRepository<Organisation,Long> {
}
