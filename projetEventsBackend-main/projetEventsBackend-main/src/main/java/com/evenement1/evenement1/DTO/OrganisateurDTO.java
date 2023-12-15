package com.evenement1.evenement1.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganisateurDTO {
    private  Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String adresse;
    private String cin;
    private String phoneNumber;
    private String password;
    private String role;
}
