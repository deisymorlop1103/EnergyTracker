// src/main/java/com/example/demo/dto/SustainabilityGoalDTO.java
package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SustainabilityGoalDTO {
    private Long id; // Para actualizar, si es nulo es para crear
    private Long userId;
    private String description;
    private double targetValue;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean completed;
}