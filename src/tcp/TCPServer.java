package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import models.Company;
import models.Employee;
import timetracker.model.CheckObject;
import timetracker.model.Packet;
import timetracker.model.ShortEmployee;

/**
 * This class represent the main server of the application.
 * The server allow simultaneous connections
 * Runs on the 35 000 port
 * @author Louis Babuchon
 *
 */
public class TCPServer implements Runnable{
	
	/**
	 * The port of the server
	 */
	private final static int port = 35000;
	
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			while(true) {
				Socket clientSocket = serverSocket.accept();
				Runnable t1 = () -> {
					try {	
						ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
						out.flush();
						
						ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
						Packet packet = (Packet)in.readObject();
						if(packet.getHeader().equals("SYNC")) {
							sync(packet, out);
						}
						else if(packet.getHeader().equals("CHECK")) {
							check(packet, out);
						}
						in.close();
						out.close();
					}catch(Exception e) {
						e.printStackTrace();
					} finally {
						try {
							clientSocket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				new Thread(t1).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * When the server receive "SYNC" the method load the employee list
	 * create shortEmployee object and send the list of short employee
	 * with "SYNC OK" header
	 * @param packet : the received packet
	 * @param out : the object output stream
	 */
	private void sync(Packet packet, ObjectOutputStream out) {
		List<Employee> employees = Company.getInstance().getEmployees();
		ArrayList<ShortEmployee> packetEmployees = new ArrayList<ShortEmployee>();
		for(Employee e : employees) {
			packetEmployees.add(new ShortEmployee(e.getId(), e.getLastName() + " " + e.getFirstName()));
		}
		Packet answerPacket = new Packet("SYNC OK", packetEmployees);
		try {
			out.writeObject(answerPacket);
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * When the server receive the header "CHECK" extract the check of the packet
	 * and save the check of the employee.
	 * The server send a new packet with the header "CHECK OK" to inform 
	 * the client that the packet was received
	 * @param packet : the received packet
	 * @param out : the object output stream
	 */
	private void check(Packet packet, ObjectOutputStream out) {
		CheckObject co = (CheckObject)packet.getObject();
		Employee e = Company.getInstance().getEmployee(co.getEmployeeId());
		if(e != null) {
			e.check(co.getCheckTime());
		}
		Packet answer = new Packet("CHECK OK");
		try {
			out.writeObject(answer);
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
