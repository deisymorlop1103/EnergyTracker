package com.example.demo.controller;

import com.example.demo.service.EnergyReadingService;
import com.example.demo.dto.DashboardConsumptionSummaryDTO; 
import com.example.demo.dto.MonthlyConsumptionDTO; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; 

@Controller
@RequestMapping("/graficos")
public class DashboardGraficController {

    @Autowired
    private EnergyReadingService energyReadingService;

    @GetMapping("/comparacion-mensual")
    public String showMonthlyComparisonGraph(
            @RequestParam(name = "year", required = false) Integer year,
            Model model,
            Authentication authentication) {

        int targetYear = (year != null) ? year : LocalDate.now().getYear();

        // Obtener el resumen completo de consumo mensual 
        DashboardConsumptionSummaryDTO summaryDTO = energyReadingService.getMonthlyConsumptionSummaryForUser(authentication, targetYear);

        // aQUÍ SE preparan los datos para Chart.js
        List<String> monthlyConsumptionLabels = new ArrayList<>();
        List<Double> monthlyConsumptionData = new ArrayList<>();
        String maxMonthLabel = "N/A";
        Double maxConsumptionValue = 0.0;
        String minMonthLabel = "N/A";
        Double minConsumptionValue = 0.0;


        if (summaryDTO != null && summaryDTO.getMonthlyBreakdown() != null && !summaryDTO.getMonthlyBreakdown().isEmpty()) {
            // Mapea los DTOs de mes a listas separadas para las etiquetas y los datos de Chart.js
            monthlyConsumptionLabels = summaryDTO.getMonthlyBreakdown().stream()
                    .map(MonthlyConsumptionDTO::getMonth)
                    .collect(Collectors.toList());

            monthlyConsumptionData = summaryDTO.getMonthlyBreakdown().stream()
                    .map(MonthlyConsumptionDTO::getTotalConsumptionKwh)
                    .collect(Collectors.toList());

            // se EXTraen los datos del mes máximo y mínimo si hay datos disponibles
            if (summaryDTO.getMaxConsumptionMonth() != null) {
                maxMonthLabel = summaryDTO.getMaxConsumptionMonth().getMonth();
                maxConsumptionValue = summaryDTO.getMaxConsumptionMonth().getTotalConsumptionKwh();
            }
            if (summaryDTO.getMinConsumptionMonth() != null) {
                minMonthLabel = summaryDTO.getMinConsumptionMonth().getMonth();
                minConsumptionValue = summaryDTO.getMinConsumptionMonth().getTotalConsumptionKwh();
            }

        }

        model.addAttribute("targetYear", targetYear);
        model.addAttribute("monthlyConsumptionLabels", monthlyConsumptionLabels); 
        model.addAttribute("monthlyConsumptionData", monthlyConsumptionData);   
        model.addAttribute("maxMonth", maxMonthLabel);
        model.addAttribute("maxConsumption", maxConsumptionValue);
        model.addAttribute("minMonth", minMonthLabel);
        model.addAttribute("minConsumption", minConsumptionValue);

        return "/graficos/comparacionMensual"; 
    }
}