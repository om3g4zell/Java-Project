package models;

import java.io.Serializable;

/**
 * This class is abstract and represent a person
 * 
 * @author Louis Babuchon
 *
 */
public abstract class Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The first name of the person
	 */
	private String firstName;
	
	/**
	 * The last name of the person
	 */
	private String lastName;
	
	/**
	 * The constructor, initiate attributes
	 * @param firstName : the first name of the person
	 * @param lastName : the last name of the person
	 */
	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * FirstName Getter
	 * @return String : the first name of the person
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * FirstName Setter
	 * @param firstName : the new first name of the person
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * LastName Getter
	 * @return String : the last name of the person
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * LasttName Setter
	 * @param lastName : the new last name of the person
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return String : firstName and LastName
	 */
	public String toString() {
		return lastName + " " + firstName;
	}
}
