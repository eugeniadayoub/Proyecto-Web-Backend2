document.addEventListener("DOMContentLoaded", function () {
    // Seleccionar el botón de más información
    const botonMasInformacion = document.querySelector(".boton-mas-informacion");

    // Agregar el evento de clic al botón
    botonMasInformacion.addEventListener("click", function (event) {
        event.preventDefault(); // Evita el comportamiento por defecto del enlace

        // Seleccionar el formulario
        const formulario = document.querySelector("#formulario-pacientes-cronicos");

        // Verificar si el formulario existe antes de intentar desplazar
        if (formulario) {
            // Desplazar al formulario de manera suave
            formulario.scrollIntoView({ behavior: "smooth", block: "start" });

            // Esperar un poco y hacer un pequeño ajuste para evitar que quede cortado
            setTimeout(() => {
                window.scrollBy(0, -850); // Ajustar la posición 50px más arriba
            }, 500); // Se ejecuta después de medio segundo (500ms)
        }
    });
});

let indice = 0;
const cantidadImagenes = 8; // Número total de imágenes
const tamañoImagen = 180; // 150px imagen + 30px margen (15px por cada lado)
const carrusel = document.querySelector(".carrusel-items");

function moverCarrusel(direccion) {
    indice += direccion;

    if (indice >= cantidadImagenes) {
        indice = 0;
    } else if (indice < 0) {
        indice = cantidadImagenes - 1;
    }

    // Mueve el carrusel horizontalmente
    carrusel.style.transform = `translateX(${-indice * tamañoImagen}px)`;
}

// Auto-scroll cada 3 segundos
setInterval(() => moverCarrusel(1), 3000);