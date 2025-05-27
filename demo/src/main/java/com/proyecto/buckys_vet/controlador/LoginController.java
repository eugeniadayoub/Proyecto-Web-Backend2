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
import com.proyecto.buckys_vet.dto.DuenoDTO;
import com.proyecto.buckys_vet.dto.DuenoMapper;
import com.proyecto.buckys_vet.dto.VeterinarioDTO;
import com.proyecto.buckys_vet.dto.VeterinarioMapper;
import com.proyecto.buckys_vet.entidad.Dueno;
import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.servicio.DuenoServicio;
import com.proyecto.buckys_vet.servicio.VeterinarioServicio;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.User;
import com.proyecto.buckys_vet.security.JWTGenerator;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private DuenoServicio duenoServicio;

    @Autowired
    private VeterinarioServicio veterinarioServicio;

    @Autowired
    private DuenoMapper duenoMapper;

    @Autowired
    private VeterinarioMapper veterinarioMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTGenerator jwtGenerator;

    // Ruta POST para autenticar al dueño
    @PostMapping("/dueno")
    public ResponseEntity<Map<String, Object>> loginDueno(@RequestParam Long cedula, @RequestParam String password,
            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Buscar dueño por cédula
            Dueno dueno = duenoServicio.obtenerPorCedula(cedula);
            if (dueno == null) {
                response.put("status", "error");
                response.put("message", "Cédula no encontrada.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Obtener el username del UserEntity asociado
            String username = dueno.getUser().getUsername();

            // Autenticar con Spring Security usando el username correcto
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("duenoId", dueno.getIdDueno());

            // Generar JWT
            String token = jwtGenerator.generateToken(authentication);

            // Convertir a DTO
            DuenoDTO duenoDTO = duenoMapper.toDTO(dueno);

            response.put("status", "success");
            response.put("token", token);
            response.put("redirectUrl", "/duenosmascotas/" + dueno.getIdDueno());
            response.put("dueno", duenoDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al intentar iniciar sesión.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /*
         * Map<String, Object> response = new HashMap<>();
         * try {
         * Dueno dueno = duenoServicio.obtenerPorCedula(cedula);
         * if (dueno != null && dueno.getPassword().equals(password)) {
         * session.setAttribute("duenoId", dueno.getIdDueno());
         * 
         * // Convertir a DTO (sin password)
         * DuenoDTO duenoDTO = duenoMapper.toDTO(dueno);
         * 
         * response.put("status", "success");
         * response.put("redirectUrl", "/duenosmascotas/" + dueno.getIdDueno());
         * response.put("dueno", duenoDTO); // Agregar DuenoDTO a la respuesta
         * return new ResponseEntity<>(response, HttpStatus.OK);
         * } else {
         * response.put("status", "error");
         * response.put("message", "Cédula o contraseña incorrectos.");
         * return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
         * }
         * } catch (Exception e) {
         * response.put("status", "error");
         * response.put("message", "Error en el sistema.");
         * return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
         * }
         */

    }

    // Ruta POST para autenticar al veterinario
    @PostMapping("/veterinario")
    public ResponseEntity<Map<String, Object>> loginVeterinario(@RequestParam Long cedula,
            @RequestParam String password, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Buscar veterinario por cédula
            Veterinario veterinario = veterinarioServicio.obtenerPorCedula(cedula);
            if (veterinario == null) {
                response.put("status", "error");
                response.put("message", "Cédula no encontrada.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Obtener el username del UserEntity asociado
            String username = veterinario.getUser().getUsername();

            // Autenticar con Spring Security usando el username correcto
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("veterinarioId", veterinario.getId());

            // Generar JWT
            String token = jwtGenerator.generateToken(authentication);

            // Convertir a DTO
            VeterinarioDTO veterinarioDTO = veterinarioMapper.toDTO(veterinario);

            response.put("status", "success");
            response.put("token", token);
            response.put("redirectUrl", "/veterinario-dashboard/" + veterinario.getId());
            response.put("veterinario", veterinarioDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al intentar iniciar sesión.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Ruta POST para autenticar al administrador
    @PostMapping("/admin")
    public ResponseEntity<Map<String, Object>> loginAdmin(@RequestParam String usuario, @RequestParam String password,
            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String usuarioAdmin = "admin"; // Usuario predefinido
            String passwordAdmin = "admin123"; // Contraseña predefinida

            if (usuario.equals(usuarioAdmin) && password.equals(passwordAdmin)) {
                session.setAttribute("adminUsuario", usuario);
                response.put("status", "success");
                response.put("redirectUrl", "/dashboard-admin");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", "error");
                response.put("message", "Usuario o contraseña incorrectos.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error en el sistema.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Ruta GET para cerrar sesión
    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("status", "logged_out");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
