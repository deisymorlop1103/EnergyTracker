
package com.example.demo.service.impl;

import com.example.demo.dto.SustainabilityGoalDTO;
import com.example.demo.model.SustainabilityGoal;
import com.example.demo.repository.SustainabilityGoalRepository;
import com.example.demo.service.GoalService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService {

    private final SustainabilityGoalRepository goalRepository;

    public GoalServiceImpl(SustainabilityGoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public List<SustainabilityGoal> getGoalsByUserId(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    @Override
    public Optional<SustainabilityGoal> getGoalById(Long id) {
        return goalRepository.findById(id);
    }

    @Override
    public SustainabilityGoal saveGoal(SustainabilityGoalDTO goalDTO) {
        SustainabilityGoal goal;
        if (goalDTO.getId() != null) {
            // Actualizar meta 
            goal = goalRepository.findById(goalDTO.getId())
                                 .orElseThrow(() -> new RuntimeException("Goal not found"));
            goal.setDescription(goalDTO.getDescription());
            goal.setTargetValue(goalDTO.getTargetValue());
            goal.setStartDate(goalDTO.getStartDate());
            goal.setEndDate(goalDTO.getEndDate());
            goal.setCompleted(goalDTO.isCompleted());
            goal.setUserId(goalDTO.getUserId());
        } else {
            // Crear nueva meta
            goal = new SustainabilityGoal();
            goal.setUserId(goalDTO.getUserId());
            goal.setDescription(goalDTO.getDescription());
            goal.setTargetValue(goalDTO.getTargetValue());
            goal.setStartDate(goalDTO.getStartDate());
            goal.setEndDate(goalDTO.getEndDate());
            goal.setCompleted(goalDTO.isCompleted());
        }
        return goalRepository.save(goal);
    }

    @Override
    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }
}