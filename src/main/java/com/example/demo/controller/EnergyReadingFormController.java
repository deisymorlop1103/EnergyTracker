package com.example.demo.controller;

import com.example.demo.model.EnergyReadingRequest;
import com.example.demo.model.EnergyDevice; 
import com.example.demo.service.EnergyReadingService;
import com.example.demo.service.EnergyDeviceService; 
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller 
@RequestMapping("/readings") 
public class EnergyReadingFormController {

    private final EnergyReadingService energyReadingService;
    private final EnergyDeviceService energyDeviceService; 

    public EnergyReadingFormController(EnergyReadingService energyReadingService, EnergyDeviceService energyDeviceService) {
        this.energyReadingService = energyReadingService;
        this.energyDeviceService = energyDeviceService;
    }

    // Endpoint para mostrar el formulario de registro de una nueva lectura
    // Mapea a GET /readings/form
    @GetMapping("/form")
    public String showEnergyReadingForm(Model model, Authentication authentication) {
        // Crea un objeto EnergyReadingRequest vacío para que Thymeleaf pueda vincular los campos del formulario.
        model.addAttribute("energyReadingRequest", new EnergyReadingRequest());

        // se obtiene la lista de consumos del usuario 
        try {
            List<EnergyDevice> devices = energyDeviceService.getDevicesForCurrentUser(authentication);
            model.addAttribute("devices", devices);
        } catch (UsernameNotFoundException e) {
            // no se encuentra el usuario /se dirige a login
            return "redirect:/login";
        }

        return "energy_reading_form";
    }

    @PostMapping("/save")
    public String saveEnergyReading(
            @Valid @ModelAttribute("energyReadingRequest") EnergyReadingRequest request, // @ModelAttribute vincula el formulario
            Authentication authentication,
            Model model, // si hay errores, se vuelve a mostrar el formulario
            RedirectAttributes redirectAttributes) { 

        try {
            energyReadingService.saveEnergyReading(request, authentication);
            redirectAttributes.addFlashAttribute("successMessage", "Lectura registrada con éxito!");
            return "redirect:/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Error al registrar la lectura: " + e.getMessage());
            try {
                model.addAttribute("devices", energyDeviceService.getDevicesForCurrentUser(authentication));
            } catch (UsernameNotFoundException userEx) {
                redirectAttributes.addFlashAttribute("errorMessage", "Sesión expirada o usuario no encontrado.");
                return "redirect:/login";
            }
            return "energy_reading_form";
        } catch (UsernameNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Usuario no válido. Por favor, inicie sesión de nuevo.");
            return "redirect:/login";
        }
    }
}