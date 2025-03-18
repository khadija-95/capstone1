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

    @PutMapping("/addStock")
    public ResponseEntity addStock(@RequestBody String productId,@RequestBody String merchantId,@RequestBody Integer additionalStock) {
        merchantStockService.addStock(productId, merchantId, additionalStock);
        return ResponseEntity.status(200).body(new ApiResponse("Stock updated successfully!"));
    }

    @PostMapping("/buy")

    public ResponseEntity buyProduct(@RequestBody String userId,@RequestBody String productId,@RequestBody String merchantId) {

        String result = merchantStockService.buyProduct(userId, productId, merchantId);
        if (result.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no product"));
        }
        return ResponseEntity.status(200).body(result);
    }
    //Compare the price of a specific product across different merchants
    @GetMapping("/compare/{productId}")
    public ResponseEntity comparePrice(@PathVariable String productId){
        ArrayList<MerchantStock> merchantStocks=merchantStockService.comparePrice(productId);
        if (merchantStocks.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("there is no product"));
        }
        return ResponseEntity.status(200).body(merchantStocks);
    }

    //Endpoint to retrieve a list of products that are low in stock
    @GetMapping("/lowStock")
    public ResponseEntity getLowStockAlert() {
        ArrayList<MerchantStock>m =merchantStockService.getLowStockAlert();

        return ResponseEntity.status(200).body(m);
    }

}

