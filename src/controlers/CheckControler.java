package controlers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import models.CheckInCheckOut;
import models.Company;
import models.Employee;
import models.StandardDepartment;
import views.check.CheckView;

/**
 * This class is the controller of the CheckView Class
 * according to the MVC Pattern
 * 
 * implements the Observer pattern of java util to notify the controller when 
 * the model has changed
 * @author Louis Babuchon
 *
 */
public class CheckControler implements Observer{

	/**
	 * The view of the controller
	 */
	private CheckView checkView;
	
	/**
	 * The model of the controller
	 */
	private Company company;
	
	/**
	 * Constructor, initiate attribute
	 * and set up observer
	 * @param checkView : the view
	 */
	public CheckControler(CheckView checkView) {
		this.checkView = checkView;
		company = Company.getInstance();
		company.addObserver(this);
	}
	
	/**
	 * This method create the CheckTable
	 * according to the filters
	 */
	public void refreshTab() {
		
		DefaultTableModel dm = new DefaultTableModel(0, 0);
		String[] name = {"Date", "Id" ,"Nom", "Prénom", "Departement" , "Heure debut", "Heure fin", "Check debut", "Check Fin"};
		dm.setColumnIdentifiers(name);
		JTable table = new JTable();
		table.setModel(dm);
		List<Vector<Object>> rows = new ArrayList<>();
		
		// if filter is checked
		if(checkView.getFilterCheckBox().isSelected()) {
			
			if(checkView.getEmployeeRadio().isSelected()) {
				computeEmployeeFilter(rows);
			} else if(checkView.getDepartmentRadio().isSelected()){
				computeDepartmentFilter(rows);
			} else {
				computeAllFilter(rows);
			}
		}
		else {
			
			// We show all the checks of all the employees
			for(Employee e : company.getEmployees()) {
				Map<LocalDate, CheckInCheckOut> checks = e.getChecks();
				for(Map.Entry<LocalDate, CheckInCheckOut> check : checks.entrySet()) {
					Vector<Object> row = prepareRow(e, check);
					rows.add(row);
				}
			}
			
		}
		AddRows(rows, dm);
		// We reset the old tab and add the new one
		checkView.getTableView().removeAll();
		
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(10, 11, 584, 405);
		
		checkView.getTableView().add(pane);
		
		checkView.getTableView().revalidate();
		checkView.getTableView().repaint();
	
	}
	
	/**
	 * Compute all the filters and calls associated methods
	 * @param rows : the row to add to the table
	 */
	private void computeAllFilter(List<Vector<Object>> rows) {
		if(checkView.getAllRadio().isSelected()) {
			for(Employee e : company.getEmployees()) {
				// if all is selected
				Map<LocalDate, CheckInCheckOut> checks = e.getChecks();
				for(Map.Entry<LocalDate, CheckInCheckOut> check : checks.entrySet()) {
					Vector<Object> row = prepareRow(e, check);
					rows.add(row);
				}
			}
			
		}else if(checkView.getPeriodRadio().isSelected()) {
			// if period is selected
			for(Employee e : company.getEmployees()) {
				Map<LocalDate, CheckInCheckOut> checks = e.getChecks();
				for(Map.Entry<LocalDate, CheckInCheckOut> check : checks.entrySet()) {
					if((check.getKey().isAfter(((Date)checkView.getBeginSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) 
							|| check.getKey().isEqual(((Date)checkView.getBeginSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
								&& (check.getKey().isBefore(((Date)checkView.getEndSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || check.getKey().isEqual(((Date)checkView.getEndSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))) {
						Vector<Object> row = prepareRow(e, check);
						rows.add(row);
					}
				}
			}
			
		} else {
			// if all is selected
			for(Employee e : company.getEmployees()) {
				Map<LocalDate, CheckInCheckOut> checks = e.getChecks();
				for(Map.Entry<LocalDate, CheckInCheckOut> check : checks.entrySet()) {
					if(check.getKey().isEqual(((Date)checkView.getBeginSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
						Vector<Object> row = prepareRow(e, check);
						rows.add(row);
					}
				}	
			}
		}
	}

	/**
	 * Compute the department filter
	 * @param rows : the rows of the table
	 */
	private void computeDepartmentFilter(List<Vector<Object>> rows) {
		
		StandardDepartment sd = (StandardDepartment)checkView.getDepartmentComboBox().getSelectedItem();
		if(sd == null) return;
		
		if(checkView.getAllRadio().isSelected()) {
			for(Employee e : company.getEmployees(sd)) {
				// if all is selected
				Map<LocalDate, CheckInCheckOut> checks = e.getChecks();
				for(Map.Entry<LocalDate, CheckInCheckOut> check : checks.entrySet()) {
					Vector<Object> row = prepareRow(e, check);
					rows.add(row);
				}
			}
			
		}else if(checkView.getPeriodRadio().isSelected()) {
			// if period is selected
			for(Employee e : company.getEmployees(sd)) {
				Map<LocalDate, CheckInCheckOut> checks = e.getChecks();
				for(Map.Entry<LocalDate, CheckInCheckOut> check : checks.entrySet()) {
					if((check.getKey().isAfter(((Date)checkView.getBeginSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) 
							|| check.getKey().isEqual(((Date)checkView.getBeginSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
								&& (check.getKey().isBefore(((Date)checkView.getEndSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || check.getKey().isEqual(((Date)checkView.getEndSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))) {
						Vector<Object> row = prepareRow(e, check);
						rows.add(row);
					}
				}
			}
			
		} else {
			// if all is selected
			for(Employee e : company.getEmployees(sd)) {
				Map<LocalDate, CheckInCheckOut> checks = e.getChecks();
				for(Map.Entry<LocalDate, CheckInCheckOut> check : checks.entrySet()) {
					if(check.getKey().isEqual(((Date)checkView.getBeginSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
						Vector<Object> row = prepareRow(e, check);
						rows.add(row);
					}
				}	
			}
		}
		
	}

	/**
	 * Compute the amployee filter
	 * @param rows : the rows of the table
	 */
	private void computeEmployeeFilter(List<Vector<Object>> rows) {
		// Filter by employee
		Employee e = (Employee)checkView.getEmployeeComboBox().getSelectedItem();
		if(e == null) return;
		
		// if the employee isn't null
		if(checkView.getAllRadio().isSelected()) {

			// if all is selected
			Map<LocalDate, CheckInCheckOut> checks = e.getChecks();
			for(Map.Entry<LocalDate, CheckInCheckOut> check : checks.entrySet()) {
				Vector<Object> row = prepareRow(e, check);
				rows.add(row);
			}
			
		}else if(checkView.getPeriodRadio().isSelected()) {
			// if period is selected
			Map<LocalDate, CheckInCheckOut> checks = e.getChecks();
			for(Map.Entry<LocalDate, CheckInCheckOut> check : checks.entrySet()) {
				if((check.getKey().isAfter(((Date)checkView.getBeginSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) 
						|| check.getKey().isEqual(((Date)checkView.getBeginSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
							&& (check.getKey().isBefore(((Date)checkView.getEndSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || check.getKey().isEqual(((Date)checkView.getEndSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))) {
					Vector<Object> row = prepareRow(e, check);
					rows.add(row);
				}
			}
			
		} else {
			// if all is selected
			Map<LocalDate, CheckInCheckOut> checks = e.getChecks();
			for(Map.Entry<LocalDate, CheckInCheckOut> check : checks.entrySet()) {
				if(check.getKey().isEqual(((Date)checkView.getBeginSpinner().getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
					Vector<Object> row = prepareRow(e, check);
					rows.add(row);
				}
			}
		}
	}
	
	/**
	 * Create a row with correct informations
	 * @param e : the employee to show information
	 * @param check : the check in/out of the empployee correspondig to a date
	 * @return vector of Object : to future row to add
	 */
	private Vector<Object> prepareRow(Employee e, Map.Entry<LocalDate, CheckInCheckOut> check) {
		
		Vector<Object> row = new Vector<>();
		row.add(check.getKey());
		row.addElement(e.getId());
		row.add(e.getLastName());
		row.add(e.getFirstName());
		if(e.getDepartement() != null)
			row.add(e.getDepartement());
		else 
			row.add("<html><font color=\"red\">Aucun</font></html>");
		row.add(e.getBeginHour());
		row.add(e.getEndHour());
		if(e.getBeginHour().isBefore(check.getValue().getBeginHourCheck()))
			row.add("<html><font color=\"red\">"+ check.getValue().getBeginHourCheck() + "</font></html>");
		else
			row.add("<html><font color=\"green\">"+ check.getValue().getBeginHourCheck() + "</font></html>");
		if(check.getValue().getEndHourCheck() != null) {
			if(e.getEndHour().isBefore(check.getValue().getEndHourCheck()))
				row.add("<html><font color=\"green\">"+ check.getValue().getEndHourCheck() + "</font></html>");
			else 
				row.add("<html><font color=\"red\">"+ check.getValue().getEndHourCheck() + "</font></html>");
		} else {
			row.add("<html><font color=\"red\">"+ "Aucun" + "</font></html>");
		}
			
		return row;
	}
	
	/**
	 * Add all the rows to the table
	 * @param rows : all the rows
	 * @param dm : the tableModel
	 */
	private void AddRows(List<Vector<Object>> rows, DefaultTableModel dm) {
		for(Vector<Object> row : rows) {
			dm.addRow(row);
		}
	}
	
	/**
	 * Refresh the table and the information label
	 */
	public void refreshInformation() {
		refreshTab();
		refreshInformationLabel();
	}
	
	/**
	 * Update the filters and refresh the informations
	 */
	public void refresh() {
		updateFilter();
		refreshInformation();
	}
	
	/**
	 * Refresh the ComboBox of the employees
	 */
	public void refreshEmployee() {
		checkView.getEmployeeComboBox().removeAllItems();
		
		List<Employee> employees = company.getEmployees();
		
		for(Employee e : employees)
			checkView.getEmployeeComboBox().addItem(e);
	}
	
	/**
	 * Refresh the infomation label
	 */
	public void refreshInformationLabel() {
		
		int nb = 0;
		for(Employee e : company.getEmployees()) {
			if(e.isPresent()) nb++;
		}
		checkView.getInfoLabel().setText("Il y a actuellement " + nb + " employé(s) présent(s) sur " + company.getEmployees().size());
	}
	
	/**
	 * Refresh the department combo box
	 */
	public void refreshDepartment() {
		checkView.getDepartmentComboBox().removeAllItems();
		
		List<StandardDepartment> departments = company.getStandardDepartments();
		
		for(StandardDepartment sd : departments)
			checkView.getDepartmentComboBox().addItem(sd);
	}
	
	/**
	 * Update all the filters:
	 * Put them visible or invisible according to the filter state
	 */
	public void updateFilter() {
		if(checkView.getFilterCheckBox().isSelected()) {
			if(checkView.getEmployeeRadio().isSelected()) {
				checkView.getEmployeeComboBox().setVisible(true);
				checkView.getDepartmentComboBox().setVisible(false);
			}else if(checkView.getDepartmentRadio().isSelected()) {
				checkView.getEmployeeComboBox().setVisible(false);
				checkView.getDepartmentComboBox().setVisible(true);
			} else {
				checkView.getEmployeeComboBox().setVisible(false);
				checkView.getDepartmentComboBox().setVisible(false);
			}
			if(checkView.getDateRadio().isSelected()) {
				checkView.getEndSpinner().setVisible(false);
				checkView.getEndLabel().setVisible(false);
				checkView.getBeginSpinner().setVisible(true);
				checkView.getBeginLabel().setVisible(true);
			} else if(checkView.getPeriodRadio().isSelected()){
				checkView.getEndSpinner().setVisible(true);
				checkView.getEndLabel().setVisible(true);
				checkView.getBeginSpinner().setVisible(true);
				checkView.getBeginLabel().setVisible(true);
			} else {
				checkView.getEndSpinner().setVisible(false);
				checkView.getEndLabel().setVisible(false);
				checkView.getBeginSpinner().setVisible(false);
				checkView.getBeginLabel().setVisible(false);
			}
			
			checkView.getDateRadio().setVisible(true);
			checkView.getDepartmentRadio().setVisible(true);
			checkView.getEmployeeRadio().setVisible(true);
			checkView.getPeriodRadio().setVisible(true);
			checkView.getAllRadio().setVisible(true);
			checkView.getAllRadioButton().setVisible(true);
			
		}
		else {
			checkView.getEmployeeComboBox().setVisible(false);
			checkView.getDepartmentComboBox().setVisible(false);
			checkView.getDateRadio().setVisible(false);
			checkView.getDepartmentRadio().setVisible(false);
			checkView.getEmployeeRadio().setVisible(false);
			checkView.getPeriodRadio().setVisible(false);
			checkView.getBeginLabel().setVisible(false);
			checkView.getEndLabel().setVisible(false);
			checkView.getBeginSpinner().setVisible(false);
			checkView.getEndSpinner().setVisible(false);
			checkView.getAllRadio().setVisible(false);
			checkView.getAllRadioButton().setVisible(false);
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		refreshInformation();
		
	}
}
