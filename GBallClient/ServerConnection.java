package GBallClient;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import Shared.MsgData;

public class ServerConnection
{
	//behöver hantera client sidan på en handshake för att etablera anslutningen när clienten ansluter.
	//samt även ta hand om att skicka meddelanden till servern under spelets gång.
	
	private InetAddress serverAddress;
	private int serverPort;
	
	public ServerConnection(InetAddress address, int port)
	{
		serverAddress = address;
		serverPort = port;
		
		try
		{
			DatagramSocket socket = new DatagramSocket(serverPort, serverAddress);
		}
		
		catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean handshake()
	{
		
		
		
		
		if (/*connection successful*/)
			return true;
		
		else
			return false;
		
	}
	
	public void sendMessage()
	{
		
	}
	
	public void receiveMessage()
	{
		MsgData receivedMessage = null;
		String receivedString = null;
		
		
				
	}
}