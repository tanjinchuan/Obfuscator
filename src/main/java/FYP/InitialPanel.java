package FYP;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

import java.net.MalformedURLException;
import java.net.URL;


@SuppressWarnings("serial")
public class InitialPanel extends JPanel {
    int currIndex = 0;
    
    public InitialPanel(LayeredPane layeredPane) {

        //button to view tutorial
        JButton btnViewTutorial = new JButton("View tutorial");
		btnViewTutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTutorial();
			}
		});
		btnViewTutorial.setBounds(120, 180, 131, 63);
        
        
        this.add(btnViewTutorial);
        this.setLayout(null);
    }


    //tutorial pop up frame
	private void showTutorial() {

		Lessons lessons = new Lessons();
		ArrayList<String> tutorials = lessons.getTutorialImages("src\\main\\java\\FYP\\imagesURL.txt");
		JDialog tutorialDialog = new JDialog();

		JLabel imgLabel = new JLabel();

		//initialize first image
		File imageFile = new File(tutorials.get(0));
		URL url = null;
		try {
			url = imageFile.toURI().toURL();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(url);
		imgLabel.setIcon(icon);
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
					File imageFile = new File(tutorials.get(currIndex));
					URL url = null;
					try {
						url = imageFile.toURI().toURL();
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon(url);
					imgLabel.setIcon(icon);

					btnTutorialNext.setVisible(true);
				}
			}
		});
		
		btnTutorialNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (currIndex < tutorials.size() -1) {
					currIndex++;
					File imageFile = new File(tutorials.get(currIndex));
					URL url = null;
					try {
						url = imageFile.toURI().toURL();
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon(url);
					imgLabel.setIcon(icon);

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
		btnTutorialEnd.setBounds(440, 710, 97, 25);
		
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