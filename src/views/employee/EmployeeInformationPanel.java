package views.employee;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * This class represent the employee information subView
 * according to the MVC pattern
 * @author Louis Babuchon
 *
 */
@SuppressWarnings("serial")
public class EmployeeInformationPanel extends JPanel{
	
	/**
	 * The name label
	 */
	private JLabel nameLabel;
	
	/**
	 * The department label
	 */
	private JLabel departmentLabel;
	
	/**
	 * The hourLabel
	 */
	private JLabel hourLabel;
	
	/**
	 * The construction, initiates attributes and set up the view
	 */
	public EmployeeInformationPanel() {
		
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informations", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setBounds(29, 247, 741, 169);
		setLayout(null);
		
		nameLabel = new JLabel("");
		nameLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
		nameLabel.setBounds(25, 29, 678, 33);
		add(nameLabel);
		
		departmentLabel = new JLabel("");
		departmentLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
		departmentLabel.setBounds(25, 73, 678, 33);
		add(departmentLabel);
		
		hourLabel = new JLabel("");
		hourLabel.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
		hourLabel.setBounds(25, 125, 678, 33);
		add(hourLabel);
		
	}

	/**
	 * Get the name Label
	 * @return JLabel : the name label
	 */
	public JLabel getNameLabel() {
		return nameLabel;
	}

	/**
	 * Get the department label
	 * @return JLabel : the department label
	 */
	public JLabel getDepartmentLabel() {
		return departmentLabel;
	}

	/**
	 * Get the hour label
	 * @return JLabel : the hour label
	 */
	public JLabel getHourLabel() {
		return hourLabel;
	}
}
