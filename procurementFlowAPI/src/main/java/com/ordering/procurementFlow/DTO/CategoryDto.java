package com.ordering.procurementFlow.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ordering.procurementFlow.Models.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CategoryDto {
    private Long id ;
    private String name ;

}
