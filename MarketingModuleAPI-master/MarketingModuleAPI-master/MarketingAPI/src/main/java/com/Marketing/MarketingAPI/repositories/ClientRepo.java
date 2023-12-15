package com.Marketing.MarketingAPI.repositories;

import com.Marketing.MarketingAPI.models.Client;
import com.Marketing.MarketingAPI.models.FieldOfActivity;
import com.Marketing.MarketingAPI.models.Gender;
import com.Marketing.MarketingAPI.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client,Long> {
    List<Client> findByGender(Gender gender);
    List<Client> findByOccupation(String occupation);
    List<Client> findByCountry(String country);
    List<Client> findByField(FieldOfActivity field);
}
