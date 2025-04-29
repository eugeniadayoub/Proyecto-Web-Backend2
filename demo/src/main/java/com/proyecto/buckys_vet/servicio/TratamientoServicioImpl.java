package com.proyecto.buckys_vet.servicio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.entidad.Medicamento;
import com.proyecto.buckys_vet.entidad.Tratamiento;
import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.repositorio.MascotaRepositorio;
import com.proyecto.buckys_vet.repositorio.MedicamentoRepositorio;
import com.proyecto.buckys_vet.repositorio.TratamientoRepositorio;
import com.proyecto.buckys_vet.repositorio.VeterinarioRepositorio;

@Service
public class TratamientoServicioImpl implements TratamientoServicio {

    @Autowired
    private TratamientoRepositorio tratamientoRepositorio;

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private MedicamentoRepositorio medicamentoRepositorio;

    @Autowired
    private VeterinarioRepositorio veterinarioRepositorio;

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
        // Asociar entidad existente correctamente
        Long mascotaId = tratamiento.getMascota().getMascotaId();
        Mascota mascota = mascotaRepositorio.findById(mascotaId)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        tratamiento.setMascota(mascota);

        Long medicamentoId = tratamiento.getMedicamento().getId();
        Medicamento medicamento = medicamentoRepositorio.findById(medicamentoId)
            .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
        tratamiento.setMedicamento(medicamento);

        Long veterinarioId = tratamiento.getVeterinario().getId();
        Veterinario veterinario = veterinarioRepositorio.findById(veterinarioId)
            .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));
        tratamiento.setVeterinario(veterinario);

        return tratamientoRepositorio.save(tratamiento);
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