package com.fridge.application.app.repository;


import com.fridge.application.app.entitites.Product;
import com.fridge.application.app.entitites.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
   
    public Product findFirstByProductNameAndUser(String productName, User user);
    public List<Product> findByUser(User user);

}