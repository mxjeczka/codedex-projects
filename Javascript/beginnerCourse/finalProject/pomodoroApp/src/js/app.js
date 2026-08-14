/* ==========================================
   PAGE ELEMENTS
   ========================================== */

const firstPage = document.getElementById("first-page");
const secondPage = document.getElementById("second-page");
const nextPageButton = document.getElementById("next-page-btn");
const backButton = document.getElementById("back-btn");

const minuteElement = document.querySelector(".minutes");
const secondElement = document.querySelector(".seconds");
const messageElement = document.querySelector(".app-message");
const startButton = document.querySelector(".start-btn");

/* ==========================================
   TIMER STATE
   ========================================== */

const sessionMinutes = Number.parseInt(minuteElement.textContent, 10);
const startSeconds = sessionMinutes * 60;

let totalSeconds = startSeconds;
let intervalId = null;
let isRunning = false;

/* ==========================================
   PAGE SWITCHING
   ========================================== */

function showFirstPage() {
    secondPage.classList.add("hidden");
    firstPage.classList.remove("hidden");
}

function showSecondPage() {
    firstPage.classList.add("hidden");
    secondPage.classList.remove("hidden");
}

/* ==========================================
   TIMER DISPLAY
   ========================================== */

function formatSeconds(seconds) {
    return seconds < 10 ? `0${seconds}` : seconds;
}

function renderTime() {
    const safeSeconds = Math.max(totalSeconds, 0);
    const minutes = Math.floor(safeSeconds / 60);
    const seconds = safeSeconds % 60;

    minuteElement.textContent = minutes;
    secondElement.textContent = formatSeconds(seconds);
}

function showMessage(message) {
    messageElement.textContent = message;
}

/* ==========================================
   TIMER CONTROL
   ========================================== */

function startTimer() {
    if (isRunning) {
        alert("Session has already started.");
        return;
    }

    isRunning = true;
    showMessage("focus time");
    startButton.textContent = "running";
    renderTime();

    intervalId = setInterval(() => {
        totalSeconds -= 1;
        renderTime();

        if (totalSeconds <= 0) {
            completeTimer();
        }
    }, 1000);
}

function stopTimer() {
    clearInterval(intervalId);
    intervalId = null;
    isRunning = false;
}

function resetTimer() {
    stopTimer();
    totalSeconds = startSeconds;
    startButton.textContent = "start";
    showMessage("press start to begin");
    renderTime();
}

function completeTimer() {
    stopTimer();
    playBell();
    showMessage("session complete");
    startButton.textContent = "start";
    totalSeconds = startSeconds;
}

/* ==========================================
   SOUND
   ========================================== */

function playBell() {
    const bell = new Audio("./sounds/bell.wav");

    bell.play().catch(() => {
        playFallbackTone();
    });
}

function playFallbackTone() {
    const AudioContext = window.AudioContext || window.webkitAudioContext;

    if (!AudioContext) {
        return;
    }

    const audioContext = new AudioContext();
    const oscillator = audioContext.createOscillator();
    const volume = audioContext.createGain();

    oscillator.connect(volume);
    volume.connect(audioContext.destination);

    oscillator.frequency.value = 880;
    volume.gain.value = 0.1;

    oscillator.start();
    oscillator.stop(audioContext.currentTime + 0.3);
}

/* ==========================================
   EVENTS
   ========================================== */

nextPageButton.addEventListener("click", async () => {
    if (document.fonts) {
        await document.fonts.load("1em TrajanusBricks");
    }

    showSecondPage();
});

backButton.addEventListener("click", () => {
    resetTimer();
    showFirstPage();
});

startButton.addEventListener("click", () => {
    startTimer();
});

/* ==========================================
   APP START
   ========================================== */

renderTime();
