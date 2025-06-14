// src/main/java/com/example/demo/controller/DashboardController.java
package com.example.demo.controller;

// Importaciones necesarias:
// 1. Para tu servicio de lectura de energía
import com.example.demo.service.EnergyReadingService;
// 2. Para los nuevos DTOs de resumen de consumo
import com.example.demo.dto.DashboardConsumptionSummaryDTO;
// 3. Si aún usas este DTO para otros datos, mantenlo

// Importaciones estándar de Spring
import org.springframework.security.core.Authentication; // Para obtener el usuario autenticado
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Para pasar datos a la vista
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final EnergyReadingService energyReadingService;
    public DashboardController(EnergyReadingService energyReadingService) {
        this.energyReadingService = energyReadingService;
    }

    @GetMapping
    public String showDashboard(
            Authentication authentication,
            @RequestParam(name = "year", required = false) Integer year,

            Model model) { 
        if (authentication == null) {

            return "redirect:/login";
        }

        int yearToQuery = (year != null) ? year : LocalDateTime.now().getYear();

        DashboardConsumptionSummaryDTO monthlySummary =
                energyReadingService.getMonthlyConsumptionSummaryForUser(authentication, yearToQuery);

        model.addAttribute("monthlySummary", monthlySummary);

        model.addAttribute("currentYear", yearToQuery);

        model.addAttribute("message", "Bienvenido al Dashboard de Energía");
        return "dashboard"; 
    }
}