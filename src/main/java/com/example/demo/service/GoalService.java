
package com.example.demo.service;

import com.example.demo.dto.SustainabilityGoalDTO;
import com.example.demo.model.SustainabilityGoal;
import java.util.List;
import java.util.Optional;

public interface GoalService {
    List<SustainabilityGoal> getGoalsByUserId(Long userId);
    Optional<SustainabilityGoal> getGoalById(Long id);
    SustainabilityGoal saveGoal(SustainabilityGoalDTO goalDTO);
    void deleteGoal(Long id);
}