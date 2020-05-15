package FYP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
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

public class TutorialFrame extends JFrame{
	
	private int currIndex = 0;
	private int currChapter = 0;

	public void showTutorial() {
		initialize();
	}
    private void initialize() {
		this.setLayout(null);
		this.setVisible(true);

		this.setResizable(false);
        this.setBounds(100, 100, 1310, 800);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


		Tutorials tutorialChapters = new Tutorials();
		ArrayList<ArrayList<ImageIcon>> chapters = tutorialChapters.getChapters();
		
		
        //panel to hold buttons
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 230, 800);
		panel.setBackground(Color.LIGHT_GRAY);

		

		
		//panel2 to hold jtextpane
		JPanel panel2 = new JPanel();
		panel2.setBounds(231, 0, 1000, 800);
		

        //create jtextpane
		JTextPane jt = new JTextPane();
		//default image on start
		jt.insertIcon(chapters.get(0).get(0));
		jt.setLocation(0, 0);
		jt.setEditable(false);
		jt.setBackground(Color.WHITE);
        jt.setSize(599, 800);
		
        
        //create Jbuttons
		JButton b0 = new JButton("0. Chapter 1");
		b0.setBounds(0, 0, 230, 40);
		JButton b1 = new JButton("1. Renaming variables");
		b1.setBounds(0, 40, 230, 40);
		JButton b2 = new JButton("2. Test");
		b2.setBounds(0, 80, 230, 40);
		JButton b3 = new JButton("3. Test");
		b3.setBounds(0, 120, 230, 40);

        //add to arraylist
		ArrayList<JButton>buttons = new ArrayList<JButton>();
		buttons.add(b0);
		buttons.add(b1);
		buttons.add(b2);
		buttons.add(b3);
		

		JButton btnTutorialBack = new JButton("Back");
		btnTutorialBack.setEnabled(false);
		JButton btnTutorialNext = new JButton("Next");

		btnTutorialBack.setBounds(410, 700, 100, 25);
		btnTutorialNext.setBounds(810, 700, 100, 25);

		this.add(btnTutorialBack);
		this.add(btnTutorialNext);
		
        //set button functionality
		b0.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				currIndex = 0;
				btnTutorialBack.setEnabled(false);
				jt.insertIcon(chapters.get(0).get(0));
			}
		});
		
		b1.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				currIndex = 0;
				btnTutorialBack.setEnabled(false);

				jt.insertIcon(chapters.get(1).get(0));
			}
		});
		
		b2.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				currIndex = 0;
				btnTutorialBack.setEnabled(false);

				jt.insertIcon(chapters.get(2).get(0));
			}
		});
		
		b3.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				currIndex = 0;
				btnTutorialBack.setEnabled(false);

				jt.insertIcon(chapters.get(3).get(0));
			}
		});
		
		for (JButton b: buttons)
		{
			b.setBorderPainted(false);
			b.setOpaque(false);
			b.setFocusable(false);
			panel.add(b);
		}


		

		btnTutorialBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currIndex--;

				switch (currChapter) {
					case 0:
						jt.insertIcon(chapters.get(0).get(currIndex));
						break;
						
					case 1:
					
						jt.insertIcon(chapters.get(1).get(currIndex));
						break;

					case 2:
					
						jt.insertIcon(chapters.get(2).get(currIndex));
						break;

					case 3:

						jt.insertIcon(chapters.get(3).get(currIndex));
						
						break;
				}
				
				btnTutorialNext.setEnabled(true);
				if (currIndex == 0) {
					btnTutorialBack.setEnabled(false);

				}
			}
			
		});
		
		btnTutorialNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currIndex++;

				switch (currChapter) {
					case 0:
						jt.insertIcon(chapters.get(0).get(currIndex));
						if (currIndex == chapters.get(0).size() - 1) {
							btnTutorialNext.setEnabled(false);
						
						}
						break;
						
					case 1:
					
						jt.insertIcon(chapters.get(1).get(currIndex));
	
						if (currIndex == chapters.get(1).size() - 1) {
							btnTutorialNext.setEnabled(false);

						}
						break;

					case 2:
					
						jt.insertIcon(chapters.get(2).get(currIndex));
						if (currIndex == chapters.get(2).size() - 1) {
							btnTutorialNext.setEnabled(false);

						}
						break;

					case 3:

						jt.insertIcon(chapters.get(3).get(currIndex));
						if (currIndex == chapters.get(3).size() -1 ) {
							btnTutorialNext.setEnabled(false);

						}
						break;
				}
				
				btnTutorialBack.setEnabled(true);


			}
		
		});
		
		
		//create jscrollpane
		JScrollPane jsp = new JScrollPane(jt); 
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		jsp.setBounds(0, 0, 1000, 800);
		
	    //add to panel2
		panel2.add(jsp);
		
		this.getContentPane().add(panel);
		this.getContentPane().add(panel2);
    
    }
    
}