package com.ordering.procurementFlow.controllers;

import com.ordering.procurementFlow.DTO.CategoryDto;
import com.ordering.procurementFlow.services.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService ;
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> CategoryDto= categoryService.findAllCategories();
        return new ResponseEntity<>(CategoryDto, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<CategoryDto> getCategoryById( @PathVariable("id")Long CategoryId){
        Optional<CategoryDto> categoryDto= categoryService.findCategoryById(CategoryId);
        if (categoryDto.isPresent()) {
            return new ResponseEntity<>(categoryDto.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    //Category doit être désérialisé (Conversion d'un objet Json ou xml en objet Java )
    public  ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto CategoryDto, HttpServletResponse response){
        CategoryDto newCategory =categoryService.addCategory(CategoryDto)  ;
        return new ResponseEntity<>(newCategory,HttpStatus.CREATED );
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> UpdateCategory(@RequestBody CategoryDto CategoryDto , HttpServletResponse response) {
        CategoryDto updateCategory = categoryService.UpdateCategory(CategoryDto);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> DeleteCategory(@PathVariable("id") Long id){
        categoryService.DeleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
