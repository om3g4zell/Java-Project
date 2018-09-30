package timetracker.controler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import lib.MyLocalDate;
import lib.MyLocalTime;
import timetracker.model.CheckObject;
import timetracker.model.Packet;
import timetracker.model.ShortEmployee;
import timetracker.model.TimeTracker;
import timetracker.view.TimeTrackerView;

/**
 * This class is the controller of the TimeTrackerView Class
 * according to the MVC Pattern
 * 
 * implements the Observer pattern of java util to notify the controller when 
 * the model has changed
 * 
 * @author Louis Babuchon
 *
 */
public class TimeTrackerControler implements Observer{
	
	/**
	 * The timeTracker model
	 */
	private TimeTracker timeTracker;
	
	/**
	 * The timeTracker view
	 */
	private TimeTrackerView timeTrackerView;
	
	/**
	 * The constructor initiate attributes and add this class to the observators list
	 * of the model
	 * @param timeTrackerView : the view
	 */
	public TimeTrackerControler(TimeTrackerView timeTrackerView) {
		this.timeTrackerView = timeTrackerView;
		timeTracker = TimeTracker.getInstance();
		timeTracker.addObserver(this);
	}
	
	/**
	 * Set up the timer thread who change the date time
	 * of the view each seconds
	 */
	public void setUpTimeTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				LocalDateTime dateTime = LocalDateTime.now();
				timeTrackerView.getDateTimeLabel().setText(dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
				timeTrackerView.getRoundsTimeLabel().setText("Disons " + MyLocalTime.of(dateTime.getHour(), dateTime.getMinute()));
			}
			
		}, 0, 1000);
	}
	
	/**
	 * inform the model that the tracker reveive a new check
	 * to send it to the main app
	 */
	public void check() {
		ShortEmployee se = (ShortEmployee)timeTrackerView.getEmployeeComboBox().getSelectedItem();
		if(se == null) return;
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = LocalDateTime.of(MyLocalDate.of(localDateTime), MyLocalTime.of(localDateTime.toLocalTime().getHour(), localDateTime.toLocalTime().getMinute()));
		CheckObject checkObject = new CheckObject(se.getId(), localDateTime);
		timeTracker.sendPacket(new Packet("CHECK", checkObject));
	}
	
	/**
	 * Inform the model that we want to synchronize
	 * the employee list from the main app
	 */
	public void sync() {
		timeTracker.syncRequest();
	}
	
	/**
	 * Refresh the employee list of the view according to the model
	 */
	public void refresh() {
		timeTrackerView.getEmployeeComboBox().removeAllItems();
		ArrayList<ShortEmployee> employees = timeTracker.getEmployees();
		for(ShortEmployee e : employees) {
			timeTrackerView.getEmployeeComboBox().addItem(e);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		refresh();
	}
}
