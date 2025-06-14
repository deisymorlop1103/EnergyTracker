// src/main/java/com/example/demo/model/EnergyReading.java
package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "energy_readings")
public class EnergyReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_id")
    private Long readingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id") // <-- ¡Cambia esto a 'device_id' (d minúscula)!
    private EnergyDevice device;

    @Column(name = "consumption_kwh", nullable = false, precision = 10, scale = 4)
    private BigDecimal consumptionKwh;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public EnergyReading(User user, EnergyDevice device, BigDecimal consumptionKwh, LocalDateTime timestamp) {
        this.user = user;
        this.device = device;
        this.consumptionKwh = consumptionKwh;
        this.timestamp = timestamp;
    }

    public double getConsumption() {
        return consumptionKwh != null ? consumptionKwh.doubleValue() : 0.0;
    }

    public void setConsumption(double consumption) {
        this.consumptionKwh = BigDecimal.valueOf(consumption);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}