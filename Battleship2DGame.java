import java.util.ArrayList;
import java.util.Scanner;

public class Battleship2DGame {

	private ArrayList<Ship> ships = new ArrayList<Ship>();

	private final String[] shipNames = {
		"Navarin",
		"Sissoi Veliky",
		"Oslyabya",
		"Borodino",
		"Imperator Aleksandr III",
		"Knyaz Suvorov",
		"Admiral Ushakov",
		"HMS Irresistible",
		"HMS Ocean",
		"Bouvet",
		"HMS Goliath",
		"SMS Pommern",
		"Bismarck",
		"USS Arizona",
		"USS Utah",
		"HMS Prince of Wales",
		"Roma",
		"Hiei",
		"Kirishima",
		"Scharnhorst",
		"Musashi",
		"Fusō",
		"Yamashiro",
		"Tirpitz",
		"Yamato",
	};

	private int shipCount = 3;

	private int size = 10;

	private int guesses = 0;
	
	public Battleship2DGame () {
	}
	
	public Battleship2DGame (int size) {
		this.size = size;
	}
	
	public Battleship2DGame (int size, int shipCount) {
		this.size = size;
		this.shipCount = shipCount;
	}

	public void init () {
		for (int i = 0; i < 3; ++i) {
			Ship battleship = new Ship(
				size,
				shipNames[(int) (Math.random() * shipNames.length)]
			);
			battleship.setLocation(getLocation(), (int) (Math.random() * size), (Math.random() >= 0.5));
			ships.add(battleship);
		}
		
		System.out.println();
		System.out.println("Welcome to BATTLESHIP");
		System.out.println("=====================");
		System.out.println();
		System.out.println("It is the time of the second world war. You are an admiral in your countries naval forces. Your battlegroup was sent to new coordinates and your scouts just reported you sightings of " + shipCount + " enemy ships. Now is the moment you have trained your men for, during these last months. You call your radio operator. Eagerly he awaits your command. Are you ready to order on which coordinates your fleets captains should concentrate their bombardment?");
		System.out.println();
		System.out.println("The map shows you the area the enemy ships are suspected to be in. Your battlefield is roughly a square area of " + size + " nautical miles. Your navigator has gridded it on the map and has labeled the rows with alphabetical letters starting at 'A' and the columns with numbers starting at '1'. So, to bombard the top left corner of the area, you would request for the coordinate set 'A1', etc.");
		System.out.println();
	}

	public void start () {
		Scanner reader = new Scanner(System.in);
		while(!ships.isEmpty()) {
			System.out.println("Radio operator: Sir, which target area should we bombard?");
			System.out.print("Admiral: Concentrate all your fire on coordinates ");
			String command = reader.next();
			Coordinate point = new Coordinate(command, size);
			if (!point.isValid()) {
				System.out.println("Navigator: That is outside of the target area.");
				wait(1);
				continue;
			}
			System.out.println("Radio operator: Repeat, target the coordinates " + command + ". Fire at will!");
			System.out.println("The Radio Operator transmits your command and you can hear and even feel the mighty cannons of your fleet fire. You lift your binoculars, eager to see if you guessed the movements of your enemies correctly.");
			System.out.println();
			wait(2);
			bomb(point);
			wait(1);
			System.out.println();
		}
		end();
	}
	
	private void bomb (Coordinate position) {
		++guesses;
		String result = "miss";
		for (Ship battleship : ships) {
			result = battleship.bomb(position);
			
			if (result.equals("hit")) {
				System.out.println("You see a billow of dark smoke just at the horizon. Your fleet must have hit something!");
				break;
			} else if (result.equals("sunk")) {
				System.out.println("You see a blinding flash, and only a few moments later you can just about see on the horizon the stern of a ship lift into the air, before it sinks forever into the depths of the sea. Your fleet sank the " + battleship.getName() + ", may Neptune have mercy on the souls of its crew.");
				ships.remove(battleship);
				break;
			}
		}
		if (result.equals("miss")) {
			System.out.println("You see nothing but calm sea. You must have missed.");
		}
	}

	private void end () {
		System.out.print("You did it! Your fleet sent all " + shipCount + " enemy vessels to the bottom of the sea. It took you " + guesses + " rounds of fire. ");
		if (guesses < 20) {
			System.out.println("Well done, Admiral!");
		} else if (guesses < 40) {
			System.out.println("Certainly you can improve your targetting before you enter your next battle.");
		} else {
			System.out.println("Your fleet just about made it out in one piece. You realize that you still have a lot to learn about the enemies tactics.");
		}
		System.out.println();
		System.out.println("=========");
		System.out.println("GAME OVER");
		System.out.println();
	}

	private ArrayList<Integer> getLocation () {
		int shipSize = (size / 2);
		int start = (int) (Math.random() * shipSize);
		ArrayList<Integer> location = new ArrayList<Integer>();
		for (int i = 0; i < shipSize; ++i) {
			location.add(start++);
		}
		return location;
	}
	
	private void wait (int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
