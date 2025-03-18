package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {


    ArrayList<Product> products = new ArrayList<>();


    public ArrayList<Product> getProducts(){
        return products ;
    }

    public boolean addProduct (Product product){
        for(int i =0;i<products.size();i++){
            if(products.get(i).getId().equals(product.getId())){
                return false;
            }
        }
        products.add(product);
        return true;
    }

    public boolean updateProduct (Product product,String id){
        for(int i =0;i<products.size();i++){
            if(products.get(i).getId().equals(id)){
                products.set(i,product);
                return true;
            }
        }

        return false;
    }
    public boolean deleteProduct(String id){
        for(int i=0;i<products.size();i++){
            if(products.get(i).getId().equals(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Product> getProductsSortedByPrice(String categoryId) {
        return products.stream()
                .filter(product -> product.getCategoryID().equals(categoryId))
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
    }

    public String applyDiscount(double discountPercentage, double priceThreshold) {
        for (Product product : products) {
            if (product.getPrice() >= priceThreshold) {
                double newPrice = product.getPrice() - (product.getPrice() * (discountPercentage / 100));
                product.setPrice(newPrice);
            }
        }
        return "Discount applied to eligible products!";
    }

    public String compareProductPrices(String productId1, String productId2) {
        Product product1 = null;
        Product product2 = null;

        for (Product product : products) {
            if (product.getId().equals(productId1)) product1 = product;
            if (product.getId().equals(productId2)) product2 = product;
        }

        if (product1 == null || product2 == null) return "One or both products not found!";

        return "Product 1: " + product1.getName() + " (" + product1.getCategoryID() + ") - $" + product1.getPrice() + "\n" +
                "Product 2: " + product2.getName() + " (" + product2.getCategoryID() + ") - $" + product2.getPrice();
    }



}
