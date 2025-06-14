package com.example.demo.service.impl;

import com.example.demo.model.EnergyDevice;
import com.example.demo.model.User;
import com.example.demo.repository.EnergyDeviceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EnergyDeviceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyDeviceServiceImpl implements EnergyDeviceService {

    private final EnergyDeviceRepository energyDeviceRepository;
    private final UserRepository userRepository;

    public EnergyDeviceServiceImpl(EnergyDeviceRepository energyDeviceRepository, UserRepository userRepository) {
        this.energyDeviceRepository = energyDeviceRepository;
        this.userRepository = userRepository;
    }

  @Override
    public List<EnergyDevice> getDevicesForCurrentUser(Authentication authentication) {
        String userEmail = authentication.getName();
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + userEmail));
        return energyDeviceRepository.findByUserId(currentUser.getUserId());
    }
}