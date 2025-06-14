
package com.example.demo.repository;

import com.example.demo.model.EnergyDevice;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 

import java.util.List; 

@Repository 
public interface DeviceRepository extends JpaRepository<EnergyDevice, Long> {

     List<EnergyDevice> findByUserId(Long userId);
}