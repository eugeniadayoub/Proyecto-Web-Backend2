package com.proyecto.buckys_vet.entidad;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
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
    @JsonIgnoreProperties({"veterinario", "mascota"})
    private List<Tratamiento> tratamientos;

    @OneToMany(mappedBy = "veterinario", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"veterinario", "dueno"})
    private List<Mascota> mascotas;

    // Constructor por defecto necesario para JPA
    public Veterinario() {
    }

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

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getNumeroAtenciones() {
        return numeroAtenciones;
    }

    public void setNumeroAtenciones(int numeroAtenciones) {
        this.numeroAtenciones = numeroAtenciones;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
        this.activo = "Activo".equalsIgnoreCase(estado);
    }
}
