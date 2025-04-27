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
@RequestMapping("/api/tratamientos")
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
        // Log de los datos recibidos para ver si llegan correctamente
        System.out.println("Tratamiento recibido: " + tratamiento);
        try {
            Tratamiento nuevoTratamiento = tratamientoServicio.guardar(tratamiento);
            return new ResponseEntity<>(nuevoTratamiento, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @PutMapping("/{id}")
    public ResponseEntity<Tratamiento> actualizarTratamiento(@PathVariable Long id, @RequestBody Tratamiento tratamiento) {
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
    public ResponseEntity<Long> obtenerCantidadTratamientosUltimoMes() {
        long total = tratamientoServicio.contarTratamientosUltimoMes();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @GetMapping("/ultimo-mes/por-medicamento")
    public ResponseEntity<List<Object[]>> obtenerTratamientosPorMedicamentoUltimoMes() {
        List<Object[]> resultados = tratamientoServicio.contarTratamientosPorMedicamentoUltimoMes();
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

}
