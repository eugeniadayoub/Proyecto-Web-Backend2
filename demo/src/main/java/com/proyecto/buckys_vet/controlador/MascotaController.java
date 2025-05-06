package com.proyecto.buckys_vet.controlador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.buckys_vet.entidad.Mascota;
import com.proyecto.buckys_vet.servicio.MascotaServicio;
import com.proyecto.buckys_vet.servicio.VeterinarioServicio;

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "http://localhost:4200")
public class MascotaController {

    @Autowired
    private MascotaServicio mascotaServicio;

    @Autowired
    private VeterinarioServicio veterinarioServicio;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listarTodas() {
        Collection<Mascota> mascotasCollection = mascotaServicio.obtenerTodas();
        if (mascotasCollection == null || mascotasCollection.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        List<Map<String, Object>> mascotasSimplificadas = mascotasCollection.stream()
                .map(m -> {
                    Map<String, Object> mascotaMap = new HashMap<>();
                    mascotaMap.put("mascotaId", m.getMascotaId());
                    mascotaMap.put("nombre", m.getNombre());
                    mascotaMap.put("especie", m.getEspecie());
                    mascotaMap.put("edad", m.getEdad());
                    mascotaMap.put("peso", m.getPeso());
                    mascotaMap.put("enfermedad", m.getEnfermedad());
                    mascotaMap.put("imagenUrl", m.getImagenUrl());
                    mascotaMap.put("imagen_url", m.getImagenUrl()); // Para compatibilidad
                    mascotaMap.put("estado", m.getEstado());

                    // Añadir información simplificada del dueño
                    if (m.getDueno() != null) {
                        Map<String, Object> duenoMap = new HashMap<>();
                        duenoMap.put("id", m.getDueno().getIdDueno()); 
                        duenoMap.put("nombre", m.getDueno().getNombre());
                        mascotaMap.put("dueno", duenoMap);
                    }
                    

                    // Añadir información simplificada del veterinario
                    if (m.getVeterinario() != null) {
                        Map<String, Object> vetMap = new HashMap<>();
                        vetMap.put("id", m.getVeterinario().getId());
                        vetMap.put("nombre", m.getVeterinario().getNombre());
                        vetMap.put("especialidad", m.getVeterinario().getEspecialidad());
                        mascotaMap.put("veterinario", vetMap);
                    }

                    return mascotaMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(mascotasSimplificadas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Mascota mascota = mascotaServicio.obtenerPorId(id);
        if (mascota == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> mascotaMap = new HashMap<>();
        mascotaMap.put("mascotaId", mascota.getMascotaId());
        mascotaMap.put("nombre", mascota.getNombre());
        mascotaMap.put("especie", mascota.getEspecie());
        mascotaMap.put("edad", mascota.getEdad());
        mascotaMap.put("peso", mascota.getPeso());
        mascotaMap.put("enfermedad", mascota.getEnfermedad());
        mascotaMap.put("imagenUrl", mascota.getImagenUrl());
        mascotaMap.put("imagen_url", mascota.getImagenUrl()); // Para compatibilidad
        mascotaMap.put("estado", mascota.getEstado());

        if (mascota.getDueno() != null) {
            Map<String, Object> duenoMap = new HashMap<>();
            duenoMap.put("id", mascota.getDueno().getIdDueno());
            duenoMap.put("nombre", mascota.getDueno().getNombre());
            mascotaMap.put("dueno", duenoMap);
        }        

        // Añadir información simplificada del veterinario
        if (mascota.getVeterinario() != null) {
            Map<String, Object> vetMap = new HashMap<>();
            vetMap.put("id", mascota.getVeterinario().getId());
            vetMap.put("nombre", mascota.getVeterinario().getNombre());
            vetMap.put("especialidad", mascota.getVeterinario().getEspecialidad());
            mascotaMap.put("veterinario", vetMap);
        }

        return ResponseEntity.ok(mascotaMap);
    }

    @GetMapping("/dueno/{duenoId}")
    public ResponseEntity<List<Map<String, Object>>> listarPorDueno(@PathVariable Long duenoId) {
        List<Mascota> mascotas = mascotaServicio.obtenerPorDuenoId(duenoId);
        if (mascotas == null || mascotas.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        List<Map<String, Object>> mascotasSimplificadas = mascotas.stream()
                .map(m -> {
                    Map<String, Object> mascotaMap = new HashMap<>();
                    mascotaMap.put("mascotaId", m.getMascotaId());
                    mascotaMap.put("nombre", m.getNombre());
                    mascotaMap.put("especie", m.getEspecie());
                    mascotaMap.put("edad", m.getEdad());
                    mascotaMap.put("peso", m.getPeso());
                    mascotaMap.put("enfermedad", m.getEnfermedad());
                    mascotaMap.put("imagenUrl", m.getImagenUrl());
                    mascotaMap.put("imagen_url", m.getImagenUrl()); // Para compatibilidad
                    mascotaMap.put("estado", m.getEstado());

                    if (m.getDueno() != null) {
                        mascotaMap.put("idDueno", m.getDueno().getIdDueno()); // este lo puedes dejar si lo usas en algún otro lugar
                        Map<String, Object> duenoMap = new HashMap<>();
                        duenoMap.put("id", m.getDueno().getIdDueno()); 
                        duenoMap.put("nombre", m.getDueno().getNombre());
                        mascotaMap.put("dueno", duenoMap);
                    }
                    

                    // Añadir información simplificada del veterinario
                    if (m.getVeterinario() != null) {
                        Map<String, Object> vetMap = new HashMap<>();
                        vetMap.put("id", m.getVeterinario().getId());
                        vetMap.put("nombre", m.getVeterinario().getNombre());
                        vetMap.put("especialidad", m.getVeterinario().getEspecialidad());
                        mascotaMap.put("veterinario", vetMap);
                    }

                    return mascotaMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(mascotasSimplificadas);
    }

    @PostMapping
    public ResponseEntity<Mascota> agregarMascota(@RequestBody Mascota mascota) {
        try {
            Mascota nuevaMascota = mascotaServicio.guardar(mascota);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMascota);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public Mascota modificarMascota(@PathVariable Long id, @RequestBody Mascota mascota) {
        Mascota existente = mascotaServicio.obtenerPorId(id);
        if (existente == null) {
            return null;
        }

        mascota.setMascotaId(id);

        // Mantener dueño si no lo cambió
        if (mascota.getDueno() == null) {
            mascota.setDueno(existente.getDueno());
        }

        // Cambiar veterinario si viene uno nuevo
        if (mascota.getVeterinario() != null && mascota.getVeterinario().getId() != null) {
            var veterinario = veterinarioServicio.obtenerPorId(mascota.getVeterinario().getId());
            mascota.setVeterinario(veterinario);
        } else {
            mascota.setVeterinario(existente.getVeterinario());
        }

        return mascotaServicio.guardar(mascota);
    }




    @DeleteMapping("/{id}")
    public void eliminarMascota(@PathVariable Long id) {
        mascotaServicio.eliminar(id);
    }

    @GetMapping("/totales")
    public ResponseEntity<Long> contarMascotasTotales() {
        Long cantidad = mascotaServicio.contarMascotasTotales();
        return new ResponseEntity<>(cantidad, HttpStatus.OK);
    }

    @GetMapping("/activas")
    public long contarMascotasActivas() {
        return mascotaServicio.contarMascotasActivas();
    }


    @GetMapping("/veterinario/{idVeterinario}/activas")
    public ResponseEntity<List<Map<String, Object>>> listarActivasPorVeterinario(@PathVariable Long idVeterinario) {
        List<Map<String, Object>> mascotas = mascotaServicio.obtenerPorVeterinarioId(idVeterinario)
            .stream()
            .filter(m -> "Activo".equalsIgnoreCase(m.getEstado()))
            .map(m -> {
                Map<String, Object> mascotaMap = new HashMap<>();
                mascotaMap.put("mascotaId", m.getMascotaId());
                mascotaMap.put("nombre", m.getNombre());
                mascotaMap.put("especie", m.getEspecie());
                return mascotaMap;
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(mascotas);
    }


}

