package net.rupf.battleship;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {

	private String position;
	
	private int size;

	private Integer X = 0;

	private Integer Y = 0;

	public Coordinate(String position, int size) {
		this.position = position;
		this.size = size;
	}

	public Coordinate(int X, int Y, int size) {
		this.X = X + 1;
		this.Y = Y + 1;
		this.size = size;
	}

	public boolean isValid() {
		if (X > 0 && X <= size && Y > 0 && Y <= size) {
			return true;
		}
		try {
			Pattern p = Pattern.compile("([a-zA-Z]+)([0-9]+)");
			Matcher m = p.matcher(position);
			if (m.find()) {
				X = Integer.parseInt(m.group(2));

				String alphabet = "abcdefghijklmnopqrstuvwxyz";
				char[] characters = m.group(1).toLowerCase().toCharArray();
				Y = 0;
				for (int i = 0; i < characters.length; ++i) {
					Y += (int) (Math.pow(26, (characters.length - 1 - i)) * (alphabet.indexOf(characters[i]) + 1));
				}
			}
		} catch(Exception e) {
			return false;
		}
		return (X > 0 && X <= size && Y > 0 && Y <= size);
	}

	public Integer getX() {
		return X - 1;
	}

	public Integer getY() {
		return Y - 1;
	}
}
