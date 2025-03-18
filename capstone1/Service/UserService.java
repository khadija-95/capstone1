package com.example.capstone1.Service;

import com.example.capstone1.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class UserService {

    ArrayList<User> users = new ArrayList<>();


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
    public String getUserStatistics(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return "User " + user.getUsername() +
                        " has made " + user.getPurchaseCount() +
                        " purchases and spent a total of $" + user.getTotalSpent();
            }
        }
        return "User not found!";
    }

}
