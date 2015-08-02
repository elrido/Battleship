package net.rupf.battleship;

import java.util.ArrayList;

public class Ship {

	private ArrayList<Integer> location;

	private int axis;
	
	private boolean isHorizontal;

	private int size;
	
	private String name;

	public Ship (int size, String name) {
		this.size = size;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setLocation (ArrayList<Integer> location, int axis, boolean isHorizontal) throws IllegalArgumentException {
		if (location.size() > size) {
			throw new IllegalArgumentException(
				"can't set a location larger then " + size +
				". Got: " + location.size()
			);
		}
		if (axis >= size || axis < 0) {
			throw new IllegalArgumentException(
				"can't set an axis larger then " + (size - 1) +
				". Got: " + axis
			);
		}
		this.location = location;
		this.axis = axis;
		this.isHorizontal = isHorizontal;
	}
	
	public String bomb (Coordinate coordinate) {
		String result = "miss";
		Integer point;
		int currentAxis;

		if (isHorizontal) {
			point = coordinate.getX();
			currentAxis = coordinate.getY();
		} else {
			point = coordinate.getY();
			currentAxis = coordinate.getX();
		}

		if (currentAxis == axis) {
			int position = location.indexOf(point);
			if (position > -1) {
				location.remove(position);
				if (location.isEmpty()) {
					result = "sunk";
				} else {
					result = "hit";
				}
			}
		}

		return result;
	}
}
