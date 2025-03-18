package com.example.capstone1.Service;

import com.example.capstone1.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class UserService {

    private final ProductService productService;
    ArrayList<User> users = new ArrayList<>();

    public UserService(ProductService productService) {
        this.productService = productService;
    }

    public ArrayList<User> getUsers(){
        return users ;
    }

    public boolean addUsers (User user){
        for(int i =0;i<users.size();i++){
            if(users.get(i).getId().equals(user.getId())){
                return false;
            }
        }
        users.add(user);
        return true;
    }

    public boolean updateUsers (User user,String id){
        for(int i =0;i<users.size();i++){
            if(users.get(i).getId().equals(id)){
                users.set(i,user);
                return true;
            }
        }

        return false;
    }
    public boolean deleteUser(String id){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getId().equals(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public String getUserPurchaseStats(String userId) {
        User user =getUserByID(userId);
        if (user==null){
            return "User not found";
        }
        return "Total Purchases: " + user.getTotalPurchaseCount() + ", Total Spent: $" + user.getTotalSpent();
     }
    public User getUserByID(String userId){
        for (User user:users){
            if (user.getId().equals(userId)){
                return user;
            }
        }
        return null;
    }

}
