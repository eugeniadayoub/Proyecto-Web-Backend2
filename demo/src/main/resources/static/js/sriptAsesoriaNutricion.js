function redireccionarFormularioNutricion() {
    let formulario = document.querySelector("#formulario-nutricion"); // ID del formulario de nutrición
    let offset = -150; // Ajuste para que suba más arriba y sea visible completamente

    if (formulario) {
        let posicion = formulario.getBoundingClientRect().top + window.scrollY + offset;
        window.scrollTo({ top: posicion, behavior: "smooth" });
    }
}

// Asignar el evento al botón
document.addEventListener("DOMContentLoaded", function() {
    let boton = document.querySelector(".boton-asesoria"); // Clase del botón
    if (boton) {
        boton.addEventListener("click", function(event) {
            event.preventDefault(); // Evita el comportamiento predeterminado del enlace
            redireccionarFormularioNutricion();
        });
    }
});

// Asignar el evento al botón
document.addEventListener("DOMContentLoaded", function() {
    let boton = document.querySelector(".boton-asesoria1"); // Clase del botón
    if (boton) {
        boton.addEventListener("click", function(event) {
            event.preventDefault(); // Evita el comportamiento predeterminado del enlace
            redireccionarFormularioNutricion();
        });
    }
});

function mostrarTabla(tipo) {
    let tablaPerros = document.getElementById("tablaPerros");
    let tablaGatos = document.getElementById("tablaGatos");
    let btnPerros = document.getElementById("btnPerros");
    let btnGatos = document.getElementById("btnGatos");

    if (tipo === 'perros') {
        tablaPerros.classList.remove("tabla-oculta");
        tablaPerros.classList.add("tabla-mostrar");
        tablaGatos.classList.remove("tabla-mostrar");
        tablaGatos.classList.add("tabla-oculta");
        btnPerros.classList.add("activo");
        btnGatos.classList.remove("activo");
    } else {
        tablaGatos.classList.remove("tabla-oculta");
        tablaGatos.classList.add("tabla-mostrar");
        tablaPerros.classList.remove("tabla-mostrar");
        tablaPerros.classList.add("tabla-oculta");
        btnGatos.classList.add("activo");
        btnPerros.classList.remove("activo");
    }
}


