package com.ordering.procurementFlow.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ordering.procurementFlow.Models.Invoice;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PurchaseManagerDto {
    private Long id;
    private  String firstname;
    private  String  lastname ;
   @JsonIgnore
    private List<Invoice> invoices;
}
