package com.ordering.procurementFlow.authentication;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationRequest {
    private String email;
    private String password;
}
