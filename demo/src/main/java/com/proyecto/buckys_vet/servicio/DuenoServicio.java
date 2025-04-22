package com.proyecto.buckys_vet.servicio;

import java.util.List;

import com.proyecto.buckys_vet.entidad.Dueno;
public interface DuenoServicio {
    
    List<Dueno> obtenerTodos();
    
    Dueno obtenerPorCedula(Long cedula);
    
    Dueno obtenerPorId(Long id);
    
    Dueno guardar(Dueno dueno);
    
    void eliminar(Long id);

    Dueno update(Dueno dueno);
}
