package com.fridge.application.app.daos;


import com.fridge.application.app.entitites.Product;
import java.util.List;

/**
 * Created by user on 28.11.2017.
 */

public class ProductsListDAO {

    public ProductsListDAO(){

    }

    public ProductsListDAO(List<Product> products){
        this.products = products;
    }

    private List<Product> products;


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
