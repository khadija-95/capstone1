package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.MerchantStockService;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final MerchantStockService merchantStockService;


    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUsers() {
        ArrayList<User> users = userService.getUsers();
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isAddUser = userService.addUsers(user);
        if (isAddUser) {
            return ResponseEntity.status(400).body(new ApiResponse("already added"));
        }
        userService.addUsers(user);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@RequestBody @Valid User user, @PathVariable String id, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdateUser = userService.updateUsers(user, id);
        if (isUpdateUser) {
            return ResponseEntity.status(200).body(new ApiResponse("updated "));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {

        boolean isDeleteUser = userService.deleteUser(id);
        if (isDeleteUser) {
            return ResponseEntity.status(200).body(new ApiResponse("deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    @PostMapping("/buyProduct")
    public ResponseEntity buyProduct(
            @RequestParam String userId,
            @RequestParam String productId,
            @RequestParam String merchantId) {

        String responseMessage = merchantStockService.buyProduct(userId, productId, merchantId);
        return ResponseEntity.ok(new ApiResponse(responseMessage));

    }
    //Retrieve user purchase statistics (number of purchases and total money spent)
    @GetMapping("/stats")
    public ResponseEntity getUserStats(@RequestParam String userId) {
        String response = userService.getUserStatistics(userId);
        return ResponseEntity.ok(new ApiResponse(response));
    }

}

