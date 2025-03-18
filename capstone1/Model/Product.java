package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "ID must not be empty")
    private String id;

    @NotEmpty(message = "Name must not be empty")
    @Min(value = 3, message = "Name must be at least 3 characters long")
    private String name;

    @Min(value = 1, message = "Price must be positive")
    private double price;

    @NotEmpty(message = "Category ID must not be empty")
    private String categoryID;
}


