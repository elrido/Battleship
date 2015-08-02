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
		for (int i = Math.max(x-1, 0); i < Math.min(x+1, side); ++i) {
			for (int j = Math.max(y-1, 0); j < Math.min(y+1, side); ++j) {
				if (map[i][j]) {
					occupied = true;
				}
			}
		}
		return occupied;
	}
}
