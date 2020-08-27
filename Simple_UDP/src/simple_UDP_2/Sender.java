package simple_UDP_2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Sender {

	public Sender() throws Exception{
		
		DatagramSocket socket = new DatagramSocket();
		Scanner keyboard = new Scanner(System.in);
		
		
		while(true){
			//Sending part
			System.out.println("Enter your message: ");
			String message = keyboard.nextLine(); // we store the message
			byte[] buffer = message.getBytes();   // translate the message into bytes, and we store those bytes in our buffer object
           
			
			// With UDP which is "best effort" protocol: each packet is built and sent separately. Even a packet is not delivered to its destination- no worries, this is how UDP is expected to work
			
			
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("127.0.0.1"), 2020); // we put the buffer into our packet. buffer.length: specify the amount of data

			socket.send(packet);
			System.out.println("Sent: " + message);

			// Receiving part
			buffer = new byte[1500]; // here we used the same buffer we used to send a message. The buffer still has the message that we sent so that's why we are creating a new array for this object
			packet = new DatagramPacket(buffer, buffer.length); // this packet has some data in it, we created a new packet that is going to be empty and ready to receive the incoming packet.
			socket.receive(packet);

			message = new String(buffer).trim(); // extract the message from the buffer, the trim methof cut all the excess data
			System.out.println("Received: " + message);
		}
	}
	
	
	// The main method is an entry to our application
	public static void main(String[] args) {
		try {
			new Sender();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();   // this method prints out anything that went wrong
		}
	}
}
