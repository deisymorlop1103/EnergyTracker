
package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnergyReadingRequest {

    private Long deviceId; 

    @NotNull(message = "El consumo en kWh no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El consumo debe ser mayor que 0")
    private BigDecimal consumptionKwh;

    @NotNull(message = "La fecha y hora no pueden ser nulas")
    private LocalDateTime timestamp;

    public EnergyReadingRequest() {}

    public EnergyReadingRequest(Long deviceId, BigDecimal consumptionKwh, LocalDateTime timestamp) {
        this.deviceId = deviceId;
        this.consumptionKwh = consumptionKwh;
        this.timestamp = timestamp;
    }
 }
