package FYP;

import java.io.File;
import java.io.FileNotFoundException;
import java.awt.*;
import java.awt.event.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.javaparser.ParseException;


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
			@Override
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

	
	
	/**
	 * Create the application.
	 */
	public Frame(){
		makedir();
		initialize();
	}
	
	//create settings dir and files
	private void makedir() {
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
		frame.setBounds(100, 100, 1024, 500);
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
		
		//testing
		layeredPane.switchPanel(initialPanel);


		///////////////////////////////////////////////////////////////////////////////////////////
		//Button to go to browsefilepanel on initial panel
		///////////////////////////////////////////////////////////////////////////////////////////
        JButton btnObfuscateFile = new JButton("Obfuscate a file");
		btnObfuscateFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				layeredPane.switchPanel(browsePanel);
			}
		});
		btnObfuscateFile.setBounds(600, 180, 170, 63);

		JButton btnQuiz = new JButton("Take quiz");
		btnQuiz.setBounds(400, 180, 131, 63);
		btnQuiz.addActionListener(new ActionListener() {
			@Override
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
		btnBrowseNextPanel.setBounds(800, 360, 100, 60);
		btnBrowseNextPanel.setEnabled(false);
		browsePanel.add(btnBrowseNextPanel);
		
		btnBrowseNextPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
                
				//String fullPath = browsePanel.getOutput() + "\\" + browsePanel.getFileName() + ".java";
				//browsePanel.setOutputPath(fullPath);
				
				// if(browsePanel.checkFullOutput(frame, fullPath) == true) {
				// 	//file path correct
				// 	layeredPane.switchPanel(basicSettingsPanel);

				// }
				String inputFilePath = browsePanel.getInput();
				String outputFilePath = browsePanel.getOutput();

				if (browsePanel.checkFileExists(inputFilePath) == false) {
					JOptionPane.showMessageDialog(frame, "Please choose valid input .java file", "Warning", JOptionPane.OK_OPTION);

				}
				
				else if (browsePanel.checkDir(outputFilePath) == false){
					JOptionPane.showMessageDialog(frame, "Please choose valid output location", "Warning", JOptionPane.OK_OPTION);

				}

				else {
					//set the source code
					try {

						obfuscator.compileCode(inputFilePath);
						
						String[] array = obfuscator.getClasses();

						DefaultComboBoxModel model = new DefaultComboBoxModel(array);
						basicSettingsPanel.comboBox.setModel(model);
						advSettingsPanel.comboBox.setModel (model);

					} catch (FileNotFoundException fe) {

					} catch (ParseException ie) {
						
					}
					layeredPane.switchPanel(basicSettingsPanel);

				}
				
				
			
			}
		});
        

        //Button to go back to initial Panel on browse panel
		JButton btnBrowsePanelBack = new JButton("Back");
		
		btnBrowsePanelBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(initialPanel);
			}
		});
		btnBrowsePanelBack.setBounds(48, 360, 100, 60);
		browsePanel.add(btnBrowsePanelBack);

		//check if textfield is valid
		browsePanel.outputTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (browsePanel.getInput().equals("") | browsePanel.getOutput().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
				}
				else {
					btnBrowseNextPanel.setEnabled(true);
				
				}
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				if (browsePanel.getInput().equals("") | browsePanel.getOutput().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
				}
				else {
					btnBrowseNextPanel.setEnabled(true);
				
				}
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (browsePanel.getInput().equals("") | browsePanel.getOutput().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
				}
				else {
					btnBrowseNextPanel.setEnabled(true);
				
				}				
			}

			
		});
		
		
		////////////////////////////////////////////////////////////////////////////////////////////
		//Button to go to advanced settings on basicSettings panel 
		///////////////////////////////////////////////////////////////////////////////////////////
		//read settings file here, get a hash map to check value after i click into advanced settings panel
		

		JButton btnAdvancedSettings = new JButton("Advanced settings");
		btnAdvancedSettings.setBounds(800, 310, 180, 25);
		basicSettingsPanel.add(btnAdvancedSettings);

		btnAdvancedSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(advSettingsPanel);
				
				advSettingsPanel.readSettingsFile();	
				advSettingsPanel.setOptions();

			}
		});
		
		
		JButton btnBackSliderPanel = new JButton("Back");
		btnBackSliderPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(browsePanel);
			}
		});
		btnBackSliderPanel.setBounds(48, 360, 100, 60);
		basicSettingsPanel.add(btnBackSliderPanel);
		
		

		/////////////////////////////////////////////////////////////////////////////
		//Button "next" to go to progressbarpanel on basicSettings panel
		////////////////////////////////////////////////////////////////////////////
		JButton btnNextSliderPanel = new JButton("Start Obfuscating");
		btnNextSliderPanel.setBounds(800, 350, 180, 80);
		basicSettingsPanel.add(btnNextSliderPanel);

		btnNextSliderPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//set the file paths
					inputFilePath = browsePanel.getInput();
					outputFilePath = browsePanel.getOutput();

					//get selected main class
					String selectedClass = String.valueOf(basicSettingsPanel.comboBox.getSelectedItem());

					
					obfuscator.obfuscate(inputFilePath, outputFilePath, basicSettingsPanel.getLevel(), selectedClass);
					//start obsfuscation
			
					
					layeredPane.switchPanel(progressBarPanel);
					
					//do delay to switch panel
					progressBarPanel.update(layeredPane, browsePanel, finalPanel, obfuscator);
			

			

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
			@Override
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(basicSettingsPanel);
			}
		});
		btnBackAdvOptions.setBounds(48, 360, 100, 60);
		advSettingsPanel.add(btnBackAdvOptions);
		
		//next button for advanced settings panel
		JButton btnNextAdvOptions = new JButton("Start Obfuscating");
		btnNextAdvOptions.setBounds(800, 350, 180, 80);
		advSettingsPanel.add(btnNextAdvOptions);

		
		btnNextAdvOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				//obfuscate the file
				try {
					//set the file paths
					inputFilePath = browsePanel.getInput();
					outputFilePath = browsePanel.getOutput();

					//get selected main class
					String selectedClass = String.valueOf(basicSettingsPanel.comboBox.getSelectedItem());

					obfuscator.advObfuscate(inputFilePath, outputFilePath, advSettingsPanel, selectedClass);
					//start obsfuscation
					
					layeredPane.switchPanel(progressBarPanel);
					progressBarPanel.update(layeredPane, browsePanel, finalPanel, obfuscator);
					
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		

		///////////////////////////////////////////////////////
		//Final Panel view output file button
		//////////////////////////////////////////////////////
		
		JButton btnViewOutput = new JButton("View output file");
		btnViewOutput.setBounds(130, 196, 180, 60);
		finalPanel.add(btnViewOutput);
		btnViewOutput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//output path = output directory + l1lIll1l.java e.g.
				outputFilePath = browsePanel.getOutput() + "\\" + obfuscator.getFileName();

				ComparePanel comparePanel = new ComparePanel(frame, obfuscator, inputFilePath, outputFilePath);
				layeredPane.add(comparePanel);
            }
		});
		
		//obfuscate another file
		JButton btnAnother = new JButton("Obfuscate another file");
		btnAnother.setBounds(630, 196, 220, 65);
		finalPanel.add(btnAnother);
		btnAnother.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//reset everything
				layeredPane.switchPanel(browsePanel); //switch back to browsepanel
				browsePanel.setTextField(); //set textfield to null
				obfuscator.statistics = new ArrayList<Statistics>(); //reset statistics
				progressBarPanel.setDefault();
			}	
		});
	}
}