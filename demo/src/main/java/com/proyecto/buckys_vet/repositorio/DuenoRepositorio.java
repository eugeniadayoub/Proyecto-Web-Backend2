package com.proyecto.buckys_vet.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.buckys_vet.entidad.Dueno;

@Repository
public interface DuenoRepositorio extends JpaRepository<Dueno, Long> {
    Dueno findByCedula(Long cedula);
} 

