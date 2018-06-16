package org.eclipse.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {
	Game game;
	
	public InputListener(Game game) {
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
			game.player.isWalking = true;
			game.player.xDir = game.player.speed;
			break;
		case KeyEvent.VK_LEFT:
			game.player.isWalking = true;
			game.player.yDir = -game.player.speed;
			break;
		case KeyEvent.VK_UP:
			game.player.isWalking = true;
			game.player.yDir = game.player.speed;
			break;
		case KeyEvent.VK_DOWN:
			game.player.isWalking = true;
			game.player.yDir = -game.player.speed;
			break;
		case KeyEvent.VK_SHIFT:
			game.player.speed = 2;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
			if (game.player.xDir == game.player.speed) {
				game.player.isWalking = false;
			}
			break;
		case KeyEvent.VK_LEFT:
			if (game.player.yDir == -game.player.speed) {
				game.player.isWalking = false;
			}
			break;
		case KeyEvent.VK_UP:
			game.player.isWalking = true;
			game.player.yDir = game.player.speed;
			break;
		case KeyEvent.VK_DOWN:
			game.player.isWalking = true;
			game.player.yDir = -game.player.speed;
			break;
		case KeyEvent.VK_SHIFT:
			game.player.speed = 1;
			break;
		}
	}

	public void keyTyped(KeyEvent e) { }
}
