package com.ordering.procurementFlow.repositories;


import com.ordering.procurementFlow.Models.Requisition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequisitionRepo extends JpaRepository<Requisition,Long> {
    Optional<Requisition> findRequisitionById(Long id);
    List<Requisition> findAllByUser_Id(Long userId);
}
