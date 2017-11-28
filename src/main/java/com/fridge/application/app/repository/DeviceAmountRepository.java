package com.fridge.application.app.repository;


import com.fridge.application.app.entitites.DeviceAmount;
import com.fridge.application.app.entitites.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceAmountRepository extends JpaRepository<DeviceAmount, Long> {
    
    public DeviceAmount findFirstByProductAndDeviceGUID(Product product, String GUID);

}