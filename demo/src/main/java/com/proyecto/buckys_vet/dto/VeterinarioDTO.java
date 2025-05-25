package com.proyecto.buckys_vet.dto;

import java.util.List;

import lombok.Data;

@Data
public class VeterinarioDTO {
    private Long id;
    private Long cedula;
    private String nombre;
    private String especialidad;
    private String foto;
    private int numeroAtenciones;
    private boolean activo;
    private String estado;
    private List<Long> mascotaIds; // Solo IDs de mascotas
    private List<Long> tratamientoIds; // Solo IDs de tratamientos
    // Excluimos 'contrasena' por seguridad
}