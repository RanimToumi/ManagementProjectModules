package com.ordering.procurementFlow.DTO;


import com.ordering.procurementFlow.Models.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private double price ;
    private Long category_id;
    private Long provider_id;
}
