package com.ordering.procurementFlow.Models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "Product")
@Entity

public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price ;
    @ManyToOne
    @JoinColumn(name="provider_id")
    private Provider provider ;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category ;
}
