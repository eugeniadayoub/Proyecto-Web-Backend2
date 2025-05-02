package com.proyecto.buckys_vet.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.buckys_vet.entidad.Medicamento;
import com.proyecto.buckys_vet.repositorio.MedicamentoRepositorio;

@Service
public class MedicamentoServicioImpl implements MedicamentoServicio {

    @Autowired
    private MedicamentoRepositorio medicamentoRepositorio;
    
    @Override
    public List<Medicamento> obtenerTodos() {
        return medicamentoRepositorio.findAll();
    }
    
    @Override
    public Medicamento obtenerPorId(Long id) {
        return medicamentoRepositorio.findById(id).orElse(null);
    }
    
    @Override
    public Medicamento guardar(Medicamento medicamento) {
        return medicamentoRepositorio.save(medicamento);
    }
    
    @Override
    public void eliminar(Long id) {
        medicamentoRepositorio.deleteById(id);
    }
    
    @Override
    public Medicamento update(Medicamento medicamento) {
        return medicamentoRepositorio.save(medicamento);
    }

    @Override
    public Double calcularVentasTotales() {
        Double total = medicamentoRepositorio.calcularVentasTotales();
        return total != null ? total : 0.0;
    }

    @Override
    public Double calcularGananciasTotales() {
        return medicamentoRepositorio.calcularGananciasTotales();
    }

    @Override
    public List<Object[]> obtenerGananciasPorMedicamento() {
        return medicamentoRepositorio.obtenerGananciasPorMedicamento();
    }


}
