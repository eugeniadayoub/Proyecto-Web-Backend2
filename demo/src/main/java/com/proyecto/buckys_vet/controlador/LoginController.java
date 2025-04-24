package com.proyecto.buckys_vet.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.buckys_vet.entidad.Dueno;
import com.proyecto.buckys_vet.servicio.DuenoServicio;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private DuenoServicio duenoServicio;

    // Ruta GET para ver el estado del login
    @GetMapping("/status")
    public Map<String, Object> checkLoginStatus(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        if (session.getAttribute("duenoId") != null) {
            response.put("status", "logged_in");
            response.put("duenoId", session.getAttribute("duenoId"));
        } else {
            response.put("status", "not_logged_in");
        }
        return response;  // Spring Boot convierte automáticamente a JSON
    }

    // Ruta POST para autenticar al usuario
    @PostMapping("/login")
public Map<String, Object> login(@RequestParam Long cedula, @RequestParam String password, HttpSession session) {
    Map<String, Object> response = new HashMap<>();
    try {
        System.out.println("Cédula recibida: " + cedula);
        System.out.println("Contraseña recibida: " + password);

        Dueno dueno = duenoServicio.obtenerPorCedula(cedula);

        if (dueno != null) {
            System.out.println("Dueno encontrado: " + dueno.getNombre());
            System.out.println("Contraseña almacenada: " + dueno.getPassword());

            if (dueno.getPassword() != null && dueno.getPassword().equals(password)) {
                session.setAttribute("duenoId", dueno.getIdDueno());
                session.setAttribute("duenoNombre", dueno.getNombre());
                response.put("status", "success");
                response.put("redirectUrl", "/mascotas/" + dueno.getIdDueno());
            } else {
                response.put("status", "error");
                response.put("message", "Contraseña incorrecta.");
            }
        } else {
            response.put("status", "error");
            response.put("message", "Cédula incorrecta o usuario no encontrado.");
        }
    } catch (Exception e) {
        e.printStackTrace(); // imprime errores si ocurren
        response.put("status", "error");
        response.put("message", "Error en el sistema. Por favor, intente más tarde.");
    }
    return response;
}




@PostMapping()
public ResponseEntity<Dueno> loginDueno(@RequestParam Long cedula, @RequestParam String password) {

    System.out.println("\n=== INICIANDO SESIÓN ===");
    System.out.println("Cédula recibida: " + cedula);
    System.out.println("Contraseña recibida: " + password);

    Dueno dueno = duenoServicio.obtenerPorCedula(cedula);

    if (dueno != null) {
            System.out.println("Dueno encontrado: " + dueno.getNombre());
            System.out.println("Contraseña almacenada: " + dueno.getPassword());

            if (dueno.getPassword() != null && dueno.getPassword().equals(password)) {
                return ResponseEntity.ok(dueno);
            } else {
                return ResponseEntity.status(401).build();
            }
    } else {
        return ResponseEntity.notFound().build();
    }

}



    // Ruta GET para cerrar sesión
    @GetMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("status", "logged_out");
        return response;  // Spring Boot convierte automáticamente a JSON
    }
}