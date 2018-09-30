package models;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * This class represent a peer of check
 * the morning check and the evening check
 * @author Louis Babuchon
 *
 */
public class CheckInCheckOut implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The begin hour check
	 */
	private LocalTime beginHour;
	
	/**
	 * The end hour check
	 */
	private LocalTime endHour;
	
	/**
	 * The constructor, initiate the begin hour
	 * according to the parameter and set the endHour to null
	 * @param beginHour : the begin hour check
	 */
	public CheckInCheckOut(LocalTime beginHour) {
		this.beginHour = beginHour;
		endHour = null;
	}
	
	/**
	 * BeginHour checked getter
	 * @return LocalTime : the begin hour checked
	 */
	public LocalTime getBeginHourCheck() {
		return beginHour;
	}
	
	/**
	 * EndHour checked getter
	 * @return LocalTime : the end hour checked
	 */
	public LocalTime getEndHourCheck() {
		return endHour;
	}
	
	/**
	 * Set the endHour check
	 * @param endHour : The hour of the check
	 */
	public void setEndHour(LocalTime endHour) {
		this.endHour = endHour;
	}
}
