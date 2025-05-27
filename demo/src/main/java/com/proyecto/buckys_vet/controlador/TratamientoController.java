package com.proyecto.buckys_vet.controlador;

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

import com.proyecto.buckys_vet.entidad.Tratamiento;
import com.proyecto.buckys_vet.servicio.TratamientoServicio;

@RestController
@RequestMapping("/tratamientos")
@CrossOrigin(origins = "http://localhost:4200")
public class TratamientoController {

    @Autowired
    private TratamientoServicio tratamientoServicio;

    @GetMapping
    public ResponseEntity<List<Tratamiento>> obtenerTodos() {
        List<Tratamiento> tratamientos = tratamientoServicio.obtenerTodos();
        return new ResponseEntity<>(tratamientos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tratamiento> obtenerPorId(@PathVariable Long id) {
        Tratamiento tratamiento = tratamientoServicio.obtenerPorId(id);
        if (tratamiento != null) {
            return new ResponseEntity<>(tratamiento, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Tratamiento> crearTratamiento(@RequestBody Tratamiento tratamiento) {
        try {
            System.out.println("=== INICIANDO CREACIÓN DE TRATAMIENTO ===");
            System.out.println("Tratamiento recibido: " + tratamiento);
            System.out.println("Fecha: " + tratamiento.getFecha());
            System.out.println("Descripción: " + tratamiento.getDescripcion());
            System.out.println("Cantidad: " + tratamiento.getCantidad());

            if (tratamiento.getMascota() != null) {
                System.out.println("Mascota ID: " + tratamiento.getMascota().getMascotaId());
            } else {
                System.out.println("ERROR: Mascota es null");
            }

            if (tratamiento.getVeterinario() != null) {
                System.out.println("Veterinario ID: " + tratamiento.getVeterinario().getId());
            } else {
                System.out.println("ERROR: Veterinario es null");
            }

            if (tratamiento.getMedicamento() != null) {
                System.out.println("Medicamento ID: " + tratamiento.getMedicamento().getId());
            } else {
                System.out.println("ERROR: Medicamento es null");
            }

            System.out.println("Guardando tratamiento...");
            Tratamiento nuevoTratamiento = tratamientoServicio.guardar(tratamiento);
            System.out.println("Tratamiento guardado con ID: " + nuevoTratamiento.getId());
            System.out.println("=== TRATAMIENTO CREADO EXITOSAMENTE ===");
            return new ResponseEntity<>(nuevoTratamiento, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("ERROR CREANDO TRATAMIENTO: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tratamiento> actualizarTratamiento(@PathVariable Long id,
            @RequestBody Tratamiento tratamiento) {
        tratamiento.setId(id);
        Tratamiento tratamientoActualizado = tratamientoServicio.update(tratamiento);
        if (tratamientoActualizado != null) {
            return new ResponseEntity<>(tratamientoActualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTratamiento(@PathVariable Long id) {
        tratamientoServicio.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/ultimo-mes/total")
    public ResponseEntity<Long> contarTratamientosUltimoMes() {
        Long cantidad = tratamientoServicio.contarTratamientosUltimoMes();
        return new ResponseEntity<>(cantidad, HttpStatus.OK);
    }

    @GetMapping("/ultimo-mes/por-medicamento")
    public ResponseEntity<List<Object[]>> contarPorMedicamentoUltimoMes() {
        List<Object[]> datos = tratamientoServicio.contarPorMedicamentoUltimoMes();
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    @GetMapping("/top3-tratamientos")
    public ResponseEntity<List<Tratamiento>> obtenerTop3Tratamientos() {
        List<Tratamiento> top3 = tratamientoServicio.obtenerTop3TratamientosMasVendidos();
        return new ResponseEntity<>(top3, HttpStatus.OK);
    }

}
