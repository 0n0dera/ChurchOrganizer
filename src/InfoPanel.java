import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;


public class InfoPanel extends JPanel implements FocusListener{
	Container c;
	JLabel name,group,spouse;
	JTextField n,s, g;
	SpringLayout layout;
	
	public InfoPanel(){
		this.setVisible(false);
		name = new JLabel("Name:");
		
		group = new JLabel("Group:");
		spouse = new JLabel("Spouse:");
		n = new JTextField(null,10);
		n.addFocusListener(null);
		s = new JTextField(null,10);
		g = new JTextField(null,3);
		layout = new SpringLayout();
		this.setLayout(layout);
		this.add(name);
		this.add(n);
		this.add(group);
		this.add(g);
		this.add(spouse);
		this.add(s);
		layout.putConstraint(SpringLayout.WEST, name, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, name, 50, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, n, 5, SpringLayout.SOUTH, name);
		layout.putConstraint(SpringLayout.WEST, n, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, group, 50, SpringLayout.SOUTH, n);
		layout.putConstraint(SpringLayout.WEST, group, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, g, 5, SpringLayout.SOUTH, group);
		layout.putConstraint(SpringLayout.WEST, g, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, spouse, 50, SpringLayout.SOUTH, g);
		layout.putConstraint(SpringLayout.WEST, spouse,10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, s, 5, SpringLayout.SOUTH, spouse);
		layout.putConstraint(SpringLayout.WEST, s, 10, SpringLayout.WEST, this);		
	}
	
	public void setVisible(String name, String string, String spouse){
		this.setVisible(true);
		n.setText(name);
		g.setText(""+string);
		s.setText(spouse);
	}


	@Override
	public void focusGained(FocusEvent arg0) {
		System.out.println("swag");
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
