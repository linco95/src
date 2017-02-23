/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GBallServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Random;

/**
 * 
 * @author brom
 */
public class ClientConnection {
	private final InetAddress m_address;
	private final int m_port;

	public ClientConnection(InetAddress address, int port) {
		m_address = address;
		m_port = port;
	}

	
	
	public void sendMessage(String message, DatagramSocket socket) {
		byte[] ackBuff = new byte[256];
		DatagramPacket ackPacket = new DatagramPacket(ackBuff, ackBuff.length);
		
		byte[] buffer = new byte[256];
		buffer = message.getBytes();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, m_address, m_port);
		
		try
		{
			System.out.println("Sending. Server -> Client");
			socket.send(packet);
		}
		
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public InetAddress getAddress() {
		return m_address;
	}

	public int getPort() {
		return m_port;
	}
}
