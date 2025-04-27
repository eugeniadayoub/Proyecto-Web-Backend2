package com.proyecto.buckys_vet.servicio;

import java.util.List;

import com.proyecto.buckys_vet.entidad.Mascota;

public interface MascotaServicio {
    
    List<Mascota> obtenerTodas();
    
    Mascota guardar(Mascota mascota);
    
    Mascota update(Mascota mascota);
    
    void eliminar(Long id);
    
    Mascota obtenerPorId(Long id);
    
    List<Mascota> obtenerPorDuenoId(Long duenoId);
    
    void cambiarEstadoMascota(Long id, String nuevoEstado);

    long contarMascotasTotales();

    long contarMascotasActivas();
}
