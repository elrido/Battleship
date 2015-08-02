#!/bin/sh
cd source
javac -d ../classes net/rupf/battleship/BattleshipGUI.java
cd ../classes
jar -cvmf manifest.txt ../Battleship.jar net images
