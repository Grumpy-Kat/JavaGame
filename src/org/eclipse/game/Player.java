package org.eclipse.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Player {
	public int x = 0;
	public int y = 0;
	
	public final int maxHealth = 100;
	public int health = maxHealth;
	
	public boolean isDead = false;
	
	public boolean isWalking = false;
	public float speed = 1.0f;
	public float xDir = speed;
	public float yDir = speed;
	
	public int playerId = -1;
	
	ArrayList<int[]> spawnCoords = new ArrayList<int[]>();
	
	public Player() {
		int[] coord = spawnCoords.get(new Random(System.currentTimeMillis()).nextInt(4));
		spawnCoords.remove(coord);
		x = coord[0] * Map.GetInstance().tileWidth;
		y = coord[1] * Map.GetInstance().tileHeight;
	}
	
	void AddSpawnCoords() {
		spawnCoords.add(new int[] {30, 23});
		spawnCoords.add(new int[] {85, 20});
		spawnCoords.add(new int[] {45, 80});
		spawnCoords.add(new int[] {90, 90});
	}
	
	public void tick() {
		if(isWalking) {
			x += xDir;
			y += yDir;
		}
	}
	
	public void TakeDamage(int amt) {
		health -= amt;
		if(health <= 0) {
			isDead = true;
		}
	}
}
