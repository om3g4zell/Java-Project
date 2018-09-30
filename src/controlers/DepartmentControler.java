package controlers;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import models.Company;
import models.Employee;
import models.Manager;
import models.StandardDepartment;
import views.department.DepartmentInformationPanel;
import views.department.DepartmentView;

/**
 * This class is the controller of the DepartmentView class
 * according to the MVC pattern
 * 
 * Implements the Observer pattern of Java util to notify the controler
 * when the model's state has changed
 * 
 * @author Louis Babuchon
 *
 */
public class DepartmentControler implements Observer{
	
	/**
	 * The model
	 */
	private Company company;
	
	/**
	 * The Department view
	 */
	private DepartmentView departmentView;
	
	/**
	 * Constructor, initiate attribute
	 * and set up observer
	 * 
	 * @param departmentView : The department View
	 */
	public DepartmentControler(DepartmentView departmentView) {
		company = Company.getInstance();
		company.addObserver(this);
		this.departmentView = departmentView;
	}
	
	/**
	 * Refresh the department comboBox
	 * and refresh the informations panel
	 */
	public void refresh() {
		refreshDepartment();
		loadInformations();
	}
	
	/**
	 * Refresh the departments list
	 */
	public void refreshDepartment() {
		departmentView.getDepartmentsComboBox().removeAllItems();
		for(StandardDepartment sd : company.getStandardDepartments()) {
			departmentView.getDepartmentsComboBox().addItem(sd);
		}
	}
	
	/**
	 * refresh the employees list
	 */
	public void refreshEmployee() {
		departmentView.getInformationPanel().getEmployeeComboBox().removeAllItems();
		List<Employee> employees = company.getEmployees();
		for(Employee e : employees) {
			if(e.getDepartement() == null)
				departmentView.getInformationPanel().getEmployeeComboBox().addItem(e);
		}
	}
	
	/**
	 * Add an employee to the selected department
	 */
	public void addEmployee() {
		Employee e = (Employee)departmentView.getInformationPanel().getEmployeeComboBox().getSelectedItem();
		if(e != null) {
			company.addEmployee(e, (StandardDepartment)departmentView.getDepartmentsComboBox().getSelectedItem());
			JOptionPane.showMessageDialog(departmentView, "L'employée " + e.getLastName() +" " + e.getFirstName() + " a bien été ajouté", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(departmentView, "L'employé n'a pas pu être ajouté ", "Attention", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Change the manager of the department
	 */
	public void changeManager() {
		Manager m = (Manager)departmentView.getInformationPanel().getManagerComboBox().getSelectedItem();
		if(m != null) {
			StandardDepartment sd = (StandardDepartment)departmentView.getDepartmentsComboBox().getSelectedItem();
			sd.changeManager(m);
			JOptionPane.showMessageDialog(departmentView, "Le nouveau manager est : " + m.getLastName() +" " + m.getFirstName(), "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(departmentView, "Le manager n'a pas pu être ajouté ", "Attention", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Refresh the manager List
	 */
	public void refreshManager() {
		departmentView.getInformationPanel().getManagerComboBox().removeAllItems();
		List<Manager> managers = company.getManagers();
		for(Manager m : managers) {
			if(m.getDepartement() != null)
				if(m.getDepartement() == departmentView.getDepartmentsComboBox().getSelectedItem())
					if(m.getStandardDepartmentManaged() == null)
						departmentView.getInformationPanel().getManagerComboBox().addItem(m);
		}
	}
	
	/**
	 * Add a new department to the company
	 */
	public void addDepartment() {
		StandardDepartment sd = company.createStandardDepartment(departmentView.getNameLabel().getText());
		departmentView.getNameLabel().setText("");
		JOptionPane.showMessageDialog(departmentView, "Le départment " + sd.getName() + " a été crée ! ", "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Remove a department from the company
	 */
	public void removeDepartment() {
		StandardDepartment sd = (StandardDepartment)departmentView.getDepartmentsComboBox().getSelectedItem();
		if(company.removeDepartment(sd)) {
			JOptionPane.showMessageDialog(departmentView, "Le departement a été supprimé ! ", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(departmentView, "Le departement n'a pas pu être supprimer ", "Attention", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Show all the informations about a departments
	 * in the information panel
	 */
	public void loadInformations() {
		
		refreshEmployee();
		refreshManager();
		
		StandardDepartment sd = (StandardDepartment)departmentView.getDepartmentsComboBox().getSelectedItem();
		DepartmentInformationPanel dip = departmentView.getInformationPanel();
		if(sd != null) {
			dip.getEmployeeComboBox().setVisible(true);
			dip.getManagerComboBox().setVisible(true);
			dip.getEmployeeButton().setVisible(true);
			dip.getManagerButton().setVisible(true);
			dip.getInfoEmployeeLabel().setVisible(true);
			dip.getInfoManagerLabel().setVisible(true);
			
			dip.getNameLabel().setText("<html>Nom : <b>"+ sd.getName() +"</b></html>");
			if(sd.getManager() != null)
				dip.getManagerLabel().setText("<html>Manager : <b>"+ sd.getManager().getFirstName() + " " + sd.getManager().getLastName() + "</b></html>");
			else 
				dip.getManagerLabel().setText("<html><b>Aucun Manager</b></html>");
			dip.getStatsLabel().setText("<html>Le departement comporte : <b>"+ sd.numberOfEmployee() +"</b> Employées dont <b>" + sd.numberOfWorkingEmployee() + " Présents</b></html>");
		}
		else {	
			dip.getEmployeeComboBox().setVisible(false);
			dip.getManagerComboBox().setVisible(false);
			dip.getEmployeeButton().setVisible(false);
			dip.getManagerButton().setVisible(false);
			dip.getInfoEmployeeLabel().setVisible(false);
			dip.getInfoManagerLabel().setVisible(false);
			
			dip.getNameLabel().setText("");
			dip.getManagerLabel().setText("");
			dip.getStatsLabel().setText("");
		}	
	}

	@Override
	public void update(Observable o, Object arg) {
		 if(arg instanceof StandardDepartment) {
			refreshDepartment();
		 }
		 else {
			 loadInformations();
		 }
		
	}
}
