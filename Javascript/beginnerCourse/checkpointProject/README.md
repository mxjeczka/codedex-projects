# 🔮 Horoscope
♈︎ ♌︎ ♐︎ ♉︎ ♍︎ ♑︎ ♊︎ ♎︎ ♒︎ ♋︎ ♏︎ ♓︎

This project is a web-based horoscope application developed as part of a beginner project on Codédex.

The original task was to create a simple program that uses a person's birth month and randomly generates a fortune. I decided to turn the idea into a small interactive horoscope website instead.

## Description

The Horoscope website lets you enter your birthday and discover your zodiac sign together with a randomly selected horoscope message.

Instead of only printing the result in the console, I wanted to make the project feel a little more magical and interactive.

The website uses a dark purple atmosphere, glowing text, stars, and a moving fog background to create a mystical feeling ✨.

Your zodiac sign is calculated from your birthday, and the website randomly chooses one of several possible fortunes for your sign.

## Features

* Interactive birthday input
* Automatic zodiac sign calculation
* Random horoscope messages for each zodiac sign
* Custom zodiac symbol font
* Animated fog background using Vanta.js
* Randomly generated twinkling stars
* Responsive layout
* Separate homepage and result page
* Back button to enter a new birthday
* Magical dark and purple visual design

## ♈ Zodiac Signs

The application supports all twelve zodiac signs:

* ♑ Capricorn
* ♒ Aquarius
* ♓ Pisces
* ♈ Aries
* ♉ Taurus
* ♊ Gemini
* ♋ Cancer
* ♌ Leo
* ♍ Virgo
* ♎ Libra
* ♏ Scorpio
* ♐ Sagittarius

Each sign has multiple possible horoscope messages, so the result can be different each time.

## How It Works

First, the user selects their birthday.

The program takes the month and day from the selected date and uses conditional statements to determine the zodiac sign.

After that, a random fortune is selected from a list of possible messages for that sign.

The result is then displayed together with the corresponding zodiac symbol.

## How to Run

You can try the project directly in your browser:

=> https://horoscope-ruby.vercel.app/

Just open the link, enter your birthday, and click **Reveal my Horoscope**.

## External Resources

This was my first time working with [Vanta.js](https://www.vantajs.com/).

I used Vanta.js to create the animated fog background and experimented with it to create the atmosphere for this project.

The animated star effect is based on a CodePen project by Jed Crowther:

=> https://codepen.io/jedcrowther/pen/wdzQvy

The original CodePen project is provided under the MIT License. I used the star effect as a starting point and adapted it for my project.

## What I Learned

* Working with HTML, CSS, and JavaScript together
* Using conditional statements to determine zodiac signs
* Working with `Math.random()` to create random results
* Handling user input from an HTML form
* Working with DOM elements and events
* Switching between different sections of a webpage
* Working with custom fonts and external libraries
* Creating animated backgrounds and visual effects
* Using Vanta.js for the first time
* Using and adapting an existing CodePen effect
* Structuring a small web project into separate files and folders

## Future Improvements

* Add more horoscope messages
* Add more detailed horoscope categories
* Add animations when revealing the result
* Add a moon or constellation background
* Add more interactive magical effects
* Improve the mobile layout

## Notes

This project started as a simple Codédex exercise about variables, control flow, and random fortunes.

I decided to expand the original idea into a small interactive website because I wanted to experiment with web design and make the project feel more personal and magical.

It is one of my first projects where I combined logic with HTML, CSS, and JavaScript to create a complete interactive experience.
