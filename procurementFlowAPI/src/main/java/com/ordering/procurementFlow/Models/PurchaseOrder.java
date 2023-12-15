package com.ordering.procurementFlow.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PurchaseOrder")
@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Requisition requisition;
}
