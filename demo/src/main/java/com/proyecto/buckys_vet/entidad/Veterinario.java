package com.proyecto.buckys_vet.entidad;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veterinario {

    @Id
    @GeneratedValue
    private Long id;

    private Long cedula;
    private String nombre;
    private String contrasena; // Usamos "contrasena" sin ñ para evitar problemas de codificación
    private String especialidad;
    private String foto; // Ruta o URL de la foto
    private int numeroAtenciones; // Número de atenciones realizadas

    @Column(name = "activo")
    private boolean activo = true; // por defecto, cuando se crea un veterinario, está activo
    private String estado;

    @OneToMany(mappedBy = "veterinario")
    @JsonIgnoreProperties({ "veterinario", "mascota" })
    private List<Tratamiento> tratamientos;

    @OneToMany(mappedBy = "veterinario", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({ "veterinario", "dueno" })
    private List<Mascota> mascotas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // Constructor sin el id (para creación)
    public Veterinario(Long cedula, String nombre, String contrasena, String especialidad, String foto,
            int numeroAtenciones, String estado) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.especialidad = especialidad;
        this.foto = foto;
        this.numeroAtenciones = numeroAtenciones;
        this.estado = estado;
        this.activo = "Activo".equalsIgnoreCase(estado);
    }

    // Constructor con id (por ejemplo, al recuperar desde la base de datos)
    public Veterinario(Long id, Long cedula, String nombre, String contrasena, String especialidad, String foto,
            int numeroAtenciones, String estado) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.especialidad = especialidad;
        this.foto = foto;
        this.numeroAtenciones = numeroAtenciones;
        this.estado = estado;
        this.activo = "Activo".equalsIgnoreCase(estado);
    }

    // Custom setter to preserve business logic
    public void setEstado(String estado) {
        this.estado = estado;
        this.activo = "Activo".equalsIgnoreCase(estado);
    }
}
