package com.ordering.procurementFlow.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "Invoice")
@Entity
public class Invoice {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "reference")
    private String reference ;
    @Column(name = "price")
    private double price ;
    @Column(name = "TVA")
    private double TVA ;
    @Column(name = "invoiceDate")
    private Date invoiceDate ;
    @ManyToOne
    @JoinColumn(name="purchaseManagerId")
    private PurchaseManager purchaseManager;
    @OneToOne
    private PurchaseOrder purchaseOrder;
}
