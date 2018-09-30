package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import lib.MyLocalDate;
import lib.MyLocalTime;

/**
 * This class represent an employee from the company
 * 
 * @author Louis Babuchon
 *
 */
public class Employee extends Person{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The static id of the employees
	 */
	public static int ID = 0;
	
	/**
	 * The id of the employees
	 */
	private int id;
	
	/**
	 * The department of the employee
	 */
	private StandardDepartment department;
	
	/**
	 * The bonus time of the employee
	 */
	private int bonusTime;
	
	/**
	 * The begin hour of the employee
	 */
	private LocalTime beginHour;
	
	/**
	 * The end hour of the employee
	 */
	private LocalTime endHour;
	
	/**
	 * The map of checks, associate a check with a day
	 */
	private Map<LocalDate, CheckInCheckOut> checks;
	
	/**
	 * The constructor initiate all the attributes
	 * @param firstName : the first name of the employee
	 * @param lastName : the last name of the employee
	 * @param beginHour : the begin hour of the employee
	 * @param endHour : the end hour of the employee
	 */
	public Employee(String firstName, String lastName, LocalTime beginHour, LocalTime endHour) {
		super(firstName, lastName);
		
		this.beginHour = beginHour;
		this.endHour = endHour;
		
		this.id = ID;
		ID++;
		
		bonusTime = 0;
		checks = new HashMap<>();
	}
	
	/**
	 * employee ID getter
	 * @return id : the id of the employee
	 */
	public int getId() {
		return id;
	}

	/**
	 * the standardDepartment where the employee work getter
	 * @return StandardDepartment : the standardDepartment where the employee works
	 */
	public StandardDepartment getDepartement() {
		return department;
	}

	/**
	 * the standardDepartment where the employee work Setter
	 * @param department : the department where he works
	 */
	public void setDepartement(StandardDepartment department) {
		this.department = department;
	}
	
	/**
	 * BeginHour getter
	 * @return LocalTime BeginHour : the beginHour of the employee
	 */
	public LocalTime getBeginHour() {
		return beginHour;
	}

	/**
	 * EndHour getter
	 * @return LocalTime EndHour : the endHour of the employee
	 */
	public LocalTime getEndHour() {
		return endHour;
	}
	
	/**
	 * Bonus time of the employee Getter
	 * @return LocalTime bonusTime : the bonus time of the employee
	 */
	public int getBonusTime() {
		return bonusTime;
	}

	public String toString() {
		return super.toString() + " ID: " + this.id;
	}
	
	/**
	 * The CheckInCheckOut instance of the check corresponding of the actual day
	 * @return CheckInCheckOut : the daily check
	 */
	public CheckInCheckOut getCheck() {
		return getCheck(LocalDate.now());
	}
	
	/**
	 * The CheckInCheckOut instance of the check corresponding of the date in parameter
	 * @param date : A date
	 * @return CheckInCheckOut : the check corresponding to the date
	 */
	public CheckInCheckOut getCheck(LocalDate date) {
		return checks.get(date);
	}
	
	public Map<LocalDate, CheckInCheckOut> getChecks() {
		return checks;
	}
	
	public boolean isPresent() {
		CheckInCheckOut today = getCheck();
		if(today == null)
			return false;
		if(today.getEndHourCheck() == null) return true;
		else return false;
	}
	
	/**
	 * The main function to check
	 * she compute the bonus time alert the boss (not implemented) if he is late
	 * and save the check
	 * Notify the company that the state has changed
	 * @param checkTime : the time when the employee checked
	 */
	public void check(LocalDateTime checkTime) {
		LocalDate todayConverted = MyLocalDate.of(checkTime);
		LocalTime time = MyLocalTime.of(checkTime.getHour(), checkTime.getMinute());
		
		if(checks.get(todayConverted) == null) {
			checks.put(todayConverted, new CheckInCheckOut(time));
		}else {
			CheckInCheckOut check = checks.get(todayConverted);
			if(checks.get(todayConverted).getEndHourCheck() != null) return;
			checks.get(todayConverted).setEndHour(time);
			
			long minutesWorked = (checks.get(todayConverted).getEndHourCheck().getHour() - check.getBeginHourCheck().getHour()) * 60 
					+ checks.get(todayConverted).getEndHourCheck().getMinute() - check.getBeginHourCheck().getMinute();
			
			long standardMinutesWorks = (endHour.getHour() - beginHour.getHour()) * 60
					+ endHour.getMinute() - beginHour.getMinute(); 
			
			bonusTime += minutesWorked - standardMinutesWorks;
			if(bonusTime < 0) {
				//TODO
				//Send Email
			}
		}
		// Someone check we notify the controller
		Company.getInstance().notifyObservators(this);
	}
}
