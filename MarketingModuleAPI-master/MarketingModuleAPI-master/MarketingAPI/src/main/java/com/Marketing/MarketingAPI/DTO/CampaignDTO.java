package com.Marketing.MarketingAPI.DTO;

import com.Marketing.MarketingAPI.models.CampaignStatus;
import com.Marketing.MarketingAPI.models.Client;
import com.Marketing.MarketingAPI.models.Product;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CampaignStatus status;
    private String campaignText;
    private List<Client> clients;
    private Long product_id;
}
