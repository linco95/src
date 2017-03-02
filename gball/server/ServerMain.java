package gball.server;

import javax.swing.JOptionPane;

public class ServerMain {

	public static void main(String[] args) {
		int port = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter server port (server will use both the entered port and the next succeding:", "Port?", JOptionPane.QUESTION_MESSAGE));
		World.getInstance().process();
	}

}
