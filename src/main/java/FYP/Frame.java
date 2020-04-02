package FYP;

import java.io.File;
import java.io.FileNotFoundException;

import java.awt.*;
import java.awt.event.*;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.*;

import com.github.javaparser.ParseException;

public class Frame {

	private JFrame frame;

	private String inputFilePath;
	private String outputFilePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Frame window = new Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	
	public boolean checkFileName(String inputFile, String outputFile) {
		if (inputFile.equals(outputFile)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Create the application.
	 */
	public Frame(){
		mkdir();
		initialize();
	}
	
	//create settings dir and files
	private void mkdir() {
		  try {
			  File folder = new File ("./src/settings");
					  
			  folder.mkdir();
			  Process p =  Runtime.getRuntime().exec("attrib +H " + folder.getPath());
			  p.waitFor();
			  
		  }
		  catch (IOException e)
		  {
			  e.printStackTrace();
		  }
		  catch (InterruptedException e)
		  {
			  e.printStackTrace();
		  }
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Obfuscator obfuscator = new Obfuscator();
		

		LayeredPane layeredPane = new LayeredPane();

		frame = new JFrame("Obsfuscator");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(layeredPane);
		
		InitialPanel initialPanel = new InitialPanel(layeredPane);
		layeredPane.add(initialPanel);

		//for advanced settings
		AdvOptionsPanel advOptionsPanel = new AdvOptionsPanel();
		advOptionsPanel.createSettingsFile(); //initialize advsettings.txt;

		//add sliderpanel
		SliderOptionPanel sliderOptionPanel = new SliderOptionPanel(advOptionsPanel);

		//add brows panel
		BrowsePanel browsePanel = new BrowsePanel(frame, layeredPane, initialPanel, sliderOptionPanel);

		//add final panel
		FinalPanel finalPanel = new FinalPanel(frame, obfuscator);


		ProgressBarPanel progressBarPanel = new ProgressBarPanel(layeredPane, finalPanel);

		
		QuizPanel quizPanel = new QuizPanel(layeredPane, initialPanel);
		
		

		///////////////////////////////////////////////////////////////////////////////////////////
		//Button to go to browsefilepanel on initial panel
		///////////////////////////////////////////////////////////////////////////////////////////
        JButton btnObfuscateFile = new JButton("Obfuscate a file");
		btnObfuscateFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(browsePanel);
			}
		});
		btnObfuscateFile.setBounds(480, 180, 170, 63);

		JButton btnQuiz = new JButton("Take quiz");
		btnQuiz.setBounds(300, 180, 131, 63);
		btnQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(quizPanel);
				
				
			}
		});

		initialPanel.add(btnQuiz);
		initialPanel.add(btnObfuscateFile);

		
		//switch to specific starting frame when testing
		//layeredPane.switchPanel(advOptionsPanel);
		
		////////////////////////////////////////////////////////////////////////////////////////////
		//Button to go to advanced settings on slider panel 
		///////////////////////////////////////////////////////////////////////////////////////////
		//read settings file here, get a hash map to check value after i click into advanced settings panel
		

		

		JButton btnAdvancedSettings = new JButton("Advanced settings");
		btnAdvancedSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(advOptionsPanel);
				
				advOptionsPanel.readSettingsFile();	
				advOptionsPanel.setOptions();

			}
		});
		btnAdvancedSettings.setBounds(608, 336, 148, 25);
		sliderOptionPanel.add(btnAdvancedSettings);
		
		JButton btnBackSliderPanel = new JButton("Back");
		btnBackSliderPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(browsePanel);
			}
		});
		btnBackSliderPanel.setBounds(48, 380, 97, 25);
		sliderOptionPanel.add(btnBackSliderPanel);
		
		/////////////////////////////////////////////////////////////////////////////
		//Button "next" to go to progressbarpanel on slider panel
		////////////////////////////////////////////////////////////////////////////
		JButton btnNextSliderPanel = new JButton("Next");
		btnNextSliderPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//set the file paths
					inputFilePath = browsePanel.getInput();
					outputFilePath = browsePanel.getFullOutput();
					
					obfuscator.obfuscate(inputFilePath, outputFilePath, sliderOptionPanel.getLevel());
					
					
					//start obsfuscation
					layeredPane.switchPanel(progressBarPanel);
					//do delay to switch panel
					int delay = 500;
					ActionListener taskPerformer = new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							if (progressBarPanel.progressBar.getValue() < 100) {
								progressBarPanel.progressBar.setValue(progressBarPanel.progressBar.getValue()+5);
								if (progressBarPanel.progressBar.getValue() == 100) {
									layeredPane.switchPanel(finalPanel);
								}
							}
						}

					};

					new Timer(delay, taskPerformer).start();
				} catch (ParseException ex) {
					
					layeredPane.switchPanel(browsePanel);
					JOptionPane.showMessageDialog(frame, "Something wrong with your .java File, please check for syntax error", 
							inputFilePath, JOptionPane.OK_OPTION);
					
				} catch (FileNotFoundException fe) {
					layeredPane.switchPanel(browsePanel);
					JOptionPane.showMessageDialog(frame, "File Not Found", 
							inputFilePath, JOptionPane.OK_OPTION);
					
				} catch (IOException ie) {
					layeredPane.switchPanel(browsePanel);
					JOptionPane.showMessageDialog(frame, "Blank ", 
							inputFilePath, JOptionPane.OK_OPTION);
					
				}

			}
		});
		
		
		btnNextSliderPanel.setBounds(608, 380, 148, 25);
		sliderOptionPanel.add(btnNextSliderPanel);
		

		////////////////////////////////////////////////////////////////////////////////////////////
		//Back button for advanced settings panel
		///////////////////////////////////////////////////////////////////////////////////////////
		JButton btnBackAdvOptions = new JButton("Back");
		btnBackAdvOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(sliderOptionPanel);
			}
		});
		btnBackAdvOptions.setBounds(55, 390, 97, 25);
		advOptionsPanel.add(btnBackAdvOptions);
		
		//next button for advanced settings panel
		JButton btnNextAdvOptions = new JButton("Next");
		btnNextAdvOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//obfuscate the file
				try {
					obfuscator.obfuscate(inputFilePath, outputFilePath, sliderOptionPanel.getLevel());
					layeredPane.switchPanel(progressBarPanel);

				} catch(Exception ex) {
					//layeredPane.switchPanel(initialPanel);
				}
			}
		});
		
		btnNextAdvOptions.setBounds(620, 390, 97, 25);
		
		advOptionsPanel.add(btnNextAdvOptions);

		

		///////////////////////////////////////////////////////
		//Final Panel view output file button
		//////////////////////////////////////////////////////
		
		JButton btnViewOutput = new JButton("View output file");
		btnViewOutput.setBounds(59, 196, 143, 25);
		finalPanel.add(btnViewOutput);
		btnViewOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ComparePanel comparePanel = new ComparePanel(frame, obfuscator, inputFilePath, outputFilePath);
				layeredPane.add(comparePanel);
            }
		});
		
	}

}