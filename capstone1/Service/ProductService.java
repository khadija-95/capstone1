package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final CategoryService categoryService;



    ArrayList<Product> products = new ArrayList<>();

    public ProductService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    public ArrayList<Product> getProducts(){
        return products ;
    }

    public boolean addProduct (Product product){
        Category category = categoryService.getCategoryById(product.getCategoryID());
        if (category == null){
            return false;
        }
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

    public String applyDiscount(User user, double discountPercentage, double priceThreshold) {
        if (!user.getRole().equalsIgnoreCase("Admin")) {
            return "Access denied! Only Admin can apply discounts.";
        }

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

        if (product1 == null || product2 == null) {
            return "One or both products not found!";
        }

        if (product1.getPrice() < product2.getPrice()) {
            return product1.getName() + " is cheaper than " + product2.getName();
        } else if (product1.getPrice() > product2.getPrice()) {
            return product2.getName() + " is cheaper than " + product1.getName();
        } else {
            return "Both products have the same price.";
        }
    }

    public Product getProductById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }



}
