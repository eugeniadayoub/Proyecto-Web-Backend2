package com.proyecto.buckys_vet.servicio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

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
        try {
            System.out.println("=== SERVICIO: GUARDANDO TRATAMIENTO ===");

            // Validar medicamento
            if (tratamiento.getMedicamento() == null) {
                System.out.println("ERROR: El medicamento no puede ser nulo");
                throw new IllegalArgumentException("El medicamento no puede ser nulo");
            }
            System.out.println("Medicamento válido: " + tratamiento.getMedicamento().getId());

            // Validar y obtener mascota
            if (tratamiento.getMascota() == null || tratamiento.getMascota().getMascotaId() == null) {
                System.out.println("ERROR: La mascota no puede ser nula");
                throw new IllegalArgumentException("La mascota no puede ser nula");
            }

            Long mascotaId = tratamiento.getMascota().getMascotaId();
            System.out.println("Buscando mascota con ID: " + mascotaId);
            Mascota mascota = mascotaRepositorio.findById(mascotaId)
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + mascotaId));
            tratamiento.setMascota(mascota);
            System.out.println("Mascota encontrada: " + mascota.getNombre());

            // Validar y obtener medicamento
            Long medicamentoId = tratamiento.getMedicamento().getId();
            System.out.println("Buscando medicamento con ID: " + medicamentoId);
            Medicamento medicamento = medicamentoRepositorio.findById(medicamentoId)
                    .orElseThrow(() -> new RuntimeException("Medicamento no encontrado con ID: " + medicamentoId));
            System.out.println("Medicamento encontrado: " + medicamento.getNombre());

            // Validar unidades disponibles
            if (tratamiento.getCantidad() > medicamento.getUnidadesDisponibles()) {
                System.out.println("ERROR: No hay suficientes unidades disponibles");
                throw new RuntimeException("No hay suficientes unidades disponibles. Disponibles: " +
                        medicamento.getUnidadesDisponibles() + ", Solicitadas: " + tratamiento.getCantidad());
            }

            // Actualizar unidades vendidas del medicamento
            System.out.println("Actualizando inventario del medicamento...");
            medicamento.setUnidadesVendidas(medicamento.getUnidadesVendidas() + tratamiento.getCantidad());
            medicamento.setUnidadesDisponibles(medicamento.getUnidadesDisponibles() - tratamiento.getCantidad());
            medicamentoRepositorio.save(medicamento);
            System.out.println("Inventario actualizado");

            tratamiento.setMedicamento(medicamento);

            // Validar y obtener veterinario
            if (tratamiento.getVeterinario() == null || tratamiento.getVeterinario().getId() == null) {
                System.out.println("ERROR: El veterinario no puede ser nulo");
                throw new IllegalArgumentException("El veterinario no puede ser nulo");
            }

            Long veterinarioId = tratamiento.getVeterinario().getId();
            System.out.println("Buscando veterinario con ID: " + veterinarioId);
            Veterinario veterinario = veterinarioRepositorio.findById(veterinarioId)
                    .orElseThrow(() -> new RuntimeException("Veterinario no encontrado con ID: " + veterinarioId));
            tratamiento.setVeterinario(veterinario);
            System.out.println("Veterinario encontrado: " + veterinario.getNombre());

            System.out.println("Guardando tratamiento en base de datos...");
            Tratamiento tratamientoGuardado = tratamientoRepositorio.save(tratamiento);
            System.out.println("=== TRATAMIENTO GUARDADO EXITOSAMENTE ===");
            return tratamientoGuardado;

        } catch (Exception e) {
            System.out.println("ERROR EN SERVICIO GUARDAR TRATAMIENTO: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
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
    public Long contarTratamientosUltimoMes() {
        LocalDate haceUnMes = LocalDate.now().minusMonths(1);
        return tratamientoRepositorio.countTratamientosDesde(haceUnMes);
    }

    @Override
    public List<Object[]> contarPorMedicamentoUltimoMes() {
        LocalDate haceUnMes = LocalDate.now().minusMonths(1);
        return tratamientoRepositorio.contarTratamientosPorMedicamentoUltimoMes(haceUnMes);
    }

    @Override
    public List<Tratamiento> obtenerTop3TratamientosMasVendidos() {
        Pageable topThree = PageRequest.of(0, 3);
        return tratamientoRepositorio.findTop3ByOrderByCantidadDesc(topThree);
    }

}