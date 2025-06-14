package com.example.demo.controller;

import com.example.demo.dto.EnergyDeviceDTO;
import com.example.demo.model.EnergyDevice;
import com.example.demo.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public String listDevices(@RequestParam(name = "userId", required = false, defaultValue = "1") Long userId, Model model) {

        List<EnergyDevice> devices = deviceService.getDevicesByUserId(userId);
        model.addAttribute("devices", devices);
        model.addAttribute("newDevice", new EnergyDeviceDTO()); 
        return "devices"; 
    }

    @GetMapping("/form")
    public String showDeviceForm(@RequestParam(name = "id", required = false) Long id, Model model) {
        EnergyDeviceDTO deviceDTO = new EnergyDeviceDTO();
        if (id != null) {
            deviceService.getDeviceById(id).ifPresent(device -> {
                deviceDTO.setId(device.getId());
                deviceDTO.setUserId(device.getUserId());
                deviceDTO.setName(device.getName());
                deviceDTO.setType(device.getType());
                deviceDTO.setLocation(device.getLocation());
                deviceDTO.setActive(device.isActive());
            });
        }

        if (deviceDTO.getUserId() == null) {
             deviceDTO.setUserId(1L); 
        }
        model.addAttribute("device", deviceDTO);
        return "device-form";
    }
    // guardar un consumo de energía
    @PostMapping("/save")
    public String saveDevice(@ModelAttribute("device") EnergyDeviceDTO deviceDTO) {
        deviceService.saveDevice(deviceDTO);
        return "redirect:/devices?userId=" + deviceDTO.getUserId(); 
    }

    // Eliminar un consumo de energía
    @PostMapping("/delete/{id}")
    public String deleteDevice(@PathVariable Long id, @RequestParam(name = "userId") Long userId) {
        deviceService.deleteDevice(id);
        return "redirect:/devices?userId=" + userId; 
    }
}