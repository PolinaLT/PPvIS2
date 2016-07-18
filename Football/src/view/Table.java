package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class Table {
	public Table(TableModel tableModel, JPanel tablePanel) {
		JTable table = new JTable(tableModel);
		tablePanel.removeAll();
		JScrollPane pane = new JScrollPane(table);
		pane.setPreferredSize(new Dimension(630, 200));
        tablePanel.add(pane);
        //setPreferredSize(new Dimension(500, 500));
        setColumnSize(table);
        setColumnNames(table);
        
        table.setVisible(true);
        //tablePanel.add(new JLabel("HELLO"), BorderLayout.CENTER);
        tablePanel.revalidate();
        tablePanel.repaint();
	}
	
	private void setColumnSize(JTable table) {
		TableColumn column = null;
        for (int i = 0; i < 6; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(200); 
            } 
            else if (i == 1) {
            	column.setPreferredWidth(80);
            }
            else if (i == 4) {
            	column.setPreferredWidth(100);
            }
            else if (i == 5) {
            	column.setPreferredWidth(150);
            }
            else {
                column.setPreferredWidth(50);
            }
        }
	}
	
	private void setColumnNames(JTable table) {
		table.getColumnModel().getColumn(0).setHeaderValue("FIO");
		table.getColumnModel().getColumn(1).setHeaderValue("Birthday");
		table.getColumnModel().getColumn(2).setHeaderValue("Club");
		table.getColumnModel().getColumn(3).setHeaderValue("Faculty");
		table.getColumnModel().getColumn(4).setHeaderValue("Composition");
		table.getColumnModel().getColumn(5).setHeaderValue("Role");
	}
}
