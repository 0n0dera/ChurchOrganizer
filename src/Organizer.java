import javax.swing.*; 

import java.io.*;
import java.awt.* ;
import java.awt.event.*;

public class Organizer extends JFrame implements ActionListener{
	static Container c;
	static JButton[] months = new JButton[12];
	static String[] monthNames = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	static JPanel monthPanel;
	static SchedulePanel schedulePanel;
	static File file;



	public Organizer(){
		
		super("Scheduler");
		c = getContentPane();
		monthPanel = new JPanel();
		monthPanel.setLayout(new GridLayout(6,2,10,10));
		for (int i=0;i<12;i++){
			months[i] = new JButton(monthNames[i]);
			months[i].addActionListener(this);
			monthPanel.add(months[i]);
		}
		c.add(monthPanel);
		this.setSize(500,500);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		file = 	new File("C:\\Users\\Andy\\Desktop\\workspace\\ChurchOrganizer\\Months\\"+b.getText()+".txt");
		try {
			schedulePanel = new SchedulePanel(b.getText(),file);
			this.setVisible(false);
			schedulePanel.setVisible(true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
		}
	}
}
