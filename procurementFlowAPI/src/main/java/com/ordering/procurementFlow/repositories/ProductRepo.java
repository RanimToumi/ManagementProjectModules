package com.ordering.procurementFlow.repositories;


import com.ordering.procurementFlow.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {
}
