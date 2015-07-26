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
			System.out.println("Caught exception: " + e.getMessage());
		}
	}
}
