package timetracker.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import timetracker.tcp.TCPClient;

/**
 * This class represent the main model of the timerTracker
 * extends Observable of java util according to the MVC Pattern
 * to notify the controller when the state has changed
 * 
 * @author Louis Babuchon
 *
 */
public class TimeTracker extends Observable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * This class is a singleton then we instantiate the model here
	 */
	private static TimeTracker timeTracker = new TimeTracker();
	
	/**
	 * The list of the synchronized employees
	 */
	private ArrayList<ShortEmployee> employees;
	
	/**
	 * The client
	 */
	private TCPClient client;
	
	/**
	 * The constructor, initiates the attributes
	 */
	public TimeTracker() {
		employees = new ArrayList<>();
		client = new TCPClient();
	}
	
	/**
	 * Get the instance of the class
	 * @return TimeTracker : the instance of the class according
	 * to the singleton pattern
	 */
	public static TimeTracker getInstance() {
		return timeTracker;
	}
	
	/**
	 * Notify observers that the state has changed
	 */
	public void notifyObservators() {
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Change the employee List
	 * notify that the state has changed
	 * @param employees : the new employee list
	 */
	public void refreshEmployeeList(ArrayList<ShortEmployee> employees) {
		this.employees = employees;
		notifyObservators();
	}
	
	/**
	 * Get the ShortEmployee list
	 * @return ArrayList of ShortEmployee : the short employee list
	 */
	public ArrayList<ShortEmployee> getEmployees() {
		return employees;
	}
	
	/**
	 * change the employee list according the the synchronization with
	 * the main app
	 */
	public void syncRequest() {
		refreshEmployeeList(client.syncEmployee());
	}
	
	/**
	 * Ask the client to send a check packet to the server
	 * @param packet : the packet to send
	 */
	public void sendPacket(Packet packet) {
		client.addPacket(packet);
	}
	
	/**
	 * The serialization method, save the model state
	 * in a file
	 * @param path : the path of the file
	 */
	public void serialize(String path) {
		FileOutputStream fos;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(path);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(getInstance());
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(oos != null)
					oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Deserialize the model with a file
	 * @param path : the path of the file
	 * @return TimeTracker : the new instance of the singleton
	 */
	public TimeTracker deserialize(String path) {
		FileInputStream fis;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(path);
			ois = new ObjectInputStream(fis);
			timeTracker = (TimeTracker)ois.readObject();
			timeTracker.client.setUpCheckTimer();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return timeTracker;
	}
}
