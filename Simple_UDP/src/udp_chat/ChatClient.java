package udp_chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class MessageSender implements Runnable {
	public final static int PORT = 2020;
	private DatagramSocket socket;
	private String hostName;
	private ClientWindow window;

	MessageSender(DatagramSocket sock, String host, ClientWindow win) {
		socket = sock;
		hostName = host;
		window = win;
	}

	private void sendMessage(String s) throws Exception {
		byte buffer[] = s.getBytes();
		InetAddress address = InetAddress.getByName(hostName);
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
		socket.send(packet);
	}

	public void run() {
		boolean connected = false;
		do {
			try {
				sendMessage("New client connected - welcome!");
				connected = true;
			} catch (Exception e) {
				window.displayMessage(e.getMessage());
			}
		} while (!connected);
		while (true) {
			try {
				while (!window.message_is_ready) {
					Thread.sleep(100);
				}
				sendMessage(window.getMessage());
				window.setMessageReady(false);
			} catch (Exception e) {
				window.displayMessage(e.getMessage());
			}
		}
	}
}

class MessageReceiver implements Runnable {
	DatagramSocket socket;
	byte buffer[];
	ClientWindow window;

	MessageReceiver(DatagramSocket sock, ClientWindow win) {
		socket = sock;
		buffer = new byte[1024];
		window = win;
	}

	public void run() {
		while (true) {
			try {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String received = new String(packet.getData(), 1, packet.getLength() - 1).trim();
				System.out.println(received);
				window.displayMessage(received);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
}

public class ChatClient {

	public static void main(String args[]) throws Exception {
		
		
		ClientWindow window = new ClientWindow();
		String host = window.getHostName();
		window.setTitle("UDP CHAT  Server: " + host);
		DatagramSocket socket = new DatagramSocket();
		MessageReceiver receiver = new MessageReceiver(socket, window);
		MessageSender sender = new MessageSender(socket, host, window);
		Thread receiverThread = new Thread(receiver);
		Thread senderThread = new Thread(sender);
		receiverThread.start();
		senderThread.start();
	}
}