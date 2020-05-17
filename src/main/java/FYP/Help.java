package FYP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class Help extends JFrame{

    public void showFrame() {
        initialize();
    }
	private void initialize() {
        this.setVisible(true);
		this.setResizable(false);
		this.setBounds(100, 100, 1500, 800);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		//panel to hold buttons
		JPanel panel = new JPanel();
		panel.setLocation(0, 0);
		panel.setSize(200,800);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(null);
		
		//panel2 to hold jtextpane
		JPanel panel2 = new JPanel();
		panel2.setLocation(201, 0);
		panel2.setSize(1000, 800);
		panel2.setBackground(Color.BLUE);
		panel2.setLayout(null);
		
		//separator to separate panel1 and panel2
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(200, 0, 60, 800);
        
              		
        this.getContentPane().add(separator);  
		this.getContentPane().add(panel);
		this.getContentPane().add(panel2);

        UserManual um = new UserManual();
        ArrayList<ArrayList<ImageIcon>> userManual = um.getUserManual();

		//create jtextpane
		JTextPane jt = new JTextPane();
		jt.insertIcon(userManual.get(0).get(0));
		jt.setLocation(0, 0);
		jt.setEditable(false);
		jt.setBackground(Color.WHITE);
	    jt.setSize(599, 800);
		
		//create Jbuttons
		JButton b0 = new JButton("0. About");
		b0.setBounds(0, 0, 200, 40);
		JButton b1 = new JButton("1. Renaming variables");
		b1.setBounds(0, 40, 200, 40);
		JButton b2 = new JButton("2. Test");
		b2.setBounds(0, 80, 200, 40);
		JButton b3 = new JButton("3. Test");
		b3.setBounds(0, 120, 200, 40);
		JButton b4 = new JButton("4. Test");
		b4.setBounds(0, 160, 200, 40);
		JButton b5 = new JButton("5. Test");
		b5.setBounds(0, 200, 200, 40);
		JButton b6 = new JButton("6. Test");
		b6.setBounds(0, 240, 200, 40);
		JButton b7 = new JButton("7. Test");
		b7.setBounds(0, 280, 200, 40);
		
		//add to arraylist
		ArrayList<JButton>buttons = new ArrayList<JButton>();
		buttons.add(b0);
		buttons.add(b1);
		buttons.add(b2);
		buttons.add(b3);
		buttons.add(b4);
		buttons.add(b5);
		buttons.add(b6);
		buttons.add(b7); 
		

		//set button functionality
		b0.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				jt.insertIcon(userManual.get(0).get(0));
			}
		});
		
		b1.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				jt.insertIcon(userManual.get(1).get(0));
			}
		});
		
		b2.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				jt.insertIcon(userManual.get(2).get(0));
			}
		});
		
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jt.insertIcon(userManual.get(3).get(0));
			}
		});
		
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jt.insertIcon(userManual.get(4).get(0));
			}
		});
		
		b5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jt.insertIcon(userManual.get(5).get(0));
			}
		});
		
		b6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jt.insertIcon(userManual.get(6).get(0));
			}
		});
		
		b7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jt.insertIcon(userManual.get(7).get(0));
			}
		});
		

		for (JButton b: buttons)
		{
			b.setBorderPainted(false);
			b.setOpaque(false);
			b.setFocusable(false);
			panel.add(b);
		}
		
	    //create jscrollpane
		JScrollPane jsp = new JScrollPane(jt); 
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		jsp.setBounds(0, 0, 1000, 800);
		
	    //add to panel2
	    panel2.add(jsp);

  
	}
}
