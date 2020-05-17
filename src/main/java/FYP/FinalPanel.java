package FYP;

import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FinalPanel extends JPanel {


	public FinalPanel(Frame frameClass, JFrame frame, Obfuscator obfuscator, LayeredPane layeredPane) {
        
		this.setLayout(null);
        
        ////////////////////////////////////////////////////////////////////////////////////////////////
		//Changelog panel button
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		JButton btnViewChangelog = new JButton("View Changelog");
		btnViewChangelog.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
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
				
				JOptionPane optionPane = new JOptionPane(changelogScrollPane, JOptionPane.INFORMATION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, customOptions, null);
				
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

						else if (optionPane.getValue() == customOptions[1]){
							try {
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							} catch (Exception exception) {
			
							}
							optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
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

									
									JOptionPane.showMessageDialog(frame, String.format("%s has been saved!", changelogFile.getName()), "Message", JOptionPane.INFORMATION_MESSAGE);

								

								} catch (IOException ie) {
									System.out.println("IO Error");
								}
							}

							try {
								UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
							} catch (Exception exception) {
			
							}
						}
					}

					
				});
				
			}
		});

		

		///////////////////////////////////////////////////////
		//Final Panel view output file button
		//////////////////////////////////////////////////////
		
		JButton btnViewOutput = new JButton("View Comparison");
		btnViewOutput.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		btnViewOutput.setBounds(130, 196, 180, 60);
		this.add(btnViewOutput);
		btnViewOutput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//output path = output directory + l1lIll1l.java e.g.
				frameClass.outputFilePath = frameClass.browsePanel.getOutput() + "\\" + obfuscator.getFileName();

				ComparePanel comparePanel = new ComparePanel(frame, obfuscator, frameClass.inputFilePath, frameClass.outputFilePath);
				layeredPane.add(comparePanel);
            }
		});
		
		//obfuscate another file
		JButton btnAnother = new JButton("Obfuscate another file");
		btnAnother.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		btnAnother.setBounds(630, 196, 220, 65);
		this.add(btnAnother);
		btnAnother.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//reset everything
				layeredPane.switchPanel(frameClass.browsePanel); //switch back to browsepanel
				frameClass.browsePanel.setTextField(); //set textfield to null
				obfuscator.statistics = new ArrayList<Statistics>(); //reset statistics
				frameClass.progressBarPanel.setDefault();
			}	
		});

		
        

        //exit button on final panel
		JButton btnFinish = new JButton("Finish");
		btnFinish.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//dump all files and close
				System.exit(0);
			}
		});
		
		btnFinish.setBounds(430, 330, 100, 50);
		this.add(btnFinish);
	
    }
   

}