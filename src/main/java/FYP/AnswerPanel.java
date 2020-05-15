package FYP;

import java.awt.event.*;
import java.awt.Font;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnswerPanel extends JPanel {

    
    public AnswerPanel(Frame frame, LayeredPane layeredPane, JPanel quizPanel, boolean answer, int currentScore, int highscore, boolean endOfQuiz) {
        
        this.setLayout(null);

        
        JButton continueBtn = new JButton("Continue");
        continueBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        continueBtn.setBounds(800, 399, 97, 25);
        this.add(continueBtn);

        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                layeredPane.switchPanel(quizPanel);
            }
        });

        JButton endQuiz = new JButton("End Quiz");
        endQuiz.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        endQuiz.setBounds(800, 350, 100, 25);
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

        JLabel highscoreLabel = new JLabel("Highscore: " + frame.highscore);
        highscoreLabel.setBounds(50, 20, 100, 100);
        this.add(highscoreLabel);

        JLabel currentScoreLabel = new JLabel("Current Score: " + Integer.toString(currentScore));
        currentScoreLabel.setBounds(800, 20, 200, 100);
        this.add(currentScoreLabel);


        JLabel responseLabel = new JLabel("");
        responseLabel.setBounds(430, 120, 200, 100);
        responseLabel.setFont(lblFont);
            
        this.add(responseLabel);
        

		//Icons
        ImageIcon sadIcon = new ImageIcon(this.getClass().getClassLoader().getResource("sadIcon.png"));
        ImageIcon happyIcon = new ImageIcon(this.getClass().getClassLoader().getResource("happyIcon.png"));
        
        JLabel imgLabel = new JLabel();
        imgLabel.setBounds(530, 120, 100, 100);
        this.add(imgLabel);

		if (answer == true) {
            responseLabel.setText("Correct!");
            responseLabel.setForeground(Color.GREEN);
            imgLabel.setIcon(happyIcon);

        }
        else {
            responseLabel.setText("Wrong!");
            responseLabel.setForeground(Color.RED);
            imgLabel.setIcon(sadIcon);
        }

        
    }
}
