package FYP;

import java.io.File;

import java.awt.*;
import java.awt.event.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class Frame {

	private JFrame frame;

	private String inputFilePath;
	private String outputFilePath;
	Obfuscator obfuscator = new Obfuscator();
	LayeredPane layeredPane = new LayeredPane();

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
			  File folder = new File ("./settings");
					  
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
		

		frame = new JFrame("Obsfuscator");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(layeredPane);
		
		InitialPanel initialPanel = new InitialPanel();
		layeredPane.switchPanel(initialPanel);

		//for advanced settings
		AdvSettingsPanel advSettingsPanel = new AdvSettingsPanel();
		advSettingsPanel.createSettingsFile(); //initialize advsettings.txt;

		//add slider panel
		BasicSettingsPanel basicSettingsPanel = new BasicSettingsPanel(advSettingsPanel);

		//add browse panel
		BrowsePanel browsePanel = new BrowsePanel(frame);

		//add progress bar panel
		ProgressBarPanel progressBarPanel = new ProgressBarPanel();

		//add final panel
		FinalPanel finalPanel = new FinalPanel(frame, obfuscator);


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
				QuizPanel quizPanel = new QuizPanel(layeredPane, initialPanel);

				layeredPane.switchPanel(quizPanel);
			}
		});

		initialPanel.add(btnQuiz);
		initialPanel.add(btnObfuscateFile);

		
		////////////////////////////////////////////////////////////////////////////////////////////
		//Button to go to Slider Panel on BrowsePanel
		////////////////////////////////////////////////////////////////////////////////////////////
		JButton btnBrowseNextPanel = new JButton("Next");
		btnBrowseNextPanel.setEnabled(false);
		btnBrowseNextPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
                
				String fullPath = browsePanel.getOutput() + "\\" + browsePanel.getFileName() + ".java";
				browsePanel.setOutputPath(fullPath);
				
				if(browsePanel.checkFullOutput(frame, fullPath) == true) {
					//file path correct
					layeredPane.switchPanel(basicSettingsPanel);

				}
				
				
			
			}
		});
        btnBrowseNextPanel.setBounds(607, 385, 97, 25);
		btnBrowseNextPanel.setEnabled(false);
        browsePanel.add(btnBrowseNextPanel);

        //Button to go back to initial Panel on browse panel
		JButton btnBrowsePanelBack = new JButton("Back");
		btnBrowsePanelBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(initialPanel);
			}
		});
		btnBrowsePanelBack.setBounds(55, 390, 97, 25);
		browsePanel.add(btnBrowsePanelBack);

		//check if textfield is valid
		browsePanel.outputFileTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (browsePanel.outputFileTextField.getText().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
				}
				else {
					btnBrowseNextPanel.setEnabled(true);
				
				}
				browsePanel.setOutputFile(browsePanel.outputFileTextField.getText());
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				if (browsePanel.outputFileTextField.getText().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
				}
				else {
					btnBrowseNextPanel.setEnabled(true);
				
				}
				browsePanel.setOutputFile(browsePanel.outputFileTextField.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (browsePanel.outputFileTextField.getText().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
				}
				else {
					btnBrowseNextPanel.setEnabled(true);
				
				}				
				browsePanel.setOutputFile(browsePanel.outputFileTextField.getText());
			}

			
		});
		
		
		////////////////////////////////////////////////////////////////////////////////////////////
		//Button to go to advanced settings on slider panel 
		///////////////////////////////////////////////////////////////////////////////////////////
		//read settings file here, get a hash map to check value after i click into advanced settings panel
		

		JButton btnAdvancedSettings = new JButton("Advanced settings");
		btnAdvancedSettings.setBounds(550, 336, 200, 25);
		basicSettingsPanel.add(btnAdvancedSettings);

		btnAdvancedSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(advSettingsPanel);
				
				advSettingsPanel.readSettingsFile();	
				advSettingsPanel.setOptions();

			}
		});
		
		
		JButton btnBackSliderPanel = new JButton("Back");
		btnBackSliderPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(browsePanel);
			}
		});
		btnBackSliderPanel.setBounds(48, 380, 97, 25);
		basicSettingsPanel.add(btnBackSliderPanel);
		
		/////////////////////////////////////////////////////////////////////////////
		//Button "next" to go to progressbarpanel on slider panel
		////////////////////////////////////////////////////////////////////////////
		JButton btnNextSliderPanel = new JButton("Start Obfuscating");
		btnNextSliderPanel.setBounds(550, 380, 200
		, 25);
		basicSettingsPanel.add(btnNextSliderPanel);

		btnNextSliderPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//set the file paths
					inputFilePath = browsePanel.getInput();
					outputFilePath = browsePanel.getFullOutput();
					obfuscator.obfuscate(inputFilePath, outputFilePath, basicSettingsPanel.getLevel());
					//start obsfuscation
					layeredPane.switchPanel(progressBarPanel);
					
					//do delay to switch panel
					progressBarPanel.update(layeredPane, finalPanel);
			

			

				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		////////////////////////////////////////////////////////////////////////////////////////////
		//Back and Next button for advanced settings panel
		///////////////////////////////////////////////////////////////////////////////////////////
		JButton btnBackAdvOptions = new JButton("Back");
		btnBackAdvOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(basicSettingsPanel);
			}
		});
		btnBackAdvOptions.setBounds(55, 390, 97, 25);
		advSettingsPanel.add(btnBackAdvOptions);
		
		//next button for advanced settings panel
		JButton btnNextAdvOptions = new JButton("Start Obfuscating");
		btnNextAdvOptions.setBounds(560, 390, 200, 25);
		advSettingsPanel.add(btnNextAdvOptions);

		
		btnNextAdvOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//obfuscate the file
				try {
					//set the file paths
					inputFilePath = browsePanel.getInput();
					outputFilePath = browsePanel.getFullOutput();
					obfuscator.advObfuscate(inputFilePath, outputFilePath, advSettingsPanel);
					//start obsfuscation
					layeredPane.switchPanel(progressBarPanel);
					progressBarPanel.update(layeredPane, finalPanel);
					
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		

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
		
		//obfuscate another file
		JButton btnAnother = new JButton("Obfuscate another file");
		btnAnother.setBounds(526, 196, 230, 25);
		finalPanel.add(btnAnother);
		btnAnother.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//reset everything
				layeredPane.switchPanel(browsePanel); //switch back to browsepanel
				browsePanel.setTextField(); //set textfield to null
				obfuscator.statistics = new ArrayList<Statistics>(); //reset statistics
			}	
		});
	}
}