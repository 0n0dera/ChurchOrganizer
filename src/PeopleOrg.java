import javax.swing.*; 

import java.io.*;
import java.awt.* ;
import java.awt.event.*;

public class PeopleOrg extends JFrame implements ActionListener{
	static Container c;
	JButton sound,proj,park,recep,seat,back;
	JPanel p;
	File f;	
	PeoplePanel pplPanel;
	public PeopleOrg(){
		super("People Organizer");
		c = getContentPane();
		p = new JPanel();
		p.setLayout(new GridLayout(3,2,0,0));
		sound = new JButton("Sound Group");
		proj = new JButton("Projection Group");
		park = new JButton("Parking Group");
		recep = new JButton("Reception Group");
		seat = new JButton("Seating Group");
		back = new JButton("Go Back");
		p.add(sound);
		p.add(proj);
		p.add(park);
		p.add(recep);
		p.add(seat);
		p.add(back);
		sound.addActionListener(this);
		proj.addActionListener(this);
		park.addActionListener(this);
		recep.addActionListener(this);
		seat.addActionListener(this);
		back.addActionListener(this);
		c.add(p);
		this.setSize(400,400);
		this.setVisible(true);


	}
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (b==back){
			this.setVisible(false);
			dispose();
		}
		else{	
			f = new File("C:\\Users\\Andy\\Desktop\\workspace\\ChurchOrganizer\\Names\\"+b.getText()+".txt");
			try {
				pplPanel = new PeoplePanel(b.getText(),f);
				pplPanel.setVisible(true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
