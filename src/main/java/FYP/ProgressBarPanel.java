package FYP;

import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.*;



public class ProgressBarPanel extends JPanel {
    JProgressBar progressBar = new JProgressBar(0, 100);

    public ProgressBarPanel() {
        this.setLayout(null);
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//Progress Bar panel
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JLabel lblProgressBarStatus = new JLabel("Scanning files...");
		lblProgressBarStatus.setBounds(88, 219, 160, 16);
		this.add(lblProgressBarStatus);
		
		progressBar.setStringPainted(true);
        progressBar.setBounds(88, 167, 560, 38);
		this.add(progressBar);
		
	}

	public void update(LayeredPane layeredPane, FinalPanel finalPanel) {
		int delay = 50;
		
		Timer timer = new Timer(delay, new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				
				if (progressBar.getValue() < 100) {
					progressBar.setValue(progressBar.getValue()+1);
					if (progressBar.getValue() == 100) {
						progressBar.setValue(0); //set back to null
						((Timer)event.getSource()).stop(); //stop the progressbar
						layeredPane.switchPanel(finalPanel);
					}
				}
			}
		
		});
		timer.start();

		
		
	}


    
}