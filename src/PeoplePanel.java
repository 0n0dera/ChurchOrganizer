import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PeoplePanel extends JFrame implements ActionListener{
	BufferedReader r;
	FileWriter w;
	JFrame fr;
	Container c;
	boolean save;
	ArrayList<String> names,spouses,groups;
	JButton add,del,saveexit;
	File f;
	JList list;
	DefaultListModel a;
	InfoPanel infoPanel;
	JPanel buttons,other;
	ListSelectionModel select;
	public PeoplePanel(String group, File file) throws IOException{
		super(group);
		this.setSize(500,500);
		fr = this;
		r = new BufferedReader(new FileReader(file));
		c = getContentPane();
		a = new DefaultListModel();
		names = new ArrayList<String>();
		groups = new ArrayList<String>();
		spouses = new ArrayList<String>();
		other = new JPanel();
		saveexit = new JButton("Save and Exit");
		f = file;
		other.setLayout(new GridLayout(1,2,50,0));
		addPpl(r);
		save = true;
		addList();
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,3));
		list = new JList(a);
		c.setLayout(new BorderLayout());
		other.add(list);
		infoPanel = new InfoPanel();
		other.add(infoPanel);
		c.add(other,BorderLayout.CENTER);
		add = new JButton("Add");
		add.setPreferredSize(new Dimension(50,50));
		del = new JButton("Delete");
		del.setEnabled(false);
		buttons.add(add);
		buttons.add(del);
		buttons.add(saveexit);
		add.addActionListener(this);
		del.addActionListener(this);
		saveexit.addActionListener(this);
		c.add(buttons, BorderLayout.SOUTH);
		select = list.getSelectionModel();
		select.addListSelectionListener(new SharedListSelectionHandler(names,groups,spouses,infoPanel,a,del));
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (!save&&JOptionPane.showConfirmDialog(fr, 
						"Would you like to save?", "Save", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					int ind = list.getSelectedIndex();
					if (ind!=-1){
						names.set(ind, infoPanel.n.getText());
						groups.set(ind, infoPanel.g.getText());
						spouses.set(ind, infoPanel.s.getText());
					}
					if (noStr()){
						try {
							w = new FileWriter(f);
							for (int i=0;i<names.size();i++){
								w.write(names.get(i)+"/"+groups.get(i).trim()+"/"+spouses.get(i)+"\n");
							}
							w.close();
							fr.setVisible(false);
							dispose();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(fr, "Please make sure everybody has a valid group number.");
						setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					}
				}
				else
					setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});

	}

	public void actionPerformed(ActionEvent e) {
		save = false;
		JButton b = (JButton) e.getSource();
		if (b == add){
			a.addElement("untitled");
			names.add("untitled");
			groups.add("");
			spouses.add("");
		}
		else if (b==del){
			int index = list.getSelectedIndex();
			a.remove(index);
			names.remove(index);
			groups.remove(index);
			spouses.remove(index);
		}
		else {
			int ind = list.getSelectedIndex();
			if (ind!=-1){
				names.set(ind, infoPanel.n.getText());
				groups.set(ind, infoPanel.g.getText());
				spouses.set(ind, infoPanel.s.getText());
			}
			if (noStr()){
				try {
					w = new FileWriter(f);
					for (int i=0;i<names.size();i++){
						w.write(names.get(i)+"/"+groups.get(i).trim()+"/"+spouses.get(i)+"\n");
					}
					w.close();
					this.setVisible(false);
					dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else{
				JOptionPane.showMessageDialog(this, "Please make sure everybody has a valid group number.");
			}
		}

	}
	public void addPpl(BufferedReader r) throws IOException{
		String line = r.readLine();
		while (line!=null){
			String name = line.substring(0, line.indexOf("/"));
			line = line.substring(line.indexOf("/")+1);
			String group =line.substring(0,line.indexOf("/"));
			line = line.substring(line.indexOf("/")+1);
			names.add(name);
			groups.add(group);
			spouses.add(line);
			line = r.readLine();
		}
	}
	public void addList(){
		for (int i=0;i<names.size();i++)
			a.addElement(names.get(i));
	}
	public boolean isNumber(String str)  
	{  
		try  
		{  
			int d = Integer.parseInt(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}
	public boolean noStr(){
		for (String s:groups){
			s = s.trim();
			if (!isNumber(s))
				return false;
			if(Integer.parseInt(s)<=0)
				return false;
		}
		return true;

	}
}

class SharedListSelectionHandler implements ListSelectionListener {	
	ArrayList<String> names,spouses;
	ArrayList<String> groups;
	int ind;
	DefaultListModel a;
	JButton del;
	InfoPanel infoPanel;
	public SharedListSelectionHandler(ArrayList<String> names,
			ArrayList<String> groups2, ArrayList<String> spouses,
			InfoPanel infoPanel, DefaultListModel a, JButton del) {
		this.names = names;
		this.spouses = spouses;
		this.groups = groups2;
		this.infoPanel = infoPanel;
		this.a = a;
		ind = -1;
		this.del = del;
	}
	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();
		if (!lsm.isSelectionEmpty()){
			del.setEnabled(true);
			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();
			for (int i = minIndex; i <= maxIndex; i++) {
				if (lsm.isSelectedIndex(i)) {
					try{
						if (ind>=0 && i!=ind){
							names.set(ind, infoPanel.n.getText());
							groups.set(ind,infoPanel.g.getText());
							spouses.set(ind, infoPanel.s.getText());
							a.setElementAt(infoPanel.n.getText(), ind);
						}
					}
					catch(Exception e1){
						infoPanel.setVisible(names.get(i),groups.get(i),spouses.get(i));
						ind = i;
					}
					infoPanel.setVisible(names.get(i),groups.get(i),spouses.get(i));
					ind = i;

				}		
			}
		}
		else{
			del.setEnabled(false);
			infoPanel.setVisible(false);
		}
	}
}
