package main;

import java.io.File;
import java.io.IOException;

import models.Company;
import tcp.TCPServer;
import views.MyWindow;

/**
 * This is the main class of the main application of timetracking
 * 
 * @author Louis Babuchon
 *
 */
public class MainApp {

	/**
	 * The folder to save/load the serialized informations
	 */
	public static final String directory = "/data";
	
	/**
	 * The name of the serialized file
	 */
	public static final String PATH = "/app.ser";
	
	/**
	 * This is the main function of the program
	 * 
	 * Deserialize the file
	 * Launch the frame
	 * Start the server
	 * @param args : args given in command line
	 */
	public static void main(String[] args) {
		Company c = Company.getInstance();
		
		try {
			String dir = new File(".").getCanonicalPath() + directory;
			String path = dir + PATH;
			if((new File(path)).exists()) {
				c.deserialize(path);
			}
			new File(dir).mkdirs();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MyWindow window = new MyWindow();
		window.setVisible(true);
		new Thread(new TCPServer()).start();
	}

}
