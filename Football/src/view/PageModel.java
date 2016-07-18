package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.peer.PanelPeer;
import java.util.List;

import view.MainWindow;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import controller.Command;
import controller.Controller;
import entity.Student;

public class PageModel extends JPanel {
	private List<Student> studentList;
	int currentRows = 10;
	private int rowNumber;
	JPanel tablePanel;
	private int pageNumber;
	private int currentPage = 1;
	private Controller controller = new Controller();
	private JPanel pagePanel = new JPanel();
	private JLabel rowLabel;
		
	public PageModel(List<Student> studentList, JPanel tablePanel) {
		this.tablePanel = tablePanel;
		this.studentList = studentList;
		rowNumber = studentList.size();
		
		if ((rowNumber % currentRows) != 0) pageNumber = rowNumber / currentRows + 1;
		else pageNumber = rowNumber / currentRows;
		pagePanel();
		info();
		
	}
	
	public TableModel newPage(int firstRow) {
		List<Student> list;
		if (firstRow + currentRows > rowNumber)
			list = studentList.subList(firstRow, rowNumber);
		else 
			list = studentList.subList(firstRow, firstRow + currentRows);
		return controller.getTableModel(Command.GET_LIST, list);
	}
	
	public void pagePanel() {    	  	
    	 	
    	JButton firstPage = new JButton("first page");
    	JButton lastPage = new JButton("last page");
    	JButton previousPage = new JButton("previous page");
    	JButton nextPage = new JButton("next page");
    	pagePanel.add(firstPage);
    	pagePanel.add(lastPage);
    	pagePanel.add(previousPage);
    	pagePanel.add(nextPage);
    
    	
    	this.add(pagePanel);
    	
    	String[] arrayOfNumbers = {"10", "4", "5", "6", "7", "1"};
    	JComboBox<String> numberBox = new JComboBox<>(arrayOfNumbers);
    	numberBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String numberString = (String) numberBox.getSelectedItem();
				currentRows = Integer.parseInt(numberString);
				if ((rowNumber % currentRows) != 0) pageNumber = rowNumber / currentRows + 1;
				else pageNumber = rowNumber / currentRows;
				currentPage = 1;
				pagePanel.remove(rowLabel);
				info();
				Table table = new Table(newPage(0), tablePanel);
			}
		});
    	
    	pagePanel.add(numberBox);
    	
    	
    	firstPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Table table = new Table(newPage(0), tablePanel);
				currentPage = 1;
			}
		});
    	
    	lastPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				Table table = new Table(newPage(rowNumber - currentRows), tablePanel);
				currentPage = pageNumber;
			}
		});
    	
    	nextPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentPage < pageNumber - 1) {
				Table table = new Table(newPage(currentRows * currentPage), tablePanel);
				currentPage++;
				}
				if (currentPage == pageNumber - 1) {
					Table table = new Table(newPage(rowNumber - currentRows), tablePanel);
					currentPage++;
				}
			}
		});
    	
    	previousPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentPage != 1) {
					Table table = new Table(newPage(currentPage * currentRows - 2 * currentRows), tablePanel);
					currentPage--;
				}
			}
		});
    }
	
	public void info(){
		
		rowLabel = new JLabel(currentRows + "/" + rowNumber + "students");
		pagePanel.add(rowLabel);
	}
}