package GBallServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;

import GBallServer.ClientConnection;

public class Server
{
	private ArrayList<ClientConnection> m_connectedClients = new ArrayList<ClientConnection>();
	private static DatagramSocket m_socket;
	private int m_port = 0;
	
	public static void main(String[] args)
	{
		if (args.length < 1)
		{
			System.err.println("Insert portnumber");
			System.exit(-1);
		}
		
		try
		{
			m_socket = new DatagramSocket(Integer.parseInt(args[0]));
		}
		
		catch (SocketException | NumberFormatException e)
		{
			e.printStackTrace();
		}
	}
	
	private void listenForMessages()
	{
		while (true)
		{
			if (m_connectedClients.size() < 4)
			{
				byte[] buff = new byte[512];
				DatagramPacket p = new DatagramPacket(buff, buff.length);
				
				try
				{
					m_socket.receive(p);
				}
				
				catch (IOException e)
				{
					e.printStackTrace();
				}	
				
				String recievedMessage = new String(p.getData(), p.getOffset(), p.getLength());
				System.out.println("IP of new client: " + String.valueOf(p.getAddress() + " Port: " + p.getPort()));
				
				if (!recievedMessage.isEmpty())
				{
					if (recievedMessage.equals("join"))
					{
						byte[] ackBuff = new byte[512];
						String ackMessage = String.valueOf(addClient(p.getAddress(), p.getPort()));
						ackBuff = ackMessage.getBytes();
						DatagramPacket ackPacket = new DatagramPacket(ackBuff, ackBuff.length, p.getAddress(), p.getPort());
						
						try
						{
							m_socket.send(ackPacket);
						}
						
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public boolean addClient(InetAddress address, int port) {
		ClientConnection c;
		for (Iterator<ClientConnection> itr = m_connectedClients.iterator(); itr.hasNext();) {
			c = itr.next();
			if (c.getAddress().equals(address) && c.getPort() == port) {
				return false; // Already exists a client with this IP and port.
			}
		}
		System.out.println("Added a new client.");
		m_connectedClients.add(new ClientConnection(address, port));
		return true;
	}
}