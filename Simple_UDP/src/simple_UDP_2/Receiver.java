package simple_UDP_2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Receiver {

	public Receiver() throws Exception{
		
		DatagramSocket socket= new DatagramSocket(2020); //here we create a Datagram socket, it is a door that we use to throw out the data and receive the new data that's coming in from the other side   //we still need to reserve a port number in our operating system, and also a network application has to have a port number
		System.out.println("Receiver is running");
		while(true){
		byte[] buffer = new byte[1500]; // MTU = 1500  // We create a buffer which is an array of bytes
		DatagramPacket packet = new DatagramPacket(buffer,buffer.length); // the buffer where we place the data, the message is in the packet object
		
		socket.receive(packet); // retreiving sender's message

		String message = new String (buffer).trim();
		System.out.println("Received: " + message);
		
		
		//Since the sender is the first one to send a packet, we had to define the destination IP address and the destination port number but since the receiver is just replying to the sender, the receiver can just pull out the sender's IP address from the packet and the sender's source port number.
		InetAddress senders_address = packet.getAddress(); //sending part, in order to know where to send our response, we need to get the IP adress of the sender, we get that from the packet.
		int senders_port = packet.getPort(); //and the port number that the sender used, their source number is our destination


        // Sending part
		message = "OK."; // and then we are going to store their message into the message string
		buffer = message.getBytes(); // switch that message (string) into bytes, and fill the buffer with the message
		packet = new DatagramPacket(buffer, buffer.length, senders_address, senders_port); // fill out our packet, here we create a new packet, and what we need the buffer, our message is actually in the buffer
		socket.send(packet);
		System.out.println("Sent: " + message);

		}
	}
	public static void main(String[] args) {
		try {
			new Receiver();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
