package gball.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import gball.server.StateUpdate;

public class ClientToServer {

	private Socket m_socket;

	public ClientToServer(String serverHostname, int serverPort/*, boolean preferredTeam*/) {
		try {
			m_socket = new Socket(InetAddress.getByName(serverHostname), serverPort);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private boolean handshake(ObjectInputStream input) {
		try (ObjectOutputStream output = new ObjectOutputStream(m_socket.getOutputStream());) {
			// TODO: Proper handshake
			output.writeObject("CONNECT:TEAM1");
			return ((String) input.readObject()).equals("SUCCESS");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return false;
	}

	public void sendInputs(InputInfo inputs) {

	}

	public void receiveStates() {
		try (ObjectInputStream input = new ObjectInputStream(m_socket.getInputStream());) {
			if (handshake(input)) {
				StateUpdate state;
				while (m_socket.isConnected() && !m_socket.isClosed()
						&& (state = (StateUpdate) input.readObject()) != null) {
					// Callback to client, updating state
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	// Receive function that will tell GUI to repaint
}
