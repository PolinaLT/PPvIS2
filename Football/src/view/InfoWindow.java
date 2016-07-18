package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;

public class InfoWindow {
	private int number;
	JFrame frame = new JFrame("Search");
	JButton okButton = new JButton("OK");
	
	public InfoWindow(int number, String key) {
		this.number = number;
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(300, 300));

        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createCompoundBorder());
        panel.setBackground(Color.LIGHT_GRAY);
        frame.add(panel, BorderLayout.CENTER);
        
        
        System.out.print(key);
        
		if (key.equals("search")) {
			
			JLabel label = new JLabel("Table contains " + number + " entries for your request");
			panel.add(label);
		}
		
		panel.add(okButton);
		frame.setVisible(true);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		
		
		
		
	}
}
