
package com.example.demo.controller;

import com.example.demo.model.EnergyReadingRequest;
import com.example.demo.model.EnergyReading;
import com.example.demo.service.EnergyReadingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestController
@RequestMapping("/api/readings")
public class EnergyReadingController {

    private final EnergyReadingService energyReadingService;

    public EnergyReadingController(EnergyReadingService energyReadingService) {
        this.energyReadingService = energyReadingService;
    }

    @PostMapping
    public ResponseEntity<EnergyReading> createEnergyReading(@Valid @RequestBody EnergyReadingRequest request, Authentication authentication) {
        try {
            EnergyReading savedReading = energyReadingService.saveEnergyReading(request, authentication);
            return new ResponseEntity<>(savedReading, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EnergyReading>> getAllEnergyReadingsForCurrentUser(Authentication authentication) {
        List<EnergyReading> readings = energyReadingService.getReadingsForCurrentUser(authentication);
        return ResponseEntity.ok(readings);
    }

    @GetMapping("/by-device/{deviceId}")
    public ResponseEntity<List<EnergyReading>> getEnergyReadingsByDevice(@PathVariable Long deviceId, Authentication authentication) {
        try {
            // ¡¡¡CAMBIO CLAVE !!!
            List<EnergyReading> readings = energyReadingService.getReadingsForCurrentUserByDeviceId(deviceId, authentication);
            return ResponseEntity.ok(readings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<EnergyReading>> getEnergyReadingsByDateRange(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr,
            Authentication authentication) {
        try {
            LocalDateTime startDate = LocalDateTime.parse(startDateStr);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr);
            List<EnergyReading> readings = energyReadingService.getReadingsForCurrentUserByDateRange(startDate, endDate, authentication);
            return ResponseEntity.ok(readings);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(List.of());
        }
    }
}