package com.proyecto.buckys_vet.servicio;

import java.util.List;

import com.proyecto.buckys_vet.entidad.Medicamento;

public interface MedicamentoServicio {

    List<Medicamento> obtenerTodos();

    Medicamento obtenerPorId(Long id);

    Medicamento guardar(Medicamento medicamento);

    void eliminar(Long id);

    Medicamento update(Medicamento medicamento);

    Double calcularVentasTotales();

    Double calcularGananciasTotales();

    List<Object[]> obtenerGananciasPorMedicamento();
}
