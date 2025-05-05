package com.proyecto.buckys_vet.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;

import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.entidad.Medicamento;
import com.proyecto.buckys_vet.entidad.Tratamiento;
import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.entidad.Dueno;
import com.proyecto.buckys_vet.repositorio.MascotaRepositorio;
import com.proyecto.buckys_vet.repositorio.MedicamentoRepositorio;
import com.proyecto.buckys_vet.repositorio.DuenoRepositorio;
import com.proyecto.buckys_vet.repositorio.TratamientoRepositorio;
import com.proyecto.buckys_vet.repositorio.VeterinarioRepositorio;

@DataJpaTest
public class MascotaRepositoryTest {

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private DuenoRepositorio duenoRepositorio;

    @Autowired
    private VeterinarioRepositorio veterinarioRepositorio;

    @Autowired
    private MedicamentoRepositorio medicamentoRepositorio;

    @Autowired
    private TratamientoRepositorio tratamientoRepositorio;

    @BeforeEach
    public void setUp() {
        mascotaRepositorio
                .save(new Mascota("Luna", "Perro", 2, 3.5, "No tiene", "https://example.com/mascota.jpg", "Activa"));
        mascotaRepositorio
                .save(new Mascota("Simba", "Gato", 1, 2.5, "No tiene", "https://example.com/mascota.jpg", "Inactiva"));
        mascotaRepositorio
                .save(new Mascota("Coco", "Perro", 3, 4.5, "No tiene", "https://example.com/mascota.jpg", "Activa"));
        mascotaRepositorio
                .save(new Mascota("Milo", "Perro", 2, 3.5, "No tiene", "https://example.com/mascota.jpg", "Inactiva"));

        duenoRepositorio.save(new Dueno(1234567890L, "Carlos Pérez", "carlos@example.com", "3104567890",
                "https://example.com/foto.jpg", "claveSegura123"));
        duenoRepositorio.save(new Dueno(987654321L, "Ana Rodríguez", "ana@example", "3109876543",
                "https://example.com/foto.jpg", "claveSegura123"));

        Mascota mascota = mascotaRepositorio.findById(1L).get();
        Dueno dueno = duenoRepositorio.findById(1L).get();

        Veterinario vet = new Veterinario();
        vet.setNombre("Dra. María");
        vet.setEspecialidad("Dermatología");
        vet.setCedula(100200300L);
        vet.setContrasena("clave123");
        vet.setFoto("urlVet");
        vet = veterinarioRepositorio.save(vet);

        // Crear Medicamento
        Medicamento medicamento = new Medicamento();
        medicamento.setNombre("Antibiótico");
        medicamento.setPrecioCompra(2000.0);
        medicamento.setPrecioVenta(3000.0);
        medicamento.setUnidadesDisponibles(50);
        medicamento.setUnidadesVendidas(0);
        medicamento = medicamentoRepositorio.save(medicamento);

        // Crear Tratamiento
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setFecha(LocalDate.now());
        tratamiento.setDescripcion("Tratamiento para infección de piel");
        tratamiento.setCantidad(2);
        tratamiento.setMascota(mascota);
        tratamiento.setVeterinario(vet);
        tratamiento.setMedicamento(medicamento);
        tratamiento = tratamientoRepositorio.save(tratamiento);
    }

    @Test
    public void MascotaRepository_save_Mascota() {
        Mascota mascota = new Mascota("Luna", "Perro", 2, 3.5, "No tiene", "https://example.com/mascota.jpg", "Activa");
        Mascota savedMascota = mascotaRepositorio.save(mascota);
        Assertions.assertThat(savedMascota).isNotNull();
    }

    @Test
    public void MascotaRepository_findById_returnsMascota() {
        Mascota mascota = new Mascota("Luna", "Perro", 2, 3.5, "No tiene", "https://example.com/mascota.jpg", "Activa");
        Mascota saved = mascotaRepositorio.save(mascota);

        Mascota found = mascotaRepositorio.findById(saved.getMascotaId()).orElse(null);
        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.getNombre()).isEqualTo("Luna");
    }

    @Test
    public void MascotaRepository_findAll_returnsList() {
        mascotaRepositorio.save(new Mascota("Luna", "Perro", 2, 3.5, "No tiene", "url", "Activa"));
        mascotaRepositorio.save(new Mascota("Max", "Gato", 3, 4.0, "No tiene", "url", "Activo"));

        var lista = mascotaRepositorio.findAll();
        Assertions.assertThat(lista).hasSize(2);
    }

    @Test
    public void MascotaRepository_updateMascota_successfully() {
        Mascota mascota = new Mascota("Luna", "Perro", 2, 3.5, "No tiene", "url", "Activa");
        Mascota saved = mascotaRepositorio.save(mascota);

        saved.setNombre("Luna Modificada");
        Mascota updated = mascotaRepositorio.save(saved);

        Assertions.assertThat(updated.getNombre()).isEqualTo("Luna Modificada");
    }

    @Test
    public void MascotaRepository_deleteById_removesMascota() {
        Mascota mascota = new Mascota("Luna", "Perro", 2, 3.5, "No tiene", "url", "Activa");
        Mascota saved = mascotaRepositorio.save(mascota);

        mascotaRepositorio.deleteById(saved.getMascotaId());

        boolean exists = mascotaRepositorio.existsById(saved.getMascotaId());
        Assertions.assertThat(exists).isFalse();
    }

    @Test
    public void TratamientoRepository_deleteById_removesTratamiento() {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setId(1L);
        tratamientoRepositorio.save(tratamiento);
        tratamientoRepositorio.deleteById(tratamiento.getId());
        boolean existe = tratamientoRepositorio.existsById(tratamiento.getId());
        Assertions.assertThat(existe).isFalse();
    }
}
