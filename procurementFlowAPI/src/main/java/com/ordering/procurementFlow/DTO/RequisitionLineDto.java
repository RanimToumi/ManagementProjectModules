package com.ordering.procurementFlow.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequisitionLineDto {
    private Long id;
    private double quantity ;
    private double unitPrice ;
    private double totalAmount = quantity * unitPrice;
    private  String nonCatalogItemDescription ; // si le produit n'est pas dans la base de donnees
    private Long productId;
}
