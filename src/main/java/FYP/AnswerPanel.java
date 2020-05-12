package FYP;

import java.awt.event.*;
import java.awt.Font;
import java.awt.Color;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class AnswerPanel extends JPanel {

    
    public AnswerPanel(Frame frame, LayeredPane layeredPane, JPanel quizPanel, boolean answer, int currentScore, int highscore, boolean endOfQuiz) {
        
        this.setLayout(null);

        JButton continueBtn = new JButton("Continue");
        continueBtn.setBounds(800, 350, 200, 25);
        this.add(continueBtn);

        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                layeredPane.switchPanel(quizPanel);
            }
        });

        JButton endQuiz = new JButton("End Quiz");
        endQuiz.setBounds(800, 300, 200, 25);
        this.add(endQuiz);
        endQuiz.setVisible(false);    

        if (endOfQuiz == true) {
            
            endQuiz.setVisible(true);
            continueBtn.setEnabled(false);
        }

        endQuiz.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                EndQuizPanel endQuizPanel = new EndQuizPanel(frame, layeredPane, currentScore);
                layeredPane.switchPanel(endQuizPanel);
            }
        });
    
        
		

        
        Font lblFont = new Font("Helvetica", Font.BOLD, 26);

        JLabel highscoreLabel = new JLabel("Highscore: " + Integer.toString(highscore));
        highscoreLabel.setBounds(50, 20, 100, 100);
        this.add(highscoreLabel);

        JLabel currentScoreLabel = new JLabel("Current Score: " + Integer.toString(currentScore));
        currentScoreLabel.setBounds(800, 20, 200, 100);
        this.add(currentScoreLabel);


        JLabel responseLabel = new JLabel("");
        responseLabel.setBounds(430, 120, 200, 100);
        responseLabel.setFont(lblFont);
            
        this.add(responseLabel);
        
        if (answer == true) {
            responseLabel.setText("Correct!");
            responseLabel.setForeground(Color.GREEN);
            

        }
        else {
            responseLabel.setText("Wrong!");
            responseLabel.setForeground(Color.RED);

        }

        
    }
}
