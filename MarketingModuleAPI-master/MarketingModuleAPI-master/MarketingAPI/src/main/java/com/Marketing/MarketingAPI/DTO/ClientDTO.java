package com.Marketing.MarketingAPI.DTO;
import com.Marketing.MarketingAPI.models.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private int age;
    private Gender gender;
    private String occupation;
    private String country;
    private Long field_id;
}
