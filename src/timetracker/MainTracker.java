package timetracker;

import java.io.File;
import java.io.IOException;

import timetracker.model.TimeTracker;
import timetracker.view.TimeTrackerView;

/**
 * This is the class who contain the main function
 * @author Louis Babuchon
 *
 */
public class MainTracker {

	/**
	 * The folder to save the serialization file
	 */
	public static final String directory = "/data";
	
	/**
	 * The name of the serialization file
	 */
	public static final String PATH = "/tracker.ser";
	
	/**
	 * The main function, execute the deserialization
	 * and open the frame
	 * @param args : the arguments of the program
	 */
	public static void main(String[] args) {
		
		TimeTracker tt = TimeTracker.getInstance();
		
		try {
			String dir = new File(".").getCanonicalPath() + directory;
			String path = dir + PATH;
			if((new File(path)).exists()) {
				tt.deserialize(path);
			}
			new File(dir).mkdirs();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TimeTrackerView timeTrackerView = new TimeTrackerView();
		timeTrackerView.setVisible(true);

	}

}
