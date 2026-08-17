/* ==========================================
   PAGE ELEMENTS
   ========================================== */

const firstPage = document.getElementById("first-page");
const secondPage = document.getElementById("second-page");
const nextPageButton = document.getElementById("next-page-btn");
const backButton = document.getElementById("back-btn");

const minuteElement = document.querySelector(".minutes");
const secondElement = document.querySelector(".seconds");
const startButton = document.querySelector(".start-btn");
const stopButton = document.querySelector(".stop-btn");
const resetButton = document.querySelector(".reset-btn");
const customMinutesInput = document.getElementById("custom-minutes");

const openingVideo = document.getElementById("openingVideo");
const bgVideo = document.getElementById("bgVideo");

/* ==========================================
   TIMER STATE
   ========================================== */

let selectedMinutes = Number.parseInt(minuteElement.textContent, 10);

let totalSeconds = selectedMinutes * 60;
let intervalId = null;
let isRunning = false;

/* ==========================================
   PAGE SWITCHING
   ========================================== */

/* Show the intro page and hide the timer page. */
function showFirstPage() {
    secondPage.classList.add("hidden");
    firstPage.classList.remove("hidden");
}

/* Show the timer page and hide the intro page. */
function showSecondPage() {
    firstPage.classList.add("hidden");
    secondPage.classList.remove("hidden");
}

/* ==========================================
   VIDEO CONTROL
   ========================================== */

/* Make the background video visible and start playback. */
function showBackgroundVideo() {
    bgVideo.classList.add("is-visible");
    playBackgroundVideo();
}

/* Stop and hide the background video. */
function hideBackgroundVideo() {
    bgVideo.pause();
    bgVideo.currentTime = 0;
    bgVideo.classList.remove("is-visible");
}

/* Reset the opening video to its first visible frame. */
function resetOpeningVideo() {
    openingVideo.pause();
    openingVideo.currentTime = 0;
    openingVideo.classList.remove("fade-out");
    openingVideo.classList.add("is-visible");
}

/* Fade from the opening video into the timer page. */
function fadeToSecondPage() {
    showBackgroundVideo();
    showSecondPage();
    openingVideo.classList.add("fade-out");
}

/* Play the opening video before showing the timer page. */
async function playOpeningVideo() {
    nextPageButton.disabled = true;
    firstPage.classList.add("hidden");
    openingVideo.currentTime = 0;
    openingVideo.classList.remove("fade-out");
    openingVideo.classList.add("is-visible");

    try {
        await openingVideo.play();
    } catch {
        fadeToSecondPage();
        nextPageButton.disabled = false;
        return;
    }

    const fadeEarlyStart = 1.0;
    let fadeTriggered = false;

    const checkTime = () => {
        const timeLeft = openingVideo.duration - openingVideo.currentTime;

        if (timeLeft <= fadeEarlyStart && !fadeTriggered) {
            fadeTriggered = true;
            openingVideo.classList.add("fade-out");
            showBackgroundVideo();
        }
    };

    openingVideo.addEventListener("timeupdate", checkTime);

    openingVideo.addEventListener("ended", () => {
        openingVideo.removeEventListener("timeupdate", checkTime);
        showSecondPage();
        nextPageButton.disabled = false;
    }, { once: true });
}

/* Keep the looping background video running quietly. */
function playBackgroundVideo() {
    bgVideo.muted = true;
    bgVideo.loop = true;
    bgVideo.play().catch(() => {});
}

/* ==========================================
   TIMER DISPLAY
   ========================================== */

/* Add a leading zero to single-digit seconds. */
function formatSeconds(seconds) {
    return seconds < 10 ? `0${seconds}` : seconds;
}

/* Update the timer text from the current remaining seconds. */
function renderTime() {
    const safeSeconds = Math.max(totalSeconds, 0);
    const minutes = Math.floor(safeSeconds / 60);
    const seconds = safeSeconds % 60;

    minuteElement.textContent = minutes;
    secondElement.textContent = formatSeconds(seconds);
}

/* Keep custom minutes inside the supported range. */
function clampMinutes(minutes) {
    if (Number.isNaN(minutes)) {
        return selectedMinutes;
    }

    return Math.min(Math.max(minutes, 1), 59);
}

/* Save the selected session length and reset the timer. */
function setSessionMinutes(minutes) {
    selectedMinutes = clampMinutes(minutes);
    customMinutesInput.value = selectedMinutes;
    resetTimer();
}


/* ==========================================
   TIMER CONTROL
   ========================================== */

/* Start or resume the countdown. */
function startTimer() {
    if (isRunning) {
        alert("Session has already started.");
        return;
    }

    if (totalSeconds <= 0) {
        totalSeconds = selectedMinutes * 60;
    }

    isRunning = true;
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

/* Pause the countdown without resetting the time. */
function stopTimer() {
    if (!intervalId) {
        return;
    }

    clearInterval(intervalId);
    intervalId = null;
    isRunning = false;
    startButton.textContent = "resume";
}

/* Reset the countdown to the selected session length. */
function resetTimer() {
    clearInterval(intervalId);
    intervalId = null;
    isRunning = false;
    totalSeconds = selectedMinutes * 60;
    startButton.textContent = "start";
    renderTime();
}

/* Finish the session and play the completion sound. */
function completeTimer() {
    stopTimer();
    playBell();
    startButton.textContent = "start";
    totalSeconds = selectedMinutes * 60;
}

/* ==========================================
   SOUND
   ========================================== */

/* Play the bell sound, or use a generated tone if the file cannot play. */
function playBell() {
    const bell = new Audio("./sounds/bell.wav");

    bell.play().catch(() => {
        playFallbackTone();
    });
}

/* Generate a short fallback tone with the Web Audio API. */
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

    await playOpeningVideo();
});

backButton.addEventListener("click", () => {
    resetTimer();
    hideBackgroundVideo();
    resetOpeningVideo();
    showFirstPage();
});

startButton.addEventListener("click", () => {
    startTimer();
});

stopButton.addEventListener("click", () => {
    stopTimer();
});

resetButton.addEventListener("click", () => {
    resetTimer();
});

customMinutesInput.addEventListener("change", () => {
    setSessionMinutes(Number.parseInt(customMinutesInput.value, 10));
});

/* ==========================================
   APP START
   ========================================== */

renderTime();
