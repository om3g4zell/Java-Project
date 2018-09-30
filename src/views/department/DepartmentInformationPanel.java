package views.department;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import controlers.DepartmentControler;
import models.Employee;
import models.Manager;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JButton;

/**
 * This class represent a subView of the department View
 * Show information abour a department
 * @author Louis Babuchon
 *
 */
@SuppressWarnings("serial")
public class DepartmentInformationPanel extends JPanel{
	
	/**
	 * The employee Button
	 */
	private JButton employeeButton;
	
	/**
	 * The manager Button
	 */
	private JButton managerButton;
	
	/**
	 * The employee comboBox
	 */
	private JComboBox<Employee> employeeComboBox;
	
	/**
	 * The manager ComboBox
	 */
	private JComboBox<Manager> managerComboBox;
	
	/**
	 * The name label
	 */
	private JLabel nameLabel;
	
	/**
	 * The manager label
	 */
	private JLabel managerLabel;
	
	/**
	 * The stats label
	 */
	private JLabel statsLabel;
	
	/**
	 * The manager info label
	 */
	private JLabel infoManagerLabel;
	
	/**
	 * The employee info label
	 */
	private JLabel infoEmployeeLabel;
	
	/**
	 * The department controller
	 */
	private DepartmentControler departmentControler;
	
	/**
	 * The constructor, initiates attributes and set up the view
	 * @param controler :  the controller 
	 */
	public DepartmentInformationPanel(DepartmentControler controler) {
		
		departmentControler = controler;
		
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informations", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setBounds(29, 182, 741, 234);
		setLayout(null);
		
		nameLabel = new JLabel("");
		nameLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 17));
		nameLabel.setBounds(24, 31, 331, 62);
		add(nameLabel);
		
		managerLabel = new JLabel("");
		managerLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 17));
		managerLabel.setBounds(24, 104, 331, 38);
		add(managerLabel);
		
		statsLabel = new JLabel("");
		statsLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 17));
		statsLabel.setBounds(24, 170, 331, 53);
		add(statsLabel);
		
		employeeComboBox = new JComboBox<>();
		employeeComboBox.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		employeeComboBox.setBounds(365, 64, 187, 38);
		add(employeeComboBox);
		employeeComboBox.setVisible(false);
		
		managerComboBox = new JComboBox<>();
		managerComboBox.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		managerComboBox.setBounds(365, 156, 187, 38);
		add(managerComboBox);
		managerComboBox.setVisible(false);
		
		employeeButton = new JButton("Ajouter l'employée");
		employeeButton.setBounds(573, 60, 144, 42);
		add(employeeButton);
		employeeButton.setVisible(false);
		employeeButton.addActionListener(e -> {departmentControler.addEmployee();});
		
		managerButton = new JButton("Changer le Manager");
		managerButton.setBounds(573, 152, 144, 42);
		add(managerButton);
		managerButton.setVisible(false);
		managerButton.addActionListener(e -> {departmentControler.changeManager();});
		
		infoManagerLabel = new JLabel("Managers travaillant dans le d\u00E9partment");
		infoManagerLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		infoManagerLabel.setBounds(365, 126, 289, 25);
		add(infoManagerLabel);
		
		infoEmployeeLabel = new JLabel("Employ\u00E9es sans d\u00E9partements");
		infoEmployeeLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		infoEmployeeLabel.setBounds(365, 31, 210, 30);
		add(infoEmployeeLabel);
	}
	
	/**
	 * Get the employee button
	 * @return JButton : the employee button
	 */
	public JButton getEmployeeButton() {
		return employeeButton;
	}

	/**
	 * Get the manager Button
	 * @return JButton : get the manager button
	 */
	public JButton getManagerButton() {
		return managerButton;
	}
	
	/**
	 * Get the employee comboBox
	 * @return JComboBox of Employee : the combo box of employee
	 */
	public JComboBox<Employee> getEmployeeComboBox() {
		return employeeComboBox;
	}

	/**
	 * Get the manager comboBox
	 * @return JComboBox of manager : the combo box of manager
	 */
	public JComboBox<Manager> getManagerComboBox() {
		return managerComboBox;
	}

	/**
	 * Get the name label
	 * @return JLabel : the name label
	 */
	public JLabel getNameLabel() {
		return nameLabel;
	}

	/**
	 * Get the manager label
	 * @return JLabel : the manager label
	 */
	public JLabel getManagerLabel() {
		return managerLabel;
	}

	/**
	 * Get the stats label
	 * @return JLabel : the stats label
	 */
	public JLabel getStatsLabel() {
		return statsLabel;
	}
	
	/**
	 * Get the manager info label
	 * @return JLabel : the manager info label
	 */
	public JLabel getInfoManagerLabel() {
		return infoManagerLabel;
	}

	/**
	 * Get the employee info label
	 * @return JLabel : the employee info label
	 */
	public JLabel getInfoEmployeeLabel() {
		return infoEmployeeLabel;
	}
}
