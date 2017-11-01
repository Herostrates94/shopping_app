package com.fridge.application.app.controller;


import com.fridge.application.app.entitites.Product;
import com.fridge.application.app.repository.ProductRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    
    
    // Get All Products
    @GetMapping(value = "/login/{login}/{password}", produces = "application/json")
    public Greeting getLogin(@PathVariable(value = "login") String login, @PathVariable(value = "password") String password) {
        
        Greeting g = new Greeting();
        g.setContent("some_content");
        g.setId("some_id");
                
        return g;
        
        
    }

    // Get All Products
    @GetMapping(value = "/products", produces = "application/json")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
}

    // Create a new Product
    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }
    
    
    // Get a Single Product
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long noteId) {
        Product note = productRepository.findOne(noteId);
        if(note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(note);
    }

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