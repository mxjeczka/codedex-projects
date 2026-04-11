# 🐉 Drakar – Dragon Pet Simulator
```
        ,     \    /      ,        
       / \    )\__/(     / \       
      /   \  (_\  /_)   /   \      
 ____/_____\__\@  @/___/_____\____ 
|             |\../|              |
|              \VV/               |
|             Drakar         
|_________________________________|
 |    /\ /      \\       \ /\    | 
 |  /   V        ))       V   \  | 
 |/     `       //        '     \| 
 `              V                '
```
This project is a console-based pet simulator developed as part of a Java checkpoint project on Codedex.

In this mini-game, you take care of a dragon named **Drakar**, managing his needs and interacting with him through simple commands.

## 📖 Description

Drakar is a dragon from the Fire Nation who loves flying and breathing fire 🔥.
However, he gets hungry and tired quickly — it's your job to keep him balanced!

The game runs entirely in the console and allows the player to choose different actions that affect Drakar's stats.

## 🎮 Features

* Interactive console input using `Scanner`
* ASCII art dragon for visual flair 🐲
* Continuous game loop until the player quits
* Dynamic stat system affected by player choices

## 📊 Stats System

Drakar has three main attributes:

* **Hunger**
* **Energy**
* **Firepower**

All stats start at `10` and change depending on your actions.

## 🎯 Actions

You can control Drakar using the following commands:

* `p` → Play

  * Hunger −2
  * Energy −4
  * Firepower +5

* `f` → Feed

  * Hunger +3
  * Energy −2

* `r` → Rest

  * Energy +5

* `q` → Quit the game

## 💡 What I Learned

* Working with variables and updating state
* Using loops to create continuous interaction
* Handling user input with `Scanner`
* Building simple game logic using conditionals
* Structuring a small interactive console application

## Future Improvements

* Add limits to stats (e.g. max/min values)
* Introduce win/lose conditions
* Add more actions and story elements
* Refactor into multiple classes (object-oriented design)

## Notes

This project is part of my Java learning journey on Codedex and represents one of my first interactive programs.

It demonstrates how basic programming concepts can already be used to build simple games and simulations.
