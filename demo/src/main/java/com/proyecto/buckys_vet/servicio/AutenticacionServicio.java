package com.proyecto.buckys_vet.servicio;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class AutenticacionServicio {

    private final Map<String, String> usuarios = new HashMap<>();
    
    public AutenticacionServicio() {
        usuarios.put("juan", "1234");
        usuarios.put("maria", "5678");
    }

    // Este método ahora devuelve una respuesta más estructurada
    public Optional<String> autenticar(String username, String password) {
        if (usuarios.containsKey(username)) {
            if (usuarios.get(username).equals(password)) {
                return Optional.of("success");
            } else {
                return Optional.of("error: Contraseña incorrecta");
            }
        } else {
            return Optional.of("error: Usuario no encontrado");
        }
    }
}
