package com.proyecto.buckys_vet.service;

import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.entidad.Medicamento;
import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.entidad.Tratamiento;
import com.proyecto.buckys_vet.repositorio.MascotaRepositorio;
import com.proyecto.buckys_vet.repositorio.MedicamentoRepositorio;
import com.proyecto.buckys_vet.repositorio.VeterinarioRepositorio;
import com.proyecto.buckys_vet.servicio.TratamientoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;

import static org.mockito.ArgumentMatchers.notNull;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class TratamientoServiceTest {

    @Autowired
    private TratamientoServicio tratamientoServicio;

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private VeterinarioRepositorio veterinarioRepositorio;

    @Autowired
    private MedicamentoRepositorio medicamentoRepositorio;

    private Mascota mascota;
    private Veterinario veterinario;
    private Medicamento medicamento;

    @BeforeEach
    public void setUp() {
        // Crear y guardar un veterinario
        veterinario = new Veterinario();
        veterinario.setNombre("Dra. Ana");
        veterinario.setCedula(123456789L);
        veterinario = veterinarioRepositorio.save(veterinario);

        // Crear y guardar una mascota
        mascota = new Mascota("Toby", "Perro", 4, 10.5, "Dolor dental", "url_imagen", "Activo");
        mascota.setVeterinario(veterinario);
        mascota = mascotaRepositorio.save(mascota); // Asegúrate de guardar la mascota en la base de datos

        // Crear y guardar medicamento
        Medicamento medicamento = new Medicamento();
        medicamento.setNombre("Analgésico");
        medicamento.setPrecioCompra(1000.0);
        medicamento.setPrecioVenta(1500.0);
        medicamento.setUnidadesDisponibles(50);
        medicamento.setUnidadesVendidas(10);

        // Guardar medicamento en la base de datos
        medicamento = medicamentoRepositorio.save(medicamento);
    }

    @Test
    public void testGuardarTratamiento() {
        // Crear y guardar medicamento
        Medicamento medicamento = new Medicamento();
        medicamento.setNombre("Analgésico");
        medicamento.setPrecioCompra(1000.0);
        medicamento.setPrecioVenta(1500.0);
        medicamento.setUnidadesDisponibles(50);
        medicamento.setUnidadesVendidas(10);

        // Guardar medicamento en la base de datos
        medicamento = medicamentoRepositorio.save(medicamento);

        // Verificar que el medicamento no sea null
        Assertions.assertThat(medicamento).isNotNull();
        Assertions.assertThat(medicamento.getId()).isNotNull(); // Verifica que el medicamento tenga un ID

        // Crear tratamiento y asociar el medicamento
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setFecha(LocalDate.now());
        tratamiento.setDescripcion("Tratamiento de prueba");
        tratamiento.setCantidad(1);
        tratamiento.setMedicamento(medicamento); // Asegúrate de que el medicamento no sea null
        tratamiento.setMascota(mascota); // Asegúrate de que la mascota no sea null
        tratamiento.setVeterinario(veterinario); // Asegúrate de que el veterinario no sea null

        // Verificar que el medicamento se guardó correctamente en el tratamiento
        Assertions.assertThat(tratamiento.getMedicamento()).isNotNull();
        Assertions.assertThat(tratamiento.getMedicamento().getId()).isEqualTo(medicamento.getId());
    }

    @Test
    public void TratamientoServicio_guardar_Tratamiento() {
        // Asegúrate de que las entidades relacionadas no sean null
        Assertions.assertThat(mascota).isNotNull();
        Assertions.assertThat(veterinario).isNotNull();

        // Crear y guardar medicamento
        Medicamento medicamento = new Medicamento();
        medicamento.setNombre("Analgésico");
        medicamento.setPrecioCompra(1000.0);
        medicamento.setPrecioVenta(1500.0);
        medicamento.setUnidadesDisponibles(50);
        medicamento.setUnidadesVendidas(10);
        medicamento = medicamentoRepositorio.save(medicamento); // Guardamos el medicamento en la base de datos

        // Verificar que el medicamento no sea null
        Assertions.assertThat(medicamento).isNotNull();

        // Crear un tratamiento
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setFecha(LocalDate.now());
        tratamiento.setDescripcion("Tratamiento de prueba");
        tratamiento.setCantidad(1);

        // Asocia las entidades previamente guardadas (no null)
        tratamiento.setMascota(mascota); // Asociar la mascota
        tratamiento.setVeterinario(veterinario); // Asociar el veterinario
        tratamiento.setMedicamento(medicamento); // Asociar el medicamento

        // Verifica que el tratamiento guardado no sea null
        Assertions.assertThat(tratamiento).isNotNull();
        Assertions.assertThat(tratamiento.getDescripcion()).isEqualTo("Tratamiento de prueba");

        // Verificar que el medicamento y las relaciones están correctamente asociadas
        Assertions.assertThat(tratamiento.getMedicamento()).isNotNull();
        Assertions.assertThat(tratamiento.getMedicamento().getId()).isEqualTo(medicamento.getId());
        Assertions.assertThat(tratamiento.getMascota()).isEqualTo(mascota);
        Assertions.assertThat(tratamiento.getVeterinario()).isEqualTo(veterinario);
    }

    @Test
    public void testObtenerTratamientosPorMascota() {
        // Crear y guardar un medicamento
        Medicamento medicamento = new Medicamento();
        medicamento.setNombre("Analgésico");
        medicamento.setPrecioCompra(1000.0);
        medicamento.setPrecioVenta(1500.0);
        medicamento.setUnidadesDisponibles(50);
        medicamento.setUnidadesVendidas(10);
        medicamento = medicamentoRepositorio.save(medicamento); // Guardar el medicamento

        // Crear y guardar un tratamiento para la mascota
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setFecha(LocalDate.now().minusDays(1));
        tratamiento.setDescripcion("Tratamiento de prueba");
        tratamiento.setCantidad(1);
        tratamiento.setMascota(mascota); // Asociar la mascota
        tratamiento.setVeterinario(veterinario); // Asociar el veterinario
        tratamiento.setMedicamento(medicamento); // Asignar medicamento al tratamiento

        // Verificar que el tratamiento fue guardado correctamente
        Assertions.assertThat(tratamiento).isNotNull();
        Assertions.assertThat(tratamiento.getDescripcion()).isEqualTo("Tratamiento de prueba");
        Assertions.assertThat(tratamiento.getMedicamento()).isNotNull(); // Verificar que el medicamento no sea null
        Assertions.assertThat(tratamiento.getMedicamento().getId()).isEqualTo(medicamento.getId());
    }

}