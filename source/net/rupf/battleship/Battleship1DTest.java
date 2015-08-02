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

public class Battleship1DTest {
	public static void main (String[] args) {
		try {
			Battleship1D game = new Battleship1D(7);
			ArrayList<Integer> location = new ArrayList<Integer>();
			location.add(2);
			location.add(3);
			location.add(4);
			game.setLocation(location);
		
			Integer guess = 2;
			String result = game.bomb(guess);
			System.out.println(result);
		} catch (Exception e) {
			System.err.println("Caught exception: " + e.getMessage());
		}
	}
}
