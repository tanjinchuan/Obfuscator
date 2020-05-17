package FYP;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Component;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class QuizPanel extends JPanel {

	Quiz quiz = new Quiz();

	protected int currentScore = 0;
	
	public QuizPanel(Frame frame, LayeredPane layeredPane, JPanel initialPanel) {
		this.setLayout(null);
		
		QuizPanel quizPanel = this;

		ArrayList<Questions> quizList = quiz.getQuizList();

		JTextArea quizTextArea = new JTextArea();

		// shuffle the question array
		Collections.shuffle(quizList);

		Questions firstQuestion = quizList.get(0);
		quizTextArea.setText("Question 1 of " + quizList.size() + "\n\n" + firstQuestion.getQuestion());

		quizTextArea.setBounds(5, 40, 1024, 249);
		quizTextArea.setEditable(false);

		JLabel highscoreLabel = new JLabel("Highscore: " + frame.highscore);
		highscoreLabel.setBounds(50, 10, 100, 25);
		this.add(highscoreLabel);
		
		JRadioButton rdbtn1 = new JRadioButton();
		rdbtn1.setBounds(20, 297, 800, 25);
		rdbtn1.setActionCommand("a");

		JRadioButton rdbtn2 = new JRadioButton();
		rdbtn2.setBounds(20, 332, 800, 25);
		rdbtn2.setActionCommand("b");

		JRadioButton rdbtn3 = new JRadioButton();
		rdbtn3.setBounds(20, 367, 800, 25);
		rdbtn3.setActionCommand("c");

		JRadioButton rdbtn4 = new JRadioButton();
		rdbtn4.setBounds(20, 402, 800, 25);
		rdbtn4.setActionCommand("d");

		ButtonGroup rdbtnGroup = new ButtonGroup();
		rdbtnGroup.add(rdbtn1);
		rdbtnGroup.add(rdbtn2);
		rdbtnGroup.add(rdbtn3);
		rdbtnGroup.add(rdbtn4);

	
		int options = firstQuestion.getOptions().length;

		if (options == 2) {
			rdbtn1.setText(quizList.get(0).getOptions()[0]);
			rdbtn2.setText(quizList.get(0).getOptions()[1]);
		}
		else {
			rdbtn1.setText(quizList.get(0).getOptions()[0]);
			rdbtn2.setText(quizList.get(0).getOptions()[1]);
			rdbtn3.setText(quizList.get(0).getOptions()[2]);
			rdbtn4.setText(quizList.get(0).getOptions()[3]);
		}

		JButton btnQuizSubmit = new JButton("Submit");
		btnQuizSubmit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		btnQuizSubmit.setBounds(830, 399, 97, 25);
		btnQuizSubmit.setEnabled(false);

		//submit button 
		btnQuizSubmit.addActionListener(new ActionListener() {
			int quizIndex = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				btnQuizSubmit.setEnabled(false);
				String answer = rdbtnGroup.getSelection().getActionCommand();
				boolean check = quiz.checkAnswer(quizIndex, answer);
						
				
				
				//if correct, add to highscore and print correct
				if (check == true) {
					currentScore = currentScore + 1;
					
				}
				if (quizIndex < quizList.size()) {
					quizIndex++;
					
					AnswerPanel answerPanel = new AnswerPanel(frame, layeredPane, quizPanel, check, currentScore, frame.highscore, false);
					layeredPane.switchPanel(answerPanel);	
			
					try {

						Questions question = quizList.get(quizIndex);
						int options = question.getOptions().length;
						quizTextArea.setText("Question " + (quizIndex + 1) + " of " + quizList.size() + "\n\n" + question.getQuestion());
						rdbtnGroup.clearSelection();
						if (options == 2) {
							rdbtn1.setText(quizList.get(quizIndex).getOptions()[0]);
							rdbtn2.setText(quizList.get(quizIndex).getOptions()[1]);
						}
						else {
							rdbtn1.setText(quizList.get(quizIndex).getOptions()[0]);
							rdbtn2.setText(quizList.get(quizIndex).getOptions()[1]);
							rdbtn3.setText(quizList.get(quizIndex).getOptions()[2]);
							rdbtn4.setText(quizList.get(quizIndex).getOptions()[3]);
						}
					} catch (IndexOutOfBoundsException exception) {
						
						AnswerPanel endanswerPanel = new AnswerPanel(frame, layeredPane, quizPanel, check, currentScore, frame.highscore, true);
						layeredPane.switchPanel(endanswerPanel);	
						
					}
				}	
				
			}

		});

		
		
		
		this.add(quizTextArea);
		this.add(btnQuizSubmit);
		this.add(rdbtn1);
		this.add(rdbtn2);
		this.add(rdbtn3);
		this.add(rdbtn4);

		for (Component r: getComponents()) {
			if (r instanceof JRadioButton) {
				JRadioButton radio = (JRadioButton)r;
				radio.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						
						if (rdbtnGroup.getSelection() != null) {
							btnQuizSubmit.setEnabled(true);
						}				
						else {
							btnQuizSubmit.setEnabled(false);
						}
					}
					
				});
			}
		}	
		//exit quiz button
		JButton exitButton = new JButton("Exit Quiz");
		exitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		exitButton.setBounds(900, 10, 100, 25);
		exitButton.setVisible(true);
		this.add(exitButton);

		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to end the Quiz?", "Select an Option...", JOptionPane.YES_NO_OPTION);
				if (input == 0) {
					
					layeredPane.switchPanel(initialPanel);
					
				}
				
			}
		});
	}


}