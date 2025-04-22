document.addEventListener("DOMContentLoaded", function () {
    const images = [
        "../images/imagesLandingPage/Presentacion1.jpg",
        "../images/imagesLandingPage/presentacion2.jpg",
        "../images/imagesLandingPage/presentacion3.jpg",
        "../images/imagesLandingPage/presentacion4.jpg",
        "../images/imagesLandingPage/Presentacion5.png",
        "../images/imagesLandingPage/presentacion6.jpg",
        "../images/imagesLandingPage/Presentacion7.jpg",
        "../images/imagesLandingPage/Presentacion8.jpg",
        "../images/imagesLandingPage/presentacion9.jpg",
        "../images/imagesLandingPage/Presentacion10.jpg",
    ];

    let currentIndex = 0;
    const heroImage = document.querySelector(".hero img");

    if (!heroImage) {
        console.error("No se encontró la imagen en el DOM.");
        return;
    }

    function changeImage(index) {
        currentIndex = (index + images.length) % images.length;
        heroImage.src = images[currentIndex];
    }

    // Manejo de error en carga de imágenes
    heroImage.addEventListener("error", () => {
        console.error('Error al cargar la imagen: ${heroImage.src}');
    });

    // Cambio automático de imágenes
    setInterval(() => {
        changeImage(currentIndex + 1);
    }, 3000);
});