package com.proyecto.buckys_vet.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.buckys_vet.entidad.Dueno;
import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.entidad.UserEntity;
import com.proyecto.buckys_vet.repositorio.DuenoRepositorio;
import com.proyecto.buckys_vet.repositorio.MascotaRepositorio;
import com.proyecto.buckys_vet.repositorio.TratamientoRepositorio;
import com.proyecto.buckys_vet.repositorio.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class DuenoServicioImpl implements DuenoServicio {

    @Autowired
    private DuenoRepositorio duenoRepositorio;

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private TratamientoRepositorio tratamientoRepositorio;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Dueno> obtenerTodos() {
        return duenoRepositorio.findAll();
    }

    public Dueno obtenerPorCedula(Long cedula) {
        return duenoRepositorio.findByCedula(cedula);
    }

    @Override
    public Dueno obtenerPorId(Long id) {
        System.out.println("\n=== BUSCANDO DUEÑO POR ID INTERNO ===");
        System.out.println("ID buscado: " + id);
        try {
            Dueno dueno = duenoRepositorio.findById(id).orElse(null);
            if (dueno != null) {
                System.out.println("Dueño encontrado:");
                System.out.println("  - Nombre: " + dueno.getNombre());
                System.out.println("  - ID interno: " + dueno.getIdDueno());
                System.out.println("  - Cédula: " + dueno.getCedula());
            } else {
                System.out.println("No se encontró ningún dueño con el ID: " + id);
            }
            return dueno;
        } catch (Exception e) {
            System.out.println("Error buscando dueño por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public Dueno guardar(Dueno dueno) {
        try {
            // Si el dueño tiene un UserEntity asociado, guardarlo primero
            if (dueno.getUser() != null) {
                UserEntity savedUser = userRepository.save(dueno.getUser());
                dueno.setUser(savedUser);
            }

            // Guardar el dueño
            return duenoRepositorio.save(dueno);
        } catch (Exception e) {
            System.out.println("Error guardando dueño: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Dueno dueno = duenoRepositorio.findById(id).orElse(null);

        if (dueno != null) {
            // Primero eliminar los tratamientos asociados a las mascotas
            for (Mascota mascota : dueno.getMascotas()) {
                tratamientoRepositorio.deleteByMascota(mascota); // Llamar al nuevo método
            }

            // Luego eliminar las mascotas
            dueno.getMascotas().forEach(mascota -> mascotaRepositorio.delete(mascota)); // Eliminar mascotas

            // Finalmente eliminar el dueño
            duenoRepositorio.delete(dueno);
        } else {
            System.out.println("No se encontró el dueño con ID: " + id);
        }
    }

    @Override
    public Dueno update(Dueno dueno) {
        // Obtener el dueño existente para preservar la relación con UserEntity
        Dueno duenoExistente = duenoRepositorio.findById(dueno.getIdDueno()).orElse(null);

        if (duenoExistente == null) {
            throw new RuntimeException("Dueño no encontrado con ID: " + dueno.getIdDueno());
        }

        // Actualizar los campos del dueño
        duenoExistente.setCedula(dueno.getCedula());
        duenoExistente.setNombre(dueno.getNombre());
        duenoExistente.setEmail(dueno.getEmail());
        duenoExistente.setTelefono(dueno.getTelefono());
        duenoExistente.setImagenUrl(dueno.getImagenUrl());
        duenoExistente.setPassword(dueno.getPassword());

        // Preservar las mascotas existentes si no se envían nuevas
        if (dueno.getMascotas() != null && !dueno.getMascotas().isEmpty()) {
            dueno.getMascotas().forEach(mascota -> mascota.setDueno(duenoExistente));
            duenoExistente.setMascotas(dueno.getMascotas());
        }

        // Actualizar también el UserEntity asociado si existe
        if (duenoExistente.getUser() != null) {
            UserEntity user = duenoExistente.getUser();
            user.setNombre(dueno.getNombre());
            user.setEmail(dueno.getEmail());
            user.setCedula(dueno.getCedula());
            user.setTelefono(dueno.getTelefono());
            user.setImagenUrl(dueno.getImagenUrl());
            // Nota: No actualizamos la contraseña aquí por seguridad
            userRepository.save(user);
        }

        return duenoRepositorio.save(duenoExistente);
    }
}
