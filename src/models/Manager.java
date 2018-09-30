package models;

import java.time.LocalTime;

/**
 * This class represent a manager form the company
 * 
 * @author Louis Babuchon
 *
 */
public class Manager extends Employee{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The standard department managed
	 */
	private StandardDepartment standardDepartmentManaged;
	
	/**
	 * The constructor, initiate the attributes
	 * @param firstName : the first name of the manager
	 * @param lastName : the last name of the manager
	 * @param beginHour : the begin hour
	 * @param endHour : the end hour
	 */
	public Manager(String firstName, String lastName, LocalTime beginHour, LocalTime endHour) {
		super(firstName, lastName, beginHour, endHour);
	}
	
	/**
	 * StandardDepartment Getter
	 * @return StandardDepartment : the StandarDepartment managed
	 */
	public StandardDepartment getStandardDepartmentManaged() {
		return standardDepartmentManaged;
	}

	/**
	 * StandardDepartment Setter
	 * @param sdm : the new department to manage
	 */
	public void setStandardDepartmentManaged(StandardDepartment sdm) {
		this.standardDepartmentManaged = sdm;
	}
	
	/**
	 * The toString method 
	 * Show the employee information + manager state
	 */
	public String toString() {
		return "Manager " + super.toString();
	}

}
