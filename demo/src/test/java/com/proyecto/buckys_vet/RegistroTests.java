package com.proyecto.buckys_vet;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistroTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private String nuevaCedulaCliente;
    private String nuevaPasswordCliente;

    @BeforeAll
    void setUp() {
        System.out.println("=== INICIO DE CONFIGURACIÓN DEL DRIVER ===");

        ChromeOptions options = new ChromeOptions();
        // Configuración básica
        options.addArguments("--start-maximized", "--disable-notifications", "--remote-debugging-port=9222");
        // Depuración
        options.addArguments("--verbose", "--log-level=0", "--enable-logging", "--v=1");
        // Estabilidad
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu",
                "--disable-extensions", "--disable-software-rasterizer",
                "--ignore-certificate-errors", "--allow-running-insecure-content");
        // Rendimiento
        options.addArguments("--disable-background-networking", "--disable-background-timer-throttling",
                "--disable-backgrounding-occluded-windows", "--disable-breakpad",
                "--disable-component-extensions-with-background-pages",
                "--disable-features=TranslateUI,BlinkGenPropertyTrees",
                "--disable-ipc-flooding-protection", "--disable-renderer-backgrounding",
                "--enable-features=NetworkService,NetworkServiceInProcess");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(60));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        System.out.println("=== DRIVER INICIALIZADO EXITOSAMENTE ===");
    }

    @AfterAll
    void tearDown() {
        System.out.println("Cerrando el driver...");
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @Timeout(600)
    void registroClienteYmascota() {
        try {
            // Login veterinario
            driver.get("http://localhost:4200/login/veterinario");
            driver.findElement(By.name("cedula")).sendKeys("123456789");
            driver.findElement(By.name("password")).sendKeys("password_correcto");
            driver.findElement(By.cssSelector("button[type='submit']")).click();
            wait.until(ExpectedConditions.urlContains("veterinario-dashboard"));
            System.out.println("Login veterinario exitoso.");

            // Navegar a lista de dueños
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.ver-duenos"))).click();
            wait.until(ExpectedConditions.urlContains("/duenos"));

            // Crear nuevo dueño
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[routerLink='/crear-dueno']")))
                    .click();
            wait.until(ExpectedConditions.urlContains("/crear-dueno"));

            nuevaCedulaCliente = "cliente" + System.currentTimeMillis();
            String nuevoEmail = "cliente" + System.currentTimeMillis() + "@test.com";
            nuevaPasswordCliente = "pass" + System.currentTimeMillis();
            String imagenCliente = "https://www.example.com/default_user.png";

            driver.findElement(By.cssSelector("input[formControlName='nombre']")).sendKeys("Cliente Nuevo Test");
            driver.findElement(By.cssSelector("input[formControlName='cedula']")).sendKeys(nuevaCedulaCliente);
            driver.findElement(By.cssSelector("input[formControlName='email']")).sendKeys(nuevoEmail);
            driver.findElement(By.cssSelector("input[formControlName='telefono']")).sendKeys("1122334455");
            driver.findElement(By.cssSelector("input[formControlName='password']")).sendKeys(nuevaPasswordCliente);
            driver.findElement(By.cssSelector("input[formControlName='imagenUrl']")).sendKeys(imagenCliente);
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            wait.until(ExpectedConditions.urlContains("/duenos"));

            // Crear mascota
            driver.get("http://localhost:4200/crear-mascota");
            wait.until(ExpectedConditions.urlContains("/crear-mascota"));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[formControlName='nombre']")))
                    .sendKeys("Mascota Test");

            driver.findElement(By.cssSelector("select[formControlName='especie']")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='Gato']"))).click();

            driver.findElement(By.cssSelector("input[formControlName='edad']")).sendKeys("2");
            driver.findElement(By.cssSelector("input[formControlName='peso']")).sendKeys("5");
            driver.findElement(By.cssSelector("input[formControlName='enfermedad']")).sendKeys("Ninguna");
            driver.findElement(By.cssSelector("input[formControlName='imagenUrl']"))
                    .sendKeys("https://www.example.com/default_pet.png");

            driver.findElement(By.cssSelector("select[formControlName='estado']")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='Activo']"))).click();

            // Seleccionar dueño recién creado
            By duenoOption = By.xpath(
                    "//select[@formControlName='idDueno']//option[contains(text(),'" + nuevaCedulaCliente + "')]");
            wait.until(ExpectedConditions.elementToBeClickable(duenoOption)).click();

            driver.findElement(By.cssSelector("button[type='submit']")).click();
            wait.until(ExpectedConditions.urlContains("/mascotas"));

            // Login dueño
            driver.get("http://localhost:4200/login/dueno");
            driver.findElement(By.name("cedula")).sendKeys(nuevaCedulaCliente);
            driver.findElement(By.name("password")).sendKeys(nuevaPasswordCliente);
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            wait.until(ExpectedConditions.urlContains("/duenosmascotas/"));

            // Verificación de mascota
            By mascotaNombre = By.xpath("//h3[contains(text(), 'Mascota Test')]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(mascotaNombre));
            String textoMascota = driver.findElement(mascotaNombre).getText();
            Assertions.assertTrue(textoMascota.contains("Mascota Test"));

            System.out.println("Verificación completa del flujo de registro.");
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Error en el flujo de prueba: " + e.getMessage());
        }
    }
}
