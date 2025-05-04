package com.proyecto.buckys_vet.repositorio;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.entidad.Tratamiento;


@Repository
public interface TratamientoRepositorio extends JpaRepository<Tratamiento, Long> {
    void deleteByMascota(Mascota mascota);

    @Query("SELECT COUNT(t) FROM Tratamiento t WHERE t.fecha >= :fechaInicio")
    Long countTratamientosDesde(@Param("fechaInicio") LocalDate fechaInicio);

    @Query("SELECT t.medicamento.nombre, SUM(t.cantidad) " +
       "FROM Tratamiento t " +
       "WHERE t.fecha >= :fechaInicio " +
       "GROUP BY t.medicamento.nombre")
    List<Object[]> contarTratamientosPorMedicamentoUltimoMes(@Param("fechaInicio") LocalDate fechaInicio);

    @Query("SELECT t FROM Tratamiento t ORDER BY t.cantidad DESC")
    List<Tratamiento> findTop3ByOrderByCantidadDesc(Pageable pageable);

}
