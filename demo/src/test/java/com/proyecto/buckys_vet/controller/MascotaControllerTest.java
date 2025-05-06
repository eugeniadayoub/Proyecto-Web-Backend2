package com.proyecto.buckys_vet.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.buckys_vet.entidad.Dueno;
import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.servicio.MascotaServicio;
import com.proyecto.buckys_vet.servicio.VeterinarioServicio;
import com.proyecto.buckys_vet.controlador.MascotaController;

@ExtendWith(MockitoExtension.class)
public class MascotaControllerTest {

    @Mock
    private MascotaServicio mascotaServicio;

    @Mock
    private VeterinarioServicio veterinarioServicio;

    @InjectMocks
    private MascotaController mascotaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Mascota mascota;
    private Dueno dueno;
    private Veterinario veterinario;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mascotaController).build();
        objectMapper = new ObjectMapper();

        // Configurar objetos de prueba
        dueno = new Dueno();
        dueno.setIdDueno(1L);
        dueno.setNombre("Juan Pérez");
        dueno.setEmail("juan@ejemplo.com");

        veterinario = new Veterinario();
        veterinario.setId(1L);
        veterinario.setNombre("Dra. Ana López");
        veterinario.setEspecialidad("Cardiología");

        mascota = new Mascota();
        mascota.setMascotaId(1L);
        mascota.setNombre("Max");
        mascota.setEspecie("Perro");
        mascota.setEdad(5);
        mascota.setPeso(15.5);
        mascota.setEnfermedad("Ninguna");
        mascota.setEstado("Activo");
        mascota.setImagenUrl("https://ejemplo.com/imagen.jpg");
        mascota.setDueno(dueno);
        mascota.setVeterinario(veterinario);
    }

    // 1. Prueba para obtener todas las mascotas (GET)
    @Test
    public void testListarTodas() throws Exception {
        // ARRANGE (Preparación)
        List<Mascota> mascotas = new ArrayList<>();
        mascotas.add(mascota);
        when(mascotaServicio.obtenerTodas()).thenReturn(mascotas);

        // ACT y ASSERT (Ejecución y Verificación)
        mockMvc.perform(get("/api/mascotas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Max"))
                .andExpect(jsonPath("$[0].especie").value("Perro"))
                .andExpect(jsonPath("$[0].mascotaId").value(1));

        // ASSERT adicional - Verificación de comportamiento
        verify(mascotaServicio, times(1)).obtenerTodas();
    }

    // 2. Prueba para obtener una mascota por ID (GET con parámetro path)
    @Test
    public void testObtenerPorId() throws Exception {
        // ARRANGE (Preparación)
        when(mascotaServicio.obtenerPorId(1L)).thenReturn(mascota);

        // ACT y ASSERT (Ejecución y Verificación)
        mockMvc.perform(get("/api/mascotas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Max"))
                .andExpect(jsonPath("$.especie").value("Perro"))
                .andExpect(jsonPath("$.mascotaId").value(1));

        // ASSERT adicional - Verificación de comportamiento
        verify(mascotaServicio, times(1)).obtenerPorId(1L);
    }

    // 3. Prueba para crear una mascota (POST)
    @Test
    public void testAgregarMascota() throws Exception {
        // ARRANGE (Preparación)
        when(mascotaServicio.guardar(any(Mascota.class))).thenReturn(mascota);

        // ACT y ASSERT (Ejecución y Verificación)
        mockMvc.perform(post("/api/mascotas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mascota)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Max"))
                .andExpect(jsonPath("$.especie").value("Perro"));

        // ASSERT adicional - Verificación de comportamiento
        verify(mascotaServicio, times(1)).guardar(any(Mascota.class));
    }

    // 4. Prueba para actualizar una mascota (PUT)
    @Test
    public void testModificarMascota() throws Exception {
        // ARRANGE (Preparación)
        when(mascotaServicio.obtenerPorId(1L)).thenReturn(mascota);
        when(mascotaServicio.guardar(any(Mascota.class))).thenReturn(mascota);

        // Crear un objeto con los datos a modificar
        Mascota mascotaModificada = new Mascota();
        mascotaModificada.setMascotaId(1L); // Establecer el ID para evitar NullPointerException
        mascotaModificada.setNombre("Max Modificado");
        mascotaModificada.setEspecie("Perro");
        mascotaModificada.setEdad(6);

        // ACT y ASSERT (Ejecución y Verificación)
        mockMvc.perform(put("/api/mascotas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mascotaModificada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Max"));

        // ASSERT adicional - Verificación de comportamiento
        verify(mascotaServicio, times(1)).obtenerPorId(1L);
        verify(mascotaServicio, times(1)).guardar(any(Mascota.class));
    }

    // 5. Prueba para eliminar una mascota (DELETE)
    @Test
    public void testEliminarMascota() throws Exception {
        // ARRANGE (Preparación)
        doNothing().when(mascotaServicio).eliminar(anyLong());

        // ACT y ASSERT (Ejecución y Verificación)
        mockMvc.perform(delete("/api/mascotas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // ASSERT adicional - Verificación de comportamiento
        verify(mascotaServicio, times(1)).eliminar(1L);
    }

    // 6. Prueba para obtener mascotas por dueño (GET con filtro)
    @Test
    public void testListarPorDueno() throws Exception {
        // ARRANGE (Preparación)
        List<Mascota> mascotas = new ArrayList<>();
        mascotas.add(mascota);
        when(mascotaServicio.obtenerPorDuenoId(1L)).thenReturn(mascotas);

        // ACT y ASSERT (Ejecución y Verificación)
        mockMvc.perform(get("/api/mascotas/dueno/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Max"));

        // ASSERT adicional - Verificación de comportamiento
        verify(mascotaServicio, times(1)).obtenerPorDuenoId(1L);
    }

    // 7. Prueba para contar mascotas totales (GET con agregación)
    @Test
    public void testContarMascotasTotales() throws Exception {
        // ARRANGE (Preparación)
        when(mascotaServicio.contarMascotasTotales()).thenReturn(10L);

        // ACT y ASSERT (Ejecución y Verificación)
        mockMvc.perform(get("/api/mascotas/totales")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(10));

        // ASSERT adicional - Verificación de comportamiento
        verify(mascotaServicio, times(1)).contarMascotasTotales();
    }
}
