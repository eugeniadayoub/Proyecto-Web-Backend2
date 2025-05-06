package com.proyecto.buckys_vet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.entidad.Medicamento;
import com.proyecto.buckys_vet.entidad.Tratamiento;
import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.repositorio.MascotaRepositorio;
import com.proyecto.buckys_vet.repositorio.MedicamentoRepositorio;
import com.proyecto.buckys_vet.repositorio.TratamientoRepositorio;
import com.proyecto.buckys_vet.repositorio.VeterinarioRepositorio;
import com.proyecto.buckys_vet.servicio.TratamientoServicioImpl;

@ExtendWith(MockitoExtension.class)
public class TratamientoServicioMockTest {

    @Mock
    private TratamientoRepositorio tratamientoRepositorio;

    @Mock
    private MascotaRepositorio mascotaRepositorio;

    @Mock
    private MedicamentoRepositorio medicamentoRepositorio;

    @Mock
    private VeterinarioRepositorio veterinarioRepositorio;

    @InjectMocks
    private TratamientoServicioImpl tratamientoServicio;

    private Mascota mascota;
    private Veterinario veterinario;
    private Medicamento medicamento;
    private Tratamiento tratamiento;

    @BeforeEach
    public void setup() {
        // Configurar los objetos de prueba
        mascota = new Mascota();
        mascota.setMascotaId(1L);
        mascota.setNombre("Max");
        mascota.setEspecie("Perro");

        veterinario = new Veterinario();
        veterinario.setId(1L);
        veterinario.setNombre("Dr. Pérez");
        veterinario.setEspecialidad("Cardiología");

        medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNombre("Antibiótico");
        medicamento.setPrecioVenta(100.0);
        medicamento.setUnidadesDisponibles(50);

        tratamiento = new Tratamiento();
        tratamiento.setId(1L);
        tratamiento.setDescripcion("Tratamiento para infección");
        tratamiento.setFecha(LocalDate.now());
        tratamiento.setCantidad(2);
        tratamiento.setMascota(mascota);
        tratamiento.setVeterinario(veterinario);
        tratamiento.setMedicamento(medicamento);
    }

    @Test
    public void testObtenerTodos() {
        // ARRANGE (Preparación)
        when(tratamientoRepositorio.findAll()).thenReturn(Arrays.asList(tratamiento));

        // ACT (Ejecución)
        List<Tratamiento> tratamientos = tratamientoServicio.obtenerTodos();

        // ASSERT (Verificación)
        assertNotNull(tratamientos);
        assertEquals(1, tratamientos.size());
        assertEquals("Tratamiento para infección", tratamientos.get(0).getDescripcion());
        verify(tratamientoRepositorio, times(1)).findAll();
    }

    @Test
    public void testObtenerPorId() {
        // ARRANGE (Preparación)
        when(tratamientoRepositorio.findById(1L)).thenReturn(Optional.of(tratamiento));
        when(tratamientoRepositorio.findById(2L)).thenReturn(Optional.empty());

        // ACT (Ejecución)
        Tratamiento encontrado = tratamientoServicio.obtenerPorId(1L);
        Tratamiento noEncontrado = tratamientoServicio.obtenerPorId(2L);

        // ASSERT (Verificación)
        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
        assertNull(noEncontrado);
        verify(tratamientoRepositorio, times(1)).findById(1L);
        verify(tratamientoRepositorio, times(1)).findById(2L);
    }

    @Test
    public void testGuardar() {
        // ARRANGE (Preparación)
        when(mascotaRepositorio.findById(1L)).thenReturn(Optional.of(mascota));
        when(medicamentoRepositorio.findById(1L)).thenReturn(Optional.of(medicamento));
        when(veterinarioRepositorio.findById(1L)).thenReturn(Optional.of(veterinario));
        when(tratamientoRepositorio.save(any(Tratamiento.class))).thenReturn(tratamiento);

        // ACT (Ejecución)
        Tratamiento guardado = tratamientoServicio.guardar(tratamiento);

        // ASSERT (Verificación)
        assertNotNull(guardado);
        assertEquals(1L, guardado.getId());
        verify(mascotaRepositorio, times(1)).findById(1L);
        verify(medicamentoRepositorio, times(1)).findById(1L);
        verify(veterinarioRepositorio, times(1)).findById(1L);
        verify(tratamientoRepositorio, times(1)).save(any(Tratamiento.class));
    }

    @Test
    public void testGuardarSinMedicamento() {
        // ARRANGE (Preparación)
        tratamiento.setMedicamento(null);

        // ACT & ASSERT (Ejecución y Verificación)
        assertThrows(IllegalArgumentException.class, () -> {
            tratamientoServicio.guardar(tratamiento);
        });
    }

    @Test
    public void testEliminar() {
        // ARRANGE (Preparación)
        doNothing().when(tratamientoRepositorio).deleteById(anyLong());

        // ACT (Ejecución)
        tratamientoServicio.eliminar(1L);

        // ASSERT (Verificación)
        verify(tratamientoRepositorio, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdate() {
        // ARRANGE (Preparación)
        when(tratamientoRepositorio.existsById(1L)).thenReturn(true);
        when(tratamientoRepositorio.existsById(2L)).thenReturn(false);
        when(tratamientoRepositorio.save(any(Tratamiento.class))).thenReturn(tratamiento);

        // ACT (Ejecución)
        Tratamiento actualizado = tratamientoServicio.update(tratamiento);
        
        // Cambiar el ID y ejecutar de nuevo para el caso negativo
        Long originalId = tratamiento.getId();
        tratamiento.setId(2L);
        Tratamiento noActualizado = tratamientoServicio.update(tratamiento);
        
        // Restaurar el ID original
        tratamiento.setId(originalId);

        // ASSERT (Verificación)
        assertNotNull(actualizado);
        assertNull(noActualizado);
        verify(tratamientoRepositorio, times(1)).save(any(Tratamiento.class));
    }

    @Test
    public void testContarTratamientosUltimoMes() {
        // ARRANGE (Preparación)
        when(tratamientoRepositorio.countTratamientosDesde(any(LocalDate.class))).thenReturn(5L);

        // ACT (Ejecución)
        Long cantidad = tratamientoServicio.contarTratamientosUltimoMes();

        // ASSERT (Verificación)
        assertEquals(5L, cantidad);
        verify(tratamientoRepositorio, times(1)).countTratamientosDesde(any(LocalDate.class));
    }

    @Test
    public void testContarPorMedicamentoUltimoMes() {
        // ARRANGE (Preparación)
        List<Object[]> datos = new ArrayList<>();
        datos.add(new Object[] { "Antibiótico", 10L });
        datos.add(new Object[] { "Analgésico", 5L });
        when(tratamientoRepositorio.contarTratamientosPorMedicamentoUltimoMes(any(LocalDate.class))).thenReturn(datos);

        // ACT (Ejecución)
        List<Object[]> resultado = tratamientoServicio.contarPorMedicamentoUltimoMes();

        // ASSERT (Verificación)
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Antibiótico", resultado.get(0)[0]);
        assertEquals(10L, resultado.get(0)[1]);
        verify(tratamientoRepositorio, times(1)).contarTratamientosPorMedicamentoUltimoMes(any(LocalDate.class));
    }

    @Test
    public void testObtenerTop3TratamientosMasVendidos() {
        // ARRANGE (Preparación)
        List<Tratamiento> top3 = new ArrayList<>();
        top3.add(tratamiento);
        when(tratamientoRepositorio.findTop3ByOrderByCantidadDesc(any(PageRequest.class))).thenReturn(top3);

        // ACT (Ejecución)
        List<Tratamiento> resultado = tratamientoServicio.obtenerTop3TratamientosMasVendidos();

        // ASSERT (Verificación)
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        verify(tratamientoRepositorio, times(1)).findTop3ByOrderByCantidadDesc(any(PageRequest.class));
    }
}