package com.proyecto.buckys_vet.servicio;

import java.util.List;

import com.proyecto.buckys_vet.entidad.Tratamiento;

public interface TratamientoServicio {
    
    List<Tratamiento> obtenerTodos();   
    
    Tratamiento obtenerPorId(Long id);
    
    Tratamiento guardar(Tratamiento tratamiento);

    void eliminar(Long id);

    Tratamiento update(Tratamiento tratamiento);

    long contarTratamientosUltimoMes();

    List<Object[]> contarTratamientosPorMedicamentoUltimoMes();
}
