package views.employee;


import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controlers.EmployeeControler;
import models.Employee;
import models.StandardDepartment;

import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * This class represent the employee view according to the MVC pattern
 * @author Louis Babuchon
 *
 */
@SuppressWarnings("serial")
public class EmployeeView extends JPanel{
	
	/**
	 * the delete button
	 */
	private JButton deleteButton;
	
	/**
	 * The add employee button
	 */
	private JButton addEmployeeButton;
	
	/**
	 * The employee controller
	 */
	private EmployeeControler employeeControler;
	
	/**
	 * The comboBox of employees
	 */
	private JComboBox<Employee> comboBoxEmployee;
	
	/**
	 * The manager checkBox
	 */
	private JCheckBox chckbxManager;
	
	/**
	 * The first name text field
	 */
	private JTextField nameField;
	
	/**
	 * The last name textFiled
	 */
	private JTextField surnameField;
	
	/**
	 * The begin hour spinner
	 */
	private JSpinner beginHourSpinner;
	
	/**
	 * The begin minutes spinner
	 */
	private JSpinner beginMinutesSpinner;
	
	/**
	 * The end hour spinner
	 */
	private JSpinner endHourSpinner;
	
	/**
	 * The end minutes spinner
	 */
	private JSpinner endMinutesSpinner;
	
	/**
	 * The currently here checkBox
	 */
	private JCheckBox currentlyHereCheckBox;
	
	/**
	 * The department checkBox
	 */
	private JCheckBox departmentCheckBox;
	
	/**
	 * The department comboBox
	 */
	private JComboBox<StandardDepartment> departmentComboBox;
	
	/**
	 * The information subView
	 */
	private EmployeeInformationPanel informationPanel;
	
	/**
	 * The filterLabel
	 */
	private JLabel filterLabel;

	/**
	 * The constructor, initiates attributes and set up the view
	 */
	public EmployeeView() {
		
		this.employeeControler = new EmployeeControler(this);
		
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Gestion des Employés", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(null);
		
		comboBoxEmployee = new JComboBox<Employee>();
		comboBoxEmployee.setToolTipText("------Choisissez Un employ\u00E9e------");
		comboBoxEmployee.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		comboBoxEmployee.setBounds(254, 211, 282, 32);
		comboBoxEmployee.addActionListener(e -> {employeeControler.loadInformation();});
		add(comboBoxEmployee);
		
		deleteButton = new JButton("Suprimer");
		deleteButton.addActionListener(e -> {employeeControler.removeEmployee();});
		deleteButton.setBounds(368, 427, 75, 23);
		add(deleteButton);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setBounds(228, 197, 322, 23);
		add(separator);
		
		addEmployeeButton = new JButton("Créer l'employé");
		addEmployeeButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		addEmployeeButton.setBounds(581, 100, 153, 57);
		add(addEmployeeButton);
		addEmployeeButton.addActionListener(e -> {employeeControler.createEmployee();});
		
		nameField = new JTextField();
		nameField.setFont(new Font("Source Sans Pro", Font.PLAIN, 17));
		nameField.setBounds(10, 68, 173, 38);
		add(nameField);
		nameField.setColumns(10);
		
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		lblNom.setBounds(10, 44, 98, 23);
		add(lblNom);
		
		JLabel lblPrnom = new JLabel("Pr\u00E9nom :");
		lblPrnom.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		lblPrnom.setBounds(228, 43, 98, 23);
		add(lblPrnom);
		
		JLabel lblDebuthhmm = new JLabel("Debut : (hh:mm)");
		lblDebuthhmm.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		lblDebuthhmm.setBounds(20, 117, 163, 23);
		add(lblDebuthhmm);
		
		JLabel lblFinhhmm = new JLabel("Fin : (hh:mm)");
		lblFinhhmm.setFont(new Font("Source Sans Pro", Font.PLAIN, 15));
		lblFinhhmm.setBounds(228, 117, 173, 23);
		add(lblFinhhmm);
		
		chckbxManager = new JCheckBox("Manager");
		chckbxManager.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		chckbxManager.setBounds(453, 76, 129, 23);
		add(chckbxManager);
		
		surnameField = new JTextField();
		surnameField.setFont(new Font("Source Sans Pro", Font.PLAIN, 17));
		surnameField.setColumns(10);
		surnameField.setBounds(228, 68, 173, 38);
		add(surnameField);
		
		beginHourSpinner = new JSpinner();
		beginHourSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		beginHourSpinner.setBounds(30, 148, 40, 38);
		add(beginHourSpinner);
		
		beginMinutesSpinner = new JSpinner();
		beginMinutesSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 15));
		beginMinutesSpinner.setBounds(80, 148, 40, 38);
		add(beginMinutesSpinner);
		
		endHourSpinner = new JSpinner();
		endHourSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		endHourSpinner.setBounds(228, 148, 40, 38);
		add(endHourSpinner);
		
		endMinutesSpinner = new JSpinner();
		endMinutesSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 15));
		endMinutesSpinner.setBounds(286, 148, 40, 38);
		add(endMinutesSpinner);
		
		informationPanel = new EmployeeInformationPanel();
		informationPanel.getNameLabel().setFont(new Font("Source Sans Pro", Font.PLAIN, 17));
		informationPanel.getDepartmentLabel().setFont(new Font("Source Sans Pro", Font.PLAIN, 17));
		informationPanel.getHourLabel().setFont(new Font("Source Sans Pro", Font.PLAIN, 17));
		informationPanel.setBounds(160, 247, 633, 169);
		add(informationPanel);
		comboBoxEmployee.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		
		filterLabel = new JLabel("Filtres");
		filterLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		filterLabel.setBounds(10, 247, 98, 32);
		add(filterLabel);
		
		currentlyHereCheckBox = new JCheckBox("Actuelement pr\u00E9sent(e)");
		currentlyHereCheckBox.setBounds(10, 286, 153, 23);
		add(currentlyHereCheckBox);
		currentlyHereCheckBox.addActionListener(e -> {employeeControler.refreshEmployee();});
		
		departmentCheckBox = new JCheckBox("Par d\u00E9partement");
		departmentCheckBox.setBounds(10, 324, 144, 23);
		add(departmentCheckBox);
		departmentCheckBox.addActionListener(e -> {employeeControler.refreshEmployee();});
		
		departmentComboBox = new JComboBox<>();
		departmentComboBox.setBounds(10, 354, 144, 32);
		add(departmentComboBox);
		departmentComboBox.addActionListener(e -> {employeeControler.refreshEmployee();});
		
		employeeControler.refresh();
	}
	
	/**
	 * Get the currently here checkBox
	 * @return JCheckBox : the currently here check box
	 */
	public JCheckBox getCurrentlyHereCheckBox() {
		return currentlyHereCheckBox;
	}

	/**
	 * Get the department check box
	 * @return JCheckBox : the department check box
	 */
	public JCheckBox getDepartmentCheckBox() {
		return departmentCheckBox;
	}

	/**
	 * Get the department combo box
	 * @return JComboBox of StandardDepartment : the combo of standard department
	 */
	public JComboBox<StandardDepartment> getDepartmentComboBox() {
		return departmentComboBox;
	}

	/**
	 * The the manager check box
	 * @return JCheckBox : the manager check box
	 */
	public JCheckBox getChckbxManager() {
		return chckbxManager;
	}

	/**
	 * The first name text field
	 * @return JTextField : the first name text field
	 */
	public JTextField getNameField() {
		return nameField;
	}

	/**
	 * Get the last name text field
	 * @return JTextField : the last name text field
	 */
	public JTextField getSurnameField() {
		return surnameField;
	}

	/**
	 * Get the begin hour spinner
	 * @return JSpinner : the begin hour spinner
	 */
	public JSpinner getBeginHourSpinner() {
		return beginHourSpinner;
	}

	/**
	 * Get the begin minutes spinner
	 * @return JSpinner : the begin minutes spinner
	 */
	public JSpinner getBeginMinutesSpinner() {
		return beginMinutesSpinner;
	}

	/**
	 * Get the end hour spinner
	 * @return JSpinner : the end hour spinner
	 */
	public JSpinner getEndHourSpinner() {
		return endHourSpinner;
	}

	/**
	 * Get the end minutes spinner
	 * @return JSpinner : the end minutes spinner
	 */
	public JSpinner getEndMinutesSpinner() {
		return endMinutesSpinner;
	}
	
	/**
	 * Get the information sub view
	 * @return EmployeeInformationPanel : the information subView
	 */
	public EmployeeInformationPanel getInformationPanel() {
		return informationPanel;
	}
	
	/**
	 * Get the employee combo box
	 * @return JComboBox of employee : the employee combo box
	 */
	public JComboBox<Employee> getComboBoxEmployee() {
		return comboBoxEmployee;
	}
	
	/**
	 * Get the employee controller
	 * @return EmployeeControler : the employee controller
	 */
	public EmployeeControler getControler() {
		return employeeControler;
	}
}
