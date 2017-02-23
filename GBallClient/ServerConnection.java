package GBallClient;

import java.net.DatagramSocket;
import java.net.InetAddress;

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
	
	public MsgData receiveMessage()
	{
		MsgData receivedMessage = null;
		
		
		
		return receivedMessage;
	}
}