
package com.example.demo.service.impl;

import com.example.demo.dto.EnergyDeviceDTO;
import com.example.demo.model.EnergyDevice;
import com.example.demo.repository.EnergyDeviceRepository;
import com.example.demo.service.DeviceService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final EnergyDeviceRepository deviceRepository;

    public DeviceServiceImpl(EnergyDeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<EnergyDevice> getDevicesByUserId(Long userId) {
        return deviceRepository.findByUserId(userId);
    }

    @Override
    public Optional<EnergyDevice> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    @Override
    public EnergyDevice saveDevice(EnergyDeviceDTO deviceDTO) {
        EnergyDevice device;
        if (deviceDTO.getId() != null) {
            // Actualizar consumo existente
            device = deviceRepository.findById(deviceDTO.getId())
                                   .orElseThrow(() -> new RuntimeException("Device not found"));
            device.setName(deviceDTO.getName());
            device.setType(deviceDTO.getType());
            device.setLocation(deviceDTO.getLocation());
            device.setActive(deviceDTO.isActive());
            device.setUserId(deviceDTO.getUserId()); 
        } else {
            // Crear nuevo consumo
            device = new EnergyDevice();
            device.setUserId(deviceDTO.getUserId());
            device.setName(deviceDTO.getName());
            device.setType(deviceDTO.getType());
            device.setLocation(deviceDTO.getLocation());
            device.setActive(deviceDTO.isActive());
        }
        return deviceRepository.save(device);
    }

    @Override
    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }
}