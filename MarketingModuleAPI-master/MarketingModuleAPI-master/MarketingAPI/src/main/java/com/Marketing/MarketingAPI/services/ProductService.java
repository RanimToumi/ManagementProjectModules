package com.Marketing.MarketingAPI.services;
import com.Marketing.MarketingAPI.DTO.ProductDto;
import com.Marketing.MarketingAPI.models.FieldOfActivity;
import com.Marketing.MarketingAPI.models.Product;
import com.Marketing.MarketingAPI.repositories.ProductRepo;
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
        return productDto;
    }
}
