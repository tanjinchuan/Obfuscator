package FYP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class TutorialFrame extends JFrame{
	
	private int currIndex = 0;
	private int currChapter = 0;

	public void showTutorial() {
		initialize();
	}
    private void initialize() {
		this.setLayout(null);
		this.setVisible(true);
		this.setTitle("Tutorial");
		this.setResizable(false);
        this.setBounds(0, 0, 1220, 620);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


		Tutorials tutorialChapters = new Tutorials();
		ArrayList<ArrayList<ImageIcon>> chapters = tutorialChapters.getChapters();
		
		
        //panel to hold buttons
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 220, 700);
		panel.setBackground(Color.LIGHT_GRAY);

	
		//panel2 to hold imageextpane
		JPanel panel2 = new JPanel();
		panel2.setBounds(220, 0, 1000, 540);
		
		JLabel image = new JLabel();
		image.setBounds(220, 0, 1000, 540);
		image.setIcon(chapters.get(0).get(0));
		this.add(image);


        //create Jbuttons
		JButton b0 = new JButton("1. Introduction");
		b0.setBounds(0, 0, 220, 40);
		JButton b1 = new JButton("2. Types of Obfuscation");
		b1.setBounds(0, 40, 220, 40);
		JButton b2 = new JButton("3. Effects of Obfuscation");
		b2.setBounds(0, 80, 220, 40);
		JButton b3 = new JButton("4. Impacts of Obfuscation");
		b3.setBounds(0, 120, 220, 40);

		JButton endTutorial = new JButton("End Tutorial");
		endTutorial.setBounds(0, 540, 220, 40);
		this.add(endTutorial);
		JFrame tut = this;
		endTutorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				tut.dispose();
			}
		});

        //add to arraylist
		ArrayList<JButton>buttons = new ArrayList<JButton>();
		buttons.add(b0);
		buttons.add(b1);
		buttons.add(b2);
		buttons.add(b3);
		

		JButton btnTutorialBack = new JButton("Back");
		btnTutorialBack.setEnabled(false);
		JButton btnTutorialNext = new JButton("Next");

		btnTutorialBack.setBounds(220, 540, 100, 40);
		btnTutorialNext.setBounds(1100, 540, 100, 40);

		this.add(btnTutorialBack);
		this.add(btnTutorialNext);
		
		JButton btnNextChapter = new JButton("Next Chapter");
		btnNextChapter.setBounds(650, 540, 180, 40);
		btnNextChapter.setVisible(false);
		
		this.add(btnNextChapter);

		btnNextChapter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evet) {
				currChapter++;
				currIndex = 0;
				btnNextChapter.setVisible(false);
				btnTutorialNext.setEnabled(true);
				if (currChapter <= chapters.size()){
					image.setIcon(chapters.get(currChapter).get(0));
				}

			}
		});
        //set button functionality
		b0.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				currIndex = 0;
				currChapter = 0;
				btnTutorialBack.setEnabled(false);
				btnTutorialNext.setEnabled(true);
				btnNextChapter.setVisible(false);
				image.setIcon(chapters.get(0).get(0));
			}
		});
		
		b1.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				currIndex = 0;
				currChapter = 1;
				btnTutorialBack.setEnabled(false);
				btnTutorialNext.setEnabled(true);
				btnNextChapter.setVisible(false);
				image.setIcon(chapters.get(1).get(0));
			}
		});
		
		b2.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				currIndex = 0;
				currChapter = 2;
				btnTutorialBack.setEnabled(false);
				btnTutorialNext.setEnabled(true);
				btnNextChapter.setVisible(false);
				image.setIcon(chapters.get(2).get(0));
			}
		});
		
		b3.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				currIndex = 0;
				currChapter = 3;
				btnTutorialBack.setEnabled(false);
				btnTutorialNext.setEnabled(true);
				btnNextChapter.setVisible(false);
				image.setIcon(chapters.get(3).get(0));
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

				if (currIndex == 0) {
					btnTutorialBack.setEnabled(false);
					
				}
				else {
					currIndex--;
				}
				switch (currChapter) {
					case 0:
						image.setIcon(chapters.get(0).get(currIndex));
						break;
						
					case 1:
					
						image.setIcon(chapters.get(1).get(currIndex));
						break;

					case 2:
					
						image.setIcon(chapters.get(2).get(currIndex));
						break;

					case 3:

						image.setIcon(chapters.get(3).get(currIndex));
						
						break;
				}

				btnTutorialNext.setEnabled(true);
				btnNextChapter.setVisible(false);
				
			}
			
		});
		
		btnTutorialNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currIndex++;

				switch (currChapter) {
					case 0:
						image.setIcon(chapters.get(0).get(currIndex));
						
						break;
						
					case 1:
					
						image.setIcon(chapters.get(1).get(currIndex));
	
						break;

					case 2:
					
						image.setIcon(chapters.get(2).get(currIndex));
						break;

					case 3:

						image.setIcon(chapters.get(3).get(currIndex));
						break;
				}
				
				if (currIndex == chapters.get(currChapter).size() -1) {
					btnTutorialNext.setEnabled(false);
					if (currChapter < chapters.size() - 1) {
						btnNextChapter.setVisible(true);
		
					}
				}
				btnTutorialBack.setEnabled(true);			

			}
		
		});
		
	    //add to panel2
		panel2.add(image);
		
		this.getContentPane().add(panel);
		this.getContentPane().add(panel2);
    
    }
    
}