import os
from pathlib import Path

import imageio.v3 as iio


# Folder where this Python file is located.
PROJECT_FOLDER = Path(__file__).parent

# This is where the finished GIF will be saved.
OUTPUT_FILE = PROJECT_FOLDER / "output" / "team.gif"

# List that contains the image files we want to use for the GIF.
filenames = [
    PROJECT_FOLDER / "images" / "image0.jpeg",
    PROJECT_FOLDER / "images" / "image1.jpeg",
    PROJECT_FOLDER / "images" / "image2.jpeg",
    PROJECT_FOLDER / "images" / "image3.jpeg",
    PROJECT_FOLDER / "images" / "image4.jpeg",
    PROJECT_FOLDER / "images" / "image5.jpeg",
    PROJECT_FOLDER / "images" / "image6.jpeg",
    PROJECT_FOLDER / "images" / "image7.jpeg",
    PROJECT_FOLDER / "images" / "image8.jpeg",
    PROJECT_FOLDER / "images" / "image9.jpeg",
    PROJECT_FOLDER / "images" / "image10.jpeg",
    PROJECT_FOLDER / "images" / "image11.jpeg",
    PROJECT_FOLDER / "images" / "image12.jpeg",
]

# This empty list will store the actual image data.
images = []

# Go through every filename, read the image, and add it to the images list.
for filename in filenames:
    images.append(iio.imread(filename))

# Create the GIF from all images.
# duration controls how long each image is shown.
# loop=0 means the GIF repeats forever.
iio.imwrite(OUTPUT_FILE, images, duration=500, loop=0)

print(f"GIF wurde erstellt: {OUTPUT_FILE}")

# Open the finished GIF automatically on Windows.
os.startfile(OUTPUT_FILE)
