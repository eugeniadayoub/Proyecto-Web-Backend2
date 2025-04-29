package com.proyecto.buckys_vet.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.buckys_vet.entidad.Dueno;
import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.servicio.DuenoServicio;
import com.proyecto.buckys_vet.servicio.VeterinarioServicio;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private DuenoServicio duenoServicio;

    @Autowired
    private VeterinarioServicio veterinarioServicio;

    // Ruta POST para autenticar al dueño
    // Ruta POST para autenticar al dueño
    @PostMapping("/dueno")
    public ResponseEntity<Map<String, Object>> loginDueno(@RequestParam Long cedula, @RequestParam String password, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            Dueno dueno = duenoServicio.obtenerPorCedula(cedula);
            if (dueno != null && dueno.getPassword().equals(password)) {
                session.setAttribute("duenoId", dueno.getIdDueno());
                response.put("status", "success");
                response.put("redirectUrl", "/duenosmascotas/" + dueno.getIdDueno());
                return new ResponseEntity<>(response, HttpStatus.OK);  // Respuesta exitosa
            } else {
                response.put("status", "error");
                response.put("message", "Cédula o contraseña incorrectos.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // Error con código 400
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error en el sistema.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);  // Error 500
        }
    }



    // Ruta POST para autenticar al veterinario
    @PostMapping("/veterinario")
    public ResponseEntity<Map<String, Object>> loginVeterinario(@RequestParam Long cedula, @RequestParam String password, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            Veterinario veterinario = veterinarioServicio.obtenerPorCedula(cedula);
            if (veterinario != null && veterinario.getContrasena().equals(password)) {
                session.setAttribute("veterinarioId", veterinario.getId());
                response.put("status", "success");
                response.put("redirectUrl", "/veterinario-dashboard/" + veterinario.getId());
                return new ResponseEntity<>(response, HttpStatus.OK);  // Respuesta exitosa
            } else {
                response.put("status", "error");
                response.put("message", "Cédula o contraseña incorrectos.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // Error con código 400
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error en el sistema.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);  // Error 500
        }
    }


    // Ruta POST para autenticar al administrador
    @PostMapping("/admin")
    public ResponseEntity<Map<String, Object>> loginAdmin(@RequestParam String usuario, @RequestParam String password, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String usuarioAdmin = "admin";  // Usuario predefinido
            String passwordAdmin = "admin123";  // Contraseña predefinida

            if (usuario.equals(usuarioAdmin) && password.equals(passwordAdmin)) {
                session.setAttribute("adminUsuario", usuario);
                response.put("status", "success");
                response.put("redirectUrl", "/dashboard-admin");
                return new ResponseEntity<>(response, HttpStatus.OK);  // Respuesta exitosa
            } else {
                response.put("status", "error");
                response.put("message", "Usuario o contraseña incorrectos.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // Error con código 400
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error en el sistema.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);  // Error 500
        }
    }


    // Ruta GET para cerrar sesión
    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("status", "logged_out");
        return new ResponseEntity<>(response, HttpStatus.OK);  // Respuesta exitosa
    }
}
