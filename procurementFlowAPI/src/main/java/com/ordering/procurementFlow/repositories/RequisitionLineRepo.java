package com.ordering.procurementFlow.repositories;


import com.ordering.procurementFlow.Models.RequisitionLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequisitionLineRepo extends JpaRepository<RequisitionLine,Long> {
}
