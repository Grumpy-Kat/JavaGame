package org.eclipse.game;

import java.applet.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JFrame;

public class Game extends Applet implements Runnable {
	private static final long serialVersionUID = 1L;

	public static DatagramSocket clientSocket;
	public static InetAddress ip;
	public static byte[] receiveBuffer;
	
	static JFrame frame;
	
	public Player player;
	
	Image screen;
	
	static final String title = "Multiplayer Roguelike";
	
	static final int width = 960;
	static final int height = 960;
	static final Dimension dim = new Dimension(width, height);
	//public static final Dimension pxSize = new Dimension(dim.width /  Map.GetInstance().tileWidth, dim.height / Map.GetInstance().tileHeight);
	//public static Dimension relativeDim = new Dimension(0, 0);
	
	boolean gameRun = false;
	
	public int fps;
	
	public Game() {
		setPreferredSize(dim);
		//addKeyListener(new InputListener(this));
	}
	
	public void start() {
		new Game();
		try {
			clientSocket = new DatagramSocket(8676);
			ip = InetAddress.getLocalHost();
		} catch (Exception e) {
			System.out.println("SocketException or UknownHostException!");
		}
		receiveBuffer = new byte[65535];
		frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(dim);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		requestFocus();
		screen = createVolatileImage(dim.width, dim.height);
		//player = new Player();
		gameRun = true;
		Thread handle = new Thread() {
			public void handleData() throws IOException {
    			int x = player.x;
    			int y = player.y;
    			int health = player.health;
				String xString = format(x);
				String yString = format(y);
				String healthString = format(health);
				String data = xString + yString + healthString;
				DatagramPacket sendPacket = new DatagramPacket(data.getBytes(), data.getBytes().length, ip, 8675);
				DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length, ip, 8675);
				clientSocket.send(sendPacket);
				clientSocket.receive(receivePacket);
				String response = data(receiveBuffer).toString();
				if (response == "Game room is full") {
					rejectMessage();
				}
				int otherX = Integer.parseInt(response.substring(0, 3));
        		int otherY = Integer.parseInt(response.substring(3, 6));
        		int otherHealth = Integer.parseInt(response.substring(6, 9));
        		int playerId = Integer.parseInt(response.substring(9, 10));
        		System.out.println(playerId);
    			System.out.println("Received from server: " + response);
    		}
		};
		handle.start();
		new Thread(this).start();
	}

	void rejectMessage() { }
	
	public void stop() {
		gameRun = false;
	}
	
	public void run() {
		screen = createVolatileImage(dim.width, dim.height);
		int frames = 0;
		double nonProcessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		while(gameRun) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			nonProcessedSeconds += passedTime / 1000000000.0;
			while(nonProcessedSeconds > secondsPerTick) {
				tick();
				nonProcessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if(tickCount % 60 == 0) {
					previousTime += 1000;
					fps = frames;
					frames = 0;
				}
			}
			if(ticked) {
				frames++;
			}
			frames++;
			requestFocus();
			tick();
			render();
			try {
				Thread.sleep(5);
			} catch (Exception e) { }
		}
	}
	
	public void tick() {
		//any gameplay interactions that must be updated
	}
	
	public void render() {
	    Graphics g = screen.getGraphics();
        Map.GetInstance().Render(g);
        g = frame.getGraphics();
		g.drawImage(screen, 0, 0, dim.width, dim.height, 0, 0, dim.width, dim.height, null);
		g.dispose();
	}

	public static StringBuilder data(byte[] a) {
        if (a == null) {
            return null;
        }
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char)a[i]);
            i++;
        }
        return ret;
    }

    public static String format(int a) {
    	if (a > 99) {
    		return "" + a;
    	} else if (a > 9) {
    		return "0" + a;
    	} else {
    		return "00" + a;
    	}
    }
}