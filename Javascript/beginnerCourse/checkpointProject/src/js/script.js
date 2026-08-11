/* ==========================================
   1. FOG BACKGROUND
   ========================================== */

// Start the Vanta fog background effect.
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

// Create small star elements and add them to the page.
function createStars(starAmount) {
    // Get the container where the stars should appear.
    var starsContainer = document.getElementById('stars');

    // Repeat this until the selected amount of stars exists.
    for (var i = 0; i < starAmount; i++) {
        // Create one star.
        var starElement = document.createElement('figure');

        // Add the CSS class so the star gets its style and animation.
        starElement.className = "star";

        // Put the star at a random place on the screen.
        starElement.style.top = (Math.random() * 100) + '%';
        starElement.style.left = (Math.random() * 100) + '%';

        // Give every star a slightly different animation timing.
        starElement.style.animationDelay = (Math.random() * 2) + 's';
        starElement.style.animationDuration = (Math.random() * 1.5 + 0.5) + 's';

        // Add the star to the star container.
        starsContainer.appendChild(starElement);
    }
}

// Create the stars when the script loads.
createStars(100);

/* ==========================================
   3. HTML ELEMENTS
   ========================================== */

// Get the button that opens the result page.
const revealButton = document.getElementById('reveal-btn');

// Get the homepage section.
const homePage = document.getElementById('home-page');

// Get the result page section.
const horoscopePage = document.getElementById('horoscope-page');

// Get the button that returns to the homepage.
const backButton = document.getElementById('back-btn');

// Get the birthday input field.
const birthdayInput = document.getElementById('birthday-input');

// Get the result title.
const resultTitle = document.getElementById('result-title');

// Get the zodiac symbol text.
const zodiacSymbol = document.getElementById('zodiac-symbol');

// Get the random horoscope text.
const horoscopeText = document.getElementById('horoscope-text');

/* ==========================================
   4. HOROSCOPE RESULT
   ========================================== */

// Wait until the zodiac icon font is ready.
function loadZodiacFont() {
    if (document.fonts) {
        return document.fonts.load('1em ZodiacFont');
    }
}

// Pick one random text from a list of possible horoscope texts.
function getRandomFortune(fortunes) {
    var randomIndex = Math.floor(Math.random() * fortunes.length);
    return fortunes[randomIndex];
}

// Check only the month and day from the birthday input.
function showHoroscopeResult() {
    var birthdayDateParts = birthdayInput.value.split('-');
    var month = Number(birthdayDateParts[1]);
    var day = Number(birthdayDateParts[2]);

    if ((month === 12 && day >= 22) || (month === 1 && day <= 20)) {
        resultTitle.textContent = 'CAPRICORN';
        zodiacSymbol.textContent = 'A';
        horoscopeText.textContent = getRandomFortune([
            'Your patience and focus help you move forward today.',
            'A careful choice will bring you closer to your goal.',
            'Trust the steady path, even if it feels slow.'
        ]);
    } else if ((month === 1 && day >= 21) || (month === 2 && day <= 19)) {
        resultTitle.textContent = 'AQUARIUS';
        zodiacSymbol.textContent = 'B';
        horoscopeText.textContent = getRandomFortune([
            'Your ideas stand out today. Trust your unique point of view.',
            'A fresh thought could change how you see the day.',
            'Someone may appreciate your honesty more than you expect.'
        ]);
    } else if ((month === 2 && day >= 20) || (month === 3 && day <= 20)) {
        resultTitle.textContent = 'PISCES';
        zodiacSymbol.textContent = 'C';
        horoscopeText.textContent = getRandomFortune([
            'Listen to your intuition today. It may guide you well.',
            'A quiet moment may show you what really matters.',
            'Let your imagination lead, but keep one foot on the ground.'
        ]);
    } else if ((month === 3 && day >= 21) || (month === 4 && day <= 19)) {
        resultTitle.textContent = 'ARIES';
        zodiacSymbol.textContent = 'D';
        horoscopeText.textContent = getRandomFortune([
            'Today is full of energy and new ideas.',
            'Take the first step before overthinking the whole path.',
            'Your courage can turn a small chance into something bigger.'
        ]);
    } else if ((month === 4 && day >= 20) || (month === 5 && day <= 20)) {
        resultTitle.textContent = 'TAURUS';
        zodiacSymbol.textContent = 'E';
        horoscopeText.textContent = getRandomFortune([
            'Stay grounded today. Slow progress still counts.',
            'A calm decision will help you more than a rushed one.',
            'Comfort is good, but a small change may be worth it.'
        ]);
    } else if ((month === 5 && day >= 21) || (month === 6 && day <= 20)) {
        resultTitle.textContent = 'GEMINI';
        zodiacSymbol.textContent = 'F';
        horoscopeText.textContent = getRandomFortune([
            'A good conversation could bring you a new idea today.',
            'Curiosity will lead you somewhere useful.',
            'Say what you mean clearly and someone will understand.'
        ]);
    } else if ((month === 6 && day >= 21) || (month === 7 && day <= 22)) {
        resultTitle.textContent = 'CANCER';
        zodiacSymbol.textContent = 'G';
        horoscopeText.textContent = getRandomFortune([
            'Give yourself space today and trust what feels right.',
            'A small act of care can change the mood of your day.',
            'Protect your energy, but do not close yourself off completely.'
        ]);
    } else if ((month === 7 && day >= 23) || (month === 8 && day <= 22)) {
        resultTitle.textContent = 'LEO';
        zodiacSymbol.textContent = 'H';
        horoscopeText.textContent = getRandomFortune([
            'Your confidence can open a door today.',
            'Let yourself be seen, but stay generous with others.',
            'A bold choice may bring the attention you need.'
        ]);
    } else if ((month === 8 && day >= 23) || (month === 9 && day <= 22)) {
        resultTitle.textContent = 'VIRGO';
        zodiacSymbol.textContent = 'I';
        horoscopeText.textContent = getRandomFortune([
            'A clear plan helps you feel more in control today.',
            'Fix one small detail and the bigger picture improves.',
            'Your effort is noticed, even when nobody says it out loud.'
        ]);
    } else if ((month === 9 && day >= 23) || (month === 10 && day <= 22)) {
        resultTitle.textContent = 'LIBRA';
        zodiacSymbol.textContent = 'J';
        horoscopeText.textContent = getRandomFortune([
            'Balance matters today. Choose what brings you peace.',
            'A fair answer may be better than a fast one.',
            'Beauty, kindness, or honesty could reset your day.'
        ]);
    } else if ((month === 10 && day >= 23) || (month === 11 && day <= 22)) {
        resultTitle.textContent = 'SCORPIO';
        zodiacSymbol.textContent = 'K';
        horoscopeText.textContent = getRandomFortune([
            'You may notice more than others today. Use that wisely.',
            'Trust your depth, but do not carry everything alone.',
            'A hidden answer may become clearer if you stay patient.'
        ]);
    } else if ((month === 11 && day >= 23) || (month === 12 && day <= 21)) {
        resultTitle.textContent = 'SAGITTARIUS';
        zodiacSymbol.textContent = 'L';
        horoscopeText.textContent = getRandomFortune([
            'A little adventure or curiosity can shift your day.',
            'Say yes to learning something new today.',
            'Your optimism can help you move past a small obstacle.'
        ]);
    }
}

/* ==========================================
   5. PAGE SWITCHING
   ========================================== */

// Show the result page when the reveal button is clicked.
revealButton.addEventListener('click', async function () {
    if (birthdayInput.value === '') {
        return;
    }

    await loadZodiacFont();
    showHoroscopeResult();
    homePage.classList.add('hidden');
    horoscopePage.classList.remove('hidden');
});

// Return to the homepage and clear the birthday input.
backButton.addEventListener('click', function () {
    horoscopePage.classList.add('hidden');
    homePage.classList.remove('hidden');
    birthdayInput.value = '';
});
