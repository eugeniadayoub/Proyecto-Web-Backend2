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
import com.proyecto.buckys_vet.servicio.MedicamentoServicio;
import com.proyecto.buckys_vet.servicio.TratamientoServicio;

@RestController 
@RequestMapping("/api/tratamientos")
@CrossOrigin(origins = "http://localhost:4200")
public class TratamientoController {

    @Autowired
    private TratamientoServicio tratamientoServicio;

    @Autowired
    private MedicamentoServicio medicamentoServicio;

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
            // Imprimir los datos recibidos para verificar
            System.out.println("Tratamiento recibido: " + tratamiento);
            
            // Validar los campos antes de guardar
            if (tratamiento.getFecha() == null || tratamiento.getDescripcion() == null || tratamiento.getCantidad() <= 0 ||
                tratamiento.getMascota() == null || tratamiento.getVeterinario() == null || tratamiento.getMedicamento() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Si algún campo está vacío
            }

            // Guardar el tratamiento
            Tratamiento nuevoTratamiento = tratamientoServicio.guardar(tratamiento);
            return new ResponseEntity<>(nuevoTratamiento, HttpStatus.CREATED);
        } catch (Exception e) {
            // Registrar el error para obtener más detalles
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Si ocurre un error inesperado
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

}
