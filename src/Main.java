import javax.swing.*; 

import java.io.*;
import java.awt.* ;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener{
	static Container c;
	JButton sched, people;
	JPanel panel;
	Organizer o;
	PeopleOrg p;
	public Main(){
		super("Menu");
		c = getContentPane();
		sched = new JButton("Edit Schedule");
		people = new JButton("Edit People");
		panel = new JPanel();
		panel.setLayout(new GridLayout(1,2,0,0));
		panel.add(sched);
		panel.add(people);
		sched.addActionListener(this);
		people.addActionListener(this);
		c.add(panel);
		this.setSize(300,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	public static void main(String[]args){
		Main m = new Main();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if ((JButton)e.getSource()==sched){
			o = new Organizer();
		}
		else{
			p = new PeopleOrg();
		}
		
		
	}
	
}
