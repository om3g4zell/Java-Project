package models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a standardDepartment from the company
 * @author Louis Babuchon
 *
 */
public class StandardDepartment extends VirtualDepartment{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The manager of the standardDepartment
	 */
	private Manager manager;
	
	/**
	 * The list of all the employees working in the department
	 */
	private List<Employee> employees;
	
	/**
	 * The constructor, initiate all the attributes
	 * @param name : the name of the department
	 */
	public StandardDepartment(String name) {
		super(name);
		employees = new ArrayList<>();
	}

	/**
	 * Manager Getter
	 * @return Manager : The manager of the department
	 */
	public Manager getManager() {
		return manager;
	}
	
	/**
	 * Manager Setter
	 * @param manager : the new manager of the department
	 */
	public void setManager(Manager manager) {
		this.manager = manager;
		manager.setStandardDepartmentManaged(this);
		if(!isWorkingHere(manager))
			addEmployee(manager);
	}
	
	/**
	 * Add an employee to the department
	 * @param e : the employee to add
	 * @return StandardDepartment : the actual department
	 */
	public StandardDepartment addEmployee(Employee e) {
		employees.add(e);
		e.setDepartement(this);
		return this;
	}
	
	/**
	 * Check if the employee is working here
	 * @param e : the employee to check
	 * @return boolean : true if he works here or false else
	 */
	public boolean isWorkingHere(Employee e) {
		return employees.contains(e);
	}
	
	/**
	 * Remove an employee from the department
	 * @param e : the employee to remove
	 */
	public void removeEmployee(Employee e) {
		employees.remove(e);
	}
	
	/**
	 * Remove the manager
	 * @param m : the Manager
	 */
	public void removeManager(Manager m) {
		if(this.manager == m) {
			manager = null;
			employees.remove(m);
		}
	}
	
	/**
	 * Change the manager of the department
	 * @param m : the new manager
	 */
	public void changeManager(Manager m) {
		if(employees.contains(m)) {
			if(manager != null)
				manager.setStandardDepartmentManaged(null);
			m.setStandardDepartmentManaged(this);
			manager = m;
			Company.getInstance().notifyObservators(m);
		}
	}
	
	/**
	 * Get the number of currently here employees
	 * @return int : the number of employees currently here
	 */
	public int numberOfWorkingEmployee() {
		int number = 0;
		for(Employee e : employees) {
			if(e.isPresent()) number++;
		}
		return number;
	}
	
	/**
	 * Get the number of employees working here
	 * @return int : the number of employees
	 */
	public int numberOfEmployee() {
		return employees.size();
	}
	
	/**
	 * toString method show the name of the standardDepartment
	 */
	public String toString() {
		return "" + getName();
	}

}
