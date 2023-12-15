package com.Marketing.MarketingAPI.repositories;

import com.Marketing.MarketingAPI.models.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepo extends JpaRepository<Campaign,Long> {

}
