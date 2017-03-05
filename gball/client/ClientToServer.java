package gball.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import gball.gui.GameWindow;
import gball.gui.RenderableEntityManager;
import gball.gui.ScoreKeeperRepresentation;
import gball.shared.EntityMeta;
import gball.shared.InputInfo;
import gball.shared.StateUpdate;

public class ClientToServer extends Thread {

	private Socket m_socket;
	private ObjectInputStream m_input;
	private ObjectOutputStream m_output;
	private GameWindow m_gui;

	public ClientToServer(GameWindow gui, String serverHostname, int serverPort) {
		try {
			System.out.println("Creating socket.");
			m_socket = new Socket(InetAddress.getByName(serverHostname), serverPort);
			m_output = new ObjectOutputStream(m_socket.getOutputStream());
			m_input = new ObjectInputStream(m_socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		m_gui = gui;
	}

	public boolean handshake() {
		try {
			return ((String) m_input.readObject()).equals("SUCCESS");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return false;
	}

	public void sendInputs(InputInfo inputs) {
//		System.out.println("Sending inputs");
		try  {
			m_output.writeObject(inputs.copy());
			m_output.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void run() {
		receiveStates();
	}

	public void receiveStates() {
		try {
			StateUpdate state;
			while (m_socket.isConnected() && !m_socket.isClosed()
					&& (state = (StateUpdate) m_input.readObject()) != null) {
//				System.out.println("State received");
				for (EntityMeta ent : state.m_entities) {
					RenderableEntityManager.getInstance().updateState(ent);
				}
				ScoreKeeperRepresentation.getInstance().updateScore(state.m_scores[0], state.m_scores[1]);
//				System.out.println("Received state");
				m_gui.repaint();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			try {
				m_input.close();
				m_output.close();
				m_socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	// Receive function that will tell GUI to repaint
}
