package com.proyecto.buckys_vet.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {

    @GetMapping("/{pagina}")
    public Map<String, String> home(@PathVariable String pagina) {
        Map<String, String> response = new HashMap<>();
        response.put("pagina", pagina);
        return response;  // Spring Boot convierte automáticamente a JSON
    }
}
