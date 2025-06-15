
package com.example.demo.service.impl;

import com.example.demo.dto.DashboardConsumptionSummaryDTO;
import com.example.demo.dto.MonthlyConsumptionDTO;
import com.example.demo.model.EnergyDevice;
import com.example.demo.model.EnergyReading;
import com.example.demo.model.EnergyReadingRequest; 
import com.example.demo.model.User;
import com.example.demo.repository.EnergyReadingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EnergyReadingService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.YearMonth; 
import java.time.format.DateTimeFormatter; 
import java.util.Comparator;

@Service
public class EnergyReadingServiceImpl implements EnergyReadingService {

    private final EnergyReadingRepository energyReadingRepository;
    private final UserRepository userRepository;
    public EnergyReadingServiceImpl(EnergyReadingRepository energyReadingRepository,
                                    UserRepository userRepository) {
        this.energyReadingRepository = energyReadingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EnergyReading saveEnergyReading(EnergyReadingRequest energyReadingRequest, Authentication authentication) {
        String userEmail = authentication.getName();
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + userEmail));

     

        EnergyReading newReading = new EnergyReading();
        newReading.setUser(currentUser);
  
        newReading.setConsumptionKwh(energyReadingRequest.getConsumptionKwh());
        newReading.setTimestamp(energyReadingRequest.getTimestamp());

        return energyReadingRepository.save(newReading);
    }

    @Override
    public List<EnergyReading> getReadingsForCurrentUser(Authentication authentication) {
        String userEmail = authentication.getName();
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + userEmail));
        return energyReadingRepository.findByUser_UserId(currentUser.getUserId());
    }

    public List<EnergyReading> getReadingsForCurrentUserByUserId(Long userId, Authentication authentication) {
        String userEmail = authentication.getName();
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + userEmail));

       
      return energyReadingRepository.findByUser_UserIdAndDevice_Id(currentUser.getUserId(), userId);
    }

    @Override
    public List<EnergyReading> getReadingsForCurrentUserByDeviceId(Long deviceId, Authentication authentication) {
        String userEmail = authentication.getName();
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + userEmail));
        return energyReadingRepository.findByUser_UserIdAndDevice_Id(currentUser.getUserId(), deviceId);
    }

    @Override
    public List<EnergyReading> getReadingsForCurrentUserByDateRange(LocalDateTime startDate, LocalDateTime endDate, Authentication authentication) {
        String userEmail = authentication.getName();
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + userEmail));
        return energyReadingRepository.findByUser_UserIdAndTimestampBetween(currentUser.getUserId(), startDate, endDate);
    }
 @Override
    public DashboardConsumptionSummaryDTO getMonthlyConsumptionSummaryForUser(Authentication authentication, int year) {
        String userEmail = authentication.getName();
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + userEmail));

        // rango de fechas para el año completo
        LocalDateTime startDate = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, 12, 31, 23, 59, 59);

        // Obtener todas las lecturas para un año en especifico
        List<EnergyReading> readings = energyReadingRepository.findByUser_UserIdAndTimestampBetween(currentUser.getUserId(), startDate, endDate);

        // Map que consolida el consumo por mes
        Map<YearMonth, Double> monthlyAggregates = new HashMap<>();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (EnergyReading reading : readings) {
            YearMonth yearMonth = YearMonth.from(reading.getTimestamp());
            // suma el consumo
            monthlyAggregates.merge(yearMonth, reading.getConsumptionKwh().doubleValue(), Double::sum);
        }

        List<MonthlyConsumptionDTO> monthlyBreakdown = new ArrayList<>();
        MonthlyConsumptionDTO maxMonth = null;
        MonthlyConsumptionDTO minMonth = null;

        if (!monthlyAggregates.isEmpty()) {
            // Convierte el map a una lista de DTOs y encuentra minímo y el máximo de los consumos
            for (Map.Entry<YearMonth, Double> entry : monthlyAggregates.entrySet()) {
                MonthlyConsumptionDTO currentMonth = new MonthlyConsumptionDTO(entry.getKey().format(monthFormatter), entry.getValue());
                monthlyBreakdown.add(currentMonth);

                if (maxMonth == null || currentMonth.getTotalConsumptionKwh() > maxMonth.getTotalConsumptionKwh()) {
                    maxMonth = currentMonth;
                }
                if (minMonth == null || currentMonth.getTotalConsumptionKwh() < minMonth.getTotalConsumptionKwh()) {
                    minMonth = currentMonth;
                }
            }
            // ordena la lista por mes 
            monthlyBreakdown.sort(Comparator.comparing(MonthlyConsumptionDTO::getMonth));
        }

        return new DashboardConsumptionSummaryDTO(monthlyBreakdown, maxMonth, minMonth);
    }
@Override
public List<EnergyDevice> getDevicesForCurrentUser(Authentication authentication) {
    return new ArrayList<>();
}
}