package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getProducts(){
        ArrayList<Product>products=productService.getProducts();
        return ResponseEntity.status(200).body(products);
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isAddProduct= productService.addProduct(product);
        if (isAddProduct){
            return ResponseEntity.status(400).body(new ApiResponse("already added"));
        }
        productService.addProduct(product);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@RequestBody @Valid Product product, @PathVariable String id, Errors errors){
        if (errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdateProduct= productService.updateProduct(product,id);
        if (isUpdateProduct){
            return ResponseEntity.status(200).body(new ApiResponse("updated "));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id){

        boolean isDeleteProduct= productService.deleteProduct(id);
        if (isDeleteProduct){
            return ResponseEntity.status(200).body(new ApiResponse("deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }
    // Get all products sorted by price within a specific category
    @GetMapping("/sorted")
    public ResponseEntity getSortedProducts(@RequestParam String categoryId) {
        return ResponseEntity.ok(productService.getProductsSortedByPrice(categoryId));
    }
    // Apply a discount to products that meet a price threshold
    @PostMapping("/discount")
    public ResponseEntity<ApiResponse> applyDiscount(@RequestParam double discountPercentage, @RequestParam double priceThreshold) {
        String response = productService.applyDiscount(discountPercentage, priceThreshold);
        return ResponseEntity.ok(new ApiResponse(response));
    }
    // Compare the prices of two different products
    @GetMapping("/compare")
    public ResponseEntity<ApiResponse> compareProducts(@RequestParam String productId1, @RequestParam String productId2) {
        String response = productService.compareProductPrices(productId1, productId2);
        return ResponseEntity.ok(new ApiResponse(response));
    }
}
