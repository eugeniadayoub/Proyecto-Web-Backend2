package com.proyecto.buckys_vet.servicio;

import java.util.List;

import com.proyecto.buckys_vet.entidad.Veterinario;

public interface VeterinarioServicio {

    List<Veterinario> obtenerTodos();
    
    Veterinario obtenerPorCedula(Long cedula);
    
    Veterinario obtenerPorId(Long id);
    
    Veterinario guardar(Veterinario veterinario);
    
    void eliminar(Long id);

    Veterinario update(Veterinario veterinario);

    Long contarVeterinariosActivos();

    Long contarVeterinariosInactivos();

}
