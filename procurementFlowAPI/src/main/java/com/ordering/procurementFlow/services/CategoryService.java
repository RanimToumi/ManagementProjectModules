package com.ordering.procurementFlow.services;

import com.ordering.procurementFlow.DTO.CategoryDto;
import com.ordering.procurementFlow.Models.Category;
import com.ordering.procurementFlow.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final ModelMapper modelMapper ;
    private final CategoryRepo categoryRepo;

    public Optional<CategoryDto> findCategoryById(Long categoryId){
        if (categoryId==0){log.error("CategoryId is null");}
        Optional<Category> category=categoryRepo.findById(categoryId);
        return category.map(u->modelMapper.map(u, CategoryDto.class));
    }

    public List<CategoryDto> findAllCategories(){
        List<Category> categories=categoryRepo.findAll();
        return categories.stream().map(u->modelMapper.map(u,CategoryDto.class)).collect(Collectors.toList());}

    public CategoryDto addCategory (CategoryDto categoryDto){
        Category category= modelMapper.map(categoryDto,Category.class);
        Category savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    public CategoryDto UpdateCategory(CategoryDto categoryDto){
        Category category= modelMapper.map(categoryDto,Category.class);
        Category savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }
    public void DeleteCategoryById(long categoryId) {
        if (categoryId == 0) {
            log.error("categoryId is null");
        }
        categoryRepo.deleteById(categoryId);
    }
}
