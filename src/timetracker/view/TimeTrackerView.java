package timetracker.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import timetracker.MainTracker;
import timetracker.controler.TimeTrackerControler;
import timetracker.model.ShortEmployee;
import timetracker.model.TimeTracker;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;

/**
 * This class represent the view of the timeTracker model
 * according to the MVC pattern
 * @author Louis Babuchon
 *
 */
@SuppressWarnings("serial")
public class TimeTrackerView extends JFrame {

	/**
	 * The controller
	 */
	private TimeTrackerControler timeTrackerControler;
	
	/**
	 * The sync button
	 */
	private JButton syncButton;
	
	/**
	 * The check Button
	 */
	private JButton checkButton;
	
	/**
	 * The combo box of all the employees
	 */
	private JComboBox<ShortEmployee> employeeComboBox;
	
	/**
	 * The label of the actual dateTime
	 */
	private JLabel dateTimeLabel;
	
	/**
	 * The label of the rounded dateTime
	 */
	private JLabel roundsTimeLabel;

	/**
	 * The constructor, initiate the frame, change the default look and feel and 
	 * add the listeners to the buttons
	 */
	public TimeTrackerView() {
		
		timeTrackerControler = new TimeTrackerControler(this);
		
		// Change the look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		addWindowListener(new WindowAdapter() {
	    	@Override
            public void windowClosing(WindowEvent e)
            {
	    		String dir;
	    		try {
					dir = new File(".").getCanonicalPath() + MainTracker.directory;
					String path = dir + MainTracker.PATH;
	                TimeTracker.getInstance().serialize(path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
		});
		
		this.setTitle("TimeTracker emulator");
	    
	   	this.setSize(300, 225);
	    
	    
	    this.setLocationRelativeTo(null);
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             

	    this.setResizable(false);
	   
	    getContentPane().setLayout(null);
	    
	    dateTimeLabel = new JLabel("New label");
	    dateTimeLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
	    dateTimeLabel.setBounds(37, 10, 247, 33);
	    getContentPane().add(dateTimeLabel);
	    
	    roundsTimeLabel = new JLabel("New label");
	    roundsTimeLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 21));
	    roundsTimeLabel.setBounds(105, 54, 179, 33);
	    getContentPane().add(roundsTimeLabel);
	    
	    syncButton = new JButton("Synchroniser");
	    syncButton.setBounds(10, 124, 135, 23);
	    getContentPane().add(syncButton);
	    syncButton.addActionListener(e -> {timeTrackerControler.sync();});
	    
	    employeeComboBox = new JComboBox<>();
	    employeeComboBox.setBounds(10, 158, 135, 27);
	    getContentPane().add(employeeComboBox);
	    
	    checkButton = new JButton("Check");
	    checkButton.setBounds(154, 158, 109, 27);
	    getContentPane().add(checkButton);
	    checkButton.addActionListener(e -> { timeTrackerControler.check();});
	    
	    timeTrackerControler.setUpTimeTimer();
	    timeTrackerControler.refresh();
	}
	
	/**
	 * Get the combobox of employees
	 * @return JComboBox of Short Employees : the comboBox of the employees
	 */
	public JComboBox<ShortEmployee> getEmployeeComboBox() {
		return employeeComboBox;
	}

	/**
	 * Get the DateTime label
	 * @return JLabel : the dateTime label
	 */
	public JLabel getDateTimeLabel() {
		return dateTimeLabel;
	}

	/**
	 * Get the rounded time label
	 * @return JLabel : the rounded time label
	 */
	public JLabel getRoundsTimeLabel() {
		return roundsTimeLabel;
	}
}
