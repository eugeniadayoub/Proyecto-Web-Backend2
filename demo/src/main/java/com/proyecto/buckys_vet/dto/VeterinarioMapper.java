package com.proyecto.buckys_vet.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.entidad.Tratamiento;
import com.proyecto.buckys_vet.entidad.Veterinario;

@Mapper(componentModel = "spring")
public interface VeterinarioMapper {
    
    VeterinarioMapper INSTANCE = Mappers.getMapper(VeterinarioMapper.class);
    
    // Mapeo de Veterinario a VeterinarioDTO (convierte mascotas y tratamientos a IDs)
    @Mapping(target = "mascotaIds", source = "mascotas", qualifiedByName = "mascotasToIds")
    @Mapping(target = "tratamientoIds", source = "tratamientos", qualifiedByName = "tratamientosToIds")
    VeterinarioDTO toDTO(Veterinario veterinario);
    
    // Mapeo de VeterinarioDTO a Veterinario (ignora contraseña, mascotas y tratamientos)
    @Mapping(target = "contrasena", ignore = true)
    @Mapping(target = "mascotas", ignore = true)
    @Mapping(target = "tratamientos", ignore = true)
    Veterinario toEntity(VeterinarioDTO veterinarioDTO);
    
    // Método auxiliar para convertir lista de mascotas a lista de IDs
    @Named("mascotasToIds")
    default List<Long> mascotasToIds(List<Mascota> mascotas) {
        if (mascotas == null) {
            return null;
        }
        return mascotas.stream()
                .map(Mascota::getMascotaId)
                .collect(Collectors.toList());
    }
    
    // Método auxiliar para convertir lista de tratamientos a lista de IDs
    @Named("tratamientosToIds")
    default List<Long> tratamientosToIds(List<Tratamiento> tratamientos) {
        if (tratamientos == null) {
            return null;
        }
        return tratamientos.stream()
                .map(Tratamiento::getId)
                .collect(Collectors.toList());
    }
} 