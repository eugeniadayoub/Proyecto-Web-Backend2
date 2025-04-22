package com.proyecto.buckys_vet.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
@CrossOrigin(origins = "http://localhost:4200")
public class ErrorController {

    @GetMapping("/error404")
    public Map<String, String> mostrarError404() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Recurso no encontrado");
        return response;  // Spring Boot convierte automáticamente a JSON
    }
}
