package com.evenement1.evenement1.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Organisation")
public class Organisation implements Serializable {
    @Id
    @GeneratedValue
    private  Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name="id_evenement")
    private Evenement evenement ;
    @ManyToOne
    @JoinColumn(name="id_organisateur")
    private Organisateur organisateur ;
}
