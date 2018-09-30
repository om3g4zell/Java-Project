package timetracker.tcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import timetracker.model.ShortEmployee;
import timetracker.model.Packet;

/**
 * This class represent the client his goal is to send packet to the 
 * main app via TCP
 * @author Louis Babuchon
 *
 */
public class TCPClient implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int frequency = 500;
	
	/**
	 * The port to send packets
	 */
	private final static int port = 35000;
	
	/**
	 * The remote  ip address
	 */
	private final static String ipAdress = "localhost";
	
	/**
	 * The list of pending packets
	 */
	private ArrayList<Packet> pendingPacket;
	
	/**
	 * The constructor, initiates attributes and start the timer
	 * for the send of checks
	 */
	public TCPClient() {
		pendingPacket = new ArrayList<>();
		setUpCheckTimer();
	}
	
	/**
	 * Add a packet to the pending packet list
	 * @param packet : the packet to send
	 */
	public void addPacket(Packet packet) {
		pendingPacket.add(packet);
	}
	
	/**
	 * Send packets at regular intervals
	 */
	public void setUpCheckTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				sendCheckPacket();
			}
			
		}, 0, frequency);
	}
	
	/**
	 * Send a check to the server according to the pending packet list
	 */
	private void sendCheckPacket() {
		if(pendingPacket.isEmpty()) return;
		
		try {
			Socket socket = new Socket(ipAdress, port);
			
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
			out.writeObject(pendingPacket.get(0));
			out.flush();
		
			Object answer = in.readObject();
			if(((Packet)answer).getHeader().equals("CHECK OK")) pendingPacket.remove(0);
			
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("La connection à échoué");
		}
	}

	/**
	 * Execute the protocol to synchronize the employee list with the main app
	 * @return ArrayList of short employee : the new employees list
	 */
	public ArrayList<ShortEmployee> syncEmployee() {
	ArrayList<ShortEmployee> shortEmployees = new ArrayList<ShortEmployee>();
		try {
			Socket socket = new Socket(ipAdress, port);
			
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
			out.writeObject(new Packet("SYNC"));
			out.flush();
			
			Packet packet = (Packet)in.readObject();
			if(packet.getHeader().equals("SYNC OK")) {
				ArrayList<?> employees = (ArrayList<?>)packet.getObject();
				for(Object o : employees) {
					ShortEmployee se = (ShortEmployee)o;
					shortEmployees.add(se);
				}
			}
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shortEmployees;
	}

}
