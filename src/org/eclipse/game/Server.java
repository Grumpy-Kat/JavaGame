package org.eclipse.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {		
	public static DatagramSocket serverSocket;
	public static InetAddress[] clients;
	public static boolean diffClient;

	public static void main(String args[]) throws IOException {
		serverSocket = new DatagramSocket(8675);
		int j = 0;
		while (true) {
			byte[] receiveBuffer = new byte[65535];
			DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			serverSocket.receive(receivePacket);
			String response = data(receiveBuffer).toString();
			System.out.println("Received from client: " + response);
            for (int i = 0; i < clients.length; i++) {
            	if (clients[i] == receivePacket.getAddress()) {
            		diffClient = false;
            	} else {
            		diffClient = true;
            	}
            }
            if (diffClient) {
	           clients[j] = receivePacket.getAddress();
	       }
	       j++;
            for (int i = 0; i < clients.length; i++) {
            	DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.getBytes().length, clients[i], 8675);
            	serverSocket.send(sendPacket);
            }
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