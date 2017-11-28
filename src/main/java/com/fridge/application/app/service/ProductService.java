/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fridge.application.app.service;

import com.fridge.application.app.daos.ProductsListDAO;
import com.fridge.application.app.entitites.DeviceAmount;
import com.fridge.application.app.entitites.Product;
import com.fridge.application.app.repository.DeviceAmountRepository;
import com.fridge.application.app.repository.ProductRepository;
import com.fridge.application.app.repository.UserRepository;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author user
 */
@Service
public class ProductService {
    
    List<Product> serverProducts, deviceProducts;
    String GUID;
    
    
    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    DeviceAmountRepository deviceAmountRepository;
    
    public List<Product> synchronize(String GUID, List<Product> deviceProducts){
        this.GUID = GUID;
        this.deviceProducts = deviceProducts;
        getUserProducts();
        updateDeviceAmountsOnServer();
        
        
        
        
        
        
        

        
        
        return serverProducts;
    }
    
    void updateDeviceAmountsOnServer(){
        
        for(Product deviceProduct : deviceProducts){
            
            DeviceAmount deviceAmount = updateProductAmountForThisDevice(deviceProduct);
        
        
        }
        
    }
    
    void updateProductAmountForThisDevice(Product product){
        
        DeviceAmount deviceAmounts = deviceAmountRepository.findFirstByProductAndDeviceGUID(product, GUID);
        
        
        
        
    }
    
    
    
    
    void getUserProducts(){
        
        serverProducts = productRepository.findAll(); 
        Iterator<Product> iter = serverProducts.iterator();

        while (iter.hasNext()) {
            Product product = iter.next();

            if(product.getUser() == null || 
                    ! product.getUser().getUsername().equals(userRepository.getCurrentUser().getUsername()))
                iter.remove();
        }     
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    void updateDeviceAmounts(String GUID){
        
        
        
        
        /*
        for(Product serverProduct : serverProducts){
            
            boolean productFreshlyAddedOnDevice = true;
            
            for(Product deviceProduct : deviceProducts){                
                if(serverProduct.getProductName().equals(deviceProduct.getProductName())){                    
                    productFreshlyAddedOnDevice = false;                    
                    serverProduct.setAmount(deviceProduct.getAmount());                    
                }               
            }   
            
            if(productFreshlyAddedOnDevice){
                
                
                
                
                
                
            }
            
            
        }*/
    }


    private void getUserProductAmounts() {
        
        
        for(Product product : serverProducts){
            
            
            
            
            
            
            
        }
        
    }
    
    
}
