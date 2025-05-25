package com.proyecto.buckys_vet.entidad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Mascota {
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String especie;
    private int edad;
    private double peso;
    private String enfermedad;
    private String estado;
    private String imagenUrl;

    @Id
    @GeneratedValue
    private Long mascotaId;

    @JsonIgnoreProperties({ "mascotas", "cedula", "telefono", "imagenUrl" })
    @ManyToOne // Eliminado el cascade
    @JoinColumn(name = "dueno_id")
    private Dueno dueno;

    @ManyToOne // Eliminado el cascade
    @JoinColumn(name = "veterinario_id")
    @JsonIgnoreProperties({ "mascotas", "tratamientos" })
    private Veterinario veterinario;

    public Mascota(String nombre, String especie, int edad, double peso, String enfermedad, String imagenUrl,
            String estado) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.peso = peso;
        this.enfermedad = enfermedad;
        this.estado = estado;
        this.imagenUrl = imagenUrl;
    }

    public Mascota(Long mascotaId, String nombre, String especie, int edad, double peso, String enfermedad,
            String imagenUrl, String estado) {
        this.mascotaId = mascotaId;
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.peso = peso;
        this.enfermedad = enfermedad;
        this.estado = estado;
        this.imagenUrl = imagenUrl;
    }
}