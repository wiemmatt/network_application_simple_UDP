package simple_UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Sender {

	public Sender() throws Exception{
		
		DatagramSocket socket = new DatagramSocket();
		Scanner keyboard = new Scanner(System.in);
		//Sending part
		System.out.println("Enter your message: ");
		String message = keyboard.nextLine();
		byte[] buffer = message.getBytes();
		
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("127.0.0.1"), 2020);
		
		socket.send(packet);
		System.out.println("Sent: " + message);
		
		
		//Receiving part
		buffer = new byte[1500];
		packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
		
		message = new String (buffer).trim();
		System.out.println("Received: " + message);
		
	}
	
	public static void main(String[] args) {
		try {
			new Sender();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
