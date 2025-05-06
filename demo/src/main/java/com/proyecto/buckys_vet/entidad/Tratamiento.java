package com.proyecto.buckys_vet.entidad;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tratamiento {

    @Id
    @GeneratedValue()
    private Long id;
    
    private LocalDate fecha;
    private String descripcion;
    private Integer cantidad; 
    
    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    @JsonIgnoreProperties({"especie", "edad", "peso", "enfermedad", "imagenUrl", "estado", "dueno"})
    private Veterinario veterinario;

    @ManyToOne(cascade = CascadeType.PERSIST) // Se asegura que cualquier persistencia en Tratamiento también persista
    @JoinColumn(name = "mascota_id")
    @JsonIgnoreProperties({"precioCompra", "precioVenta", "unidadesDisponibles", "unidadesVendidas"})
    private Mascota mascota;
    
    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    @JsonIgnoreProperties({"especialidad", "numeroAtenciones", "cedula", "contrasena", "foto", "mascotas", "tratamientos"})
    private Medicamento medicamento;
    
    @Override
    public String toString() {
        return "Tratamiento{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", mascota=" + (mascota != null ? mascota.getMascotaId() : "null") +  // o el atributo que quieras mostrar
                ", veterinario=" + (veterinario != null ? veterinario.getId() : "null") +
                ", medicamento=" + (medicamento != null ? medicamento.getId() : "null") +
                '}';
    }
    public Tratamiento(String fecha, String descripcion, int cantidad, Mascota mascota, Veterinario veterinario, Medicamento medicamento) {
        // Initialize the object's fields here
    }
    public Tratamiento() {

    }

    public Tratamiento(LocalDate fecha, String descripcion, Veterinario veterinario, 
                       Mascota mascota, Medicamento medicamento, Integer cantidad) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.veterinario = veterinario;
        this.mascota = mascota;
        this.medicamento = medicamento;
        this.cantidad = cantidad;
    }

    public Tratamiento(Long id, LocalDate fecha, String descripcion, Veterinario veterinario, 
                       Mascota mascota, Medicamento medicamento, Integer cantidad) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.veterinario = veterinario;
        this.mascota = mascota;
        this.medicamento = medicamento;
        this.cantidad = cantidad;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }
    
    public Mascota getMascota() {
        return mascota;
    }
    
    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
    
    public Medicamento getMedicamento() {
        return medicamento;
    }
    
    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
