package com.ordering.procurementFlow.services;
import com.ordering.procurementFlow.DTO.ProductDto;
import com.ordering.procurementFlow.Models.Category;
import com.ordering.procurementFlow.Models.Product;
import com.ordering.procurementFlow.Models.Provider;
import com.ordering.procurementFlow.repositories.CategoryRepo;
import com.ordering.procurementFlow.repositories.ProductRepo;
import com.ordering.procurementFlow.repositories.ProviderRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ModelMapper modelMapper ;
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ProviderRepo providerRepo;
    public Optional<ProductDto> findProductById(Long productId){
        if (productId==0){log.error("ProductId is null");}
        Optional<Product> product=productRepo.findById(productId);
        return product.map(p->convertToDto(p));
    }
    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public ProductDto addProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Category category = categoryRepo.findById(productDto.getCategory_id())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productDto.getCategory_id()));
        Provider provider = providerRepo.findById(productDto.getProvider_id())
                .orElseThrow(() -> new EntityNotFoundException("Provider not found with id: " + productDto.getProvider_id()));
        product.setCategory(category);
        product.setProvider(provider);
        Product savedProduct = productRepo.save(product);
        return convertToDto(savedProduct);
    }

    public ProductDto UpdateProduct(ProductDto productDto){
        Product product= modelMapper.map(productDto,Product.class);
        Product updatedProduct = productRepo.save(product);
        return convertToDto(updatedProduct);
    }
    public void DeleteProductById(long productId) {
        if (productId == 0) {
            log.error("productId is null");
        }
        productRepo.deleteById(productId);
    }
    private ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        if (product.getProvider() != null) {
            productDto.setProvider_id(product.getProvider().getId());
        }
        if (product.getCategory() != null) {
            productDto.setCategory_id(product.getCategory().getId());
        }
        return productDto;
    }
}
