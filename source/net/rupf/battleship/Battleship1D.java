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

public class Battleship1D {

	private ArrayList<Integer> location;

	private int size = 7;

	public Battleship1D () {
	}
	
	public Battleship1D (int size) {
		this.size = size;
	}
	
	public void setLocation (ArrayList<Integer> location) throws IllegalArgumentException {
		if (location.size() > this.size) {
			throw new IllegalArgumentException(
				"can't set a location larger then " + this.size +
				". Got: " + location.size()
			);
		}
		this.location = location;
	}
	
	public String bomb (Integer coordinate) {
		String result = "miss";

		int position = this.location.indexOf(coordinate);
		if (position > -1) {
			this.location.remove(coordinate);
			if (location.isEmpty()) {
				result = "sunk";
			} else {
				result = "hit";
			}
		}

		return result;
	}
}
