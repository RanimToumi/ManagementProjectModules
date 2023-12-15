package com.Marketing.MarketingAPI.repositories;

import com.Marketing.MarketingAPI.models.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionnaireRepo extends JpaRepository<Questionnaire,Long> {
    Optional<Questionnaire> findByCampaignId(Long campaignId);
}
