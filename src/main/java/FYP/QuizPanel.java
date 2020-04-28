package FYP;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class QuizPanel extends JPanel {
    

    public QuizPanel(LayeredPane layeredPane, JPanel initialPanel) {
        this.setLayout(null);

		JTextArea quizTextArea = new JTextArea();

        Quiz quiz = new Quiz();
		ArrayList<Questions> quizList = quiz.getQuizList();
		//shuffle the question array
		Collections.shuffle(quizList);

		Questions firstQuestion = quizList.get(0);
		quizTextArea.setText("Question 1 of " + quizList.size() + "\n" + firstQuestion.getQuestion());
	
		quizTextArea.setBounds(8, 31, 758, 249);
		quizTextArea.setEditable(false);

		JRadioButton rdbtn1 = new JRadioButton("a");
		rdbtn1.setBounds(113, 297, 127, 25);
		rdbtn1.setActionCommand("a");

		JRadioButton rdbtn2 = new JRadioButton("b");
		rdbtn2.setBounds(113, 332, 127, 25);
		rdbtn2.setActionCommand("b");
		
		JRadioButton rdbtn3 = new JRadioButton("c");
		rdbtn3.setBounds(113, 367, 127, 25);
		rdbtn3.setActionCommand("c");
		
		JRadioButton rdbtn4 = new JRadioButton("d");
		rdbtn4.setBounds(113, 402, 127, 25);
		rdbtn4.setActionCommand("d");
		
		
		ButtonGroup rdbtnGroup = new ButtonGroup();
		rdbtnGroup.add(rdbtn1);
		rdbtnGroup.add(rdbtn2);
		rdbtnGroup.add(rdbtn3);
		rdbtnGroup.add(rdbtn4);
	
		int options = firstQuestion.getOptions();

		if (options == 2) {
			rdbtn3.setVisible(false);
			rdbtn4.setVisible(false);
		}

		JLabel answerLabel = new JLabel();
		answerLabel.setBounds(450, 387, 100,50);
		this.add(answerLabel);

		
		JButton btnQuizSubmit = new JButton("Submit");

		btnQuizSubmit.setBounds(560, 399, 97, 25);

		JButton btnQuizNext = new JButton("Next");
		btnQuizNext.setBounds(660, 399, 97, 25);

		btnQuizNext.setVisible(false);

		//submit button 
		btnQuizSubmit.addActionListener(new ActionListener() {
			int quizIndex = 0;
			public void actionPerformed(ActionEvent e) {
				
				String answer = rdbtnGroup.getSelection().getActionCommand();
				boolean check = quiz.checkAnswer(quizIndex, answer);
				
				if (check == true) {
					answerLabel.setText("Correct!");
				}
				else {
					answerLabel.setText("Wrong");
				}
				btnQuizNext.setVisible(true);
				btnQuizSubmit.setEnabled(false);
				answerLabel.setVisible(true);
				quizIndex++; //increase index in question arraylist
				
			}
		});
		
		//button for next question
		btnQuizNext.addActionListener(new ActionListener() {
			int quizIndex = 0;
			public void actionPerformed(ActionEvent e) {
				quizIndex++;
				btnQuizNext.setVisible(false);
				btnQuizSubmit.setEnabled(true);
				answerLabel.setVisible(false);
				if (quizIndex == quizList.size()) { // if no more questions, close window
					layeredPane.switchPanel(initialPanel);
				}
				else { //else set the next question
					Questions question = quizList.get(quizIndex);
					int options = question.getOptions();
					quizTextArea.setText("Question " + (quizIndex+1) + " of " + quizList.size() + "\n" + question.getQuestion());
					rdbtnGroup.clearSelection();
					if (options == 2) {
						rdbtn3.setVisible(false);
						rdbtn4.setVisible(false);
					}
					else {
						rdbtn3.setVisible(true);
						rdbtn4.setVisible(true);
					}
					
				}
			}
		});
		this.add(quizTextArea);
		this.add(btnQuizSubmit);
		this.add(btnQuizNext);
		this.add(rdbtn1);
		this.add(rdbtn2);
		this.add(rdbtn3);
		this.add(rdbtn4);

		//exit quiz button
		JButton exitButton = new JButton("Exit Quiz");
		exitButton.setBounds(650, 10, 100, 20);
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