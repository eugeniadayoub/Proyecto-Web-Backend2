package com.proyecto.buckys_vet.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.buckys_vet.entidad.Mascota;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, Long> {
    List<Mascota> findByEstadoIgnoreCase(String estado);
    List<Mascota> findByEstadoIgnoreCaseNot(String estado);
    List<Mascota> findByDuenoId(Long duenoId); 
    
}
