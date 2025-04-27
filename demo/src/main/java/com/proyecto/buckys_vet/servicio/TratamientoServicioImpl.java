package com.proyecto.buckys_vet.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.buckys_vet.entidad.Tratamiento;
import com.proyecto.buckys_vet.repositorio.TratamientoRepositorio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TratamientoServicioImpl implements TratamientoServicio {

    @Autowired
    private TratamientoRepositorio tratamientoRepositorio;

    @Override
    public List<Tratamiento> obtenerTodos() {
        return tratamientoRepositorio.findAll();
    }

    @Override
    public Tratamiento obtenerPorId(Long id) {
        Optional<Tratamiento> tratamiento = tratamientoRepositorio.findById(id);
        return tratamiento.orElse(null); 
    }

    @Override
    public Tratamiento guardar(Tratamiento tratamiento) {

        var respuesta = tratamientoRepositorio.save(tratamiento);

        System.out.println("Tratamiento guardado: " + respuesta);

        return respuesta;  
    }

    @Override
    public void eliminar(Long id) {
        tratamientoRepositorio.deleteById(id);  
    }

    @Override
    public Tratamiento update(Tratamiento tratamiento) {
        if (tratamientoRepositorio.existsById(tratamiento.getId())) {
            return tratamientoRepositorio.save(tratamiento);  
        }
        return null;  
    }

    @Override
    public long contarTratamientosUltimoMes() {
        LocalDate haceUnMes = LocalDate.now().minusMonths(1);
        List<Tratamiento> tratamientos = tratamientoRepositorio.findAll();
        return tratamientos.stream()
                .filter(t -> t.getFecha() != null && t.getFecha().isAfter(haceUnMes))
                .count();
    }

    @Override
    public List<Object[]> contarTratamientosPorMedicamentoUltimoMes() {
        LocalDate haceUnMes = LocalDate.now().minusMonths(1);
        List<Tratamiento> tratamientos = tratamientoRepositorio.findAll();
        
        return tratamientos.stream()
                .filter(t -> t.getFecha() != null && t.getFecha().isAfter(haceUnMes))
                .collect(Collectors.groupingBy(
                    t -> t.getMedicamento() != null ? t.getMedicamento().getNombre() : "Desconocido",
                    Collectors.summingInt(t -> t.getCantidad() != null ? t.getCantidad() : 0)
                ))
                .entrySet()
                .stream()
                .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
                .collect(Collectors.toList());
    }
}