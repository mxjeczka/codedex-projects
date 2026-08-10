/* ==========================================
   1. FOG BACKGROUND
   ========================================== */

// Starts the Vanta fog background effect.
VANTA.FOG({
    el: "#fog",
    mouseControls: true,
    touchControls: true,
    gyroControls: false,
    minHeight: 500.00,
    minWidth: 500.00,
    highlightColor: 0x640186,
    midtoneColor: 0x0c0077,
    lowlightColor: 0xb30d8b,
    baseColor: 0x0,
    blurFactor: 0.7,
    zoom: 1,
    speed: 1.5
});

/* ==========================================
   2. STAR BACKGROUND
   ========================================== */

// Creates small star elements and adds them to the page.
function createStars(amount) {
    // Get the container where the stars should appear.
    var starsContainer = document.getElementById('stars');

    // Repeat this until the selected amount of stars exists.
    for (var i = 0; i < amount; i++) {
        // Create one star.
        var tmpStar = document.createElement('figure');

        // Add the CSS class so the star gets its style and animation.
        tmpStar.className = "star";

        // Put the star at a random place on the screen.
        tmpStar.style.top = (Math.random() * 100) + '%';
        tmpStar.style.left = (Math.random() * 100) + '%';

        // Give every star a slightly different animation timing.
        tmpStar.style.animationDelay = (Math.random() * 2) + 's';
        tmpStar.style.animationDuration = (Math.random() * 1.5 + 0.5) + 's';

        // Add the star to the star container.
        starsContainer.appendChild(tmpStar);
    }
}

// Create the stars when the page loads.
createStars(100);

/* ==========================================
   3. HTML ELEMENTS
   ========================================== */

const revealButton = document.getElementById('reveal-btn');
const homepage = document.getElementById('home-page');
const horoscopePage = document.getElementById('horoscope-page');
const backButton = document.getElementById('back-btn');
const birthdayInput = document.getElementById('birthday-input');

/* ==========================================
   4. PAGE SWITCHING
   ========================================== */

// When the reveal button is clicked, hide the homepage and show the result page.
revealButton.addEventListener('click', function () {
    if (birthdayInput.value === '') {
        return;
    }
    homepage.classList.add('hidden');
    horoscopePage.classList.remove('hidden');
});

// When the back button is clicked, hide the result page, delete the data input and show the homepage again.
backButton.addEventListener('click', function () {
    horoscopePage.classList.add('hidden');
    homepage.classList.remove('hidden');
    if (birthdayInput.value !== "") {
        birthdayInput.value = '';
    }
});