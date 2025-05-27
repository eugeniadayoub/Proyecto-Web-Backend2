package com.proyecto.buckys_vet.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.entidad.UserEntity;
import com.proyecto.buckys_vet.repositorio.VeterinarioRepositorio;
import com.proyecto.buckys_vet.repositorio.UserRepository;

@Service
public class VeterinarioServicioImpl implements VeterinarioServicio {

    @Autowired
    private VeterinarioRepositorio veterinarioRepositorio;

    @Autowired
    private UserRepository userRepository;

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
    @Transactional
    public Veterinario guardar(Veterinario veterinario) {
        try {
            // Si el veterinario tiene un UserEntity asociado, guardarlo primero
            if (veterinario.getUser() != null) {
                UserEntity savedUser = userRepository.save(veterinario.getUser());
                veterinario.setUser(savedUser);
            }

            // Usar el setter personalizado para mantener la lógica del atributo activo
            if (veterinario.getEstado() != null) {
                veterinario.setEstado(veterinario.getEstado());
            }

            // Guardar el veterinario
            return veterinarioRepositorio.save(veterinario);
        } catch (Exception e) {
            System.out.println("Error guardando veterinario: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void eliminar(Long id) {
        veterinarioRepositorio.deleteById(id);
    }

    @Override
    public Veterinario update(Veterinario veterinario) {
        // Obtener el veterinario existente para preservar la relación con UserEntity
        Veterinario veterinarioExistente = veterinarioRepositorio.findById(veterinario.getId()).orElse(null);
        if (veterinarioExistente == null) {
            throw new RuntimeException("Veterinario no encontrado con ID: " + veterinario.getId());
        }

        // Actualizar los campos del veterinario
        veterinarioExistente.setCedula(veterinario.getCedula());
        veterinarioExistente.setNombre(veterinario.getNombre());
        veterinarioExistente.setContrasena(veterinario.getContrasena());
        veterinarioExistente.setEspecialidad(veterinario.getEspecialidad());
        veterinarioExistente.setFoto(veterinario.getFoto());
        veterinarioExistente.setNumeroAtenciones(veterinario.getNumeroAtenciones());

        // Usar el setter personalizado para mantener la lógica del atributo activo
        veterinarioExistente.setEstado(veterinario.getEstado());

        // Actualizar también el UserEntity asociado si existe
        if (veterinarioExistente.getUser() != null) {
            UserEntity user = veterinarioExistente.getUser();
            user.setNombre(veterinario.getNombre());
            user.setCedula(veterinario.getCedula());
            user.setEspecialidad(veterinario.getEspecialidad());
            user.setNumeroAtenciones(veterinario.getNumeroAtenciones());
            userRepository.save(user);
        }

        return veterinarioRepositorio.save(veterinarioExistente);
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
