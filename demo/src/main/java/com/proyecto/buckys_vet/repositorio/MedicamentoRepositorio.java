package com.proyecto.buckys_vet.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.buckys_vet.entidad.Medicamento;

@Repository
public interface MedicamentoRepositorio extends JpaRepository<Medicamento, Long> {
    
}
