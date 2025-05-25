package com.proyecto.buckys_vet.entidad;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dueno {
    private Long cedula;
    private String nombre;
    private String email;
    private String telefono;
    private String imagenUrl;
    private String password;

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "dueno", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Mascota> mascotas = new ArrayList<>();

    public Dueno(Long cedula, String nombre, String email, String telefono, String imagenUrl, String password) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.imagenUrl = imagenUrl;
        this.password = password;
    }

    public Dueno(Long id, Long cedula, String nombre, String email, String telefono, String imagenUrl,
            String password) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.imagenUrl = imagenUrl;
        this.password = password;
    }

    // Custom getter method to maintain API compatibility
    public Long getIdDueno() {
        return id;
    }

    // Custom setter method to maintain API compatibility
    public void setIdDueno(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
}
