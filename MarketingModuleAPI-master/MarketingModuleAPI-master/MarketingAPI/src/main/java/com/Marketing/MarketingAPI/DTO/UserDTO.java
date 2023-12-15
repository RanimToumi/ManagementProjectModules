package com.Marketing.MarketingAPI.DTO;
import com.Marketing.MarketingAPI.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private String position;
    private String imageURL;
    private Role role;
    private boolean tfaEnabled;
}
