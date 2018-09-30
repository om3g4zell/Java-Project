package controlers;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.Company;
import views.MyWindow;

/**
 * This class is the controller of the mainWindow
 * according to the MVC pattern
 * 
 * @author Louis Babuchon
 *
 */
public class WindowControler {
	
	/**
	 * The company
	 */
	private Company company;
	
	/**
	 * The view
	 */
	private MyWindow window;
	
	/**
	 * The constructor
	 * initiate attributes
	 * @param window : the WindowView
	 */
	public WindowControler(MyWindow window) {
		this.window = window;
		company = Company.getInstance();
	}

	/**
	 * Change the view that is displayed in the employee view
	 */
	public void showEmployeeView() {
		window.getEmployeeView().setVisible(true);
		window.getDepartmentView().setVisible(false);
		window.getCheckView().setVisible(false);
		
	}

	/**
	 * Change the view that is displayed in the department view
	 */
	public void showDepartmentView() {
		window.getEmployeeView().setVisible(false);
		window.getDepartmentView().setVisible(true);
		window.getCheckView().setVisible(false);
		
	}

	/**
	 * Change the view that is displayed in the check view
	 */
	public void showCheckView() {
		window.getEmployeeView().setVisible(false);
		window.getDepartmentView().setVisible(false);
		window.getCheckView().setVisible(true);
		
	}

	/**
	 * Call the model to export all the employees
	 * in the CSV format according to the selected path
	 * in the dialogue frame
	 */
	public void exportCSV() {
        File currentFile = null;
        try {
        	currentFile = new File(".").getCanonicalFile();
        } catch(IOException e) {
        	e.printStackTrace();
        }
        JFileChooser dialog = new JFileChooser(currentFile);
        dialog.setDialogTitle("Choisissez le nom du fichier à exporter");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
       	     "csv files (*.csv)", "csv");
        dialog.setFileFilter(filter);
        dialog.showSaveDialog(window);

        String path = "" + dialog.getSelectedFile();
        
        if(path.equals("null")) return;
        
        int point = path.lastIndexOf(".");
        
        if(point == -1 ) {
        	JOptionPane.showMessageDialog(window, "Le format n'est pas .csv !", "Attention", JOptionPane.INFORMATION_MESSAGE);
        	return;
        }
        else if(!(path.substring(point + 1).equals("csv"))) {
        	JOptionPane.showMessageDialog(window, "Le format n'est pas .csv !", "Attention", JOptionPane.INFORMATION_MESSAGE);
        	return;
        }
        
        company.exportCSV("" + dialog.getSelectedFile());
        JOptionPane.showMessageDialog(window, "Le fichier a été crée", "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Call the model to import employees
	 * from a file in the CSV format according to the selected path
	 * in the dialogue frame
	 */
	public void importCSV() {
        File currentFile = null;
        try {
        	currentFile = new File(".").getCanonicalFile();
        } catch(IOException e) {
        	e.printStackTrace();
        }
        JFileChooser dialog = new JFileChooser(currentFile);
        dialog.setDialogTitle("Choisissez le fichier à importer");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        	     "csv files (*.csv)", "csv");
        dialog.setFileFilter(filter);
        dialog.showOpenDialog(window);
        
        String path = "" + dialog.getSelectedFile();
        
        if(path.equals("null")) return;
        
        int point = path.lastIndexOf(".");
        if(point == -1 ) {
        	JOptionPane.showMessageDialog(window, "Le format n'est pas .csv !", "Attention", JOptionPane.INFORMATION_MESSAGE);
        	return;
        }
        else if(!(path.substring(point + 1).equals("csv"))) {
        	JOptionPane.showMessageDialog(window, "Le format n'est pas .csv !", "Attention", JOptionPane.INFORMATION_MESSAGE);
        	return;
        }

        company.importCSV("" + dialog.getSelectedFile());
        JOptionPane.showMessageDialog(window, "Le fichier a été importé", "Information", JOptionPane.INFORMATION_MESSAGE);
	}
}
