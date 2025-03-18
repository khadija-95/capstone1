package com.example.capstone1.Service;

import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MerchantStockService {

    private final UserService userService;
    private final ProductService productService;
    private final MerchantService merchantService;

    ArrayList<MerchantStock>merchantStocks=new ArrayList<>();

    public MerchantStockService(UserService userService, ProductService productService,MerchantService merchantService) {
        this.userService = userService;
        this.productService = productService;
        this.merchantService= merchantService;
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

    public String buyProduct(String userId, String productId, String merchantId) {
        User user = null;
        Product product = null;
        MerchantStock stockItem = null;

        for (User u : userService.getUsers()) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }
        if (user == null) return " User not found!";

        for (Product p : productService.getProducts()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product == null) return " Product not found!";

        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductid().equals(productId)&& stock.getMerchantid().equals(merchantId)){
               stockItem = stock;
               break;
            }
        }
        if (stockItem == null) return "Merchant does not have this product in stock!";
        if (stockItem.getStock() < 1) return "Out of stock!";
        if (user.getBalance() < product.getPrice()) return "Insufficient balance!";

        stockItem.setStock(stockItem.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());

        user.setTotalSpent(user.getTotalSpent() + product.getPrice());
        user.setPurchaseCount(user.getPurchaseCount() + 1);

        return "Purchase successful! New balance: $" + user.getBalance();
    }

    public String addStock(String productId, String merchantId, int additionalStock) {

        for (MerchantStock stock : merchantStocks) {

            if (productId.equals(stock.getProductid())&& merchantId.equals(stock.getMerchantid())) {

                stock.setStock(stock.getStock() + additionalStock);

                return "Stock updated successfully! New stock: " + stock.getStock();

            }

        }

        return "Product or Merchant not found!";

    }

    public List<String> checkLowStock(int threshold) {
        List<String> lowStockAlerts = new ArrayList<>();
        for (MerchantStock stock : merchantStocks) {
            if (stock.getStock() <= threshold) {
                lowStockAlerts.add("Product ID: " + stock.getProductid() + " in Merchant ID: " + stock.getMerchantid() + " is running low (Stock: " + stock.getStock() + ")");
            }
        }
        return lowStockAlerts;
    }

}



