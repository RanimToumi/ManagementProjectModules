package com.evenement1.evenement1.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvenementDTO {
    private  Long id;
    private String name;
    private String lieu;
    private String description;

}
