package com.proyecto.buckys_vet.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.proyecto.buckys_vet.entidad.Dueno;
import com.proyecto.buckys_vet.entidad.Mascota;

@Mapper(componentModel = "spring")
public interface DuenoMapper {

    DuenoMapper INSTANCE = Mappers.getMapper(DuenoMapper.class);

    // Mapeo de Dueno a DuenoDTO (convierte mascotas a IDs)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "mascotaIds", source = "mascotas", qualifiedByName = "mascotasToIds")
    DuenoDTO toDTO(Dueno dueno);

    // Mapeo de DuenoDTO a Dueno (ignora mascotas y password)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "mascotas", ignore = true)
    Dueno toEntity(DuenoDTO duenoDTO);

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
}