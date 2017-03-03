package gball.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import gball.client.InputInfo;
import gball.engine.Ship;
import gball.engine.World;
import gball.shared.Const;

public class Server {
	List<ClientThread> m_connectedClients = new ArrayList<ClientThread>();

	public static void main(String[] args) {
		int port = Integer.parseInt(JOptionPane.showInputDialog(null,
				"Enter server port (server will use both the entered port and the next succeding:", "Port?",
				JOptionPane.QUESTION_MESSAGE));
		// Setup port and socket
		Server instance = new Server();
		if (instance.waitForClients(port))
			instance.start();
		JOptionPane.showMessageDialog(null, "Main thread exiting.", "Bye main.", JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean waitForClients(final int port) {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			// Wait for the right amount of clients to connect
			for (int i = 0; i < Const.NEEDEDPLAYERS; i++) {
				// Create a new client with its own socket
				// TODO: add ship reference and keep count of which ship it should have
				ClientThread client = new ClientThread(serverSocket.accept());
				// Add the client if the handshake is successful
				if (client.handshake()) {
					m_connectedClients.add(client);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void start() {
		for(ClientThread c : m_connectedClients){
			c.start();
		}
		World.getInstance().process();
		// TODO: Keep state up to date and sync clients? 
		// Best effort? Just send recent states to clients all the time. Threads should keep clients input up to date anyway?
	}

	public Server() {

	}

	/**
	 * Inner class of server that represents a client. This is made an inner
	 * class as it should be able to remove itself from the connected clients if
	 * it needs to.
	 * 
	 * @author a15andkj
	 *
	 */
	private class ClientThread extends Thread {
		Socket m_socket;
		Ship m_ship;

		public ClientThread(Socket socket) {
			super("ClientThread");
			m_socket = socket;
		}

		private void parseInput(InputInfo inputs) {
			m_ship.updateInput(inputs.m_rightKey, inputs.m_leftKey, inputs.m_accelerateKey, inputs.m_brakeKey);
		}

		public void sendState(final StateUpdate state) {
			try (ObjectOutputStream output = new ObjectOutputStream(m_socket.getOutputStream())) {
				output.writeObject(state);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

		public boolean handshake() {
			try (ObjectOutputStream output = new ObjectOutputStream(m_socket.getOutputStream())) {
				// TODO: Conditions?? Is name necessary? Client choose team? position? Requesting a team but joining the other if that team is full?
				// Do handshake
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			return true;
		}

		@Override
		public void run() {
			// Read input and send states
			try (ObjectInputStream input = new ObjectInputStream(m_socket.getInputStream())) {
				InputInfo inputs;
				while (!interrupted() && m_socket.isConnected() && !m_socket.isClosed()
						&& (inputs = (InputInfo) input.readObject()) != null) {
					parseInput(inputs);
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				System.exit(-1);
			} finally {
				// Make sure the socket gets closed
				try {
					m_socket.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}

	}

}
