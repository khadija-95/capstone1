package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty(message = "ID should be not empty")
    private String id;
    @NotEmpty(message = "Name should be not empty")
    @Size(min = 4)
    private String name;

}
