package com.Marketing.MarketingAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    private String toEmail;
    private String subject;
    private String body;
    private Long campaignId;

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }
}
