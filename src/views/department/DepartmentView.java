package views.department;

import javax.swing.JPanel;

import controlers.DepartmentControler;
import models.StandardDepartment;

import javax.swing.JComboBox;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

/**
 * This class represent the department View 
 * According to the MVC pattern
 * @author Louis Babuchon
 *
 */
@SuppressWarnings("serial")
public class DepartmentView extends JPanel{
	
	/**
	 * The department controller
	 */
	private DepartmentControler departmentControler;
	
	/**
	 * The subView, the information panel
	 */
	private DepartmentInformationPanel informationPanel;
	
	/**
	 * The name text field
	 */
	private JTextField nameLabel;
	
	/**
	 * The department comboBox
	 */
	private JComboBox<StandardDepartment> departmentsComboBox;
	
	/**
	 * The create department button
	 */
	private JButton createButton;
	
	/**
	 * The delete department Button
	 */
	private JButton deleteButton;
	
	/**
	 * The constructor, initiate attributes and set up the view
	 */
	public DepartmentView() {
		this.departmentControler = new DepartmentControler(this);
		
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Gestion des departements", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(null);
		
		informationPanel = new DepartmentInformationPanel(departmentControler);
		informationPanel.getInfoManagerLabel().setBounds(365, 126, 352, 25);
		informationPanel.getInfoEmployeeLabel().setBounds(365, 31, 376, 30);
		add(informationPanel);
		
		departmentsComboBox = new JComboBox<>();
		departmentsComboBox.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		departmentsComboBox.setBounds(276, 133, 236, 40);
		add(departmentsComboBox);
		departmentsComboBox.addActionListener(e -> {departmentControler.loadInformations();});
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(175, 115, 434, 27);
		add(separator);
		
		nameLabel = new JTextField();
		nameLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		nameLabel.setBounds(29, 47, 236, 51);
		add(nameLabel);
		nameLabel.setColumns(10);
		
		JLabel nameLbl = new JLabel("Nom du d\u00E9partement :");
		nameLbl.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		nameLbl.setBounds(29, 22, 236, 25);
		add(nameLbl);
		
		createButton = new JButton("Cr\u00E9er le d\u00E9partement");
		createButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		createButton.setBounds(314, 47, 179, 51);
		add(createButton);
		createButton.addActionListener(e -> {departmentControler.addDepartment();});
		
		deleteButton = new JButton("Supprimer");
		deleteButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		deleteButton.setBounds(301, 427, 144, 21);
		add(deleteButton);
		deleteButton.addActionListener(e -> {departmentControler.removeDepartment();});
		
		departmentControler.refresh();
	}

	/**
	 * Get the name text field
	 * @return JTextField : the name text field
	 */
	public JTextField getNameLabel() {
		return nameLabel;
	}

	/**
	 * Get the department comboBox
	 * @return JComboBox of department : the combo box of departments
	 */
	public JComboBox<StandardDepartment> getDepartmentsComboBox() {
		return departmentsComboBox;
	}
	
	/**
	 * Get the controller
	 * @return DepartmentControler : the department controller
	 */
	public DepartmentControler getControler() {
		return departmentControler;
	}
	
	/**
	 * Get the information subView
	 * @return DepartmentInformationPanel : the department information view
	 */
	public DepartmentInformationPanel getInformationPanel() {
		return informationPanel;
	}
}
