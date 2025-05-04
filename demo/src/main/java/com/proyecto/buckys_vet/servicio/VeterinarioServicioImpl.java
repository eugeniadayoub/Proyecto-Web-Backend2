package com.proyecto.buckys_vet.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.repositorio.VeterinarioRepositorio;

@Service
public class VeterinarioServicioImpl implements VeterinarioServicio {

    @Autowired
    private VeterinarioRepositorio veterinarioRepositorio;

    @Override
    public List<Veterinario> obtenerTodos() {
        return veterinarioRepositorio.findAll();
    }

    @Override
    public Veterinario obtenerPorCedula(Long cedula) {
        // Si deseas implementar búsqueda por cédula, podrías recorrer la lista o
        // definir un método en el repositorio
        for (Veterinario v : veterinarioRepositorio.findAll()) {
            if (v.getCedula() != null && v.getCedula().equals(cedula)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public Veterinario obtenerPorId(Long id) {
        return veterinarioRepositorio.findById(id).orElse(null);
    }

    @Override
    public Veterinario guardar(Veterinario veterinario) {
        // Actualizar el campo activo basado en el estado
        if (veterinario.getEstado() != null) {
            veterinario.setActivo(veterinario.getEstado().equalsIgnoreCase("activo"));
        }
        return veterinarioRepositorio.save(veterinario);
    }

    @Override
    public void eliminar(Long id) {
        veterinarioRepositorio.deleteById(id);
    }

    @Override
    public Veterinario update(Veterinario veterinario) {
        // Actualizar el campo activo basado en el estado
        if (veterinario.getEstado() != null) {
            veterinario.setActivo(veterinario.getEstado().equalsIgnoreCase("activo"));
        }
        return veterinarioRepositorio.save(veterinario);
    }

    @Override
    public Long contarVeterinariosActivos() {
        return veterinarioRepositorio.contarVeterinariosActivos();
    }

    @Override
    public Long contarVeterinariosInactivos() {
        return veterinarioRepositorio.contarVeterinariosInactivos();
    }

}
