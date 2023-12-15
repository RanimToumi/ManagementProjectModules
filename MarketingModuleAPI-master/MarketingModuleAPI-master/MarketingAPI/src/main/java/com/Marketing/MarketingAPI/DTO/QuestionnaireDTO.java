package com.Marketing.MarketingAPI.DTO;

import com.Marketing.MarketingAPI.models.Campaign;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireDTO {
    private Long id;
    private String title;
    private String description;
    private List<QuestionDTO> questions;
    private Long campaign_id;

}
