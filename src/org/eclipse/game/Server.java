package org.eclipse.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {		
	public static DatagramSocket serverSocket;
	public static InetAddress[] clients;
	public static boolean diffClient;
	public static DatagramPacket receivePacket;
	public static int j;
	public static int clientsLength; 
	public static byte[] receiveBuffer;
	public static String response;
	public static int inc;

	public static void main(String args[]) throws IOException {
		serverSocket = new DatagramSocket(8675);
		receiveBuffer = new byte[65535];
		inc = 1;
		while(true) {
			receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			serverSocket.receive(receivePacket);
			response = data(receiveBuffer).toString();
            for(int i = 0; i < clientsLength + inc; i++) {
            	if(clientsLength == 0) {
            		diffClient = true;
            		inc = 0;
            	} else {
		           	if (clients[i] == receivePacket.getAddress()) {
		           		diffClient = false;
		           		response = response + i;
		           	} else {
		           		diffClient = true;
		           	}
            	}
            }
            if (diffClient) {
	           clients[j] = receivePacket.getAddress();
	           response = response + j;
	           j++;
	           clientsLength++;
            }
            for (int i = 0; i < clientsLength; i++) {
            	DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.getBytes().length, clients[i], 8676);
            	serverSocket.send(sendPacket);
            }
            int x = Integer.parseInt(response.substring(0, 3));
            int y = Integer.parseInt(response.substring(3, 6));
            int health = Integer.parseInt(response.substring(6, 9));
            System.out.println("X: " + x + ". Y: " + y + ". Health: " + health);
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