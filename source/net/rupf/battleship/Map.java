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

public class Map {
	boolean[][] map;
	ArrayList<Integer> location;
	int axis;
	boolean isHorizontal;
	int side;
	int generationCount = 0;
	
	public Map(int sideLength) {
		side = sideLength;
		map = new boolean[side][side];
	}
	
	public void generateLocation() {
		isHorizontal = (Math.random() >= 0.5);
		axis = (int) (Math.random() * side);

		int shipSize = (side / 2);
		int start = (int) (Math.random() * shipSize);
		
		location = new ArrayList<Integer>();
		for (int i = 0; i < shipSize; ++i) {
			if (isOccupied(start, axis, isHorizontal)) {
				++generationCount;
				if (generationCount > 100) {
					System.out.println("Navigator: Sir, its not possible that there are so many ships in this area! One ship must have been reportet twice.");
					for (int x = 0; x < side; ++x) {
						for (int y = 0; y < side; ++y) {
							System.out.print(map[x][y] ? "x" : "o");
						}
						System.out.println();
					}
				} else {
					generateLocation();
				}
				break;
			} else {
				location.add(start++);
			}
		}
		if (location.size() == shipSize) {
			for (int i = 0; i < shipSize; ++i) {
				if (isHorizontal) {
					map[location.get(i)][axis] = true;
				} else {
					map[axis][location.get(i)] = true;
				}
			}
		}
		generationCount = 0;
	}

	public ArrayList<Integer> getLocation () {
		return location;
	}

	public int getAxis () {
		return axis;
	}

	public boolean isHorizontal () {
		return isHorizontal;
	}
	
	private boolean isOccupied(int point, int axis, boolean isHorizontal) {
		int x, y;
		if (isHorizontal) {
			x = point;
			y = axis;
		} else {
			y = point;
			x = axis;
		}
		boolean occupied = false;
		for (int i = Math.max(x-2, 0); i < Math.min(x+2, side); ++i) {
			for (int j = Math.max(y-2, 0); j < Math.min(y+2, side); ++j) {
				if (map[i][j]) {
					occupied = true;
				}
			}
		}
		return occupied;
	}
}
