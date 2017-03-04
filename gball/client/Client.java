package gball.client;

import gball.gui.GameWindow;
import gball.shared.Const;

public class Client {
	private final GameWindow m_gameWindow;
	private final InputListener m_inputs;
	private double m_lastTime = System.currentTimeMillis();
	private double m_actualUpdateRate = 0.0;
	private ClientToServer m_connection;
	
	
	public static void main(String[] args) {
		// Setup connection to server and launch gui
		// repaint gui on new server update (no need to do it before that as no
		// simulation is happening on the clients)
		Client instance = new Client("localhost", 1337);
		// start loop
	}

	public Client(String serverHostname, int serverPort) {
		m_inputs = new InputListener(/* Ask for preferred controls */);
		m_gameWindow = new GameWindow();
		m_gameWindow.addKeyListener(m_inputs);
		m_connection = new ClientToServer(serverHostname, serverPort);
	}
	
	public void start(){
		while(true){
			if(newUpdate()){
				// send input to server
				m_connection.sendInputs(m_inputs.getInputs());
			}
		}
	}
	
	private boolean newUpdate() {
		double currentTime = System.currentTimeMillis();
		double delta = currentTime - m_lastTime;
		boolean rv = (delta > Const.CLIENT_INCREMENT);
		if (rv) {
			m_lastTime += Const.CLIENT_INCREMENT;
			if (delta > 10 * Const.CLIENT_INCREMENT) {
				m_lastTime = currentTime;
			}
			m_actualUpdateRate = 1000 / delta;
		}
		return rv;
	}

}
