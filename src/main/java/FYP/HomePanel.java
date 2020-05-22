package FYP;


import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class HomePanel extends JPanel {


	public HomePanel(Frame frame, LayeredPane layeredPane) {
		
		
		HomePanel homePanel = this;

		//add help label
		JButton helpButton = new JButton("Help");
		helpButton.setBounds(50, 50, 100, 25);
		helpButton.setVisible(true);
		this.add(helpButton);
		helpButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				Help help = new Help();
				help.openPDF();
			}
		});
		// button to view tutorial
		JButton btnViewTutorial = new JButton("View tutorial");
		btnViewTutorial.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		
		btnViewTutorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TutorialFrame tutorialFrame = new TutorialFrame();
				tutorialFrame.showTutorial();
			}
		});
		btnViewTutorial.setBounds(200, 180, 131, 63);
        
        
        this.add(btnViewTutorial);
        this.setLayout(null);
    

	
		///////////////////////////////////////////////////////////////////////////////////////////
		//Button to go to browsefilepanel on initial panel
		///////////////////////////////////////////////////////////////////////////////////////////
		JButton btnObfuscateFile = new JButton("Obfuscate File");
		btnObfuscateFile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		btnObfuscateFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				layeredPane.switchPanel(frame.browsePanel);
			}
		});
		btnObfuscateFile.setBounds(600, 180, 170, 63);

		JButton btnQuiz = new JButton("Take quiz");
		btnQuiz.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		btnQuiz.setBounds(400, 180, 131, 63);
		btnQuiz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				QuizPanel quizPanel = new QuizPanel(frame, layeredPane, homePanel);

				layeredPane.switchPanel(quizPanel);

			}
		
		});

		this.add(btnQuiz);
		this.add(btnObfuscateFile);

	}
	
}