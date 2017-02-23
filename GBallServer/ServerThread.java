package GBallServer;

import java.net.DatagramSocket;

public class ServerThread extends Thread
{
	private DatagramSocket m_socket;
	
	public ServerThread(DatagramSocket socket)
	{
		m_socket = socket;
	}
	
	
	
}