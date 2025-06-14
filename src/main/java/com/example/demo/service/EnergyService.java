
package com.example.demo.service;

import com.example.demo.model.EnergyReading;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EnergyService {
    List<EnergyReading> getDailyConsumptionForUser(Long userId, LocalDateTime start, LocalDateTime end);
    Map<String, Double> getDailyConsumptionSummary(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    Map<String, Double> getConsumptionBreakdownByDevice(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}