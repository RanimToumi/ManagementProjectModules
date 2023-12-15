package com.ordering.procurementFlow.DTO;

import com.ordering.procurementFlow.Models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private String position;
    private String imageURL;
    private Role role;
    private boolean tfaEnbled;
}
