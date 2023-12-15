package com.Marketing.MarketingAPI.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String occupation;
    private String country;
    @ManyToOne
    @JoinColumn(name="field_id")
    private FieldOfActivity field;
}
