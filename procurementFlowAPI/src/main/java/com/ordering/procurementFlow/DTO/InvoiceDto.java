package com.ordering.procurementFlow.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class InvoiceDto {
    private Long id ;
    private String reference ;
    private double price ;
    private double TVA ;
    private Date invoiceDate ;
}
