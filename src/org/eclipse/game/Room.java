package org.eclipse.game;

import java.util.Random;

public class Room {
	int ammoBoxes;
	
	public Room(int x, int y) {
		ammoBoxes = new Random(System.currentTimeMillis()).nextInt();
	}
}
