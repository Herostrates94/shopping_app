/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fridge.application.app.entitites;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "amounts")
public class Amount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    public Amount(){
        
    }
    
    private int deviceAmount;
    private String deviceGUID;
    

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDeviceAmount() {
        return deviceAmount;
    }

    public void setDeviceAmount(int deviceAmount) {
        this.deviceAmount = deviceAmount;
    }

    public String getDeviceGUID() {
        return deviceGUID;
    }

    public void setDeviceGUID(String deviceGUID) {
        this.deviceGUID = deviceGUID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    
}
