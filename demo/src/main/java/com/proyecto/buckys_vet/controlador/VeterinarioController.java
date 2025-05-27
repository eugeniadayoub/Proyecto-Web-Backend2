package com.proyecto.buckys_vet.controlador;

import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.buckys_vet.entidad.Veterinario;
import com.proyecto.buckys_vet.entidad.UserEntity;
import com.proyecto.buckys_vet.servicio.VeterinarioServicio;
import com.proyecto.buckys_vet.repositorio.UserRepository;
import com.proyecto.buckys_vet.security.CustomUserDetailService;

@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {

    @Autowired
    private VeterinarioServicio veterinarioServicio;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    // Obtener todos los veterinarios
    @GetMapping
    public List<Veterinario> obtenerVeterinarios() {
        return veterinarioServicio.obtenerTodos();
    }

    // Obtener un veterinario por ID con datos completos pero sin referencias
    // circulares
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVeterinarioPorId(@PathVariable Long id) {
        Veterinario veterinario = veterinarioServicio.obtenerPorId(id);
        if (veterinario == null) {
            return ResponseEntity.notFound().build();
        }

        // Preparamos un mapa con los datos del veterinario y sus relaciones
        Map<String, Object> response = new HashMap<>();
        response.put("id", veterinario.getId());
        response.put("cedula", veterinario.getCedula());
        response.put("nombre", veterinario.getNombre());
        response.put("especialidad", veterinario.getEspecialidad());
        response.put("foto", veterinario.getFoto());
        response.put("numeroAtenciones", veterinario.getNumeroAtenciones());
        response.put("activo", veterinario.isActivo());
        response.put("estado", veterinario.getEstado());

        // Simplificamos las listas para evitar referencias circulares
        if (veterinario.getMascotas() != null) {
            List<Map<String, Object>> mascotasSimplificadas = veterinario.getMascotas().stream()
                    .map(m -> {
                        Map<String, Object> mascotaMap = new HashMap<>();
                        mascotaMap.put("mascotaId", m.getMascotaId());
                        mascotaMap.put("nombre", m.getNombre());
                        mascotaMap.put("especie", m.getEspecie());
                        mascotaMap.put("edad", m.getEdad());
                        mascotaMap.put("peso", m.getPeso());
                        mascotaMap.put("enfermedad", m.getEnfermedad());
                        mascotaMap.put("imagenUrl", m.getImagenUrl());
                        mascotaMap.put("estado", m.getEstado());
                        mascotaMap.put("imagen_url", m.getImagenUrl());
                        return mascotaMap;
                    })
                    .collect(Collectors.toList());
            response.put("mascotas", mascotasSimplificadas);
        }

        if (veterinario.getTratamientos() != null) {
            List<Map<String, Object>> tratamientosSimplificados = veterinario.getTratamientos().stream()
                    .map(t -> {
                        Map<String, Object> tratamientoMap = new HashMap<>();
                        tratamientoMap.put("id", t.getId());
                        tratamientoMap.put("fecha", t.getFecha());
                        tratamientoMap.put("descripcion", t.getDescripcion());
                        tratamientoMap.put("cantidad", t.getCantidad());

                        // Datos simplificados de la mascota
                        if (t.getMascota() != null) {
                            Map<String, Object> mascotaMap = new HashMap<>();
                            mascotaMap.put("mascotaId", t.getMascota().getMascotaId());
                            mascotaMap.put("nombre", t.getMascota().getNombre());
                            tratamientoMap.put("mascota", mascotaMap);
                        }

                        // Datos simplificados del medicamento
                        if (t.getMedicamento() != null) {
                            Map<String, Object> medicamentoMap = new HashMap<>();
                            medicamentoMap.put("id", t.getMedicamento().getId());
                            medicamentoMap.put("nombre", t.getMedicamento().getNombre());
                            tratamientoMap.put("medicamento", medicamentoMap);
                        }

                        return tratamientoMap;
                    })
                    .collect(Collectors.toList());
            response.put("tratamientos", tratamientosSimplificados);
        }

        return ResponseEntity.ok(response);
    }

    // Crear un nuevo veterinario
    @PostMapping
    public ResponseEntity<Veterinario> agregarVeterinario(@RequestBody Veterinario veterinario) {
        try {
            System.out.println("=== INICIANDO CREACIÓN DE VETERINARIO ===");
            System.out.println("Datos recibidos: " + veterinario.getNombre() + ", " + veterinario.getEspecialidad());

            String username = "vet_" + veterinario.getNombre().toLowerCase().replace(" ", "_");
            if (userRepository.existsByUsername(username)) {
                System.out.println("ERROR: Username ya existe: " + username);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            System.out.println("Creando UserEntity...");
            UserEntity user = customUserDetailService.VetToUser(veterinario);
            System.out.println("UserEntity creado: " + user.getUsername());

            veterinario.setUser(user);
            System.out.println("Guardando veterinario...");
            Veterinario nuevoVeterinario = veterinarioServicio.guardar(veterinario);
            System.out.println("Veterinario guardado con ID: " + nuevoVeterinario.getId());

            System.out.println("=== VETERINARIO CREADO EXITOSAMENTE ===");
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVeterinario);
        } catch (Exception e) {
            System.out.println("ERROR CREANDO VETERINARIO: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Eliminar un veterinario por ID
    @DeleteMapping("/{id}")
    public void eliminarVeterinario(@PathVariable Long id) {
        veterinarioServicio.eliminar(id);
    }

    // Actualizar un veterinario
    @PutMapping("/{id}")
    public Veterinario actualizarVeterinario(@PathVariable Long id, @RequestBody Veterinario veterinario) {
        veterinario.setId(id); // Asigna el ID para asegurarse de que se actualice correctamente
        return veterinarioServicio.update(veterinario);
    }

    @GetMapping("/activos/total")
    public ResponseEntity<Long> contarVeterinariosActivos() {
        Long cantidad = veterinarioServicio.contarVeterinariosActivos();
        return new ResponseEntity<>(cantidad, HttpStatus.OK);
    }

    @GetMapping("/inactivos/total")
    public ResponseEntity<Long> contarVeterinariosInactivos() {
        Long cantidad = veterinarioServicio.contarVeterinariosInactivos();
        return new ResponseEntity<>(cantidad, HttpStatus.OK);
    }
}
