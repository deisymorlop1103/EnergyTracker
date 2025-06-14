
package com.example.demo.repository;

import com.example.demo.model.SustainabilityGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SustainabilityGoalRepository extends JpaRepository<SustainabilityGoal, Long> {
    List<SustainabilityGoal> findByUserId(Long userId);
}