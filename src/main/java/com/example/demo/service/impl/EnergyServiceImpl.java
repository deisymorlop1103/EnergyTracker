
package com.example.demo.service.impl;

import com.example.demo.model.EnergyReading;
import com.example.demo.repository.EnergyReadingRepository;
import com.example.demo.service.EnergyService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



@Service 
public class EnergyServiceImpl implements EnergyService {

    private final EnergyReadingRepository energyReadingRepository;

    public EnergyServiceImpl(EnergyReadingRepository energyReadingRepository) {
        this.energyReadingRepository = energyReadingRepository;
    }

    @Override
    public List<EnergyReading> getDailyConsumptionForUser(Long userId, LocalDateTime start, LocalDateTime end) {
        return energyReadingRepository.findByUser_UserIdAndTimestampBetween(userId, start, end);
    }

    @Override
    public Map<String, Double> getDailyConsumptionSummary(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<EnergyReading> readings = energyReadingRepository.findByUser_UserIdAndTimestampBetween(userId, startDate, endDate);
        Map<String, Double> dailySummary = new HashMap<>();
        for (EnergyReading reading : readings) {
            String date = reading.getTimestamp().toLocalDate().toString();
            dailySummary.merge(date, reading.getConsumption(), Double::sum);
        }
        return dailySummary;
    }

    @Override
    public Map<String, Double> getConsumptionBreakdownByDevice(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return new HashMap<>(); 
    }
}