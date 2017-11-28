/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fridge.application.app.service;

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
        getUserProductsFromDatabase();
        addNewProductsToDatabase();
        deleteOldProductsFromDatabase();
        updateDeviceAmountsOnServer();
        getUserProductsFromDatabase();
        calculateTotalAmountsForProducts();
                           
        return serverProducts;
    }
    
    void calculateTotalAmountsForProducts(){
        
        for(Product serverProduct : serverProducts){            
            
            List<DeviceAmount> deviceAmountsForProduct = deviceAmountRepository.findByProduct(serverProduct);
            
            int totalAmount = 0;
            
            for(DeviceAmount deviceAmount : deviceAmountsForProduct){
                totalAmount += deviceAmount.getDeviceAmount();
            }
            
            serverProduct.setAmount(totalAmount);
            productRepository.save(serverProduct);            
            
        }         
        
    }
    
    void updateDeviceAmountsOnServer(){
        
        for(Product deviceProduct : deviceProducts){            
            updateProductAmountForThisDevice(deviceProduct); 
        }   
        
    }
    
    void updateProductAmountForThisDevice(Product product){
        
        DeviceAmount deviceAmountOnServer = deviceAmountRepository.findFirstByProductAndDeviceGUID(product, GUID);
        
        if(deviceAmountOnServer != null){
            deviceAmountOnServer.setDeviceAmount(product.getAmount());
            deviceAmountRepository.save(deviceAmountOnServer);
        }else{
            deviceAmountRepository.save(new DeviceAmount(product.getAmount(), GUID, product));
        }
        
        
    }
    
    
    
    
    void getUserProductsFromDatabase(){
        
        serverProducts = productRepository.findAll(); 
        Iterator<Product> iter = serverProducts.iterator();

        while (iter.hasNext()) {
            Product product = iter.next();

            if(product.getUser() == null || 
                    ! product.getUser().getUsername().equals(userRepository.getCurrentUser().getUsername()))
                iter.remove();
        }     
    }

    private void addNewProductsToDatabase() {
        
        for(Product deviceProduct : deviceProducts){
            
            boolean isProductNew = true;
            
            for(Product serverProduct : serverProducts){
            
                if(deviceProduct.getProductName().equals(serverProduct.getProductName())){
                    isProductNew = false;
                    break;
                } 
            }
            
            if(isProductNew){
                productRepository.save(deviceProducts);
            }  
        }
    }

    private void deleteOldProductsFromDatabase() {
        
       for(Product serverProduct : serverProducts){
            
            boolean wasProductDeleted = true;
            
            for(Product deviceProduct : deviceProducts){
            
                if(deviceProduct.getProductName().equals(serverProduct.getProductName())){
                    wasProductDeleted = false;
                    break;
                } 
            }
            
            if(wasProductDeleted){
                productRepository.delete(deviceProducts);
            }  
        } 
    }
        
}
