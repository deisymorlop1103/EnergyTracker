// src/main/java/com/example/demo/controller/GoalController.java
package com.example.demo.controller;

import com.example.demo.dto.SustainabilityGoalDTO;
import com.example.demo.model.SustainabilityGoal;
import com.example.demo.service.GoalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    // Listar todas las metas de un usuario
    @GetMapping
    public String listGoals(@RequestParam(name = "userId", required = false, defaultValue = "1") Long userId, Model model) {

        List<SustainabilityGoal> goals = goalService.getGoalsByUserId(userId);
        model.addAttribute("goals", goals);
        model.addAttribute("newGoal", new SustainabilityGoalDTO()); // Para el formulario de añadir
        return "goals"; // Retorna goals.html
    }

    // Mostrar formulario para añadir/editar meta
    @GetMapping("/form")
    public String showGoalForm(@RequestParam(name = "id", required = false) Long id, Model model) {
        SustainabilityGoalDTO goalDTO = new SustainabilityGoalDTO();
        if (id != null) {
            goalService.getGoalById(id).ifPresent(goal -> {
                goalDTO.setId(goal.getId());
                goalDTO.setUserId(goal.getUserId());
                goalDTO.setDescription(goal.getDescription());
                goalDTO.setTargetValue(goal.getTargetValue());
                goalDTO.setStartDate(goal.getStartDate());
                goalDTO.setEndDate(goal.getEndDate());
                goalDTO.setCompleted(goal.isCompleted());
            });
        }
        // Asignar un userId de prueba si es una nueva meta y no está autenticado
        if (goalDTO.getUserId() == null) {
            goalDTO.setUserId(1L);
        }
        model.addAttribute("goal", goalDTO);
        return "goal-form"; 
    }

    // Guardar (añadir/actualizar) una meta
    @PostMapping("/save")
    public String saveGoal(@ModelAttribute("goal") SustainabilityGoalDTO goalDTO) {
        goalService.saveGoal(goalDTO);
        return "redirect:/goals?userId=" + goalDTO.getUserId(); // Redirige de vuelta a la lista
    }

    // Eliminar una meta
    @PostMapping("/delete/{id}")
    public String deleteGoal(@PathVariable Long id, @RequestParam(name = "userId") Long userId) {
        goalService.deleteGoal(id);
        return "redirect:/goals?userId=" + userId; // Redirige de vuelta a la lista
    }
}