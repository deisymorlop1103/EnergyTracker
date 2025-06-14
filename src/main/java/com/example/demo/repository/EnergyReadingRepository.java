
package com.example.demo.repository;

import com.example.demo.model.EnergyReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnergyReadingRepository extends JpaRepository<EnergyReading, Long> {
    List<EnergyReading> findByUser_UserId(Long userId);
    List<EnergyReading> findByUser_UserIdAndDevice_Id(Long userId, Long deviceId);
    List<EnergyReading> findByUser_UserIdAndTimestampBetween(Long userId, LocalDateTime start, LocalDateTime end);
}