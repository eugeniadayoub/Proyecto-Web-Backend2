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
import org.springframework.web.server.ResponseStatusException;

import com.proyecto.buckys_vet.dto.DuenoDTO;
import com.proyecto.buckys_vet.dto.DuenoMapper;
import com.proyecto.buckys_vet.entidad.Dueno;
import com.proyecto.buckys_vet.servicio.DuenoServicio;

@RestController
@RequestMapping("/duenos")
@CrossOrigin(origins = "http://localhost:4200")
public class DuenoController {

    @Autowired
    private DuenoServicio duenoServicio;

    @Autowired
    private DuenoMapper duenoMapper;

    @GetMapping
    public List<Dueno> listarDuenos() {
        return duenoServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Dueno obtenerDueno(@PathVariable Long id) {
        return duenoServicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<DuenoDTO> agregarDueno(@RequestBody Dueno dueno) {
        try {
            Dueno nuevoDueno = duenoServicio.guardar(dueno);
            DuenoDTO duenoDTO = duenoMapper.toDTO(nuevoDueno);
            return ResponseEntity.status(HttpStatus.CREATED).body(duenoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public Dueno updateDueno(@PathVariable Long id, @RequestBody Dueno dueno) {
        Dueno existente = duenoServicio.obtenerPorId(id);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dueño no encontrado");
        }

        dueno.setIdDueno(id);
        dueno.setMascotas(existente.getMascotas());
        return duenoServicio.update(dueno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDueno(@PathVariable Long id) {
        try {
            duenoServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace(); // Esto puede ayudarte a ver la causa del error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
