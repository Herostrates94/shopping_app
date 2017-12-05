/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fridge.application.app.service;

import com.fridge.application.app.entitites.DeviceAmount;
import com.fridge.application.app.entitites.Product;
import com.fridge.application.app.entitites.User;
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
    User currentUser;
    
    
    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    DeviceAmountRepository deviceAmountRepository;
    
    public List<Product> synchronize(String GUID, List<Product> deviceProducts){
        this.GUID = GUID;
        this.currentUser = userRepository.getCurrentUser();
        this.deviceProducts = deviceProducts;
        
        deleteOldProductsFromServerDatabase();
        addNewProductsToServerDatabase();        
        addOrUpdateDeviceAmountsOnServer();
        getUserProductsFromServerDatabase();
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
    
    void addOrUpdateDeviceAmountsOnServer(){
        
        for(Product deviceProduct : deviceProducts){            
            updateProductAmountForThisDevice(deviceProduct); 
        }   
        
    }
    
    void updateProductAmountForThisDevice(Product deviceProduct){
        
        Product correspondingServerProduct = productRepository.findFirstByProductNameAndUser(deviceProduct.getProductName(), currentUser);
        
        DeviceAmount deviceAmountOnServer = deviceAmountRepository.findFirstByProductAndDeviceGUID(correspondingServerProduct, GUID);
        
        if(deviceAmountOnServer != null){
            deviceAmountOnServer.setDeviceAmount(deviceProduct.getAmount());
            deviceAmountRepository.save(deviceAmountOnServer);
        }else{
            deviceAmountRepository.save(new DeviceAmount(deviceProduct.getAmount(), GUID, correspondingServerProduct));
        }
        
        
    }
    
    
    
    
    void getUserProductsFromServerDatabase(){
        
        serverProducts = productRepository.findByUser(currentUser);
        
    }

    private void addNewProductsToServerDatabase() {
        
        for(Product deviceProduct : deviceProducts){            
            
                Product product = productRepository.findFirstByProductNameAndUser(deviceProduct.getProductName(), currentUser);

                if(deviceProduct.getDeleted().equals("false" ) && product == null){
                    deviceProduct.setUser(currentUser);
                    productRepository.save(deviceProduct); 
                }
        }
    }

    private void deleteOldProductsFromServerDatabase() {        
      
        Iterator<Product> productIterator = deviceProducts.iterator();
        while (productIterator.hasNext()) {
            
                Product deviceProduct = productIterator.next();
                
                if(deviceProduct.getDeleted().equals("true")){
                
                    Product product = productRepository.findFirstByProductNameAndUser(deviceProduct.getProductName(), currentUser);
                    
                    if(product != null){
                        List<DeviceAmount> deviceAmounts = deviceAmountRepository.findByProduct(deviceProduct);
                        deviceAmountRepository.delete(deviceAmounts);
                        productRepository.delete(product);   
                    }           

                    productIterator.remove();

                
                }
        }
       
    }
        
}
