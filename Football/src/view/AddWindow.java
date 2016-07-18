package view;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.RoleInfo;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import controller.Command;
import controller.Controller;
import entity.Student;

public class AddWindow {
	private JFrame frame = new JFrame("Add");
	private JPanel panel = new JPanel();
	private String roleInfo;
	private String compositionInfo;
	private String facultyInfo;
	private final Controller controller = new Controller();
	private String searchDate;
	
	public AddWindow(JPanel tablePanel) {
		
		
		frame.setSize(500, 500);
		frame.add(panel);
		
		JLabel firstNameLabel = new JLabel("First Name");
		TextField firstName = new TextField(60);
		JLabel secondNameLabel = new JLabel("Second Name");
		TextField secondName = new TextField(60);
		JLabel thirdNameLabel = new JLabel("Third Name");
		TextField thirdName = new TextField(60);
		JLabel birthdayLabel = new JLabel("Birthday");
		
		String[] faculty = {"", "FITY",	"KSIS",	"FRE",	"FTK",	"FKP",	"IEF"};
		JComboBox<String> facultyCombo = new JComboBox<>(faculty);
		
		String[] role = {"", "GOALKEEPER", "QUARTERBACK", "HALFBACK", "FORWARD"};
		JComboBox<String> roleCombo = new JComboBox<>(role);
		
		String[] composition = {"", "MAIN", "RESERVE"};
		JComboBox<String> compositionCombo = new JComboBox<>(composition);
		
		facultyCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facultyInfo = (String) facultyCombo.getSelectedItem();				
			}
		});
		
		roleCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roleInfo = (String) roleCombo.getSelectedItem();				
			}
		});
		
		compositionCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compositionInfo = (String) compositionCombo.getSelectedItem();				
			}
		});
		
		JLabel clubLabel = new JLabel("Input Football Club");
		TextField club = new TextField(60);
		
		JLabel dateLabel = new JLabel("Input Birthday");
        String[] dateArray = new String[32];
        dateArray[0] = "";
        for (int day = 1; day < 32; day++) {
        	if (day < 10) dateArray[day] = "0" + String.valueOf(day); 
        	else dateArray[day] = String.valueOf(day); 
        }
        System.out.println(dateArray);
        JComboBox<String> daysCombo = new JComboBox<>(dateArray);
        daysCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchDate = (String) daysCombo.getSelectedItem() + ".";
			}
		});
        
        String[] mounthArray = new String[13];
        mounthArray[0] = "";
        for (int mounth = 1; mounth < 13; mounth++) {
        	if (mounth<10) mounthArray[mounth] = "0" + String.valueOf(mounth);
        	else mounthArray[mounth] = String.valueOf(mounth);
        }
        JComboBox<String> mounthCombo = new JComboBox<>(mounthArray);
        mounthCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchDate = searchDate + (String) mounthCombo.getSelectedItem() + ".";
			}
		});
        
        String[] yearArray = new String[54];
        yearArray[0] = "";
        for (int year = 1964; year < 2017; year++) {
        	yearArray[year-1963] = String.valueOf(year);
        }
        JComboBox<String> yearCombo = new JComboBox<>(yearArray);
        yearCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchDate = searchDate + (String) yearCombo.getSelectedItem();
			}
		});
		
		panel.add(firstNameLabel);
		panel.add(firstName);
		panel.add(secondNameLabel);
		panel.add(secondName);
		panel.add(thirdNameLabel);
		panel.add(thirdName);
		panel.add(birthdayLabel);
		panel.add(daysCombo);
		panel.add(mounthCombo);
		panel.add(yearCombo);
		panel.add(clubLabel);
		panel.add(club);
		panel.add(facultyCombo);
		panel.add(roleCombo);
		panel.add(compositionCombo);
		
		JButton addButton = new JButton("Add");
		panel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<String, String> infoMap = new HashMap<>();
				
				String firstNameInfo = firstName.getText();
				infoMap.put("firstName", firstNameInfo);
				
				String secondNameInfo = secondName.getText();
				infoMap.put("secondName", secondNameInfo);
				
				String thirdNameInfo = thirdName.getText();
				infoMap.put("thirdName", thirdNameInfo);
				
				String birthdayInfo = searchDate;
				infoMap.put("date", birthdayInfo);
				
				String clubInfo = club.getText();
				infoMap.put("footballClub", clubInfo);
				
				infoMap.put("role", roleInfo);
				infoMap.put("composition", compositionInfo);
				infoMap.put("faculty", facultyInfo);
				
				
				List<Student> list = controller.execute(Command.ADD, null, infoMap);
				System.out.println(list.size());
				PageModel pageModel = new PageModel(list, tablePanel);
				frame.add(pageModel, BorderLayout.SOUTH);
				
				TableModel pageTable = pageModel.newPage(0);
				
                Table table = new Table(pageTable, tablePanel);
				
				frame.setVisible(false);
				
			}
		});
		
		
		
		frame.setVisible(true);
	}
}
