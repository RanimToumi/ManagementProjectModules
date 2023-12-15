package com.Marketing.MarketingAPI.repositories;

import com.Marketing.MarketingAPI.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question, Long> {
}
