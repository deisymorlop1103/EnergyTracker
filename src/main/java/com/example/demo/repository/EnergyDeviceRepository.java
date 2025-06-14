// src/main/java/com/example/demo/repository/EnergyDeviceRepository.java
package com.example.demo.repository;

import com.example.demo.model.EnergyDevice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyDeviceRepository extends JpaRepository<EnergyDevice, Long> {

    List<EnergyDevice> findByUserId(Long userId);

}
