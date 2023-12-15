package com.evenement1.evenement1.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Organisateur")
public class Organisateur implements Serializable {
    @Id
    @GeneratedValue
    private  Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String adresse;
    private String cin;
    private String phoneNumber;

    private String password;
    private String role;
    @OneToMany(mappedBy = "organisateur")
    private List<Organisation> Organisations;
}
