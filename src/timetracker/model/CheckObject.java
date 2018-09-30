package timetracker.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class represent a check object from the timeTracker
 * This class has for goal to be send via a packet to the main app
 * @author Louis Babuchon
 *
 */
public class CheckObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The employee id of the check
	 */
	private int employeeId;
	
	/**
	 * The date time of the check
	 */
	private LocalDateTime checkTime;
	
	/**
	 * The constructor, initiate attributes
	 * @param employeeId : the id of the employee who check
	 * @param checkTime : the date time of the check
	 */
	public CheckObject(int employeeId, LocalDateTime checkTime) {
		this.checkTime = checkTime;
		this.employeeId = employeeId;
	}

	/**
	 * Get the employee id
	 * @return int : the id of the employee
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * Get the check time of the employee
	 * @return LocalDateTime : the check time of the employee
	 */
	public LocalDateTime getCheckTime() {
		return checkTime;
	}
	
	
}
