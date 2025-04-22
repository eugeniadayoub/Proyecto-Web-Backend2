package com.proyecto.buckys_vet.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.buckys_vet.entidad.RecursoNoEncontradoException;

@RestController
@RequestMapping("/error")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomErrorController {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public Map<String, String> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return response; // Spring Boot convertirá automáticamente a JSON
    }
    
    @RequestMapping("/error404")
    public Map<String, String> mostrarError404() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Recurso no encontrado");
        return response; // Spring Boot convertirá automáticamente a JSON
    }
}
