package gball.client;

import gball.gui.GameWindow;

public class Client {
	private final GameWindow m_gameWindow;
	private final InputListener m_inputs;

	public static void main(String[] args) {
		// Setup connection to server and launch gui
		Client instance = new Client(/* server info */);
	}

	public Client() {
		m_inputs = new InputListener(/* Ask for preferred controls */);
		m_gameWindow = new GameWindow();
		m_gameWindow.addKeyListener(m_inputs);
	}

}
