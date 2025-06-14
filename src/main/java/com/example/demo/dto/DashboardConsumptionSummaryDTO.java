package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardConsumptionSummaryDTO {
    private List<MonthlyConsumptionDTO> monthlyBreakdown; // listado por mes
    private MonthlyConsumptionDTO maxConsumptionMonth; // Mes de mayor consumo
    private MonthlyConsumptionDTO minConsumptionMonth; // Mes de menor consumo
}