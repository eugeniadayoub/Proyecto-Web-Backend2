package com.proyecto.buckys_vet.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.buckys_vet.entidad.Dueno;
import com.proyecto.buckys_vet.servicio.DuenoServicio;

@RestController
@RequestMapping("/api/duenos")
@CrossOrigin(origins = "http://localhost:4200")
public class DuenoController {

    @Autowired
    private DuenoServicio duenoServicio;

    @GetMapping
    public List<Dueno> listarDuenos() {
        return duenoServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Dueno obtenerDueno(@PathVariable Long id) {
        return duenoServicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<Dueno> agregarDueno(@RequestBody Dueno dueno) {
        try {
            Dueno nuevoDueno = duenoServicio.guardar(dueno); // Aquí guardas el nuevo dueño en la base de datos
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDueno); // Devuelves el dueño creado
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // En caso de error, retornas un error
        }
    }

    @PutMapping("/{id}")
    public Dueno updateDueno(@PathVariable Long id, @RequestBody Dueno dueno) {
        Dueno existente = duenoServicio.obtenerPorId(id);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dueño no encontrado");
        }

        dueno.setIdDueno(id);
        dueno.setMascotas(existente.getMascotas()); // opcional, si no se modifican desde el cliente
        return duenoServicio.update(dueno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDueno(@PathVariable Long id) {
        try {
            duenoServicio.eliminar(id);  // Llama al servicio para eliminar el dueño y sus mascotas
            return ResponseEntity.noContent().build();  // Responde con 204 No Content en caso de éxito
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Error 500 si hay un problema
        }
    }

}
        