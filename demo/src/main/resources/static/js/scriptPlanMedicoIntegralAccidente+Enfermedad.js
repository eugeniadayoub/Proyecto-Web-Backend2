document.addEventListener("DOMContentLoaded", function () {
    function scrollToContact(event) {
        event.preventDefault(); // Evita el salto inmediato

        let target = document.querySelector("#contacto");
        let headerOffset = document.querySelector(".navbar").offsetHeight; // Altura del header
        let elementPosition = target.getBoundingClientRect().top + window.scrollY;
        let offsetPosition = elementPosition - headerOffset - 20; // Ajuste extra de 20px

        window.scrollTo({
            top: offsetPosition,
            behavior: "smooth"
        });
    }

    // Asignar evento a ambos botones si existen en el DOM
    let boton1 = document.querySelector("#boton-contacto");
    let boton2 = document.querySelector("#boton-contactar");

    if (boton1) {
        boton1.addEventListener("click", scrollToContact);
    }

    if (boton2) {
        boton2.addEventListener("click", scrollToContact);
    }
});
