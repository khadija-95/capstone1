package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;

@Service
public class ProductService {

    private final UserService userService;

    ArrayList<Product> products = new ArrayList<>();

    public ProductService(UserService userService) {
        this.userService = userService;
    }


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

    public Product getProductById( String productId) {
        for (Product product:products){
            if (product.getId().equals(productId)){
                return product;
            }
        }
        return null;
    }

    public ArrayList<Product> getCheapestProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.sort(Comparator.comparingDouble(Product::getPrice));
        return products;
    }

    public ArrayList<Product> getDiscountedProducts() {
        ArrayList<Product> discountedProducts = new ArrayList<>();

        for (Product product : products) {
            if (product.getDiscount() > 0) {
                discountedProducts.add(product);
            }
        }

        return discountedProducts;
    }

}
