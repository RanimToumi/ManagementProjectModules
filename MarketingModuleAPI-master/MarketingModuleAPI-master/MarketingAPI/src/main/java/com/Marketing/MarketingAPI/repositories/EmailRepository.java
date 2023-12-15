package com.Marketing.MarketingAPI.repositories;
import com.Marketing.MarketingAPI.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email,Long> {

}
