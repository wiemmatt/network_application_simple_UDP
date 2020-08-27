package udp_chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer implements  Runnable {
	public final static int PORT = 2020;
	private final static int BUFFER = 1024;

	private DatagramSocket socket;
	private ArrayList<InetAddress> client_addresses;
	private ArrayList<Integer> client_ports;
	private HashSet<String> existing_clients;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ChatServer() throws IOException {
		socket = new DatagramSocket(PORT);
		System.out.println("Server is running and is listening on port " + PORT);
		client_addresses = new ArrayList();
		client_ports = new ArrayList();
		existing_clients = new HashSet();
	}

	public void run() {
		byte[] buffer = new byte[BUFFER];
		while (true) {
			try {
				Arrays.fill(buffer, (byte) 0);
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);

				String message = new String(buffer, 0, buffer.length);

				InetAddress clientAddress = packet.getAddress();
				int client_port = packet.getPort();

				String id = clientAddress.toString() + "|" + client_port;
				if (!existing_clients.contains(id)) {
					existing_clients.add(id);
					client_ports.add(client_port);
					client_addresses.add(clientAddress);
				}

				System.out.println(id + " : " + message);
				byte[] data = (id + " : " + message).getBytes();
				for (int i = 0; i < client_addresses.size(); i++) {
					InetAddress cl_address = client_addresses.get(i);
					int cl_port = client_ports.get(i);
					packet = new DatagramPacket(data, data.length, cl_address, cl_port);
					socket.send(packet);
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public static void main(String args[]) throws Exception {
		ChatServer server_thread = new ChatServer();
		server_thread.run();
	}
}