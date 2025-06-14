
package com.example.demo.service;

import com.example.demo.dto.EnergyDeviceDTO;
import com.example.demo.model.EnergyDevice;
import java.util.List;
import java.util.Optional;

public interface DeviceService {
    List<EnergyDevice> getDevicesByUserId(Long userId);
    Optional<EnergyDevice> getDeviceById(Long id);
    EnergyDevice saveDevice(EnergyDeviceDTO deviceDTO);
    void deleteDevice(Long id);
}