package com.proyecto.buckys_vet.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.repositorio.MascotaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class MascotaServicioImpl implements MascotaServicio {

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Override
    public List<Mascota> obtenerTodas() {
        return mascotaRepositorio.findAll();
    }

    @Override
    @Transactional
    public Mascota guardar(Mascota mascota) {
        return mascotaRepositorio.save(mascota);
    }

    @Override
    @Transactional
    public Mascota update(Mascota mascota) {
        return mascotaRepositorio.save(mascota);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        mascotaRepositorio.deleteById(id);
    }

    @Override
    public Mascota obtenerPorId(Long id) {
        return mascotaRepositorio.findById(id).orElse(null);
    }

    @Override
    public List<Mascota> obtenerPorDuenoId(Long duenoId) {
        return mascotaRepositorio.findByDuenoId(duenoId);
    }

    @Override
    @Transactional
    public void cambiarEstadoMascota(Long id, String nuevoEstado) {
        Mascota mascota = mascotaRepositorio.findById(id).orElse(null);
        if (mascota != null) {
            mascota.setEstado(nuevoEstado); // Ej: "activa", "inactiva"
            mascotaRepositorio.save(mascota);
            System.out.println("Estado de la mascota con ID " + id + " cambiado a " + nuevoEstado);
        } else {
            System.out.println("Mascota con ID " + id + " no encontrada.");
        }
    }

}
