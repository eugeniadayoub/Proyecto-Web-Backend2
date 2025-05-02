package com.proyecto.buckys_vet.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.proyecto.buckys_vet.entidad.Medicamento;

@Repository
public interface MedicamentoRepositorio extends JpaRepository<Medicamento, Long> {
    
    @Query("SELECT SUM(m.precioVenta * m.unidadesVendidas) FROM Medicamento m")
    Double calcularVentasTotales();

    @Query("SELECT SUM((m.precioVenta - m.precioCompra) * m.unidadesVendidas) FROM Medicamento m")
    Double calcularGananciasTotales();

    @Query("SELECT m.nombre, (m.precioVenta - m.precioCompra) * m.unidadesVendidas FROM Medicamento m")
    List<Object[]> obtenerGananciasPorMedicamento();

}
