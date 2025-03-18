package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class User {
    @NotEmpty(message = "ID should be not empty")
    private String id;
    @NotEmpty(message = "UserName should be not empty")
    @Size(min = 6)
    private String userName;
    @NotEmpty(message = "Password should be not empty")
    @Size(min = 7)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,}$")
    private String password;
    @NotEmpty(message = "Email should be not empty")
    @Email
    private String email;
    @NotEmpty(message = "Role should be not empty")
    @Pattern(regexp = "Admin|Customer")
    private String role;
    @NotNull(message = "Balance should be not empty")
    @Positive(message = "Balance should be positive number")
    private double balance;

    @NotEmpty(message = "TotalPurchaseCount should be not empty")
    private int totalPurchaseCount;
    @NotEmpty(message ="TotalSpent should be not empty" )
    private double totalSpent;



}
