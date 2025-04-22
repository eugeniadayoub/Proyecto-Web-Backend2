document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector(".formulario-banos form");
    
    form.addEventListener("submit", function(event) {
        event.preventDefault();
        alert("¡Tu cita ha sido agendada con éxito! Nos pondremos en contacto contigo.");
        form.reset();
    });
});

function redireccionarFormulario() {
    let formulario = document.querySelector("#formulario-banos"); // ID del formulario
    let offset = -130; // Ajuste para que suba más arriba

    if (formulario) {
        let posicion = formulario.getBoundingClientRect().top + window.scrollY + offset;
        window.scrollTo({ top: posicion, behavior: "smooth" });
    }
}

