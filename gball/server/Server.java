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
import gball.engine.EntityManager;
import gball.engine.GameEntity;
import gball.engine.ScoreKeeper;
import gball.engine.Ship;
import gball.engine.World;
import gball.shared.Const;
import gball.shared.EntityMeta;

public class Server {
	private List<ClientThread> m_connectedClients = new ArrayList<ClientThread>();
	private double m_lastTime = System.currentTimeMillis();
	
	@SuppressWarnings("unused") // This is unused for now
	private double m_actualUpdateRate = 0.0;
	
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

	public Server() {

	}

	
	public boolean waitForClients(final int port) {
		// TODO: Check if this breaks all child sockets
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			// Wait for the right amount of clients to connect
			int currentID = 1;
			for (int i = 0; i < Const.NEEDEDPLAYERS; i++) {
				// Create a new client with its own socket
				ClientThread client = new ClientThread(currentID++, serverSocket.accept());
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
		while(true){
			if(newUpdate()){
				sendStates();
			}
		}
	}
	
	private void sendStates(){
		StateUpdate state = getState();
		for(ClientThread c : m_connectedClients){
			c.sendState(state);
		}
	}
	
	public StateUpdate getState() {
		List<EntityMeta> entityInfo = new ArrayList<EntityMeta>();
		int[] currentScores = { 0, 0 };
		for (GameEntity ent : EntityManager.getEntites()) {
			entityInfo.add(ent.getMeta());
		}
		currentScores = ScoreKeeper.getScores();
		return new StateUpdate(currentScores, entityInfo);
	}
	
	/**
	 * Check if enough time has passed to send state to clients
	 * @return true if a new state update is to be sent to the clients
	 */
	private boolean newUpdate() {
		double currentTime = System.currentTimeMillis();
		double delta = currentTime - m_lastTime;
		boolean rv = (delta > Const.SERVER_INCREMENT);
		if (rv) {
			m_lastTime += Const.SERVER_INCREMENT;
			if (delta > 10 * Const.SERVER_INCREMENT) {
				m_lastTime = currentTime;
			}
			m_actualUpdateRate = 1000 / delta;
		}
		return rv;
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
		private Socket m_socket;
		private final int m_shipID;

		public ClientThread(final int shipID, Socket socket) {
			super("ClientThread");
			m_shipID = shipID;
			m_socket = socket;
		}

		
		private void parseInput(InputInfo inputs) {
			GameEntity entity = EntityManager.getEntity(m_shipID);
			if(entity instanceof Ship){
				((Ship)entity).updateInput(inputs.m_rightKey, inputs.m_leftKey, inputs.m_accelerateKey, inputs.m_brakeKey);
			}
		}

		public void sendState(final StateUpdate state) {
			try (ObjectOutputStream output = new ObjectOutputStream(m_socket.getOutputStream())) {
				output.writeObject(state);
				output.flush();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

		public boolean handshake() {
			try (ObjectOutputStream output = new ObjectOutputStream(m_socket.getOutputStream());
					/*ObjectInputStream input = new ObjectInputStream(m_socket.getInputStream());*/) {
				// TODO: Conditions?? Is name necessary? Client choose team? position? Requesting a team but joining the other if that team is full? Version control?
				// Do handshake
				// send send handshake message
//				String msg = (String)input.readObject();
//				if(msg.equals("TEAM1")) {
//					// ... check if room on that team etc.
//				}
				output.writeObject("SUCCESS");
				output.flush();
			} catch (IOException/* | ClassNotFoundException*/ e) {
				e.printStackTrace();
				System.exit(-1);
			}
			return true;
		}

		@Override
		public void run() {
			// Read input
			try (ObjectInputStream input = new ObjectInputStream(m_socket.getInputStream());) {
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
