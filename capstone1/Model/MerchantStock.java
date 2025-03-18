package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "ID should be not empty")
    private String id;
    @NotEmpty(message = "Product ID should be not empty")
    private String productId;
    @NotEmpty(message = "Merchant ID should be not empty")
    private String merchantId;
    @NotNull(message = "Stock should be not empty")
    @Size(min = 11)
    private int stock;

}
