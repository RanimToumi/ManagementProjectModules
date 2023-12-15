package com.evenement1.evenement1.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Evenement")
public class Evenement implements Serializable {
    @Id
    @GeneratedValue
    private  Long id;
    private String name;
    private String lieu;
    private String description;
    @OneToMany(mappedBy = "evenement")
    private List<Organisation> Organisations;
}
