package com.ordering.procurementFlow.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Provider {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String name;
    private Long phone ;
    private String city;
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
