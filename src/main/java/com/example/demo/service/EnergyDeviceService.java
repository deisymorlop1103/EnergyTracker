package com.example.demo.service;

import com.example.demo.model.EnergyDevice;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface EnergyDeviceService {
    List<EnergyDevice> getDevicesForCurrentUser(Authentication authentication); 
}