package com.proyecto.buckys_vet.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.buckys_vet.entidad.Medicamento;
import com.proyecto.buckys_vet.servicio.MedicamentoServicio;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoServicio medicamentoServicio;

    // Obtener todos los medicamentos
    @GetMapping
    public List<Medicamento> obtenerMedicamentos() {
        return medicamentoServicio.obtenerTodos();
    }

    // Obtener un medicamento por ID
    @GetMapping("/{id}")
    public Medicamento obtenerMedicamentoPorId(@PathVariable Long id) {
        return medicamentoServicio.obtenerPorId(id);
    }

    // Crear un nuevo medicamento
    @PostMapping
    public Medicamento agregarMedicamento(@RequestBody Medicamento medicamento) {
        return medicamentoServicio.guardar(medicamento);
    }

    // Eliminar un medicamento por ID
    @DeleteMapping("/{id}")
    public void eliminarMedicamento(@PathVariable Long id) {
        medicamentoServicio.eliminar(id);
    }

    // Actualizar un medicamento
    @PutMapping("/{id}")
    public Medicamento actualizarMedicamento(@PathVariable Long id, @RequestBody Medicamento medicamento) {
        medicamento.setId(id);  // Asigna el ID para asegurarse de que se actualice correctamente
        return medicamentoServicio.update(medicamento);
    }
}
