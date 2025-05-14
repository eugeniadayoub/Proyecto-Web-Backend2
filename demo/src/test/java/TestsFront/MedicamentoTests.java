package TestsFront;

import org.junit.jupiter.api.*;
import java.time.Duration;
import java.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicamentoTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    void setUp() {
        System.out.println("Iniciando configuración del driver (Medicamento)...");

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless=new", "--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(60));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        System.out.println("Driver inicializado exitosamente (Medicamento)");
    }

    @AfterAll
    void tearDown() {
        /*.out.println("Cerrando el driver (Medicamento)...");
        if (driver != null) {
            driver.quit();
            System.out.println("Driver cerrado exitosamente (Medicamento)");
        }*/
    }

    @Test
    @Order(1)
    @Timeout(600)
    void suministroMedicamentoYverificacion() {
        String cedulaClienteExistente = "1000043";
        String nombreMascotaExistente = "Bella";
        String idMedicamentoExistente = "11";
        String nombreMedicamentoExistente = "ACOLAN";
        String veterinarioCedula = "100100100";
        String veterinarioPassword = "QjGGlGuM";
        String adminUsuario = "admin";
        String adminPassword = "admin123";

        // Login como veterinario
        System.out.println("Iniciando login como veterinario...");
        driver.get("http://localhost:4200/login/veterinario");
        driver.findElement(By.name("cedula")).sendKeys(veterinarioCedula);
        driver.findElement(By.name("password")).sendKeys(veterinarioPassword);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("veterinario-dashboard"));

        // Ir a registrar tratamiento
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.registrar-tratamiento"))).click();
        wait.until(ExpectedConditions.urlContains("/tratamientos"));

        // Seleccionar mascota
        By mascotaOption = By.xpath("//select[@name='mascota']//option[contains(text(), '" + nombreMascotaExistente + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("seleccionar-mascota"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(mascotaOption)).click();

        // Seleccionar medicamento
        By medicamentoOption = By.xpath("//select[@name='medicamento']//option[contains(text(), '" + nombreMedicamentoExistente + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(medicamentoOption)).click();

        // Completar el formulario
        // Obtener la fecha actual
        LocalDate fechaHoy = LocalDate.now();
        String dia = String.format("%02d", fechaHoy.getDayOfMonth()); // ej: "05"
        String mes = String.format("%02d", fechaHoy.getMonthValue()); // ej: "04"
        String anio = String.valueOf(fechaHoy.getYear());             // ej: "2025"

        // Enviar la fecha como 3 campos separados
        driver.findElement(By.name("fecha")).sendKeys(dia);
        driver.findElement(By.name("fecha")).sendKeys(mes);
        driver.findElement(By.name("fecha")).sendKeys(anio);
        driver.findElement(By.name("descripcion")).sendKeys("Tratamiento de prueba Selenium");
        driver.findElement(By.name("cantidad")).sendKeys("1");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/veterinario-dashboard"));
        System.out.println("Tratamiento registrado exitosamente.");

        // Login como admin
        driver.get("http://localhost:4200/login/admin");
        driver.findElement(By.name("usuario")).sendKeys(adminUsuario);
        driver.findElement(By.name("password")).sendKeys(adminPassword);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/dashboard-admin"));

        // Verificar ganancias
        By gananciasSelector = By.xpath("//h3[normalize-space()='Ganancias Totales']/following-sibling::p[@class='kpi-number']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(gananciasSelector));
        String gananciasTexto = driver.findElement(gananciasSelector).getText();
        double ganancias = Double.parseDouble(gananciasTexto.replaceAll("[^\\d.]", ""));
        System.out.println("Ganancias totales: " + gananciasTexto + " (parsed: " + ganancias + ")");
        Assertions.assertTrue(ganancias > 0, "Las ganancias deberían ser mayores que 0.");

        // Verificar cantidad vendida de medicamento
        By cantidadVendidaSelector = By.xpath(
            "//h3[contains(text(), 'Tratamientos por Medicamento')]/following-sibling::table" +
            "//td[1][normalize-space()='" + nombreMedicamentoExistente + "']/following-sibling::td[1]"
        );
        wait.until(ExpectedConditions.visibilityOfElementLocated(cantidadVendidaSelector));
        String cantidadText = driver.findElement(cantidadVendidaSelector).getText();
        int cantidadVendida = Integer.parseInt(cantidadText);
        System.out.println("Cantidad vendida de " + nombreMedicamentoExistente + ": " + cantidadVendida);
        Assertions.assertTrue(cantidadVendida > 0, "La cantidad vendida debería ser mayor que 0.");
    }
}
