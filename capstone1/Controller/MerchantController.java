package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity getMerchants(){
        ArrayList<Merchant> merchants=merchantService.getMerchants();
        return ResponseEntity.status(200).body(merchants);
    }
    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if (errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isAddMerchant= merchantService.addMerchant(merchant);
        if (isAddMerchant){
            return ResponseEntity.status(400).body(new ApiResponse("Merchant already added"));
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added Successfully"));

    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@RequestBody @Valid Merchant merchant, @PathVariable String id, Errors errors){
        if (errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdateMerchant= merchantService.updateMerchant(merchant,id);
        if (isUpdateMerchant){
            return ResponseEntity.status(200).body(new ApiResponse("updated "));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable String id){

        boolean isDeleteMerchant= merchantService.deleteMerchant(id);
        if (isDeleteMerchant){
            return ResponseEntity.status(200).body(new ApiResponse("deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getMerchantById(@PathVariable String id) {
        Merchant merchant = merchantService.getMerchantById(id);
        if (merchant == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        return ResponseEntity.status(200).body(merchant);
    }

}
