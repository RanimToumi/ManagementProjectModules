package com.Marketing.MarketingAPI.repositories;


import com.Marketing.MarketingAPI.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {
}
