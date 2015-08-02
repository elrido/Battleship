package net.rupf.battleship;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;

public class BattleshipGUI {

	private JFrame frame = new JFrame("Battleship");

	private JPanel mainPanel;
	
	private JTextArea messages;
	
	private ArrayList<JCheckBox> checkboxList = new ArrayList<JCheckBox>();

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
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		new BattleshipGUI().createGUI();
	}

	public void createGUI() {
		// create menu bar
		JMenu menu = new JMenu("Game");
		menu.setMnemonic(KeyEvent.VK_G);
		menu.getAccessibleContext().setAccessibleDescription(
			"Game options"
		);
		JMenuItem menuItem = new JMenuItem("Restart", KeyEvent.VK_R);
		menuItem.getAccessibleContext().setAccessibleDescription(
			"Restart the game"
		);
		menuItem.addActionListener(new RestartListener());
		menu.add(menuItem);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);

		// create message area
		messages = new JTextArea(10, 20);
		Font small = new Font("sans", Font.PLAIN, 10);
		messages.setFont(small);
		//messages.setEnabled(false);
		messages.setLineWrap(true);
		messages.setWrapStyleWord(true);
		TextAreaOutputStream msgOutputStream = new TextAreaOutputStream(messages);
		System.setOut(new PrintStream(msgOutputStream));
		JScrollPane messagePane = new JScrollPane(
			messages,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);
	
		// build center grid with checkboxes
		int side = size + 1;
		GridLayout grid = new GridLayout(side, side);
		grid.setVgap(1);
		grid.setHgap(2);
		mainPanel = new JPanel(grid);
		BombardmentListener scout = new BombardmentListener();
		for (int i = 0, max = side * side; i < max; ++i) {
			if (i == 0) {
				mainPanel.add(new JLabel(""));
			} else if (i % side == 0) {
				JLabel l = new JLabel(getLetter((int) i / side), SwingConstants.CENTER);
				l.setFont(small);
				mainPanel.add(l);
			} else if (i < side) {
				JLabel l = new JLabel(Integer.toString(i), SwingConstants.CENTER);
				l.setFont(small);
				mainPanel.add(l);
			} else {
				JCheckBox c = new JCheckBox();
				c.setSelected(false);
				c.addItemListener(scout);
				checkboxList.add(c);
				mainPanel.add(c);
			}
		}
		
		// build background
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// attach UI elements
		background.add(BorderLayout.WEST, messagePane);
		background.add(BorderLayout.CENTER, mainPanel);

		// build frame
		ImageIcon img = new ImageIcon("/images/loongson-club.png");
		frame.setIconImage(img.getImage());
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(background);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(50, 50, 300, 300);
		frame.pack();
		frame.setVisible(true);

		init();
	}

	public void init () {
		System.out.println("Welcome to BATTLESHIP");
		System.out.println();
		System.out.println("It's the time of the second world war. You are an admiral in your countries naval forces. Your battlegroup was sent to new coordinates and your scouts just reported sightings of " + shipCount + " enemy ships. This is the moment you have trained your men for, in these last months.");
		System.out.println();
		System.out.println("The map shows you the gridded area the enemy ships are suspected to be in. Your battlefield is roughly a square area of " + size + " nautical miles. You turn to your radio operator. Eagerly he awaits your command.");
		System.out.println();
		System.out.println("Radio operator: Sir, which target area should we bombard?");
		System.out.println();
		
		Map map = new Map(size);
		for (int i = 0; i < 3; ++i) {
			map.generateLocation();
			Ship battleship = new Ship(
				size,
				shipNames[(int) (Math.random() * shipNames.length)]
			);
			battleship.setLocation(map.getLocation(), map.getAxis(), map.isHorizontal());
			ships.add(battleship);
		}
	}
	
	private void bomb (int code) {
		int x = code % size;
		int y = code / size;
		Coordinate position = new Coordinate(x, y, size);
		JCheckBox c = checkboxList.get(code);
		if (!c.isEnabled()) {
			return;
		}
		c.setEnabled(false);

		++guesses;
		String result = "miss";
		for (Ship battleship : ships) {
			result = battleship.bomb(position);
			
			if (result.equals("hit")) {
				System.out.println("You see a billow of dark smoke just at the horizon. Your fleet must have hit something!");
				break;
			} else if (result.equals("sunk")) {
				System.out.println("You see a blinding flash, and only a few moments later you can just about see on the horizon the stern of a ship lift into the air, before it sinks forever into the depths of the sea.");
				System.out.println();
				System.out.println("Your fleet sank the " + battleship.getName() + ", may Neptune have mercy on the souls of its crew.");
				ships.remove(battleship);
				break;
			}
		}
		if (result.equals("miss")) {
			System.out.println("You see nothing but calm sea. You must have missed.");
			c.setSelected(false);
		}
		System.out.println();
		if (ships.size() == 0) {
			end();
		}
	}
	
	private void end () {
		System.out.print("You did it! Your fleet sent all " + shipCount + " enemy vessels to the bottom of the sea. It took you " + guesses + " rounds of fire. ");
		System.out.println();
		if (guesses < (shipCount * (size / 2) * 1.5)) {
			System.out.println("Well done, Admiral!");
		} else if (guesses < (shipCount * (size / 2) * 2.5)) {
			System.out.println("Certainly you can improve your targetting before you enter your next battle.");
		} else {
			System.out.println("Your fleet just about made it out in one piece. You realize that you still have a lot to learn about the enemies tactics.");
		}
		System.out.println();
		System.out.println("GAME OVER");
	}

	private static String getLetter(int i) {
		String result = "";
		while (i-- > 0) {
			result = String.valueOf((char) ((i % 26) + 65)) + result;
			i /= 26;
		}
		return result;
	}
	
	class RestartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (JCheckBox c : checkboxList) {
				if (c.isSelected()) {
					c.setSelected(false);
				}
				c.setEnabled(true);
			}
			guesses = 0;
			messages.setText("");
			init();
		}
	}
	
	class BombardmentListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			Object source = e.getItemSelectable();
			int position = checkboxList.indexOf(source);
			bomb(position);
		}
	}
}
