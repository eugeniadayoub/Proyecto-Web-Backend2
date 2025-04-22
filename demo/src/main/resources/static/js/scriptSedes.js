// Funcionalidad para mostrar y ocultar información de cada sede
function toggleSede(id) {
    let sede = document.getElementById(id);
    let todasLasSedes = document.querySelectorAll('.info-sede');
    let todasLasFlechas = document.querySelectorAll('.sede .flecha');
    
    todasLasSedes.forEach(s => {
        if (s.id !== id) {
            s.style.display = 'none';
        }
    });
    
    todasLasFlechas.forEach(f => {
        f.style.transform = 'rotate(0deg)';
    });
    
    let flecha = sede.previousElementSibling.querySelector('.flecha');
    
    if (sede.style.display === 'block') {
        sede.style.display = 'none';
        flecha.style.transform = 'rotate(0deg)';
    } else {
        sede.style.display = 'block';
        flecha.style.transform = 'rotate(180deg)';
    }
}

document.addEventListener("scroll", function () {
    let scrollPosition = window.scrollY;
    let parallaxSection = document.querySelector(".parallax-section");

    if (parallaxSection) {
        let maxScroll = document.body.scrollHeight - window.innerHeight;
        let positionY = (scrollPosition / maxScroll) * 50; // Ajusta el rango del desplazamiento
        parallaxSection.style.backgroundPositionY = `${positionY}%`;
    }
});


document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector(".formulario-cita");
    
    form.addEventListener("submit", function(event) {
        event.preventDefault();
        
        let nombre = form.nombre.value.trim();
        let apellidos = form.apellidos.value.trim();
        let email = form.email.value.trim();
        let telefono = form.telefono.value.trim();
        let mascota = form.mascota.value.trim();
        let fecha = form.fecha.value.trim();
        let motivoCita = form["motivo-cita"].value;
        let sede = form.sede.value;

        if (!nombre || !apellidos || !email || !telefono || !mascota || !fecha || !motivoCita || !sede) {
            alert("Por favor completa todos los campos.");
            return;
        }
        
        alert("Tu cita ha sido enviada correctamente. Pronto te contactaremos.");
        form.reset();
    });
});


