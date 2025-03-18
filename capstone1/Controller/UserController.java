package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/controller")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUsers(){
        ArrayList<User>users=userService.getUsers();
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isAddUser=userService.addUsers(user);
        if (isAddUser){
            return ResponseEntity.status(400).body(new ApiResponse("already added"));
        }
        userService.addUsers(user);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@RequestBody @Valid User user, @PathVariable String id, Errors errors){
        if (errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdateUser=userService.updateUsers(user,id);
        if (isUpdateUser){
            return ResponseEntity.status(200).body(new ApiResponse("updated "));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){

        boolean isDeleteUser=userService.deleteUser(id);
        if (isDeleteUser){
            return ResponseEntity.status(200).body(new ApiResponse("deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getUserByID(@PathVariable String id) {
        User user = userService.getUserByID(id);
        if (user == null) {
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));
        }
        return ResponseEntity.status(200).body(user);
    }

    //Endpoint to retrieve user purchase statistics
    @GetMapping("/purchase/{userId}")
    public ResponseEntity getUserPurchaseStats(@PathVariable String  userId) {
        if (userId==null){
            return ResponseEntity.status(400).body(new ApiResponse("user not found"));
        }
        String stats=userService.getUserPurchaseStats(userId);
        return ResponseEntity.status(200).body(stats);
    }


}

