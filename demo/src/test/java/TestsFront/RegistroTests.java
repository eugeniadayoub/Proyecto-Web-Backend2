package TestsFront;

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
        options.addArguments("--start-maximized", "--disable-notifications", "--remote-debugging-port=9222");
        options.addArguments("--verbose", "--log-level=0", "--enable-logging", "--v=1");
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu",
                             "--disable-extensions", "--disable-software-rasterizer",
                             "--ignore-certificate-errors", "--allow-running-insecure-content");
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
    void registroClienteYmascota() throws InterruptedException {
        // Login veterinario
        driver.get("http://localhost:4200/login/veterinario");
        driver.findElement(By.name("cedula")).sendKeys("100100100");
        driver.findElement(By.name("password")).sendKeys("QjGGlGuM");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("veterinario-dashboard"));
        System.out.println("Login veterinario exitoso.");

        // Navegar a lista de dueños
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.ver-duenos"))).click();
        wait.until(ExpectedConditions.urlContains("/duenos"));

        // Recorrer la lista hacia abajo (scroll al final)
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(500);

        // Volver arriba (scroll al tope)
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        Thread.sleep(500);

        // Clic en Agregar
        WebElement botonAgregar = wait.until(ExpectedConditions.elementToBeClickable(By.id("Agregar")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", botonAgregar);
        wait.until(ExpectedConditions.urlContains("/crear-dueno"));

        // Llenar formulario
        // Generar cédula aleatoria de 10 dígitos
        nuevaCedulaCliente = String.valueOf(1000000000L + (long)(Math.random() * 8999999999L));

        // Generar contraseña aleatoria de 10 caracteres alfanuméricos
        nuevaPasswordCliente = generarPasswordAleatoria(10);

        String nuevoEmail = "123@diego.com";
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
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[formControlName='nombre']"))).sendKeys("Mascota Test");

        driver.findElement(By.cssSelector("select[formControlName='especie']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='Gato']"))).click();

        driver.findElement(By.cssSelector("input[formControlName='edad']")).sendKeys("2");
        driver.findElement(By.cssSelector("input[formControlName='peso']")).sendKeys("5");
        driver.findElement(By.cssSelector("input[formControlName='enfermedad']")).sendKeys("Ninguna");
        driver.findElement(By.cssSelector("input[formControlName='imagenUrl']")).sendKeys("https://www.example.com/default_pet.png");

        driver.findElement(By.cssSelector("select[formControlName='estado']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("option[value='Activo']"))).click();

        // Seleccionar dueño recién creado
        By duenoOption = By.xpath("//select[@formControlName='idDueno']//option[contains(text(),'" + nuevaCedulaCliente + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(duenoOption)).click();

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/mascotas"));

        // Login dueño
        driver.get("http://localhost:4200/login/dueno");

        WebElement campoCedula = driver.findElement(By.name("cedula"));
        campoCedula.clear();
        campoCedula.sendKeys(Keys.CONTROL + "a");
        campoCedula.sendKeys(Keys.DELETE);
        campoCedula.sendKeys(nuevaCedulaCliente);


        driver.findElement(By.name("password")).sendKeys(nuevaPasswordCliente);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/duenosmascotas/"));

        // Verificación de mascota
        String nombreMascota = "Mascota Test";
        By mascotaNombre = By.xpath("//*[contains(text(), '" + nombreMascota + "') and not(self::script or self::style)]");

        WebElement mascotaElemento = wait
            .withTimeout(Duration.ofSeconds(30))
            .until(ExpectedConditions.visibilityOfElementLocated(mascotaNombre));

        Assertions.assertTrue(mascotaElemento.getText().contains(nombreMascota),
                "El nombre de la mascota no se encontró visiblemente en el DOM.");

        System.out.println("Verificación completa del flujo de registro.");

    }

    private String generarPasswordAleatoria(int longitud) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            int index = (int) (Math.random() * caracteres.length());
            password.append(caracteres.charAt(index));
        }
        return password.toString();
    }    
}


