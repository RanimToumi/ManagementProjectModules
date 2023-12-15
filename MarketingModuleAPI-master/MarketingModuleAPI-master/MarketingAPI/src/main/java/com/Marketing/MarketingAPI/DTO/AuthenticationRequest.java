package com.Marketing.MarketingAPI.DTO;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationRequest {
    private String email;
    private String password;
}
