package com.proyecto.buckys_vet.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.servicio.VeterinarioServicio;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

    @Autowired
    private VeterinarioServicio veterinarioServicio;

    // Obtener todos los veterinarios
    @GetMapping
    public List<Veterinario> obtenerVeterinarios() {
        return veterinarioServicio.obtenerTodos();
    }

    // Obtener un veterinario por ID
    @GetMapping("/{id}")
    public Veterinario obtenerVeterinarioPorId(@PathVariable Long id) {
        return veterinarioServicio.obtenerPorId(id);
    }

    // Crear un nuevo veterinario
    @PostMapping
    public Veterinario agregarVeterinario(@RequestBody Veterinario veterinario) {
        return veterinarioServicio.guardar(veterinario);
    }

    // Eliminar un veterinario por ID
    @DeleteMapping("/{id}")
    public void eliminarVeterinario(@PathVariable Long id) {
        veterinarioServicio.eliminar(id);
    }

    // Actualizar un veterinario
    @PutMapping("/{id}")
    public Veterinario actualizarVeterinario(@PathVariable Long id, @RequestBody Veterinario veterinario) {
        veterinario.setId(id);  // Asigna el ID para asegurarse de que se actualice correctamente
        return veterinarioServicio.update(veterinario);
    }
}
