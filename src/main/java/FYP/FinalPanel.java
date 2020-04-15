package FYP;

import java.awt.Dimension;
import java.awt.event.*;
import java.awt.GridLayout;

import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;



public class FinalPanel extends JPanel {


    public FinalPanel(JFrame frame, Obfuscator obfuscator) {
        
		this.setLayout(null);
		
		
		
        
        ////////////////////////////////////////////////////////////////////////////////////////////////
		//Changelog panel button
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		JButton btnViewChangelog = new JButton("View changelog");
		btnViewChangelog.setBounds(310, 196, 143, 25);
		this.add(btnViewChangelog);
		btnViewChangelog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String changelogtext = "";

				//put statistics in changelog
                changelogtext = obfuscator.getStatistics();
                                
				JTextArea changelog = new JTextArea(changelogtext); 
				changelog.setLineWrap(true);  
				changelog.setWrapStyleWord(true); 
				changelog.setEditable(false);
				JScrollPane changelogScrollPane = new JScrollPane(changelog); 
				changelogScrollPane.setPreferredSize(new Dimension (800, 800));
				JOptionPane.showConfirmDialog(frame, changelogScrollPane, "Changelog", JOptionPane.CLOSED_OPTION , JOptionPane.PLAIN_MESSAGE, null);
				
				
			}
		});
        

        //exit button on final panel
        JButton btnFinish = new JButton("Finish");
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//dump all files and close
				System.exit(0);
			}
		});
		
		btnFinish.setBounds(629, 386, 97, 25);
		this.add(btnFinish);
	
    }
   

}