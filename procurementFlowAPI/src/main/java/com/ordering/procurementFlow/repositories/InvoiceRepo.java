package com.ordering.procurementFlow.repositories;
import com.ordering.procurementFlow.Models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepo extends JpaRepository<Invoice,Long> {
}
