package FYP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;



@SuppressWarnings("serial")
public class InitialPanel extends JPanel {
    int currIndex = 0;
    
    public InitialPanel() {

        //button to view tutorial
        JButton btnViewTutorial = new JButton("View tutorial");
		btnViewTutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTutorial();
			}
		});
		btnViewTutorial.setBounds(200, 180, 131, 63);
        
        
        this.add(btnViewTutorial);
        this.setLayout(null);
    }


    //tutorial pop up frame
	private void showTutorial() {

		Tutorials tutorial = new Tutorials();
		ArrayList<String> tutorials = tutorial.getTutorialImages();

		JDialog tutorialDialog = new JDialog();

		JLabel imgLabel = new JLabel();

		//initialize first image
		
		ImageIcon img = new ImageIcon(this.getClass().getClassLoader().getResource(tutorials.get(0)));
		
		imgLabel.setIcon(img);
		imgLabel.setBounds(0, 0, 1000, 650);	
	

		JButton btnTutorialEnd = new JButton("End tutorial");
		btnTutorialEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tutorialDialog.dispose();
			}
		});
		
		JButton btnTutorialBack = new JButton("Back");
		JButton btnTutorialNext = new JButton("Next");

		btnTutorialBack.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if (currIndex > 0) {
					currIndex--;
					
					ImageIcon img = new ImageIcon(this.getClass().getClassLoader().getResource(tutorials.get(currIndex)));
					imgLabel.setIcon(img);

					btnTutorialNext.setVisible(true);
				}
			}
		});
		
		btnTutorialNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (currIndex < tutorials.size() -1) {
					currIndex++;
					ImageIcon img = new ImageIcon(this.getClass().getClassLoader().getResource(tutorials.get(currIndex)));

					imgLabel.setIcon(img);

				}
				if (currIndex == tutorials.size()-1) {
					btnTutorialNext.setVisible(false);
				}
			}
		});
		
		JPanel tutorialPanel = new JPanel();
		tutorialPanel.add(imgLabel);
		tutorialPanel.setLayout(null);
		tutorialPanel.setPreferredSize(new Dimension(1200, 700));

		btnTutorialBack.setBounds(30, 710, 97, 25);
		btnTutorialNext.setBounds(870, 710, 97, 25);
		btnTutorialEnd.setBounds(440, 710, 150, 25);
		
		tutorialPanel.add(btnTutorialBack);
		tutorialPanel.add(btnTutorialNext);
		tutorialPanel.add(btnTutorialEnd);
		
		tutorialDialog.setLayout(null);
		tutorialDialog.setSize(1050,800);
		tutorialDialog.add(btnTutorialBack);
		tutorialDialog.add(btnTutorialNext);
		tutorialDialog.add(btnTutorialEnd);
		tutorialDialog.setTitle("Tutorial");
		tutorialDialog.setLocationRelativeTo(null);
		tutorialDialog.add(imgLabel);

		tutorialDialog.setVisible(true);
	}

}