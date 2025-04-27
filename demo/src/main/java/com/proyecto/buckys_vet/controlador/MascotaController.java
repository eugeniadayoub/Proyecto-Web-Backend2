package com.proyecto.buckys_vet.controlador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import com.proyecto.buckys_vet.servicio.DuenoServicio;
import com.proyecto.buckys_vet.servicio.MascotaServicio;

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "http://localhost:4200")
public class MascotaController {

    @Autowired
    private MascotaServicio mascotaServicio;

    @Autowired
    private DuenoServicio duenoServicio;

    @GetMapping
    public List<Mascota> listarTodas() {
        Collection<Mascota> mascotasCollection = mascotaServicio.obtenerTodas();
        return mascotasCollection != null ? new ArrayList<>(mascotasCollection) : new ArrayList<>();
    }

    @GetMapping("/{id}")
    public Mascota obtenerPorId(@PathVariable Long id) {
        return mascotaServicio.obtenerPorId(id);
    }

    @GetMapping("/dueno/{duenoId}")
    public List<Mascota> listarPorDueno(@PathVariable Long duenoId) {
        return mascotaServicio.obtenerPorDuenoId(duenoId);
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
        if (existente != null) {
            mascota.setMascotaId(id);
            mascota.setDueno(existente.getDueno());
            return mascotaServicio.guardar(mascota);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void eliminarMascota(@PathVariable Long id) {
        mascotaServicio.eliminar(id);
    }

    @GetMapping("/totales")
    public ResponseEntity<Long> obtenerCantidadMascotasTotales() {
        long total = mascotaServicio.contarMascotasTotales();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @GetMapping("/activas/total")
    public ResponseEntity<Long> obtenerCantidadMascotasActivas() {
        long total = mascotaServicio.contarMascotasActivas();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
}
