package com.ordering.procurementFlow.repositories;


import com.ordering.procurementFlow.Models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepo extends JpaRepository<Provider,Long> {
}
