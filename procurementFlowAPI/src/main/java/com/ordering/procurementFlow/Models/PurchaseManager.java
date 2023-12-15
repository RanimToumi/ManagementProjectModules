package com.ordering.procurementFlow.Models;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class PurchaseManager extends User {
    @OneToMany(mappedBy="purchaseManager")
    private List<Invoice>  invoices;
}
