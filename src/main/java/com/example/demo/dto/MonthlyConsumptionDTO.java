
package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyConsumptionDTO {
    private String month; // Formato "YYYY-MM", 
    private Double totalConsumptionKwh; // Consumo total para ese mes
}