package com.evenement1.evenement1.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationDTO {
    private  Long id;
    private String name;
   private Long id_evenement ;
   private Long id_organisateur ;


}
