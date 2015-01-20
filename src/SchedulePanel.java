import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class SchedulePanel extends JFrame implements ActionListener{
	JPanel panel;
	Organizer o;
	JLabel sound,proj,park,recep,seating,lunch;
	JComboBox[] allList;
	JComboBox soundList,projList,parkList,recepList1,recepList2,seatList,lunchList;
	ArrayList<String[][]> soundA,projA,parkA,recepA1,recepA2,seatA,lunchA;
	String[] ref;
	ArrayList<ArrayList<String[][]>> allArray;
	BufferedReader r,x;
	JButton save,clear;
	PrintWriter w;
	File fil,f;
	Container c;

	public SchedulePanel(String month,File file) throws IOException{
		super("Duties");
		panel  = new JPanel();
		f = file;
		this.setSize(600,600);
		save = new JButton("Save and Go Back");	
		clear = new JButton("Clear");
		ref = new String[]{"Sound","Projection","Parking","Reception","Reception","Seating","Lunch"};
		r = new BufferedReader(new FileReader(file));
		sound = new JLabel("Sound");
		proj = new JLabel("Projection");
		park = new JLabel("Parking");
		recep = new JLabel("Reception");
		seating = new JLabel("Seating Helper");
		lunch = new JLabel("Lunch Helper");
		soundA = new ArrayList<String[][]>();
		projA = new ArrayList<String[][]>();
		parkA = new ArrayList<String[][]>();
		recepA1 = new ArrayList<String[][]>();
		recepA2 = new ArrayList<String[][]>();
		seatA = new ArrayList<String[][]>();
		lunchA = new ArrayList<String[][]>();
		allArray = new ArrayList<ArrayList<String[][]>>(6);
		allArray.add(soundA);
		allArray.add(projA);
		allArray.add(parkA);
		allArray.add(recepA1);
		allArray.add(recepA2);
		allArray.add(seatA);
		allArray.add(lunchA);		
		soundList = new JComboBox();
		projList = new JComboBox();
		parkList = new JComboBox();
		recepList1 = new JComboBox();
		recepList2 = new JComboBox();
		seatList = new JComboBox();
		lunchList = new JComboBox();
		allList = new JComboBox[]{soundList,projList,parkList,recepList1,recepList2,seatList,lunchList};
		populateLists();
		setBoxes(r);		
		r.close();
		c = getContentPane();
		panel.setLayout(new GridLayout(9,2,10,10));
		panel.add(sound);
		panel.add(soundList);
		panel.add(proj);
		panel.add(projList);
		panel.add(park);
		panel.add(parkList);
		panel.add(recep);
		panel.add(recepList1);
		panel.add(new JLabel());
		panel.add(recepList2);
		panel.add(seating);
		panel.add(seatList);
		panel.add(lunch);
		panel.add(lunchList);
		panel.add(save);
		panel.add(clear);
		c.add(panel);
		save.addActionListener(this);
		clear.addActionListener(this);
		for (JComboBox j:allList){
			j.addActionListener(this);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource()==save){
			try {
				w = new PrintWriter(f);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			w.println(soundList.getSelectedIndex());
			w.println(projList.getSelectedIndex());
			w.println(parkList.getSelectedIndex());
			w.println(recepList1.getSelectedIndex());
			w.println(recepList2.getSelectedIndex());
			w.println(seatList.getSelectedIndex());
			w.println(lunchList.getSelectedIndex());
			w.close();
			this.setVisible(false);
			o = new Organizer();

		}
		else if (e.getSource() == clear){
			for (JComboBox j:allList){
				j.setSelectedIndex(-1);
			}
		}
		else if (e.getSource() == soundList){
			int a  = soundList.getSelectedIndex();
			if (a!=-1){
				String[][] s  = soundA.get(a);
				checkOK(soundList,s);
			}

		}
		else if (e.getSource() == projList){
			int a  = projList.getSelectedIndex();
			if (a!=-1){
				String[][] s = projA.get(a);
				checkOK(projList,s);
			}
		}
		else if (e.getSource() == parkList){
			int a  = parkList.getSelectedIndex();
			if (a!=-1){
				String[][] s = parkA.get(a);
				checkOK(parkList,s);
			}
		}
		else if (e.getSource() == recepList1){
			int a  = recepList1.getSelectedIndex();
			if (a!=-1){
				String[][] s = recepA1.get(a);
				checkOK(recepList1,s);
				checkSpouse(s,1);
			}
		}
		else if (e.getSource() == recepList2){
			int a  = recepList2.getSelectedIndex();
			if (a!=-1){
				String[][] s = recepA2.get(a);
				checkOK(recepList2,s);
				checkSpouse(s,2);
			}
		}
		else if (e.getSource() == seatList){
			int a  = seatList.getSelectedIndex();
			if (a!=-1){
				String[][] s = seatA.get(a);
				checkOK(seatList,s);
			}

		}
		else if (e.getSource() == lunchList){
			int a  = lunchList.getSelectedIndex();
			if (a!=-1){
				String[][] s = lunchA.get(a);
				checkLunch(s);
			}
		}


	}

	public void checkOK(JComboBox box, String[][] s){
		for(JComboBox j:allList){
			if (j!=box&&!s[0][0].equals("")&&j.getSelectedItem()!=null){
				if(j.getSelectedItem().equals(s[0][0])){
					JOptionPane.showMessageDialog(null, "That person is already scheduled for another position.", "Conflict Error", JOptionPane.ERROR_MESSAGE);
					box.setSelectedIndex(-1);
				}
			}
			if (j==lunchList&&j.getSelectedIndex()!=-1&&s[0][1].equals(lunchA.get(j.getSelectedIndex())[0][0].substring(7))){
				JOptionPane.showMessageDialog(null, s[0][0]+" is already part of the lunch group for this month.", "Conflict Error", JOptionPane.ERROR_MESSAGE);
				box.setSelectedIndex(-1);
			}
		}
	}

	public void populateLists() throws IOException{
		for (int i=0;i<7;i++){

			fil = new File("C:\\Users\\Andy\\Desktop\\workspace\\ChurchOrganizer\\Names\\"+ref[i]+" Group.txt");
			x = new BufferedReader (new FileReader (fil));
			String line = x.readLine();
			while (line!=null){
				String[][] s = new String[1][3];
				s[0][0] = line.substring(0, line.indexOf("/"));
				line = line.substring(line.indexOf("/")+1);
				s[0][1]=line.substring(0,line.indexOf("/"));
				line = line.substring(line.indexOf("/")+1);
				s[0][2] = line;
				allArray.get(i).add(s);
				line = x.readLine();	
			}
			for (int j=0;j<allArray.get(i).size();j++){
				allList[i].addItem(allArray.get(i).get(j)[0][0]);				
			}
		}

		for (int i=1;i<31;i++){
			String [][] s = new String[1][3];
			s[0][0] = "Group "+i;
			s[0][1] = "";
			s[0][2] = "";			
			allArray.get(6).add(s);
			allList[6].addItem("Group "+i);
		}
	}

	public void setBoxes(BufferedReader r) throws NumberFormatException, IOException{
		for (int i=0;i<7;i++){
			if (allArray.get(i).size()==0){
				allList[i].setSelectedItem("");
				r.readLine();
			}
			else{
				Object l = r.readLine();
				if (l!=null){
					int z = Integer.parseInt((String) l);
					System.out.println(z);
					if (z>=allArray.get(i).size())
						allList[i].setSelectedIndex(-1);
					else allList[i].setSelectedIndex(z);
				}
			}
		}
	}

	public void checkLunch(String[][] s){
		String g = s[0][0].substring(6);
		for (int i=0;i<6;i++){
			if (allList[i].getSelectedIndex()!=-1&&allArray.get(i).get(allList[i].getSelectedIndex())[0][1].equals(g)){
				JOptionPane.showMessageDialog(null, "One or more members from Group "+g+" is already scheduled for a task this month.", "Conflict Error", JOptionPane.ERROR_MESSAGE);
				lunchList.setSelectedIndex(-1);
				break;
			}
		}

	}
	public void checkSpouse(String[][] s,int n){
		String spouse = s[0][2];
		if (spouse!=""){
			for (int i=0;i<recepA1.size();i++){
				if (recepA1.get(i)[0][0].equals(spouse)){
					if (JOptionPane.showConfirmDialog(null, "Would you like to assign "+spouse+" (spouse) to Reception as well?", "Spouse Assign",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION && n==1){
						System.out.println("swag");
					}
					else{
						
					}


				}

			}
		}
	}
}
