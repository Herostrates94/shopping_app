package com.fridge.application.app.repository;


import com.fridge.application.app.entitites.Product;
import com.fridge.application.app.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    
    default User getCurrentUser(){                
        
        String username;        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        username = ((UserDetails)principal).getUsername();        
        User user = findOne(username);       
      
        return user;
    }


}