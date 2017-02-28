package GBallClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import Shared.MsgData;

public class ServerConnection {
	// behöver hantera client sidan på en handshake för att etablera
	// anslutningen när clienten ansluter.
	// samt även ta hand om att skicka meddelanden till servern under spelets
	// gång.

	private InetAddress mServerAddress;
	private int mServerPort;
	private DatagramSocket mSocket;

	public ServerConnection(InetAddress address, int port) {
		mServerAddress = address;
		mServerPort = port;

		try {
			mSocket = new DatagramSocket(mServerPort, mServerAddress);
		}

		catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean handshake() {

		// if (/*connection successful*/)
		/*
		 * return true;
		 * 
		 * else return false;
		 */
		return true;

	}

	public void sendMessage(MsgData data) {
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput output = new ObjectOutputStream(bStream);
		output.writeObject(data);
		output.close();

		byte[] serializedMessage = bStream.toByteArray();

		DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length, mServerAddress,
				mServerPort);
		try {
			mSocket.send(packet);
		} catch (IOException e) {
			System.err.println("Failed to send message.");
			e.printStackTrace();
		}
	}

	public String receiveMessage() {
		// MsgData receivedMessage = null;

		byte[] buf = new byte[256];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);

		try {
			mSocket.receive(packet); // Ta emot paket
		} catch (IOException e) {
			System.err.println("Couldn't recieve message (ServerConnection, recieveChatMessage");
			e.printStackTrace();
		}

		String message = new String(packet.getData(), packet.getOffset(), packet.getLength()); // Unmarshal
																								// packet
																								// till
																								// message-string

		return message;

	}
}