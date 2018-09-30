package controlers;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import lib.MyLocalTime;
import models.Company;
import models.Employee;
import models.StandardDepartment;
import views.employee.EmployeeInformationPanel;
import views.employee.EmployeeView;

/**
 * This class is the controller of the EmployeeView class
 * according to the MVC pattern
 * 
 * Implements the Observer pattern of Java util to notify the controler
 * when the model's state has changed
 * 
 * @author Louis Babuchon
 *
 */
public class EmployeeControler implements Observer{
	
	/**
	 * The company model
	 */
	private Company company;
	
	/**
	 * The EmployeeView
	 */
	private EmployeeView employeeView;
	
	/**
	 * Constructor, initiate attribute
	 * and set up observer
	 * 
	 * @param employeeView : The employee View
	 */
	public EmployeeControler(EmployeeView employeeView) {
		this.employeeView = employeeView;
		company = Company.getInstance();
		company.addObserver(this);
	}
	
	/**
	 * Create an employee in the company according
	 * to the form in the view
	 */
	public void createEmployee() {

		if(employeeView.getNameField().getText().isEmpty() || employeeView.getSurnameField().getText().isEmpty()) {
			JOptionPane.showMessageDialog(employeeView, "Tout les champs doivent être rempli", "Attention", JOptionPane.WARNING_MESSAGE);
			return;
		} 
		
		if(!employeeView.getChckbxManager().isSelected()) {
			company.createEmployee(employeeView.getSurnameField().getText()
				, employeeView.getNameField().getText()
				, MyLocalTime.of((Integer)employeeView.getBeginHourSpinner().getValue(), (Integer)employeeView.getBeginMinutesSpinner().getValue())
				, MyLocalTime.of((Integer)employeeView.getEndHourSpinner().getValue(),(Integer) employeeView.getEndMinutesSpinner().getValue())
				, null);
			JOptionPane.showMessageDialog(employeeView, "L'employé a été crée ! ", "Information", JOptionPane.INFORMATION_MESSAGE);
		}else {
			company.createManager(employeeView.getNameField().getText()
				, employeeView.getSurnameField().getText()
				, MyLocalTime.of((Integer)employeeView.getBeginHourSpinner().getValue(),(Integer)employeeView.getBeginMinutesSpinner().getValue())
				, MyLocalTime.of((Integer)employeeView.getEndHourSpinner().getValue(),(Integer) employeeView.getEndMinutesSpinner().getValue()));
			JOptionPane.showMessageDialog(employeeView, "Le manager a été crée ! ", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		resetLabels();
	}
	
	/**
	 * Show all the informations about an employee
	 * in the information panel
	 */
	public void loadInformation() {
	
		Employee e = (Employee)employeeView.getComboBoxEmployee().getSelectedItem();
		
		EmployeeInformationPanel panel = employeeView.getInformationPanel();
		if(e == null) {
			panel.getNameLabel().setText("");
			panel.getDepartmentLabel().setText("");
			panel.getHourLabel().setText("");
			return;
		}
		// We load the name
		if(company.getManagers().contains(e))
			panel.getNameLabel().setText("<html>[Manager] Nom : <b>" + e.getLastName() + "</b> Prénom : <b>" + e.getFirstName() + "</b></html>");
		else
			panel.getNameLabel().setText("<html>Nom : <b>" + e.getLastName() + "</b> Prénom : <b>" + e.getFirstName() + "</b></html>");	
		
		// We load the department
		if(e.getDepartement() != null) {
			if(company.getManagers().contains(e)) {
				if(e.getDepartement().getManager() == e)
					panel.getDepartmentLabel().setText("<html>Manage le département : <b>" + e.getDepartement().getName() + "</b></html>");
				else 
					panel.getDepartmentLabel().setText("<html>Travaille dans le département : <b>" + e.getDepartement().getName() + "</b></html>");
			}
			else
				panel.getDepartmentLabel().setText("<html>Travaille dans le département : <b>" + e.getDepartement().getName() + "</b></html>");
		}else {
			panel.getDepartmentLabel().setText("<html><b>Aucun département</b></html>");
		}
		
		// We load hours
		if(e.isPresent())
			panel.getHourLabel().setText("<html>Debut : <b>" + e.getBeginHour() + "</b> Fin : <b>" 
						+ e.getEndHour() + "</b> Temps Bonus :<b> " + e.getBonusTime() + "</b> Actuellement<font color=\"green\"> Présent</font></html>");
		else 
			panel.getHourLabel().setText("<html>Debut : <b>" + e.getBeginHour() + "</b> Fin : <b>"
						+ e.getEndHour() + "</b> Temps Bonus :<b> " + e.getBonusTime() + "</b> Actuellement<font color=\"red\"> Absent</font></html>");
	}

	/**
	 * Remove the selected employee from the company
	 */
	public void removeEmployee() {
		Employee e = (Employee)employeeView.getComboBoxEmployee().getSelectedItem();
		if(company.removeEmployee(e)) {
			JOptionPane.showMessageDialog(employeeView, "L'employé a été supprimé ! ", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		else 
			JOptionPane.showMessageDialog(employeeView, "L'employé n'a pa été supprimé ! ", "Information", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Refresh all the frame
	 */
	public void refresh() {
		refreshDepartment();
		refreshEmployee();
	}
	
	/**
	 * Refresh all the employee list
	 */
	public void refreshEmployee() {
		employeeView.getComboBoxEmployee().removeAllItems();
		
		List<Employee> employees = company.getEmployees();
		
		for(Employee e : employees) {
			if(employeeView.getCurrentlyHereCheckBox().isSelected()) {
				if(e.isPresent()) {
					if(employeeView.getDepartmentCheckBox().isSelected()) {
						if(e.getDepartement() == employeeView.getDepartmentComboBox().getSelectedItem()) {
							employeeView.getComboBoxEmployee().addItem(e);
						} 
					}
					else {
						employeeView.getComboBoxEmployee().addItem(e);
					}
				}
			}
			else if(employeeView.getDepartmentCheckBox().isSelected()) {
				if(e.getDepartement() == employeeView.getDepartmentComboBox().getSelectedItem()) {
					employeeView.getComboBoxEmployee().addItem(e);
				}
			} else {
				employeeView.getComboBoxEmployee().addItem(e);
			}
			
		}
	}
	
	/**
	 * Refresh all the department list
	 */
	public void refreshDepartment() {
		employeeView.getDepartmentComboBox().removeAllItems();
		List<StandardDepartment> departments = company.getStandardDepartments();
		
		for(StandardDepartment sd : departments) {
			employeeView.getDepartmentComboBox().addItem(sd);
		}
	}

	/**
	 * Reset all the labels
	 */
	public void resetLabels() {
		employeeView.getChckbxManager().setSelected(false);;
		
		employeeView.getNameField().setText("");
		employeeView.getSurnameField().setText("");
		
		employeeView.getBeginHourSpinner().setValue(0);
		employeeView.getBeginMinutesSpinner().setValue(0);
		employeeView.getEndHourSpinner().setValue(0);
		employeeView.getEndMinutesSpinner().setValue(0);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Employee) {
			refreshEmployee();
		}else if(arg instanceof StandardDepartment) {
			refreshDepartment();
		}
	}
}
