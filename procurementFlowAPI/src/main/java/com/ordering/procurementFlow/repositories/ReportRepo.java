package com.ordering.procurementFlow.repositories;


import com.ordering.procurementFlow.Models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<Report,Long> {
}
