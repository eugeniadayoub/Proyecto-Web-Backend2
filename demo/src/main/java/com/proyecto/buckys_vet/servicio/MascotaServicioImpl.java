package com.proyecto.buckys_vet.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.entidad.Tratamiento;
import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.entidad.Dueno;
import com.proyecto.buckys_vet.entidad.RecursoNoEncontradoException;
import com.proyecto.buckys_vet.repositorio.MascotaRepositorio;
import com.proyecto.buckys_vet.repositorio.DuenoRepositorio;
import jakarta.persistence.EntityNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class MascotaServicioImpl implements MascotaServicio {

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private DuenoRepositorio duenoRepositorio;

    @Override
    public List<Mascota> obtenerTodas() {
        return mascotaRepositorio.findAll();
    }

    @Override
    public Mascota obtenerPorId(Long id) {
        return mascotaRepositorio.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Mascota guardar(Mascota mascota) {
        if (mascota.getDueno() != null && mascota.getDueno().getIdDueno() != null) {
            Long idDueno = mascota.getDueno().getIdDueno();
            Dueno dueno = duenoRepositorio.findById(idDueno)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Dueño no encontrado con ID: " + idDueno));
            mascota.setDueno(dueno);
        } else {
            mascota.setDueno(null);
        }

        if (mascota.getVeterinario() != null && mascota.getVeterinario().getId() != null) {
            // Similar logic to fetch and set Veterinario would go here
            // Fetch Veterinario based on ID and set it
            // Veterinario vet =
            // veterinarioRepositorio.findById(mascota.getVeterinario().getId()).orElseThrow(...);
            // mascota.setVeterinario(vet);
        } else {
            mascota.setVeterinario(null);
        }

        return mascotaRepositorio.save(mascota);
    }

    @Override
    @Transactional
    public Mascota update(Mascota mascotaActualizada) {
        if (mascotaActualizada == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula");
        }

        Mascota mascotaExistente = mascotaRepositorio.findById(mascotaActualizada.getMascotaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Mascota no encontrada con ID: " + mascotaActualizada.getMascotaId()));

        mascotaExistente.setNombre(mascotaActualizada.getNombre());
        mascotaExistente.setEspecie(mascotaActualizada.getEspecie());
        mascotaExistente.setEdad(mascotaActualizada.getEdad());
        mascotaExistente.setPeso(mascotaActualizada.getPeso());
        mascotaExistente.setEnfermedad(mascotaActualizada.getEnfermedad());
        mascotaExistente.setEstado(mascotaActualizada.getEstado());
        mascotaExistente.setImagenUrl(mascotaActualizada.getImagenUrl());

        if (mascotaActualizada.getDueno() != null && mascotaActualizada.getDueno().getIdDueno() != null) {
            Long idDueno = mascotaActualizada.getDueno().getIdDueno();
            if (mascotaExistente.getDueno() == null || !mascotaExistente.getDueno().getIdDueno().equals(idDueno)) {
                Dueno dueno = duenoRepositorio.findById(idDueno)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Dueño no encontrado con ID: " + idDueno));
                mascotaExistente.setDueno(dueno);
            }
        } else {
            mascotaExistente.setDueno(null);
        }

        return mascotaRepositorio.save(mascotaExistente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        mascotaRepositorio.deleteById(id);
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
            mascota.setEstado(nuevoEstado);
            mascotaRepositorio.save(mascota);
            System.out.println("Estado de la mascota con ID " + id + " cambiado a " + nuevoEstado);
        } else {
            System.out.println("Mascota con ID " + id + " no encontrada.");
        }
    }

    @Override
    public Long contarMascotasTotales() {
        return mascotaRepositorio.contarMascotasTotales();
    }

    @Override
    public long contarMascotasActivas() {
        return mascotaRepositorio.contarMascotasActivas();
    }
}
