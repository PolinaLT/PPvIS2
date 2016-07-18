package view;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;

import controller.Command;
import controller.Controller;
import entity.Composition;
import entity.Faculty;
import entity.Role;

public class SearchRemoveWindow extends JFrame {
	private Checkbox birthdayCheckbox = new Checkbox("Search by FIO & Birthday");
	private Checkbox roleCheckbox = new Checkbox("Search by Role & Composition");
	private Checkbox footballClubCheckbox = new Checkbox("Search by Football Club & FIO");
	private Checkbox facultyCheckbox = new Checkbox("Search by FIO & Faculty");
	private String searchFirst;
	private String searchSecond;
	private String searchThird;
	private String searchDate;
	private String faculty;
	private String searchFootballClub;
	private String role;
	private String composition;
	private final Controller controller = new Controller();
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JButton okButton = new JButton("OK");
	private JButton searchRemoveButton = new JButton("Search & Remove");
	private JButton searchButton = new JButton("Search");
	private JFrame infoFrame;
	private JPanel infoPanel;
	
	
	
	public SearchRemoveWindow(JPanel tablePanel) {
		
		
		frame.setTitle("Request");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(300, 300));

        frame.setLayout(new BorderLayout());

        
        panel.setBorder(BorderFactory.createCompoundBorder());
        panel.setBackground(Color.LIGHT_GRAY);
        frame.add(panel, BorderLayout.CENTER);
        
        panel.add(birthdayCheckbox);
        panel.add(facultyCheckbox);
        panel.add(roleCheckbox);
        panel.add(footballClubCheckbox);
             
        birthdayCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				inputDialog();
				inputBirthday(tablePanel);
				frame.setVisible(false);
			}
        });
        
        facultyCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				inputDialog();
				inputFaculty(tablePanel);
			}
        });
		
        footballClubCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				inputDialog();
				inputFootballClub(tablePanel);
			}
        });
        
        roleCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				inputDialog();
				inputRole(tablePanel);
				
			}
        });

        frame.setVisible(true);
	}
	
	private void inputDialog() {
		infoFrame = new JFrame("Request Info");
        infoFrame.setSize(new Dimension(500, 300));
        infoFrame.setLayout(new BorderLayout());

        infoPanel = new JPanel();
        
        infoPanel.setBorder(BorderFactory.createCompoundBorder());
        infoPanel.setBackground(Color.WHITE);
        infoFrame.add(infoPanel, BorderLayout.CENTER);
        
        okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoFrame.setVisible(false);
			}
		});
		
	}
	
	private void inputBirthday(JPanel tablePanel) {
		JLabel firstLabel = new JLabel("Input First Name");
        TextField firstField = new TextField(60);
        JLabel secondLabel = new JLabel("Input Second Name");
        TextField secondField = new TextField(60);
        JLabel thirdLabel = new JLabel("Input Third Name");
        TextField thirdField = new TextField(60);
        
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
        
        
		infoPanel.add(firstLabel);
		infoPanel.add(firstField);
		infoPanel.add(secondLabel);
		infoPanel.add(secondField);
		infoPanel.add(thirdLabel);
		infoPanel.add(thirdField);
		infoPanel.add(dateLabel);
		infoPanel.add(daysCombo);
		infoPanel.add(mounthCombo);
		infoPanel.add(yearCombo);
		infoPanel.add(searchButton);
		infoPanel.add(searchRemoveButton);
		
		infoFrame.setVisible(true);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFirst = firstField.getText();
				searchSecond = secondField.getText();
				searchThird = thirdField.getText();
						
				Map<String, String> infoMap = new HashMap<>();
				infoMap.put("firstName", searchFirst);
				infoMap.put("secondName", searchSecond);
				infoMap.put("thirdName", searchThird);
				infoMap.put("date", searchDate);
				
				infoPanel.removeAll();
				infoPanel.add(okButton, BorderLayout.SOUTH);
				
				PageModel pageModel = new PageModel(controller.execute(Command.SEARCH, null, infoMap), tablePanel);
				infoFrame.add(pageModel, BorderLayout.SOUTH);
				infoFrame.setSize(new Dimension(650, 300));
				
				TableModel pageTable = pageModel.newPage(0);
				Table tableInfo = new Table(pageTable, infoPanel);
                				
			}
		});
		
		searchRemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFirst = firstField.getText();
				searchSecond = secondField.getText();
				searchThird = thirdField.getText();
				
				Map<String, String> infoMap = new HashMap<>();
				infoMap.put("firstName", searchFirst);
				infoMap.put("secondName", searchSecond);
				infoMap.put("thirdName", searchThird);
				infoMap.put("date", searchDate);

				infoFrame.setVisible(false);
				
				tablePanel.removeAll();
				
				PageModel pageModel = new PageModel(controller.execute(Command.REMOVE, null, infoMap), tablePanel);
								
				TableModel pageTable = pageModel.newPage(0);
				
                Table table = new Table(pageTable, tablePanel);
				
			}
		});
		
		
	}
	
	private void inputFaculty(JPanel tablePanel) {	
		JLabel firstLabel = new JLabel("Input First Name");
        TextField firstField = new TextField(60);
        JLabel secondLabel = new JLabel("Input Second Name");
        TextField secondField = new TextField(60);
        JLabel thirdLabel = new JLabel("Input Third Name");
        TextField thirdField = new TextField(60);

		infoPanel.add(firstLabel);
		infoPanel.add(firstField);
		infoPanel.add(secondLabel);
		infoPanel.add(secondField);
		infoPanel.add(thirdLabel);
		infoPanel.add(thirdField);
		
		
		Checkbox fityCheckbox = new Checkbox("FITY");
		infoPanel.add(fityCheckbox);
		fityCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				faculty = "FITY";
			}
        });
		
		Checkbox fksisCheckbox = new Checkbox("FKSIS");
		infoPanel.add(fksisCheckbox);
		fksisCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				faculty = "FKSIS";
			}
        });
		
		Checkbox ftkCheckbox = new Checkbox("FTK");
		infoPanel.add(ftkCheckbox);
		ftkCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				faculty = "FTK";
			}
        });
		
		Checkbox freCheckbox = new Checkbox("FRE");
		infoPanel.add(freCheckbox);
		freCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				faculty = "FRE";
			}
        });
		
		Checkbox fkpCheckbox = new Checkbox("FKP");
		infoPanel.add(fkpCheckbox);
		fkpCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				faculty = "FKP";
			}
        });
		
		Checkbox iefCheckbox = new Checkbox("IEF");
		infoPanel.add(iefCheckbox);
		iefCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				faculty = "IEF";
			}
        });
		
		infoPanel.add(searchButton);
		infoPanel.add(searchRemoveButton);
		
		infoFrame.setVisible(true);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFirst = firstField.getText();
				searchSecond = secondField.getText();
				searchThird = thirdField.getText();
								
				Map<String, String> infoMap = new HashMap<>();
				infoMap.put("firstName", searchFirst);
				infoMap.put("secondName", searchSecond);
				infoMap.put("thirdName", searchThird);
				
				infoMap.put("faculty", faculty);
				
				infoPanel.removeAll();
				infoPanel.add(okButton, BorderLayout.SOUTH);
				
				PageModel pageModel = new PageModel(controller.execute(Command.SEARCH, null, infoMap), tablePanel);
				infoFrame.add(pageModel, BorderLayout.SOUTH);
				infoFrame.setSize(new Dimension(650, 300));
				
				TableModel pageTable = pageModel.newPage(0);
				Table tableInfo = new Table(pageTable, infoPanel);

				frame.setVisible(false);
			}
		});
		
		searchRemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFirst = firstField.getText();
				searchSecond = secondField.getText();
				searchThird = thirdField.getText();
								
				Map<String, String> infoMap = new HashMap<>();
				infoMap.put("firstName", searchFirst);
				infoMap.put("secondName", searchSecond);
				infoMap.put("thirdName", searchThird);
				
				infoMap.put("faculty", faculty);
								
				tablePanel.removeAll();
				
				PageModel pageModel = new PageModel(controller.execute(Command.REMOVE, null, infoMap), tablePanel);
				infoFrame.setVisible(false);
				
				TableModel pageTable = pageModel.newPage(0);
                Table table = new Table(pageTable, tablePanel);
				frame.setVisible(false);
			}
		});
	}

	private void inputFootballClub(JPanel tablePanel) {
		JLabel firstLabel = new JLabel("Input First Name");
        TextField firstField = new TextField(60);
        JLabel secondLabel = new JLabel("Input Second Name");
        TextField secondField = new TextField(60);
        JLabel thirdLabel = new JLabel("Input Third Name");
        TextField thirdField = new TextField(60);

		infoPanel.add(firstLabel);
		infoPanel.add(firstField);
		infoPanel.add(secondLabel);
		infoPanel.add(secondField);
		infoPanel.add(thirdLabel);
		infoPanel.add(thirdField);
		
        JLabel footballClubLabel = new JLabel("Input Football Club");
        TextField footballClubField = new TextField(60);
        
		infoPanel.add(footballClubLabel);
		infoPanel.add(footballClubField);
		infoPanel.add(searchButton);
		infoPanel.add(searchRemoveButton);
		
		infoFrame.setVisible(true);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFirst = firstField.getText();
				searchSecond = secondField.getText();
				searchThird = thirdField.getText();
								
				Map<String, String> infoMap = new HashMap<>();
				infoMap.put("firstName", searchFirst);
				infoMap.put("secondName", searchSecond);
				infoMap.put("thirdName", searchThird);
				searchFootballClub = footballClubField.getText();
								
				infoMap.put("footballClub", searchFootballClub);
				
				infoPanel.removeAll();
				infoPanel.add(okButton, BorderLayout.SOUTH);
								
				PageModel pageModel = new PageModel(controller.execute(Command.SEARCH, null, infoMap), tablePanel);
				infoFrame.add(pageModel, BorderLayout.SOUTH);
				infoFrame.setSize(new Dimension(650, 300));
				
				TableModel pageTable = pageModel.newPage(0);
				Table tableInfo = new Table(pageTable, infoPanel);
                				
				frame.setVisible(false);
			}
		});
		
		searchRemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFirst = firstField.getText();
				searchSecond = secondField.getText();
				searchThird = thirdField.getText();
								
				Map<String, String> infoMap = new HashMap<>();
				infoMap.put("firstName", searchFirst);
				infoMap.put("secondName", searchSecond);
				infoMap.put("thirdName", searchThird);
				searchFootballClub = footballClubField.getText();
								
				infoMap.put("footballClub", searchFootballClub);
				infoFrame.setVisible(false);
				
				tablePanel.removeAll();
				
				PageModel pageModel = new PageModel(controller.execute(Command.REMOVE, null, infoMap), tablePanel);
				infoFrame.add(pageModel, BorderLayout.SOUTH);
				
				TableModel pageTable = pageModel.newPage(0);
				Table table = new Table(pageTable, tablePanel);
				
				frame.setVisible(false);
			}
		});
	}
	
	private void inputRole(JPanel tablePanel) {
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		JLabel roleLabel = new JLabel("Choose role");
		infoPanel.add(roleLabel);
		
		Checkbox goalkeeper = new Checkbox("GOALKEEPER");
		infoPanel.add(goalkeeper);
		goalkeeper.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				role = "GOALKEEPER";
			}
        });
		
		Checkbox quarterback = new Checkbox("QUARTERBACK");
		infoPanel.add(quarterback);
		quarterback.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				role = "QUARTERBACK";
			}
        });
		
		Checkbox halfback = new Checkbox("HALFBACK");
		infoPanel.add(halfback);
		halfback.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				role = "HALFBACK";
			}
        });
		
		Checkbox forward = new Checkbox("FORWARD");
		infoPanel.add(forward);
		forward.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				role = "FORWARD";
			}
        });
		
		JLabel compositionLabel = new JLabel("Choose composition");
		infoPanel.add(compositionLabel);
		
		Checkbox main = new Checkbox("MAIN");
		infoPanel.add(main);
		main.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				composition = "MAIN";
			}
        });
		
		Checkbox reserve = new Checkbox("RESERVE");
		infoPanel.add(reserve);
		reserve.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				composition = "RESERVE";
			}
        });
		
		infoPanel.add(searchButton);
		infoPanel.add(searchRemoveButton);
		
		infoFrame.setVisible(true);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Map<String, String> infoMap = new HashMap<>();
				infoMap.put("role", role);
				infoMap.put("composition", composition);
				
				infoPanel.removeAll();
				infoPanel.add(okButton, BorderLayout.SOUTH);
								
				PageModel pageModel = new PageModel(controller.execute(Command.SEARCH, null, infoMap), tablePanel);
				infoFrame.add(pageModel, BorderLayout.SOUTH);
				infoFrame.setSize(new Dimension(650, 300));
				
				TableModel pageTable = pageModel.newPage(0);
				Table tableInfo = new Table(pageTable, infoPanel);
                				
				frame.setVisible(false);
			}
		});
		
		searchRemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Map<String, String> infoMap = new HashMap<>();
				infoMap.put("role", role);
				infoMap.put("composition", composition);
				
				tablePanel.removeAll();
				
				PageModel pageModel = new PageModel(controller.execute(Command.REMOVE, null, infoMap), tablePanel);				
				infoFrame.setVisible(false);
				
				TableModel pageTable = pageModel.newPage(0);
                Table table = new Table(pageTable, tablePanel);
				frame.setVisible(false);
			}
		});
	}
	
	
        
	

	
}

	