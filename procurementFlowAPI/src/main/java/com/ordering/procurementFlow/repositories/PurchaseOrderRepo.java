package com.ordering.procurementFlow.repositories;

import com.ordering.procurementFlow.Models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder,Long> {
}
