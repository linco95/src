package gball.client;

import java.net.Socket;

public class ClientToServer {
	Socket m_socket;
	
	public ClientToServer(Socket socket){
		m_socket = socket;
	}
}
