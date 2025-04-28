package com.proyecto.buckys_vet.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.buckys_vet.entidad.Dueno;
import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.repositorio.DuenoRepositorio;
import com.proyecto.buckys_vet.repositorio.MascotaRepositorio;
import com.proyecto.buckys_vet.repositorio.TratamientoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class DuenoServicioImpl implements DuenoServicio {
    
    @Autowired
    private DuenoRepositorio duenoRepositorio;

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private TratamientoRepositorio tratamientoRepositorio;

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
    public Dueno guardar(Dueno dueno) {
        return duenoRepositorio.save(dueno);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Dueno dueno = duenoRepositorio.findById(id).orElse(null);

        if (dueno != null) {
            // Primero eliminar los tratamientos asociados a las mascotas
            for (Mascota mascota : dueno.getMascotas()) {
                tratamientoRepositorio.deleteByMascota(mascota);  // Llamar al nuevo método
            }

            // Luego eliminar las mascotas
            dueno.getMascotas().forEach(mascota -> mascotaRepositorio.delete(mascota));  // Eliminar mascotas

            // Finalmente eliminar el dueño
            duenoRepositorio.delete(dueno);
        } else {
            System.out.println("No se encontró el dueño con ID: " + id);
        }
    }

 
    
    @Override
    public Dueno update(Dueno dueno) {
        Dueno duenoExistente = duenoRepositorio.findById(dueno.getIdDueno()).orElse(null);
        
        if (duenoExistente != null) {
            if (dueno.getMascotas() != null && !dueno.getMascotas().isEmpty()) {
                dueno.getMascotas().forEach(mascota -> mascota.setDueno(dueno));
            }
            return duenoRepositorio.save(dueno);
        }
        return null;
    }
}
