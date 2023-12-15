package com.ordering.procurementFlow.repositories;

import com.ordering.procurementFlow.Models.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email,Long> {

}
