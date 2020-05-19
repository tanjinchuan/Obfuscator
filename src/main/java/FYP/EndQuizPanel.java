package FYP;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class EndQuizPanel extends JPanel {
    
	protected JLabel highscoreLabel = new JLabel("Highscore: -");
    
    public EndQuizPanel(Frame frame, LayeredPane layeredPane, int score) {

        
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        

		Font lblFont = new Font("Helvetica", Font.BOLD, 26);
        Font scoreFont = new Font("Helvetica", Font.PLAIN, 16);

        JLabel highscoreLabel = new JLabel("Highscore: " + Integer.toString(frame.highscore));
        highscoreLabel.setBounds(50, 20, 100, 100);
        this.add(highscoreLabel);

        JLabel congrats = new JLabel("");
        congrats.setBounds(430, 120, 200, 100);
        this.add(congrats);

        if (score > frame.highscore) {
            congrats.setText("New Highscore!");
            congrats.setFont(lblFont);
            congrats.setForeground(Color.GREEN);
            
        }
        else if (score == frame.highscore) {
            congrats.setText("Good Try!");
            congrats.setFont(lblFont);
            congrats.setForeground(Color.GREEN);
            
        }
        else {
            congrats.setText("Loser");
            congrats.setFont(lblFont);
            congrats.setForeground(Color.RED);
        }


        
        if (score > frame.highscore) {
            frame.highscore = score;
        }

        JLabel scoreLabel = new JLabel("Score: " + Integer.toString(score));
        scoreLabel.setBounds(480, 170, 100, 100);
        scoreLabel.setFont(scoreFont);
        this.add(scoreLabel);

        JButton backBtn = new JButton("Back To Home");
        backBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        backBtn.setBounds(800, 400, 150, 25);
        this.add(backBtn);

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layeredPane.switchPanel(frame.homePanel);
            }
        });

        
		/////////////////////////////////////////////////////////////////
		//Retake quiz button on endquizpanel
		////////////////////////////////////////////////////////////////
		JButton retakeQuizBtn = new JButton("Retake Quiz");
        retakeQuizBtn.setBounds(60, 400, 150, 25);
        retakeQuizBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        this.add(retakeQuizBtn);

        retakeQuizBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				QuizPanel quizPanel = new QuizPanel(frame, layeredPane, frame.homePanel);
                layeredPane.switchPanel(quizPanel);
            }
        });


        
    }


    
    
}