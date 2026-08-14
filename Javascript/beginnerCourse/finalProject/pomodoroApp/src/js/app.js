// ==============================
// Class 1: Timer
// This class stores and controls the countdown time.
// ==============================
class Timer {
    // SECTION: Setup

    // Creates a new timer with the given number of minutes.
    constructor(minutes) {
        this.startMinutes = minutes;
        this.totalSeconds = minutes * 60;
        this.intervalId = null;
        this.isRunning = false;
    }

    // SECTION: Timer control

    // Starts the countdown and calls the update functions every second.
    start(onTick, onComplete) {
        if (this.isRunning) {
            return false;
        }

        this.isRunning = true;
        onTick(this.getTime());

        this.intervalId = setInterval(() => {
            this.totalSeconds -= 1;
            onTick(this.getTime());

            if (this.totalSeconds <= 0) {
                this.stop();
                onComplete();
            }
        }, 1000);

        return true;
    }

    // Stops the countdown and clears the interval.
    stop() {
        clearInterval(this.intervalId);
        this.intervalId = null;
        this.isRunning = false;
    }

    // SECTION: Time data

    // Returns the current minutes and seconds left.
    getTime() {
        const safeSeconds = Math.max(this.totalSeconds, 0);

        return {
            minutes: Math.floor(safeSeconds / 60),
            seconds: safeSeconds % 60,
        };
    }
}

// ==============================
// Class 2: Display
// This class updates the text, button, and sound in the page.
// ==============================
class Display {
    // SECTION: Setup

    // Finds all page elements that the app needs.
    constructor() {
        this.minuteDiv = document.querySelector(".minutes");
        this.secondDiv = document.querySelector(".seconds");
        this.messageDiv = document.querySelector(".app-message");
        this.startBtn = document.querySelector(".btn-start");
        this.bell = new Audio("./sounds/bell.wav");
    }

    // SECTION: Timer text

    // Shows the current minutes and seconds in the timer.
    renderTime(time) {
        this.minuteDiv.textContent = time.minutes;
        this.secondDiv.textContent = this.formatSeconds(time.seconds);
    }

    // Adds a zero before seconds below ten.
    formatSeconds(seconds) {
        return seconds < 10 ? `0${seconds}` : seconds;
    }

    // SECTION: Message

    // Shows a short message above the timer.
    showMessage(message) {
        this.messageDiv.textContent = message;
    }

    // SECTION: Button

    // Changes the text inside the start button.
    setButtonText(text) {
        this.startBtn.textContent = text;
    }

    // SECTION: Sound

    // Plays the bell sound when the timer is done.
    playBell() {
        this.bell.play().catch(() => {
            this.playFallbackTone();
        });
    }

    // Plays a simple tone if the bell file is missing.
    playFallbackTone() {
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
}

// ==============================
// Class 3: PomodoroApp
// This class connects the timer, the display, and the button.
// ==============================
class PomodoroApp {
    // SECTION: Setup

    // Creates the app and reads the first timer value from the page.
    constructor() {
        this.display = new Display();
        this.sessionMinutes = Number.parseInt(this.display.minuteDiv.textContent, 10);
        this.timer = new Timer(this.sessionMinutes);

        this.bindEvents();
    }

    // SECTION: Button

    // Adds the click event to the start button.
    bindEvents() {
        this.display.startBtn.addEventListener("click", () => {
            this.handleStartButtonClick();
        });
    }

    // Starts the timer when the user clicks the button.
    handleStartButtonClick() {
        const started = this.timer.start(
            (time) => this.handleTimerTick(time),
            () => this.handleTimerComplete()
        );

        if (!started) {
            alert("Session has already started.");
            return;
        }

        this.display.showMessage("focus time");
        this.display.setButtonText("running");
    }

    // SECTION: Timer

    // Updates the page every time the timer changes.
    handleTimerTick(time) {
        this.display.renderTime(time);
    }

    // Resets the app state when the timer reaches zero.
    handleTimerComplete() {
        this.display.playBell();
        this.display.showMessage("session complete");
        this.display.setButtonText("start");
        this.timer = new Timer(this.sessionMinutes);
    }
}

// SECTION: App start

// Starts the Pomodoro app after the page is loaded.
new PomodoroApp();
