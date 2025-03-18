package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity getCategories(){
        ArrayList<Category> categories=categoryService.getCategories();
        return ResponseEntity.status(200).body(categories);
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isAddCategory=categoryService.addCategory(category);
        if (isAddCategory){
            return ResponseEntity.status(400).body(new ApiResponse("already added"));
        }
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@RequestBody @Valid Category category,@PathVariable String id,Errors errors){
        if (errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdateCategory=categoryService.updateCategory(category,id);
        if (isUpdateCategory){
            return ResponseEntity.status(200).body(new ApiResponse("updated "));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id){

        boolean isDeleteCategory=categoryService.deleteCategory(id);
        if (isDeleteCategory){
            return ResponseEntity.status(200).body(new ApiResponse("updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }


}
