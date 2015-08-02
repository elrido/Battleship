/**
 * Battleship game implemented with Swing UI in Java
 * Copyright (C) 2015 Simon Rupf <simon@rupf.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.rupf.battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class Battleship1DGame {
	public static void main (String[] args) {
		int guessCounter = 0;
		int size = 7;
		Scanner reader = new Scanner(System.in);
		
		Battleship1D game = new Battleship1D(size);
		int start = (int) (Math.random() * 5);
		ArrayList<Integer> location = new ArrayList<Integer>();
		location.add(start);
		location.add(++start);
		location.add(++start);
		game.setLocation(location);

		boolean swims = true;
		while (swims == true) {
			System.out.print("Please enter a number between 0 and " + size + ": ");
			String result = game.bomb((Integer) reader.nextInt());
			System.out.println(result);
			++guessCounter;
			if (result.equals("sunk")) {
				System.out.println("Ship destroyed. You win. game over");
				System.out.println("You needed " + guessCounter + " tries.");
				swims = false;
			}
		}
	}
}
