package com.Marketing.MarketingAPI.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionnaire")
    private List<Question> questions;
    @OneToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
}
