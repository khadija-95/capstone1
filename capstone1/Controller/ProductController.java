package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.ProductService;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

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
    @PostMapping("/applyDiscount")
    public ResponseEntity<String> applyDiscount(@RequestParam String role,@RequestParam double discountPercentage,@RequestParam double priceThreshold) {

        User user = userService.getUserByRole(role);

        if (!user.getRole().equalsIgnoreCase("Admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied! Only Admin can apply discounts.");
        }

        String result = productService.applyDiscount(user, discountPercentage, priceThreshold);
        return ResponseEntity.ok(result);
    }
    // Compare the prices of two different products
    @GetMapping("/compare")
    public ResponseEntity<ApiResponse> compareProducts(@RequestParam String productId1, @RequestParam String productId2) {
        String response = productService.compareProductPrices(productId1, productId2);
        return ResponseEntity.ok(new ApiResponse(response));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        return ResponseEntity.status(200).body(product);
    }
}
