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
const resultTitle = document.getElementById('result-title');
const starSign = document.getElementById('star-sign');
const horoscopeText = document.getElementById('horoscope-text');

/* ==========================================
   4. HOROSCOPE RESULT
   ========================================== */

// Checks only the month and day from the birthday input.
function showHoroscopeResult() {
    var birthdayParts = birthdayInput.value.split('-');
    var month = Number(birthdayParts[1]);
    var day = Number(birthdayParts[2]);

    if ((month === 12 && day >= 22) || (month === 1 && day <= 20)) {
        resultTitle.textContent = 'CAPRICORN';
        starSign.textContent = 'Capricorn';
        horoscopeText.textContent = 'Your patience and focus help you move forward today.';
    } else if ((month === 1 && day >= 21) || (month === 2 && day <= 19)) {
        resultTitle.textContent = 'AQUARIUS';
        starSign.textContent = 'Aquarius';
        horoscopeText.textContent = 'Your ideas stand out today. Trust your unique point of view.';
    } else if ((month === 2 && day >= 20) || (month === 3 && day <= 20)) {
        resultTitle.textContent = 'YPISCES';
        starSign.textContent = 'Pisces';
        horoscopeText.textContent = 'Listen to your intuition today. It may guide you well.';
    } else if ((month === 3 && day >= 21) || (month === 4 && day <= 19)) {
        resultTitle.textContent = 'ARIES';
        starSign.textContent = 'Aries';
        horoscopeText.textContent = 'Today is full of energy and new ideas.';
    } else if ((month === 4 && day >= 20) || (month === 5 && day <= 20)) {
        resultTitle.textContent = 'TAURUS';
        starSign.textContent = 'Taurus';
        horoscopeText.textContent = 'Stay grounded today. Slow progress still counts.';
    } else if ((month === 5 && day >= 21) || (month === 6 && day <= 20)) {
        resultTitle.textContent = 'GEMINI';
        starSign.textContent = 'Gemini';
        horoscopeText.textContent = 'A good conversation could bring you a new idea today.';
    } else if ((month === 6 && day >= 21) || (month === 7 && day <= 22)) {
        resultTitle.textContent = 'CANCER';
        starSign.textContent = 'Cancer';
        horoscopeText.textContent = 'Give yourself space today and trust what feels right.';
    } else if ((month === 7 && day >= 23) || (month === 8 && day <= 22)) {
        resultTitle.textContent = 'LEO';
        starSign.textContent = 'Leo';
        horoscopeText.textContent = 'Your confidence can open a door today.';
    } else if ((month === 8 && day >= 23) || (month === 9 && day <= 22)) {
        resultTitle.textContent = 'VIRGO';
        starSign.textContent = 'Virgo';
        horoscopeText.textContent = 'A clear plan helps you feel more in control today.';
    } else if ((month === 9 && day >= 23) || (month === 10 && day <= 22)) {
        resultTitle.textContent = 'LIBRA';
        starSign.textContent = 'Libra';
        horoscopeText.textContent = 'Balance matters today. Choose what brings you peace.';
    } else if ((month === 10 && day >= 23) || (month === 11 && day <= 22)) {
        resultTitle.textContent = 'SCORPIO';
        starSign.textContent = 'Scorpio';
        horoscopeText.textContent = 'You may notice more than others today. Use that wisely.';
    } else if ((month === 11 && day >= 23) || (month === 12 && day <= 21)) {
        resultTitle.textContent = 'SAGITTARIUS';
        starSign.textContent = 'Sagittarius';
        horoscopeText.textContent = 'A little adventure or curiosity can shift your day.';
    }
}

/* ==========================================
   5. PAGE SWITCHING
   ========================================== */

// When the reveal button is clicked, hide the homepage and show the result page.
revealButton.addEventListener('click', function () {
    if (birthdayInput.value === '') {
        return;
    }

    showHoroscopeResult();
    homepage.classList.add('hidden');
    horoscopePage.classList.remove('hidden');
});

// When the back button is clicked, hide the result page, delete the date input and show the homepage again.
backButton.addEventListener('click', function () {
    horoscopePage.classList.add('hidden');
    homepage.classList.remove('hidden');
    birthdayInput.value = '';
});
