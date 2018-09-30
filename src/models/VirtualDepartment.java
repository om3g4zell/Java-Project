package models;

import java.io.Serializable;

/**
 * This is the abstract class of a department in the company
 * @author Louis Babuchon
 *
 */
public abstract class VirtualDepartment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The static id of the departments
	 */
	public static int ID = 0;
	
	/**
	 * The id of the department
	 */
	private int id;
	
	/**
	 * The name of the department
	 */
	private String name;
	
	/**
	 * The constructor, initiate all the attributes
	 * @param name : the name of the department
	 */
	public VirtualDepartment(String name) {
		this.name = name;
		this.id = ID;
		ID++;
	}

	/**
	 * Id Getter
	 * @return int : the department's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Name Getter
	 * @return String : the name of the department
	 */
	public String getName() {
		return name;
	}

	/**
	 * Name Setter
	 * @param name : the new department name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
