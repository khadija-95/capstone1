package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity getMerchantStocks(){
        ArrayList<MerchantStock> m=merchantStockService.getMerchantStocks();
        return ResponseEntity.status(200).body(m);
    }
    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock m, Errors errors){
        if (errors.hasErrors()){
            String message =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isAddMerchantStock= merchantStockService.addMerchantStock(m);
        if (isAddMerchantStock){
            return ResponseEntity.status(400).body(new ApiResponse("MerchantStock already added"));
        }
        merchantStockService.addMerchantStock(m);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added Successfully"));

    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@RequestBody @Valid MerchantStock m, @PathVariable String id, Errors errors){
        if (errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdateMerchantStock= merchantStockService.updateMerchantStock(m,id);
        if (isUpdateMerchantStock){
            return ResponseEntity.status(200).body(new ApiResponse("updated "));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable String id){

        boolean isDeleteMerchantStock= merchantStockService.deleteMerchantStock(id);
        if (isDeleteMerchantStock){
            return ResponseEntity.status(200).body(new ApiResponse("deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    @PostMapping("/addStock")

    public ResponseEntity addStock(

            @RequestParam String productId,

            @RequestParam String merchantId,

            @RequestParam int additionalStock) {



        String response = merchantStockService.addStock(productId, merchantId, additionalStock);

        return ResponseEntity.ok(new ApiResponse(response));

    }
    // Check if any product stock is running low (below the given threshold)
    @GetMapping("/alert")
    public ResponseEntity checkLowStock(@RequestParam int threshold) {
        return ResponseEntity.ok(merchantStockService.checkLowStock(threshold));
    }

}

