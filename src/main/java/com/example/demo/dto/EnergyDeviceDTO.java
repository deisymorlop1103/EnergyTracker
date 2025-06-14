// src/main/java/com/example/demo/dto/EnergyDeviceDTO.java
package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyDeviceDTO {
    private Long id; // Para actualizar, si es nulo es para crear
    private Long userId;
    private String name;
    private String type;
    private String location;
    private boolean active;
}