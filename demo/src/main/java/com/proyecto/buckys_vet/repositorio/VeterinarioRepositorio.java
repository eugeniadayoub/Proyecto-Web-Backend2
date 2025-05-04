package com.proyecto.buckys_vet.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.proyecto.buckys_vet.entidad.Veterinario;

@Repository
public interface VeterinarioRepositorio extends JpaRepository<Veterinario, Long> {

    @Query("SELECT COUNT(v) FROM Veterinario v WHERE LOWER(v.estado) = 'activo'")
    Long contarVeterinariosActivos();

    @Query("SELECT COUNT(v) FROM Veterinario v WHERE LOWER(v.estado) = 'inactivo'")
    Long contarVeterinariosInactivos();

}
