// src/main/java/com/example/demo/dto/EnergyConsumptionSummaryDTO.java
package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyConsumptionSummaryDTO {
    private Long userId;
    private Map<String, Double> dailyConsumption;
    private Map<String, Double> deviceConsumption;
}