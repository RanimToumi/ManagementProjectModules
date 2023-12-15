package com.ordering.procurementFlow.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Requisition")
@Entity
public class Requisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String purpose;
    private String details;
    private Date requestedDate ;
    private LocalDateTime requisitionDate ;
    private double amount ;
    @Enumerated(EnumType.STRING)
    private RequisitionStatus requisitionStatus;
    @ManyToOne
    @JoinColumn(name="id_user")
    private User user ;
    @OneToMany(mappedBy = "requisition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RequisitionLine> requisitionLines;
}
