package com.fridge.application.app.controller;


import com.fridge.application.app.daos.ProductsListDAO;
import com.fridge.application.app.entitites.Product;
import com.fridge.application.app.repository.ProductRepository;
import com.fridge.application.app.repository.UserRepository;
import com.fridge.application.app.service.ProductService;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api")
public class ProductController {


    
    @Autowired
    ProductService productService;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    


    // Get All Products
    @PostMapping(value = "/products", produces = "application/json")
    @ResponseBody
    public ProductsListDAO getAllProducts(@Valid @RequestBody ProductsListDAO productsListDAO, @RequestHeader("GUID") String GUID) {
        
        logger.info("GetAllProducts REST service was executed");
        
        // synchronizacja      
        
        ProductsListDAO newProductsListDAO = new ProductsListDAO(productService.synchronize(GUID, productsListDAO.getProducts()));
        
        return newProductsListDAO;
    }
}