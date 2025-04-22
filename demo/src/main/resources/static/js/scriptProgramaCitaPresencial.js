document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector(".formulario-cita");
    
    form.addEventListener("submit", function(event) {
        event.preventDefault();
        alert("¡Tu cita ha sido agendada! Pronto nos pondremos en contacto contigo.");
        form.reset();
    });
});
