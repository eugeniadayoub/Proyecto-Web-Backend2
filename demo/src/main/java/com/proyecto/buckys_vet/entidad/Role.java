package com.proyecto.buckys_vet.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String displayName;

    public String getAuthority() {
        return "ROLE_" + this.name;
    }

    // Constantes para los roles
    public static final String DUENO = "DUENO";
    public static final String VETERINARIO = "VETERINARIO";
    public static final String ADMIN = "ADMIN";
}