package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "ID must not be empty")
    private String id;

    @NotEmpty(message = "Product ID must not be empty")
    private String productid;

    @NotEmpty(message = "Merchant ID must not be empty")
    private String merchantid;

    @Min(value = 10, message = "Stock must be at least 10 at start")
    private int stock;
}
