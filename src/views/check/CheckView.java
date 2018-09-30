package views.check;

import java.awt.Color;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import controlers.CheckControler;
import models.Employee;
import models.StandardDepartment;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.GregorianCalendar;
import java.util.Calendar;
import javax.swing.JRadioButton;

/**
 * This class represent the checkView according to
 * the MVC pattern
 * @author Louis Babuchon
 *
 */
@SuppressWarnings("serial")
public class CheckView extends JPanel{

	/**
	 * The controller
	 */
	private CheckControler checkControler;
	
	/**
	 * the table of check
	 */
	private JPanel tableView;
	
	/**
	 * The employee combo boc
	 */
	private JComboBox<Employee> employeeComboBox;
	
	/**
	 * The department combo box
	 */
	private JComboBox<StandardDepartment> departmentComboBox;
	
	/**
	 * The begin date spinner
	 */
	private JSpinner beginSpinner;
	
	/**
	 * the end date spinner
	 */
	private JSpinner endSpinner;
	
	/**
	 * The info label
	 */
	private JLabel infoLabel;
	
	/**
	 * The begin date label
	 */
	private JLabel beginLabel;
	
	/**
	 * The end date label
	 */
	private JLabel endLabel;
	
	/**
	 * The filter checkBox
	 */
	private JCheckBox filterCheckBox;
	
	/**
	 * The date radio
	 */
	private JRadioButton dateRadio;
	

	/**
	 * The period radio
	 */
	private JRadioButton periodRadio;
	

	/**
	 * The employee radio
	 */
	private JRadioButton employeeRadio;
	

	/**
	 * The department radio
	 */
	private JRadioButton departmentRadio;
	

	/**
	 * The all radio for employees and department
	 */
	private JRadioButton allRadio;
	

	/**
	 * The all radio for date and period
	 */
	private JRadioButton allRadioButton;
	
	/**
	 * The refresh button
	 */
	private JButton refreshButton;
	
	/**
	 * The constructor, initiates attributes and set up the view and the listeners
	 */
	public CheckView() {
		this.checkControler = new CheckControler(this);
		
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Gestion des présences", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(null);
		
		tableView = new JPanel();
		tableView.setBounds(186, 32, 594, 416);
		tableView.setLayout(null);
		add(tableView);
		
		employeeComboBox = new JComboBox<>();
		employeeComboBox.setBounds(10, 114, 166, 32);
		add(employeeComboBox);
		
		departmentComboBox = new JComboBox<>();
		departmentComboBox.setBounds(10, 114, 166, 32);
		add(departmentComboBox);
		
		beginLabel = new JLabel("Debut");
		beginLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		beginLabel.setBounds(10, 192, 68, 32);
		add(beginLabel);
		
		endLabel = new JLabel("Fin");
		endLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		endLabel.setBounds(10, 271, 68, 32);
		add(endLabel);
		
		Calendar calendar = new GregorianCalendar();
		
		SpinnerDateModel dateBeginModel = new SpinnerDateModel();
		SpinnerDateModel dateEndModel = new SpinnerDateModel();
		SimpleDateFormat model = new SimpleDateFormat("dd.MM.yy");
		
		beginSpinner = new JSpinner(dateBeginModel);
		beginSpinner.setEditor(new JSpinner.DateEditor(beginSpinner, model.toPattern()));
		beginSpinner.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		beginSpinner.setValue(calendar.getTime());
		beginSpinner.setBounds(10, 224, 142, 36);
		add(beginSpinner);
		
		endSpinner = new JSpinner(dateEndModel);
		endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, model.toPattern()));
		endSpinner.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		endSpinner.setValue(calendar.getTime());
		endSpinner.setBounds(10, 303, 142, 34);
		add(endSpinner);
		
		infoLabel = new JLabel("");
		infoLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 14));
		infoLabel.setBounds(196, 11, 607, 23);
		add(infoLabel);
		
		filterCheckBox = new JCheckBox("Filtres");
		filterCheckBox.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		filterCheckBox.setBounds(21, 32, 97, 23);
		add(filterCheckBox);
		filterCheckBox.addActionListener(e -> {checkControler.updateFilter();});
		
		ButtonGroup dateGroup = new ButtonGroup();
		ButtonGroup employeeGroup = new ButtonGroup();
		
		dateRadio = new JRadioButton("Date");
		dateRadio.setBounds(10, 150, 89, 23);
		add(dateRadio);
		dateRadio.setSelected(true);
		dateRadio.addActionListener(e-> {checkControler.updateFilter();});
		
		periodRadio = new JRadioButton("Période");
		periodRadio.setBounds(104, 153, 76, 23);
		add(periodRadio);
		periodRadio.addActionListener(e-> {checkControler.updateFilter();});
		
		allRadio = new JRadioButton("Tout");
		allRadio.setBounds(58, 176, 94, 23);
		add(allRadio);
		allRadio.addActionListener(e-> {checkControler.updateFilter();});
		
		dateGroup.add(allRadio);
		dateGroup.add(dateRadio);
		dateGroup.add(periodRadio);
		
		departmentRadio = new JRadioButton("Département");
		departmentRadio.setBounds(10, 58, 89, 23);
		add(departmentRadio);
		departmentRadio.addActionListener(e ->{
			checkControler.updateFilter();
			checkControler.refreshDepartment();
		});
		
		employeeRadio = new JRadioButton("Employé");
		employeeRadio.setBounds(101, 58, 79, 23);
		add(employeeRadio);
		employeeRadio.addActionListener(e -> {
			checkControler.updateFilter();
			checkControler.refreshEmployee();
		});
		
		allRadioButton = new JRadioButton("Tout");
		allRadioButton.setSelected(true);
		allRadioButton.setBounds(56, 84, 96, 23);
		add(allRadioButton);
		allRadioButton.addActionListener(e-> {checkControler.updateFilter();});
		
		employeeGroup.add(departmentRadio);
		employeeGroup.add(employeeRadio);
		employeeGroup.add(allRadioButton);
		
		refreshButton = new JButton("Appliquer le Filtre");
		refreshButton.setBounds(10, 348, 166, 23);
		add(refreshButton);
		refreshButton.addActionListener(e -> {
			checkControler.refreshTab();
			checkControler.refreshInformation();
		});
		
		checkControler.refresh();
		
	}

	/**
	 * Get the info Label
	 * @return JLabel : the info Label
	 */
	public JLabel getInfoLabel() {
		return infoLabel;
	}

	/**
	 * Get the begin date Label
	 * @return JLabel : the begin date label
	 */
	public JLabel getBeginLabel() {
		return beginLabel;
	}

	/**
	 * Get the end date Label
	 * @return JLabel : the end date label
	 */
	public JLabel getEndLabel() {
		return endLabel;
	}

	/**
	 * Get the begin date spinner
	 * @return JSpinner : the begin date spinner
	 */
	public JSpinner getBeginSpinner() {
		return beginSpinner;
	}

	/**
	 * Get the end date spinner
	 * @return JSpinner : the end date spinner
	 */
	public JSpinner getEndSpinner() {
		return endSpinner;
	}

	/**
	 * Get the filter checkBox
	 * @return JCheckBox : the filter CheckBox
	 */
	public JCheckBox getFilterCheckBox() {
		return filterCheckBox;
	}

	/**
	 * Get the date radio
	 * @return JRadioButton : the date radio button
	 */
	public JRadioButton getDateRadio() {
		return dateRadio;
	}

	/**
	 * Get the period radio
	 * @return JRadioButton : the period radio button
	 */
	public JRadioButton getPeriodRadio() {
		return periodRadio;
	}

	/**
	 * Get the employee radio
	 * @return JRadioButton : the employee radio button
	 */
	public JRadioButton getEmployeeRadio() {
		return employeeRadio;
	}

	/**
	 * Get the department radio
	 * @return JRadioButton : the department radio button
	 */
	public JRadioButton getDepartmentRadio() {
		return departmentRadio;
	}
	
	/**
	 * Get the all radio
	 * @return JRadioButton : the all radio button
	 */
	public JRadioButton getAllRadio() {
		return allRadio;
	}
	
	/**
	 * Get the all radio
	 * @return JRadioButton : the all radio button
	 */
	public JRadioButton getAllRadioButton() {
		return allRadioButton;
	}
	
	/**
	 * Get the refresh button
	 * @return JButton : the refresh button
	 */
	public JButton getRefreshButton() {
		return refreshButton;
	}

	/**
	 * Get the employee comboBox
	 * @return JComboBox of Employee : the combo box of employee
	 */
	public JComboBox<Employee> getEmployeeComboBox() {
		return employeeComboBox;
	}

	/**
	 * Get the department comboBox
	 * @return JComboBox of Employee : the combo box of department
	 */
	public JComboBox<StandardDepartment> getDepartmentComboBox() {
		return departmentComboBox;
	}

	/**
	 * Get the check table panel
	 * @return JPanel : the check table panel
	 */
	public JPanel getTableView() {
		return tableView;
	}
	
	/**
	 * Get the check controller
	 * @return CheckControler : the checkController
	 */
	public CheckControler getCheckControler() {
		return this.checkControler;
	}
}
