package org.eclipse.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Game {
	public static DatagramSocket clientSocket;
	
	public static void main(String args[]) throws IOException {
		clientSocket = new DatagramSocket();
		while (true) {
			int x = 80;
			int y = 90;
			int health = 40;
			String xString = Integer.toString(x);
			String yString = Integer.toString(y);
			String healthString = Integer.toString(health);
			String data = xString + yString + healthString;
			byte[] receiveBuffer = new byte[65535];
			InetAddress ip = InetAddress.getLocalHost();
			DatagramPacket sendPacket = new DatagramPacket(data.getBytes(), data.getBytes().length, ip, 8675);
			DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length, ip, 8675);
			clientSocket.send(sendPacket);
			clientSocket.receive(receivePacket);
			String response = data(receiveBuffer).toString();
			System.out.println("Received from server: " + response);
		}
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
}