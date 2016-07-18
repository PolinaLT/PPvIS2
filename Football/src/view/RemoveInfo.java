package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RemoveInfo {
	public RemoveInfo(int number) {
		JFrame frame = new JFrame("Remove");
		frame.setSize(200, 100);
		JPanel pane = new JPanel();
		frame.add(pane);
		
		JLabel label = new JLabel();
		if (number > 1) {
			label.setText(number + " students were remove");
		}
		else if (number == 1) {
			label.setText("1 student was remove");
		}
		else label.setText("No one student was not found for your request");
		pane.add(label);
		frame.setVisible(true);
	}
}
