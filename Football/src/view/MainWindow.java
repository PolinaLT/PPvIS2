package view;

import javax.swing.*;
import javax.swing.table.TableModel;

import controller.Command;
import controller.Controller;
import entity.Student;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends JFrame {

    private JPanel buttonPanel;
    private JPanel tablePanel;
    private final Controller controller = new Controller();
    
    private JFrame frame = new JFrame("Table");
    private String file = "d:/workspace/Football/resources/request.png";
    
    private JPanel pagePanel;
    

    public MainWindow() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(700, 350));

        frame.setLayout(new BorderLayout());

        tablePanel = new JPanel();
        tablePanel.setBorder(BorderFactory.createCompoundBorder());
        tablePanel.setBackground(Color.LIGHT_GRAY);
        frame.add(tablePanel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
                               
        JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem getButton = new JMenuItem("Open");
        menu.add(getButton);
        
        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("resources");
                fileChooser.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String filePath = fileChooser.getSelectedFile().getPath();
                                                
                        PageModel pageModel = new PageModel(controller.execute(Command.GET_DATA, filePath, null), tablePanel);
						frame.add(pageModel, BorderLayout.SOUTH);
						
						TableModel pageTable = pageModel.newPage(0);
						
                        Table table = new Table(pageTable, tablePanel);
                        
                        
						
                        frame.revalidate();
                    }
                });
                fileChooser.showOpenDialog(buttonPanel.getParent());
            }
        });
        
        
        
        JMenuItem saveButton = new JMenuItem("Save");
        menu.add(saveButton);
        
        saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser("resources");
				fileChooser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String saveFile = fileChooser.getSelectedFile().getPath();
						java.util.List<Student> tableModel = controller.execute(Command.SAVE, saveFile, null);				        
					}
				});
				fileChooser.showSaveDialog(buttonPanel.getParent());
			}
		});
        
        menuBar.add(menu);
        frame.add(menuBar, BorderLayout.NORTH);
        
        JButton searchButton = new JButton(new ImageIcon("d:/workspace/Football/resources/request.png"));
        //JButton searchButton = new JButton("Request");
        searchButton.setBorder(BorderFactory.createEmptyBorder());
        searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchRemoveWindow request = new SearchRemoveWindow(tablePanel);
				frame.revalidate();
				
			}
        });
        
        JButton addButton = new JButton(new ImageIcon("d:/workspace/Football/resources/add.png"));
        addButton.setBorder(BorderFactory.createEmptyBorder());
        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddWindow add = new AddWindow(tablePanel);
				frame.revalidate();
			}
		});
        
        buttonPanel.add(searchButton);
        buttonPanel.add(addButton);
 
        frame.add(buttonPanel, BorderLayout.WEST);

        frame.setVisible(true);
    }

     
    
    
    public void setTable(TableModel tableModel) {
    	JTable table = new JTable(tableModel);
        tablePanel.removeAll();
        tablePanel.add(table);
        table.setVisible(true);
        frame.revalidate();
    }

    

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}

