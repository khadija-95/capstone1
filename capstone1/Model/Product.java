package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "ID should be not empty")
    private String id;
    @NotEmpty(message = "Name should be not empty")
    @Size(min = 4)
    private String name;
    @NotNull(message = "Price should be not null")
    @Positive
    private double price;
    @NotEmpty(message = "Category ID should be not empty")
    private String categoryID;

    @NotNull(message = "discount should be not empty")
    @Max(50)
    private double discount;


}
