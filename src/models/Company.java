package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import lib.MyLocalTime;


/**
 * This is the main class of the model
 * She compute all the operations
 * 
 * Extends Observable of the java.util package
 * To notify the controller when the state change
 * according to the MVC pattern
 * @author Louis Babuchon
 *
 */
public class Company extends Observable implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The company is a singleton
	 * Then we instantiate her here
	 */
	private static Company company = new Company("Mon entreprise");
	
	/**
	 * The boss attribute
	 */
	private Boss boss;
	
	/**
	 * The manager department attribute
	 */
	private ManagementDepartment managerDepartment;
	
	/**
	 * The list of all the employees in the company
	 */
	private List<Employee> employees;
	
	/**
	 * The list of all the standardDepartments of the company
	 */
	private List<StandardDepartment> standardDepartments;
	
	/**
	 * The name of the company
	 */
	private String name;
	
	/**
	 * The separator constant according to the CSV format
	 */
	private final String CSVSperarator = ";";
	
	/**
	 * The first line of the CSV format
	 */
	private final String CSVInfo = "Status (Manager/Employé);Nom;Prénom;HeureDebut(hh:mm);HeureFin(hh:mm)";
	
	/**
	 * The constructor, initiate all the attributes
	 * @param name : the name of the company
	 */
	private Company(String name) {
		employees = new ArrayList<>();
		standardDepartments = new ArrayList<>();
		boss = Boss.getInstance();
		managerDepartment = ManagementDepartment.getInstance();
		this.name = name;
	}
	
	/**
	 * The singleton Getter of the company
	 * @return Company : the singleton of the company
	 */
	public static Company getInstance() {
		return company;
	}
	
	/**
	 * Name of the company getter
	 * @return String : the name of the company
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Boss of the company getter
	 * @return Boss : the boss of the company
	 */
	public Boss getBoss() {
		return boss;
	}
	
	/**
	 * ManagmentDepartment getter
	 * @return ManagementDepartment : the managment department
	 */
	public ManagementDepartment getManagementDepartment() {
		return managerDepartment;
	}
	
	/**
	 * Create a manager and add him on the different collections
	 * Notify observers that the state has changed
	 * @param firstName : the first name of the manager
	 * @param lastName : the last name of the manager
	 * @param beginHour : the begin hour of the manager
	 * @param endHour : the end hour of the manager
	 * @return Manager : the manager created
	 * 
	 * @throws IllegalArgumentException : if one of the parameters is null we throw an exception
	 */
	public Manager createManager(String firstName, String lastName, LocalTime beginHour, LocalTime endHour) throws IllegalArgumentException{
		if(firstName == null || lastName == null || beginHour == null || endHour == null) throw new IllegalArgumentException("Un des arguments est null");
		Manager m = new Manager(firstName, lastName, beginHour, endHour);
		this.employees.add(m);
		managerDepartment.addManager(m);
		notifyObservators(m);
		return m;
	}
	
	/**
	 * set up the manager and the standard department
	 * Notify observers that the state has changed
	 * @param m : a manager
	 * @param sd : the standardDepartment where the manager will work
	 * @throws IllegalArgumentException : if one of the parameters is null we throw an exception
	 */
	public void addManager(Manager m, StandardDepartment sd) throws IllegalArgumentException{
		if(m == null || sd == null) throw new IllegalArgumentException("Un des arguments est null");
		sd.setManager(m);
		if(!employees.contains(m))
			employees.add(m);
		if(!managerDepartment.isHere(m))
			managerDepartment.addManager(m);
		notifyObservators(m);
	}
	
	/**
	 * Create an employee and initialize it
	 * Notify observers that the state has changed
	 * @param firstName : the first name of the employee
	 * @param lastName : the last name of the employee
	 * @param beginHour : the begin hour of the employee
	 * @param endHour : the end hour of the employee
	 * @param sd : the standard department where the employee will work
	 * @return Employee : the employee created
	 * 
	 * @throws IllegalArgumentException : if one of the fourth first parameters is null we throw an exception
	 */
	public Employee createEmployee(String firstName, String lastName, LocalTime beginHour, LocalTime endHour, StandardDepartment sd) throws IllegalArgumentException{
		if(firstName == null || lastName == null || beginHour == null || endHour == null) throw new IllegalArgumentException("Un des arguments est null");
		Employee e = new Employee(firstName, lastName, beginHour, endHour);
		employees.add(e);
		if(sd != null)
			sd.addEmployee(e);
		
		notifyObservators(e);
		return e;
	}
	
	/**
	 * Add an employee created to the standard department
	 * Notify observers that the state has changed
	 * @param e : the employee to add
	 * @param sd : the standard department where the employee will work
	 * @throws IllegalArgumentException : if one the the arguments is null
	 */
	public void addEmployee(Employee e, StandardDepartment sd) throws IllegalArgumentException{
		if(e == null || sd == null) throw new IllegalArgumentException("Un des arguments est null");
		if(!employees.contains(e))
			employees.add(e);
		sd.addEmployee(e);
		notifyObservators(e);
	}
	
	/**
	 * Remove an employee from all collections
	 * Notify observers that the state has changed
	 * @param e : the employee to remove
	 * @return boolean : true if the employee has been removed false else
	 */
	public boolean removeEmployee(Employee e) {
		if(!employees.contains(e)) return false;
		if(e instanceof Manager) {
			Manager m = (Manager) e;
			if(m.getDepartement() != null)
				m.getDepartement().removeManager(m);
			managerDepartment.removeManager(m);
		}
		if(e.getDepartement() != null)
			e.getDepartement().removeEmployee(e);
		employees.remove(e);
		notifyObservators(e);
		return true;
	}
	
	/**
	 * Remove a standard department and mark all the employees
	 * has "no departments"
	 * Notify observers that the state has changed
	 * @param sd : the standard department to remove
	 * @return boolean : true if the department has been created false else
	 */
	public boolean removeDepartment(StandardDepartment sd) {
		if(sd == null) return false;
		List<Employee> employees = getEmployees(sd);
		for(Employee e : employees) {
			if(e instanceof Manager) {
				Manager m = (Manager)e;
				m.setStandardDepartmentManaged(null);
			}
			e.setDepartement(null);
		}
		standardDepartments.remove(sd);
		notifyObservators(sd);
		return true;
	}
	/**
	 * Create a standard department
	 * Notify observers that the state has changed
	 * @param name : the name of the standard department
	 * @return StandardDepartment : the standard department created
	 * @throws IllegalArgumentException : if the name is null
	 */
	public StandardDepartment createStandardDepartment(String name) throws IllegalArgumentException{
		if(name == null) throw new IllegalArgumentException("Le nom ne peut pas être null");
		StandardDepartment sd = new StandardDepartment(name);
		standardDepartments.add(sd);
		notifyObservators(sd);
		return sd;
	}
	
	/**
	 * Change the actual manager of the department into the 
	 * new manager
	 * Notify observers that the state has changed
	 * @param sd : the standard department to change the manager
	 * @param m : the new manager
	 * @throws IllegalStateException : if the new manager don't work 
	 * in the department
	 */
	public void setManager(StandardDepartment sd, Manager m) throws IllegalStateException{
		if(!sd.isWorkingHere(m)) throw new IllegalStateException("Le manager doit travailler dans le department");
		sd.setManager(m);
		notifyObservators(sd);
	}
	
	/**
	 * Standard department getter via the id
	 * @param id : the id of the department
	 * @return StandardDepartment : the standard department corresponding to the id
	 * null if the department don't exist
	 */
	public StandardDepartment getStandarDepartment(int id) {
		for(StandardDepartment st : standardDepartments) {
			if(st.getId() == id) return st;
		}
		return null;
	}
	
	/**
	 * Standard department getter via the name of the department
	 * @param name : the name of the department
	 * @return StandardDepartment : the standard department corresponding to the name of the department
	 * null if the department don't exist
	 */
	public StandardDepartment getStandarDepartment(String name) {
		for(StandardDepartment st : standardDepartments) {
			if(st.getName().equals(name)) return st;
		}
		return null;
	}
	
	/**
	 * Return all the standard departments of the company
	 * @return List of StandardDepartment : all the departments
	 */
	public List<StandardDepartment> getStandardDepartments() {
		return standardDepartments;
	}
	
	/**
	 * Get all the managers
	 * @return List of Manager : the list of all the managers
	 */
	public List<Manager> getManagers() {
		List<Manager> managers = new ArrayList<Manager>();
		for(Employee e : employees) {
			if(e instanceof Manager) managers.add((Manager) e);
		}
		return managers;
	}
	
	/**
	 * Get the list of all the employees
	 * @return List of Employee : the list of all the employees
	 */
	public List<Employee> getEmployees() {
		return this.employees;
	}
	
	/**
	 * Get all the employees working in the department
	 * @param sd : the standard department
	 * @return List of Employee : the list of the employees working in the standard department
	 */
	public List<Employee> getEmployees(StandardDepartment sd) {
		List<Employee> employees = new ArrayList<>();
		for(Employee e : this.employees) {
			if(e.getDepartement() == sd) employees.add(e);
		}
		return employees;
	}
	
	/**
	 * Get the employee corresponding to his id
	 * @param id : the id of the employee
	 * @return Employee : the employee
	 * null if the employee don't exist
	 */
	public Employee getEmployee(int id) {
		for(Employee e : employees) {
			if(e.getId() == id) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Get the employee corresponding to his first name and last name
	 * @param firstName : the first name of the employee
	 * @param lastName : the last name of the employee
	 * @return Employee : the employee corresponding to his first name and last name
	 * null if the employee don't exist
	 */
	public Employee getEmployee(String firstName, String lastName) {
		for(Employee e : employees) {
			if(e.getFirstName().equals(firstName) && e.getLastName().equals(lastName)) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Serialize the company in a file according to the path
	 * @param path : the path of the new file
	 */
	public void serialize(String path) {
		FileOutputStream fos;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(path);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(getInstance());
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(oos != null)
					oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Deserialize the company from a file according to the given path
	 * @param path : the path of the file
	 * @return Company the new company
	 */
	public Company deserialize(String path) {
		FileInputStream fis;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(path);
			ois = new ObjectInputStream(fis);
			company = (Company)ois.readObject();
			VirtualDepartment.ID = getMaxDepartmentId() + 1;
			Employee.ID = getMaxEmployeeId() + 1;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ois != null)
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return company;
	}
	
	/**
	 * Get the max id of the employees
	 * @return int : the max Id of the employees
	 */
	private int getMaxEmployeeId() {
		int max = -1;
		for(Employee e : company.employees) {
			if(e.getId() > max) max = e.getId();
		}
		return max;
	}
	
	/**
	 * Get the max id of the departments
	 * @return int : the max Id of the departments
	 */
	private int getMaxDepartmentId() {
		int max = -1;
		for(VirtualDepartment vd : company.standardDepartments) {
			if(vd.getId() > max) max = vd.getId();
		}
		if(managerDepartment.getId() > max) max = managerDepartment.getId();
		
		return max;
	}
	
	/**
	 * Export all the employees in a CSV file
	 * with the format : STATUS;LASTNAME;FIRSTNAME;BEGINHOUR;ENDHOUR
	 * @param path : the path of the file
	 */
	public void exportCSV(String path) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			
			// On écrit l'entête
			bw.write(CSVInfo);
			bw.newLine();
			// On écrit tout les employés
			for(Employee e : employees) {
				
				bw.newLine();
				
				if(e instanceof Manager) bw.write("Manager");
				else bw.write("Employé");
				
				bw.write(CSVSperarator);
				bw.write(e.getLastName());
				bw.write(CSVSperarator);
				bw.write(e.getFirstName());
				bw.write(CSVSperarator);
				bw.write(e.getBeginHour().toString());
				bw.write(CSVSperarator);
				bw.write(e.getEndHour().toString());
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bw != null)
					bw.close();
				if(fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Import employyes form a CSV file
	 * with the format : STATUS;LASTNAME;FIRSTNAME;BEGINHOUR;ENDHOUR
	 * @param path : the path of the file
	 */
	public void importCSV(String path) {
		FileReader fr = null;
		BufferedReader br = null;
		String line = "";
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			
			// On enleve la première ligne
			br.readLine();
			
			while((line = br.readLine()) != null) {
				String[] elements = line.split(CSVSperarator);
				
				if(elements[0].equals("Manager")) {
					LocalTime beginHour = MyLocalTime.of(Integer.parseInt(elements[3].split(":")[0]), Integer.parseInt(elements[3].split(":")[1]));
					LocalTime endHour = MyLocalTime.of(Integer.parseInt(elements[4].split(":")[0]), Integer.parseInt(elements[4].split(":")[1]));
					createManager(elements[2], elements[1], beginHour, endHour);
				}
				else if(elements[0].equals(("Employé"))) {
					LocalTime beginHour = MyLocalTime.of(Integer.parseInt(elements[3].split(":")[0]), Integer.parseInt(elements[3].split(":")[1]));
					LocalTime endHour = MyLocalTime.of(Integer.parseInt(elements[4].split(":")[0]), Integer.parseInt(elements[4].split(":")[1]));
					createEmployee(elements[2], elements[1], beginHour, endHour, null);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(br != null)
					br.close();
				if(fr != null)
					fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Notify all the observators that the state has changed
	 * @param o : the object given to the observators
	 */
	public void notifyObservators(Object o) {
		setChanged();
		notifyObservers(o);
	}
}
