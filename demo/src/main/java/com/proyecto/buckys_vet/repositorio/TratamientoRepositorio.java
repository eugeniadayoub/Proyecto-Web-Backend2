package com.proyecto.buckys_vet.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.buckys_vet.entidad.Tratamiento;

@Repository
public interface TratamientoRepositorio extends JpaRepository<Tratamiento, Long> {
    
}
