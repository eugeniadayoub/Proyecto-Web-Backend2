package com.proyecto.buckys_vet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.proyecto.buckys_vet.servicio.DuenoServicio;
import com.proyecto.buckys_vet.servicio.MascotaServicio;
import com.proyecto.buckys_vet.servicio.MedicamentoExcelService;

@SpringBootApplication
public class BuckysVetApplication {

    /*@Autowired
    private MedicamentoExcelService medicamentoExcelService;

    public static void main(String[] args) {
        SpringApplication.run(BuckysVetApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(DuenoServicio duenoServicio, MascotaServicio mascotaServicio) {
        return args -> {
            System.out.println("\n=== INICIALIZANDO DATOS DE PRUEBA ===");

            // Importar medicamentos desde Excel
            medicamentoExcelService.importarDesdeExcel("MEDICAMENTOS_VETERINARIA.xlsx");

        };
    }*/
}
