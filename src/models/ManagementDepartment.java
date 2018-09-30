package models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent the management department
 * @author Louis Babuchon
 *
 */
public class ManagementDepartment extends VirtualDepartment{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The management department is a singleton
	 * then we instanciate him here
	 */
	private static ManagementDepartment managmentDepartement = new ManagementDepartment("Département Management", Boss.getInstance());
	
	/**
	 * The list of all the managers
	 */
	private List<Manager> managers;
	
	/**
	 * The boss
	 */
	private Boss boss;
	
	/**
	 * The constructor, initiate attributes
	 * @param name : the name of the department
	 * @param boss : the boss of the company
	 */
	private ManagementDepartment(String name, Boss boss) {
		super(name);
		managers = new ArrayList<>();
		this.boss = boss;
	}
	
	/**
	 * Add a manager
	 * @param m : The manager to add
	 */
	public void addManager(Manager m) {
		if(!managers.contains(m))
			managers.add(m);
	}
	
	/**
	 * Remove a manager
	 * @param m : The manager to remove
	 */
	public void removeManager(Manager m) {
		managers.remove(m);
	}

	/**
	 * Boss Getter
	 * @return Boss : the Boss
	 */
	public Boss getBoss() {
		return boss;
	}
	
	/**
	 * Singleton Getter
	 * @return ManagementDepartment : The singleton
	 */
	public static ManagementDepartment getInstance() {
		return managmentDepartement;
	}
	
	/**
	 * Check if the manager is present
	 * @param m : The manager to check
	 * @return boolean : true if he is present false else
	 */
	public boolean isHere(Manager m) {
		return managers.contains(m);
	}

}
