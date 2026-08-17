/* ==========================================
   PAGE ELEMENTS
   ========================================== */

const minuteElement = document.querySelector(".minutes");
const secondElement = document.querySelector(".seconds");
const startButton = document.querySelector(".start-btn");
const stopButton = document.querySelector(".stop-btn");
const resetButton = document.querySelector(".reset-btn");
const customMinutesInput = document.getElementById("custom-minutes");
const musicButton = document.getElementById("music-btn");

const bgVideo = document.getElementById("bgVideo");
const backgroundMusic = document.getElementById("backgroundMusic");

/* ==========================================
   TIMER STATE
   ========================================== */

let selectedMinutes = Number.parseInt(minuteElement.textContent, 10);

let totalSeconds = selectedMinutes * 60;
let intervalId = null;
let isRunning = false;
let isMusicOn = false;

const normalMusicVolume = 1;
const loweredMusicVolume = 0.25;

/* ==========================================
   VIDEO CONTROL
   ========================================== */

/* Show the page after the background video is ready. */
function showPage() {
    bgVideo.classList.add("is-visible");
    document.body.classList.remove("is-loading");
}

/* Keep the looping background video running quietly. */
function playBackgroundVideo() {
    bgVideo.muted = true;
    bgVideo.loop = true;
    bgVideo.play().catch(() => {
        showPage();
    });
}

/* Wait for the background video before showing the page. */
function loadBackgroundVideo() {
    if (bgVideo.readyState >= 3) {
        showPage();
        playBackgroundVideo();
        return;
    }

    bgVideo.addEventListener("canplay", () => {
        showPage();
        playBackgroundVideo();
    }, {once: true});

    bgVideo.addEventListener("error", () => {
        showPage();
    }, {once: true});

    setTimeout(() => {
        showPage();
    }, 3000);

    bgVideo.load();
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

/* Lock or unlock the custom minutes input. */
function setCustomMinutesDisabled(isDisabled) {
    customMinutesInput.disabled = isDisabled;
}

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
    setCustomMinutesDisabled(true);
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
    setCustomMinutesDisabled(false);
    startButton.textContent = "resume";
}

/* Reset the countdown to the selected session length. */
function resetTimer() {
    clearInterval(intervalId);
    intervalId = null;
    isRunning = false;
    setCustomMinutesDisabled(false);
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

/* Update the music button icon and label. */
function updateMusicButton() {
    musicButton.classList.toggle("is-music-on", isMusicOn);
    musicButton.setAttribute("aria-label", isMusicOn ? "Turn music off" : "Turn music on");
}

/* Start the background music from the beginning. */
function turnMusicOn() {
    backgroundMusic.pause();
    backgroundMusic.currentTime = 0;
    backgroundMusic.loop = true;
    backgroundMusic.volume = normalMusicVolume;
    isMusicOn = true;
    updateMusicButton();

    backgroundMusic.play().catch(() => {
        isMusicOn = false;
        updateMusicButton();
    });
}

/* Turn the background music off and reset it. */
function turnMusicOff() {
    backgroundMusic.pause();
    backgroundMusic.currentTime = 0;
    isMusicOn = false;
    updateMusicButton();
}

/* Switch the background music on or off. */
function toggleMusic() {
    if (isMusicOn) {
        turnMusicOff();
        return;
    }

    turnMusicOn();
}

/* Lower the background music while the ringtone plays. */
function lowerBackgroundMusic() {
    if (!isMusicOn || backgroundMusic.paused) {
        return;
    }

    backgroundMusic.volume = loweredMusicVolume;
}

/* Restore the background music volume. */
function restoreBackgroundMusic() {
    backgroundMusic.volume = normalMusicVolume;
}

/* Play the ringtone, or use a generated tone if the file cannot play.
* Ringtone: "Ringtone 030" by Universfield, found and downloaded from Pixabay.
* Source: https://pixabay.com/de/sound-effects/musical-ringtone-030-437513/ */
function playBell() {
    const bell = new Audio("./resources/music/universfield-ringtone-023-376906.mp3");

    lowerBackgroundMusic();
    bell.addEventListener("ended", restoreBackgroundMusic, {once: true});
    bell.addEventListener("error", restoreBackgroundMusic, {once: true});

    bell.play().catch(() => {
        restoreBackgroundMusic();
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

musicButton.addEventListener("click", () => {
    toggleMusic();
});

/* ==========================================
   APP START
   ========================================== */

loadBackgroundVideo();
renderTime();
