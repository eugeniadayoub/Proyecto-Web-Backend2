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
        // Si deseas implementar búsqueda por cédula, podrías recorrer la lista o definir un método en el repositorio
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
        return veterinarioRepositorio.save(veterinario);
    }
    
    @Override
    public void eliminar(Long id) {
        veterinarioRepositorio.deleteById(id);
    }
    
    @Override
    public Veterinario update(Veterinario veterinario) {
        return veterinarioRepositorio.save(veterinario);
    }

    @Override
    public long contarVeterinariosActivos() {
        List<Veterinario> veterinarios = veterinarioRepositorio.findAll();
        return veterinarios.stream()
                .filter(Veterinario::isActivo) // Filtra solo los activos
                .count();
    }

    @Override
    public long contarVeterinariosInactivos() {
        List<Veterinario> veterinarios = veterinarioRepositorio.findAll();
        return veterinarios.stream()
                .filter(v -> !v.isActivo()) // Aquí negamos para que tome los inactivos
                .count();
    }
}
