package views;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controlers.WindowControler;
import main.MainApp;
import models.Company;
import views.check.CheckView;
import views.department.DepartmentView;
import views.employee.EmployeeView;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * This is the main view of the application according to the MVC pattern
 * @author Louis Babuchon
 *
 */
@SuppressWarnings("serial")
public class MyWindow extends JFrame{
	
	/**
	 * The employee button
	 */
	private JButton employeeButton;
	
	/**
	 * The department button
	 */
	private JButton departmentButton;
	
	/**
	 * The check button
	 */
	private JButton checkButton;
	
	/**
	 * The employee view
	 */
	private EmployeeView employeeView;
	
	/**
	 * The department view
	 */
	private DepartmentView departmentView;
	
	/**
	 * The check view
	 */
	private CheckView checkView;

	/**
	 * The menu bar
	 */
	private JMenuBar menuBar;
	
	/**
	 * The file menu
	 */
	private JMenu mnFile;
	
	/**
	 * The import menu item
	 */
	private JMenuItem menuItemImportCsv;
	
	/**
	 * The export menu item
	 */
	private JMenuItem menuItemExportCsv;
	
	/**
	 * The window controller
	 */
	private WindowControler windowControler;
	
	/**
	 * The constructor, initiates attributes, change the look and feel of the window
	 * Set up the serialization when the window is closed and set up the view
	 */
	public MyWindow() {
		// Change the look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		addWindowListener(new WindowAdapter() {
	    	@Override
            public void windowClosing(WindowEvent e)
            {
	    		String dir;
				try {
					dir = new File(".").getCanonicalPath() + MainApp.directory;
					String path = dir + MainApp.PATH;
	                Company.getInstance().serialize(path);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
		});
		
		windowControler = new WindowControler(this);
		
	    this.setTitle("TimeTracker");
	    
	    this.setSize(870, 630);
	    
	    
	    this.setLocationRelativeTo(null);
	    
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             

	    this.setResizable(false);
	   
	    getContentPane().setLayout(null);
	    
	    employeeButton = new JButton("Employ\u00E9es");
	    employeeButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
	    employeeButton.setBounds(70, 28, 176, 67);
	    getContentPane().add(employeeButton);
	    employeeButton.addActionListener(e -> {windowControler.showEmployeeView();});
	    
	    departmentButton = new JButton("D\u00E9partements");
	    departmentButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
	    departmentButton.setBounds(357, 28, 182, 67);
	    getContentPane().add(departmentButton);
	    departmentButton.addActionListener(e -> {windowControler.showDepartmentView();});
	    
	    checkButton = new JButton("Pr\u00E9sences");
	    checkButton.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
	    checkButton.setBounds(633, 28, 176, 67);
	    getContentPane().add(checkButton);
	    checkButton.addActionListener(e -> {windowControler.showCheckView();});
	    
	    menuBar = new JMenuBar();
	    setJMenuBar(menuBar);
	    
	    mnFile = new JMenu("Fichier");
	    menuBar.add(mnFile);
	    
	    menuItemImportCsv = new JMenuItem("Importer CSV");
	    menuItemImportCsv.setFont(new Font("Segoe UI", Font.PLAIN, 11));
	    mnFile.add(menuItemImportCsv);
	    menuItemImportCsv.addActionListener(e -> {windowControler.importCSV();});
	    
	    menuItemExportCsv = new JMenuItem("Exporter CSV");
	    menuItemExportCsv.setFont(new Font("Segoe UI", Font.PLAIN, 11));
	    mnFile.add(menuItemExportCsv);
	    menuItemExportCsv.addActionListener(e -> {windowControler.exportCSV();});
	    
	    addElement();
	    
	}
	
	/**
	 * Create and instanciate views
	 */
	public void addElement() {
		
		employeeView = new EmployeeView();
	    employeeView.setBounds(36, 108, 803, 459);
	    getContentPane().add(employeeView);
	    employeeView.setVisible(false);
	    
	    departmentView = new DepartmentView();
	    departmentView.setBounds(36, 110, 803, 459);
	    getContentPane().add(departmentView);
	    departmentView.setVisible(false);
	    
	    checkView = new CheckView();
	    checkView.setBounds(36, 110, 803, 459);
	    getContentPane().add(checkView);
	    checkView.setVisible(false);
	    
	}
	
	/**
	 * Get the employeeView
	 * @return EmployeeView : the employee view
	 */
	public EmployeeView getEmployeeView() {
		return employeeView;
	}

	/**
	 * Get the department view
	 * @return DepartmentView : the department view
	 */
	public DepartmentView getDepartmentView() {
		return departmentView;
	}

	/**
	 * Get the checkView
	 * @return CheckView : the check view
	 */
	public CheckView getCheckView() {
		return checkView;
	}
}
