package com.Marketing.MarketingAPI.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO {
    private Long id;
    private Long question_id;
    private String answer;
}