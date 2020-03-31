package FYP;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import java.awt.event.*;

public class ProgressBarPanel extends JPanel {
    JProgressBar progressBar = new JProgressBar(0, 100);

    public ProgressBarPanel(LayeredPane layeredPane, FinalPanel finalPanel) {
        
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//Progress Bar panel
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JLabel lblProgressBarStatus = new JLabel("Scanning files...");
		lblProgressBarStatus.setBounds(88, 219, 160, 16);
		this.add(lblProgressBarStatus);
		
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
        progressBar.setBounds(88, 167, 560, 38);
        this.add(progressBar);

    
    }


    
}