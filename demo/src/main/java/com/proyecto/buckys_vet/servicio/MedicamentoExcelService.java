package com.proyecto.buckys_vet.servicio;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.proyecto.buckys_vet.entidad.Medicamento;
import com.proyecto.buckys_vet.repositorio.MedicamentoRepositorio;

@Service
public class MedicamentoExcelService {

    private static final Logger logger = LoggerFactory.getLogger(MedicamentoExcelService.class);

    @Autowired
    private MedicamentoRepositorio medicamentoRepository;

    public void importarDesdeExcel(String nombreArchivo) {
        logger.info("⏳ Cargando archivo Excel: " + nombreArchivo);

        try (InputStream fis = new ClassPathResource(nombreArchivo).getInputStream();
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet hoja = workbook.getSheetAt(0);
            List<Medicamento> medicamentos = new ArrayList<>();

            for (int i = 1; i <= hoja.getLastRowNum(); i++) {
                Row fila = hoja.getRow(i);
                if (fila == null) continue;

                try {
                    String nombre = obtenerStringDeCelda(fila, 0);
                    double precioCompra = obtenerDoubleDeCelda(fila, 1);
                    double precioVenta = obtenerDoubleDeCelda(fila, 2);
                    int unidadesDisponibles = (int) obtenerDoubleDeCelda(fila, 3);
                    int unidadesVendidas = (int) obtenerDoubleDeCelda(fila, 4);

                    if (nombre != null && precioCompra > 0 && precioVenta > 0) {
                        Medicamento medicamento = new Medicamento(
                                nombre, precioCompra, precioVenta, unidadesDisponibles, unidadesVendidas
                        );
                        medicamentos.add(medicamento);
                    } else {
                        logger.warn("⚠️ Fila " + i + " tiene datos inválidos, se omite.");
                    }

                } catch (Exception filaError) {
                    logger.error("⚠️ Error en fila " + i + ": " + filaError.getMessage());
                }
            }

            if (!medicamentos.isEmpty()) {
                medicamentoRepository.saveAll(medicamentos);
                logger.info("Importación de medicamentos completada. Se guardaron " + medicamentos.size() + " medicamentos.");
            } else {
                logger.warn("No se encontraron medicamentos válidos para importar.");
            }

        } catch (Exception e) {
            logger.error("Error al leer el archivo Excel: " + e.getMessage(), e);
        }
    }

    private String obtenerStringDeCelda(Row fila, int columna) {
        try {
            if (fila.getCell(columna) != null) {
                return fila.getCell(columna).getStringCellValue();
            }
        } catch (Exception e) {
            logger.error("Error al leer la celda de texto en la columna " + columna, e);
        }
        return null;
    }

    private double obtenerDoubleDeCelda(Row fila, int columna) {
        try {
            if (fila.getCell(columna) != null) {
                return fila.getCell(columna).getNumericCellValue();
            }
        } catch (Exception e) {
            logger.error("Error al leer la celda numérica en la columna " + columna, e);
        }
        return 0;
    }
}
