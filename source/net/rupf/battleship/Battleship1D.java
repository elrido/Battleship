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
