package com.ordering.procurementFlow.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ordering.procurementFlow.Models.Product;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProviderDto {
    private Long id;
    private String email;
    private  String name;
    private Long phone;
    private String city;

}
