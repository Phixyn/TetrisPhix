# TetrisPhix - Java Tetris Clone

![Gameplay Screenshot 1](screenshots/gameplay2.png?raw=true)

> **Author:** Alpeche Pancha ([@Phixyn](https://twitter.com/Phixyn) / [phixyn.com](http://www.phixyn.com))  
> **Version:** v1.0.5  
> **Date started:** 21/11/2014  
> **Latest release:** 19/12/2014

## Introduction

**TetrisPhix** is a simple Java Tetris clone written for a University assignment. The game features some nice sprites and images, as well as a hardcore game mode and the ability to save highscores.

To play, just unzip the file. On Windows, double click the file **"TetrisPhix.jar"** to play it. If that does not work, double click the file **"tetris.bat"**. On Linux and maybe MacOS (untested) you can run the game from the terminal with `java -jar TetrisPhix.jar`.

To get rid of the game, delete the **TetrisPhix** folder and the `TetrisPhix.dat` file. The `TetrisPhix.dat` file will be stored in your home directory:

```
Windows:          C:\Users\<your-user>\TetrisPhix.dat
Linux and MacOS:  ~/TetrisPhix.dat
```

License information and a disclaimer can be found in the top level directory, under `LICENSE` and `DISCLAIMER` respectively.

## Controls

```
A - Move left
D - Move right
S - Drop piece
R - Rotate piece
P - Pause
Q - Quit/Main Menu
```
## Gameplay

- **Classic mode:** classic Tetris game with normal rules and TGM rotations (S, Z and I pieces only have 2 rotations instead of 4).
- **Hardcore mode:** Tetris game where you can only clear a line if all the blocks filling that line are the same color (i.e. a line must be filled entirely with only one color).

### Points and Levels

- Every line cleared grants 10 points.
- For every 10 lines that you clear, you will advance to the next level.
- The speed at which the pieces fall increases every level and caps at level 8.
- Every level has a different background image, up to level 10.

### Highscores

- When you lose the game, you can choose to enter your name to save your highscore.
- Highscores only save if they are higher than the current lowest high score (i.e. if the lowest score is 100, you need a score of 110 for it to be saved).

## License

TetrisPhix is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

TetrisPhix is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with TetrisPhix, located in LICENSE; If not, see [https://www.gnu.org/licenses/](https://www.gnu.org/licenses/).

## Disclaimer

TetrisPhix does not hold any rights or copyright. This program is not subject to any copyright. You may use, modify and distribute the program as long as you provide the LICENSE and DISCLAIMER files included in the top level directory.

This program was developed as part of a University assignment and is intended to be used solely for teaching, educational and demonstration purposes. No copyright infringement is intended and no monetization shall be made from this program.

Under Section 107 of the Copyright Act 1976, allowance is made for fair use for purposes such as criticism, comment, news reporting, teaching, scholarship, and research. Fair use is a use permitted by copyright statute that might otherwise be infringing. Non-profit, educational or personal use tips the balance in favor of fair use.

TetrisPhix, Phixyn and Alpeche Pancha are not affiliated with the Tetris trademark, Tetris Holding or The Tetris Company LLC.

Tetris is a registered trademark and its rightful copyright holder is the Tetris Holding, who reserves all its rights.

Tetris logos, Tetris theme song and Tetriminos are trademarks of the Tetris Holding.

The Tetris trade dress is owned by Tetris Holding and licenses are issued by The Tetris Company LLC. Game Design by Alexey Pajitnov.

- - -

![Gameplay Screenshot 2](screenshots/gameplay1.png?raw=true)