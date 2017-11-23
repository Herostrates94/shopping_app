package com.fridge.application.app.controller;


import com.fridge.application.app.entitites.Product;
import com.fridge.application.app.repository.ProductRepository;
import com.fridge.application.app.repository.UserRepository;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    UserRepository userRepository;
    


    // Get All Products
    @GetMapping(value = "/products", produces = "application/json")
    public List<Product> getAllProducts() {
        
        List<Product> products = productRepository.findAll(); 
        Iterator<Product> iter = products.iterator();

        while (iter.hasNext()) {
            Product product = iter.next();

            if(product.getUser() == null || 
                    ! product.getUser().getUsername().equals(userRepository.getCurrentUser().getUsername()))
                iter.remove();
        }
        
        return productRepository.findAll();
}

    // Create a new Product
    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product) {
        product.setUser(userRepository.getCurrentUser());
        return productRepository.save(product);
    }
    
    
    // Get a Single Product
    // not needed in this project - 06.11.17 Birgiel 
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long noteId) {
        Product note = productRepository.findOne(noteId);
        if(note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(note);
    }

    //TODO:  is id really needed here? 
    // Update a Product
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long noteId, 
                                           @Valid @RequestBody Product productDetails) {
        Product product = productRepository.findOne(noteId);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        
        product.setNameOfStore(productDetails.getNameOfStore());
        product.setProductName(productDetails.getProductName());
        product.setPrice(productDetails.getPrice());
        product.setAmount(productDetails.getAmount());

        Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a Product
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable(value = "id") Long noteId) {
        Product note = productRepository.findOne(noteId);
        if(note == null) {
            return ResponseEntity.notFound().build();
        }

        productRepository.delete(note);
        return ResponseEntity.ok().build();
    }	
    

    
    
}