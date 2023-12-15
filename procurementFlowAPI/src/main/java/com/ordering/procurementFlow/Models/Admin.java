package com.ordering.procurementFlow.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User{

}
