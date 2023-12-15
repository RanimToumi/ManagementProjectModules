package com.Marketing.MarketingAPI.DTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private double price ;
}
