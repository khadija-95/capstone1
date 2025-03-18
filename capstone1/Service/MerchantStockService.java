package com.example.capstone1.Service;

import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;

@Service
public class MerchantStockService {

    private final UserService userService;
    private final ProductService productService;
    ArrayList<MerchantStock>merchantStocks=new ArrayList<>();

    public MerchantStockService(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    public ArrayList<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }

    public boolean addMerchantStock (MerchantStock m){
        for(int i =0;i<merchantStocks.size();i++){
            if(merchantStocks.get(i).getId().equals(m.getId())){
                return false;
            }
        }
        merchantStocks.add(m);
        return true;
    }

    public boolean updateMerchantStock (MerchantStock m,String id){
        for(int i =0;i<merchantStocks.size();i++){
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.set(i,m);
                return true;
            }
        }

        return false;
    }
    public boolean deleteMerchantStock(String id){
        for(int i=0;i<merchantStocks.size();i++){
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    public String addStock(String productId, String merchantId, Integer additionalStock) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductId().equals(productId) && stock.getMerchantId().equals(merchantId)) {
                stock.setStock(stock.getStock() + additionalStock);
                return "Stock updated successfully!";
            }
        }
        return "Stock record not found. No changes were made.";
    }

    public String buyProduct(String userId, String productId, String merchantId){
        User user = null;
        Product product = null;
        MerchantStock stock = null;

        for (User u : userService.getUsers()) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            return "User not found.";
        }

        for (Product p : productService.getProducts()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product == null) {
            return "Product not found.";
        }

        for (MerchantStock ms : merchantStocks) {
            if (ms.getProductId().equals(productId) && ms.getMerchantId().equals(merchantId)) {
                stock = ms;
                break;
            }
        }
        if (stock == null || stock.getStock() <= 0) {
            return "Merchant does not have this product in stock.";
        }

        if (user.getBalance() < product.getPrice()) {
            return "Insufficient balance.";
        }

        stock.setStock(stock.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());

        return "Purchase successful! Product: " + product.getName();
    }

    public ArrayList<MerchantStock> comparePrice(String productId){
        ArrayList<MerchantStock> stocks=new ArrayList<>();
        for (MerchantStock stock:merchantStocks){
            if (stock.getProductId().equals(productId)&&stock.getStock()>0){
                stocks.add(stock);
            }
        }
        stocks.sort(Comparator.comparing(stock -> productService.getProductById(stock.getProductId()).getPrice()));

        return stocks;
    }

    public ArrayList<MerchantStock> getLowStockAlert() {
        ArrayList<MerchantStock> lowStock = new ArrayList<>();

        for (MerchantStock stock : merchantStocks) {
            if (stock.getStock() < 5) {
                lowStock.add(stock);
            }
        }

        return lowStock;
    }

}



