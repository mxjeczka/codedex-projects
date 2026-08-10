// Initialize the Vanta Fog background effect with custom colors and settings
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

// Function to generate individual star elements and append them to the DOM
function createStars(amount) {
    // Get the HTML container element where stars will be placed
    var starsContainer = document.getElementById('stars');

    // Loop to create the specified number of stars
    for (var i = 0; i < amount; i++) {
        // Create a new figure element for the individual star
        var tmpStar = document.createElement('figure');
        // Assign the CSS class name for styling and animation
        tmpStar.className = "star";

        // Assign random top and left coordinates to position stars across the screen
        tmpStar.style.top = (Math.random() * 100) + '%';
        tmpStar.style.left = (Math.random() * 100) + '%';

        // Apply a random delay and duration to make the twinkling look natural
        tmpStar.style.animationDelay = (Math.random() * 2) + 's';
        tmpStar.style.animationDuration = (Math.random() * 1.5 + 0.5) + 's';

        // Append the newly created star element into the main container
        starsContainer.appendChild(tmpStar);
    }
}

// Generate the CSS twinkling stars on page startup
createStars(100);