package com.proyecto.buckys_vet.dto;

import java.util.List;

import lombok.Data;

@Data
public class DuenoDTO {
    private Long id;
    private Long cedula;
    private String nombre;
    private String email;
    private String telefono;
    private String imagenUrl;
    private List<Long> mascotaIds; // Solo IDs de mascotas

    // Método de compatibilidad para mantener la API existente
    public Long getIdDueno() {
        return id;
    }

    public void setIdDueno(Long id) {
        this.id = id;
    }
}