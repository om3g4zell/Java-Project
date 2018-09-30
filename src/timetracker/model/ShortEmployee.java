package timetracker.model;

import java.io.Serializable;

/**
 * This class represent a simplified version of an employee
 * @author Louis Babuchon
 *
 */
public class ShortEmployee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The id of the employee
	 */
	private int id;
	
	/**
	 * The last name and the fist name of the employee
	 */
	private String name;
	
	/**
	 * The constructor, initiates attributes
	 * @param id : the id of the employee
	 * @param name : the name of the employee
	 */
	public ShortEmployee(int id, String name){
		this.id = id;
		this.name = name;
	}

	/**
	 * Get the id of the employee
	 * @return int : the id of the employee
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the name of the employee
	 * @return String : the name of the employee
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * The toString method
	 * @return String : "ID : the id of the employee, the name of the employee"
	 */
	public String toString() {
		return "ID : "+ id+ " " + name;
	}
}
