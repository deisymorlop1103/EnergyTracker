
package com.example.demo.service;

import com.example.demo.model.EnergyDevice;
import com.example.demo.model.EnergyReading;
import com.example.demo.model.EnergyReadingRequest;

import org.springframework.security.core.Authentication; 

import java.util.List;
import java.time.LocalDateTime;
import com.example.demo.dto.DashboardConsumptionSummaryDTO;

public interface EnergyReadingService {
    EnergyReading saveEnergyReading(EnergyReadingRequest request, Authentication authentication);
    List<EnergyReading> getReadingsForCurrentUser(Authentication authentication);
    List<EnergyReading> getReadingsForCurrentUserByDeviceId(Long userId, Authentication authentication);
    List<EnergyReading> getReadingsForCurrentUserByDateRange(LocalDateTime startDate, LocalDateTime endDate, Authentication authentication);
    DashboardConsumptionSummaryDTO getMonthlyConsumptionSummaryForUser(Authentication authentication, int year);
    List<EnergyDevice> getDevicesForCurrentUser(Authentication authentication); 
}


