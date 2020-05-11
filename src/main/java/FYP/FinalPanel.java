package FYP;

import java.awt.Dimension;
import java.awt.event.*;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FinalPanel extends JPanel {


    public FinalPanel(JFrame frame, Obfuscator obfuscator) {
        
		this.setLayout(null);
        
        ////////////////////////////////////////////////////////////////////////////////////////////////
		//Changelog panel button
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		JButton btnViewChangelog = new JButton("View Changelog");
		btnViewChangelog.setBounds(380, 196, 180, 60);
		this.add(btnViewChangelog);

		//changelog panel options
		Object[] customOptions = {"Ok", "Save Changelog"};
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
				changelogScrollPane.setPreferredSize(new Dimension (1500, 800));
				
				JOptionPane optionPane = new JOptionPane(changelogScrollPane, JOptionPane.YES_NO_OPTION,  JOptionPane.PLAIN_MESSAGE, null, customOptions, null);
				
				JDialog dialog = new JDialog(frame);
				dialog.add(optionPane);

				dialog.setBounds(0, 0, 1500, 800);
				dialog.setVisible(true);
				optionPane.addPropertyChangeListener(new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (optionPane.getValue() == customOptions[0]) {
							dialog.setVisible(false);
						}
						else if (optionPane.getValue() == customOptions[1]) {
							
							JFileChooser fc = new JFileChooser();
							fc.setDialogTitle("Specify a place to save Changelog");
							fc.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
							int user_selection = fc.showSaveDialog(frame);

							if (user_selection == JFileChooser.APPROVE_OPTION) {
								
								File changelogFile = new File(fc.getSelectedFile() + ".txt");
								try {

									FileWriter fw = new FileWriter(changelogFile);
									fw.write(changelog.getText());
									fw.close();

								} catch (IOException ie) {
									System.out.println("IO Error");
								}
							}
						
						}
					}
				});
				
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