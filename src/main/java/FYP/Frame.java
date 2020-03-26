package FYP;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileWriter;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Frame implements ChangeListener, PropertyChangeListener{

	private JFrame frame;
	private final JLayeredPane layeredPane = new JLayeredPane();
	private JTextField inputTextfield;
	private JTextField outputDirTextfield;
	private int difficulty;
	private JTextField outputFileTextfield;
	private String inputFilePath;
	private String outputFilePath;
	private int currIndex = 0;
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
	
	//function for next/back btns
	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	//check selected file ext	
	private boolean checkExt(String fileName) {
		fileName = fileName.toLowerCase();
		if(fileName.indexOf(".")!=-1) {
			String s =(fileName.substring(fileName.lastIndexOf(".") + 1));
			//if (s.equals("java") || s.equals("jar")) {
			if (s.equals("java")) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
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
	
	
	//tutorial pop up frame
	private void showTutorial() {
		//add questions inside arraylist
		ArrayList<String> questions = new ArrayList<String>();
		JTextArea tutorialTextArea = new JTextArea();
		JDialog tutorialDialog = new JDialog();
		tutorialTextArea.setSize(900,780);
		
		for (int i = 1; i < 10; i++)
		{
			questions.add("Line " + (i) + " jkntewnkj;rewagnerngheroagneor[");
			
		}
		
		JButton btnTutorialEnd = new JButton("End tutorial");
		btnTutorialEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tutorialDialog.dispose();
			}
		});
		
		JButton btnTutorialBack = new JButton("Back");
		btnTutorialBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if (currIndex > 0) {
					currIndex--;
					tutorialTextArea.setText(questions.get(currIndex));
				}
			}
		});
		
		JButton btnTutorialNext = new JButton("Next");
		btnTutorialNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (currIndex < questions.size() -1) {
					currIndex++;
					tutorialTextArea.setText(questions.get(currIndex));
				}
			}
		});
		
		JPanel tutorialPanel = new JPanel();
		tutorialPanel.setLayout(null);
		tutorialPanel.setPreferredSize(new Dimension(800,800));
		tutorialTextArea.setText(questions.get(currIndex));
		tutorialTextArea.setLineWrap(true);  
		tutorialTextArea.setWrapStyleWord(true); 
		tutorialTextArea.setEditable(false);
		tutorialPanel.add(tutorialTextArea);
		btnTutorialBack.setBounds(30, 800, 97, 25);
		btnTutorialNext.setBounds(750, 800, 97, 25);
		btnTutorialEnd.setBounds(390, 800, 97, 25);
		
		tutorialPanel.add(btnTutorialBack);
		tutorialPanel.add(btnTutorialNext);
		tutorialPanel.add(btnTutorialEnd);
		
		tutorialDialog.setLayout(null);
		tutorialDialog.setSize(900,900);
		tutorialDialog.add(tutorialTextArea);
		tutorialDialog.add(btnTutorialBack);
		tutorialDialog.add(btnTutorialNext);
		tutorialDialog.add(btnTutorialEnd);
		tutorialDialog.setTitle("Tutorial");
		tutorialDialog.setLocationRelativeTo(null);
		tutorialDialog.setVisible(true);
		
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Obfuscator obfuscator = new Obfuscator();

		frame = new JFrame("Obsfuscator");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		layeredPane.setBounds(0, 0, 782, 453);
		frame.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JFileChooser fc = new JFileChooser();
		JFileChooser fc2 = new JFileChooser();
		fc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JPanel initialPanel = new JPanel();
		layeredPane.add(initialPanel);
		initialPanel.setLayout(null);
		
		JPanel browsePanel = new JPanel();
		layeredPane.add(browsePanel);
		browsePanel.setLayout(null);
		
		JPanel sliderOptionPanel = new JPanel();
		layeredPane.add(sliderOptionPanel);
		sliderOptionPanel.setLayout(null);
		
		JPanel finalPanel = new JPanel();
		layeredPane.add(finalPanel);
		finalPanel.setLayout(null);
		
		//for advanced settings
		JPanel advOptionsPanel = new JPanel();
		layeredPane.add(advOptionsPanel);
		advOptionsPanel.setLayout(null);
		
		JPanel progressBarPanel = new JPanel();
		layeredPane.add(progressBarPanel);
		progressBarPanel.setLayout(null);
		
		JPanel changelogPanel = new JPanel();
		layeredPane.add(changelogPanel);
		changelogPanel.setLayout(null);
		
		JPanel quizPanel = new JPanel();
		layeredPane.add(quizPanel);
		quizPanel.setLayout(null);
		
		//switch to specific starting frame when testing
		switchPanel(finalPanel);
		
		

		////////////////////////////////////////////////////////////////////////////////////////////
		//Advanced settings panel
		///////////////////////////////////////////////////////////////////////////////////////////
		JButton btnBackAdvOptions = new JButton("Back");
		btnBackAdvOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(sliderOptionPanel);
			}
		});
		btnBackAdvOptions.setBounds(55, 390, 97, 25);
		advOptionsPanel.add(btnBackAdvOptions);
		
		JButton btnNextAdvOptions = new JButton("Next");
		btnNextAdvOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				switchPanel(progressBarPanel);
				//obfuscate the file
				obfuscator.obfuscate(inputTextfield.getText(), outputFilePath, difficulty);

			}
		});
		
		btnNextAdvOptions.setBounds(620, 390, 97, 25);
		advOptionsPanel.add(btnNextAdvOptions);
		
		////////////////////////////////////////////////////////////////////////////////////////////
		//initial panel 
		///////////////////////////////////////////////////////////////////////////////////////////
		/*
		ArrayList<String> testText = new ArrayList<String>();
		JTextArea tutorialTextArea = new JTextArea();
		JDialog tutorialDialog = new JDialog();
		//JTextArea tutorialTextArea = new JTextArea(50, 110);
		tutorialTextArea.setSize(900,780);
		
		for (int i = 1; i < 101; i++)
		{
			testText.add("Line " + (i) + " jkntewnkj;rewagnerngheroagneor[");
			
		}*/
		
		/*
		JButton btnTutorialEnd = new JButton("End tutorial");
		btnTutorialEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tutorialDialog.setVisible(false);
				
			}
		});
		
		JButton btnTutorialBack = new JButton("Back");
		btnTutorialBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if (currIndex > 0) {
					currIndex--;
					tutorialTextArea.setText(testText.get(currIndex));
				}
			}
		});
		
		JButton btnTutorialNext = new JButton("Next");
		btnTutorialNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					currIndex++;
					tutorialTextArea.setText(testText.get(currIndex));
				
			}
		});
		
		*/
		
		/*
		JButton btnViewTutorial = new JButton("View tutorial");
		btnViewTutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				JPanel tutorialPanel = new JPanel();
				tutorialPanel.setLayout(null);
				tutorialPanel.setPreferredSize(new Dimension(800,800));
				tutorialTextArea.setText(testText.get(currIndex));
				tutorialTextArea.setLineWrap(true);  
				tutorialTextArea.setWrapStyleWord(true); 
				tutorialTextArea.setEditable(false);
				tutorialPanel.add(tutorialTextArea);
				btnTutorialBack.setBounds(30, 800, 97, 25);
				btnTutorialNext.setBounds(750, 800, 97, 25);
				btnTutorialEnd.setBounds(390, 800, 97, 25);
				
				tutorialPanel.add(btnTutorialBack);
				tutorialPanel.add(btnTutorialNext);
				tutorialPanel.add(btnTutorialEnd);
				
				tutorialDialog.setLayout(null);
				tutorialDialog.setSize(900,900);
				tutorialDialog.add(tutorialTextArea);
				tutorialDialog.add(btnTutorialBack);
				tutorialDialog.add(btnTutorialNext);
				tutorialDialog.add(btnTutorialEnd);
				tutorialDialog.setTitle("Tutorial");
				tutorialDialog.setLocationRelativeTo(null);
				tutorialDialog.setVisible(true);
				
				
				
				
				
				//JOptionPane.showConfirmDialog(frame, tutorialPanel, "Tutorial", JOptionPane.CLOSED_OPTION , JOptionPane.PLAIN_MESSAGE, null);
			}
		});
		btnViewTutorial.setBounds(163, 180, 131, 63);
		
		*/
		JButton btnViewTutorial = new JButton("View tutorial");
		btnViewTutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTutorial();
				switchPanel(finalPanel);
			}
		});
		btnViewTutorial.setBounds(163, 180, 131, 63);
		
		JButton btnObfuscateFile = new JButton("Obfuscate a file");
		btnObfuscateFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(browsePanel);
			}
		});
		btnObfuscateFile.setBounds(426, 180, 144, 63);
		
		initialPanel.add(btnViewTutorial);
		initialPanel.add(btnObfuscateFile);
		
		
		////////////////////////////////////////////////////////////////////////////////////////////
		//Slider panel 
		///////////////////////////////////////////////////////////////////////////////////////////
		JButton btnAdvancedSettings = new JButton("Advanced settings");
		btnAdvancedSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(advOptionsPanel);
			}
		});
		btnAdvancedSettings.setBounds(608, 336, 148, 25);
		sliderOptionPanel.add(btnAdvancedSettings);
		
		JButton btnBackSliderPanel = new JButton("Back");
		btnBackSliderPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(browsePanel);
			}
		});
		btnBackSliderPanel.setBounds(48, 380, 97, 25);
		sliderOptionPanel.add(btnBackSliderPanel);
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//Progress Bar panel
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JLabel lblProgressBarStatus = new JLabel("Scanning files...");
		lblProgressBarStatus.setBounds(88, 219, 160, 16);
		progressBarPanel.add(lblProgressBarStatus);
		
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setBounds(88, 167, 560, 38);
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		//Quiz panel
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		//add questions to key and answer to value
		HashMap<String,String> qnPool=new HashMap<String,String>(); 
		
		qnPool.put("Answer true or false to the following statement: \nObfuscation is used to make source code as unreadable as possible", "True");
		
		Random rand = new Random();
		int random = rand.nextInt(qnPool.size()) + 0;
		
		Object[] values = qnPool.keySet().toArray();
		String question = values[random].toString();
		
		
		JTextArea quizTextArea = new JTextArea(question);
		quizTextArea.setEditable(false);
		quizTextArea.setBounds(12, 13, 758, 249);
		
		JRadioButton rdbtnTrue = new JRadioButton("True");
		rdbtnTrue.setBounds(113, 297, 127, 25);
		
		JRadioButton rdbtnFalse = new JRadioButton("False");
		rdbtnFalse.setBounds(113, 332, 127, 25);
		
		JLabel lblStatus = new JLabel();
		lblStatus.setBounds(460, 399, 207, 25);
		
		ButtonGroup rdbtnGroup = new ButtonGroup();
		rdbtnGroup.add(rdbtnFalse);
		rdbtnGroup.add(rdbtnTrue);
		
		JButton btnQuizNext = new JButton("Next");
		btnQuizNext.setBounds(640, 399, 97, 25);
		btnQuizNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String ans = qnPool.get(question);
				
				
				if (rdbtnGroup.getSelection()==null) {
					lblStatus.setText("Please select an answer");
				}
				else if (rdbtnTrue.isSelected() && (rdbtnTrue.getText().toLowerCase().equals(ans.toLowerCase()))) {
					lblStatus.setText("Correct!");
				}
				else {
					lblStatus.setText("Wrong! The answer is " + ans.toLowerCase());
				}
				qnPool.remove(question);
				
			}
		});
		
		
		quizPanel.add(quizTextArea);
		quizPanel.add(btnQuizNext);
		quizPanel.add(rdbtnTrue);
		quizPanel.add(rdbtnFalse);
		quizPanel.add(lblStatus);
		
		
		
		
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		//Final panel
		//////////////////////////////////////////////////////////////////////////////////////////////////
		JButton btnViewOutput = new JButton("View output file");
		btnViewOutput.setBounds(59, 196, 143, 25);
		finalPanel.add(btnViewOutput);
		btnViewOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/*
				JOptionPane originalText = new JOptionPane();
				JDialog dialog = originalText.createDialog(null, "Original");
				dialog.setPreferredSize(new Dimension(400,400));
				dialog.setLocationRelativeTo(frame);
				dialog.pack();
				dialog.setModal(false);
				dialog.show();*/
				
				/*
				JTextArea textArea = new JTextArea("Insert your Text here" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n");
				JScrollPane scrollPane = new JScrollPane(textArea);  
				textArea.setLineWrap(true);  
				textArea.setWrapStyleWord(true); 
				scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
				JOptionPane originalText = new JOptionPane();
				JOptionPane.showConfirmDialog(frame, scrollPane, "Original", JOptionPane.CLOSED_OPTION , JOptionPane.PLAIN_MESSAGE, null);
					
				JTextArea textArea2 = new JTextArea(text);
				JScrollPane scrollPane2 = new JScrollPane(textArea);  
				textArea2.setLineWrap(true);  
				textArea2.setWrapStyleWord(true); 
				scrollPane2.setPreferredSize( new Dimension( 500, 500 ) );
				JOptionPane originalText2 = new JOptionPane();
				JOptionPane.showConfirmDialog(frame, scrollPane, "Original", JOptionPane.CLOSED_OPTION , JOptionPane.PLAIN_MESSAGE, null);
				*/

		        /*
				UIManager.put("OptionPane.minimumSize",new Dimension(800,800)); 
				JOptionPane.showConfirmDialog(frame, dialog, null, JOptionPane.CLOSED_OPTION , JOptionPane.PLAIN_MESSAGE, null);
				*/
				String originalText = "";
				String obfuscatedText = "";
			
				originalText = obfuscator.compileCode(inputFilePath);
				obfuscatedText = obfuscator.compileCode(outputFilePath);

				JPanel comparePanel = new JPanel(new GridLayout(1, 2));
						
				
				JTextArea originalTextArea = new JTextArea(originalText); 
				originalTextArea.setLineWrap(true);  
				originalTextArea.setWrapStyleWord(true); 
				originalTextArea.setEditable(false);
				JScrollPane originalScrollPane = new JScrollPane(originalTextArea); 
				originalScrollPane.setPreferredSize(new Dimension(500, 500));
				
				JTextArea obfuscatedTextArea = new JTextArea(obfuscatedText);
				obfuscatedTextArea.setEditable(false);
				obfuscatedTextArea.setLineWrap(true);  
				obfuscatedTextArea.setWrapStyleWord(true); 
				JScrollPane obfuscatedScrollPane = new JScrollPane(obfuscatedTextArea);  
				obfuscatedScrollPane.setPreferredSize(new Dimension(500, 500));
				
				comparePanel.add(originalScrollPane);
				comparePanel.add(obfuscatedScrollPane);
				
				UIManager.put("OptionPane.minimumSize",new Dimension(800,800)); 
				JOptionPane.showConfirmDialog(frame, comparePanel, "Comparing files", JOptionPane.CLOSED_OPTION , JOptionPane.PLAIN_MESSAGE, null);
				
				
				/*
				//try try
				originalText = obfuscator.compileCode(inputTextfield.getText());
				originalTextArea.setText(originalText);
				obfuscatedText = obfuscator.compileCode(outputFilePath);
				obfuscatedTextArea.setText(obfuscatedText); 
				
				*/
				
				
				/*
				Object[] scrollPanes = {
						"Original", originalScrollPane,
						"Obfuscated", obfuscatedScrollPane,
				};
				
				UIManager.put("OptionPane.minimumSize",new Dimension(800,800)); 
				JOptionPane.showConfirmDialog(frame, scrollPanes, null, JOptionPane.CLOSED_OPTION , JOptionPane.PLAIN_MESSAGE, null);
				*/
				
				
			}
		});
		
		JButton btnAnother = new JButton("Obfuscate another file");
		btnAnother.setBounds(526, 196, 184, 25);
		finalPanel.add(btnAnother);
		
		JButton btnTakeTutorial = new JButton("Take tutorial");
		btnTakeTutorial.setBounds(178, 270, 143, 25);
		
		btnTakeTutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTutorial();
				switchPanel(finalPanel);
			}
		});
		finalPanel.add(btnTakeTutorial);
		
		
		JButton btnQuiz = new JButton("Take quiz");
		btnQuiz.setBounds(436, 270, 143, 25);
		btnQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(quizPanel);
			}
		});
		finalPanel.add(btnQuiz); 
		
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		//Changelog panel
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		JButton btnViewChangelog = new JButton("View changelog");
		btnViewChangelog.setBounds(310, 196, 143, 25);
		finalPanel.add(btnViewChangelog);
		btnViewChangelog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String changelogtext = "";
				
				
				/*
				for (int i = 0; i < 50; i++)
				{
				 	changelogtext = changelogtext + "Line " + i +  " daNP:DWUhfiofhneuwneGNOEWGNMROE[HGMNROE[HNRE[OHNREO[HNEIOHNREIOHNREI[R\n";
				}
				*/

				//put statistics in changelog
				changelogtext = obfuscator.getStatistics();
				System.out.println(outputFilePath);
				JTextArea changelog = new JTextArea(changelogtext); 
				changelog.setLineWrap(true);  
				changelog.setWrapStyleWord(true); 
				changelog.setEditable(false);
				JScrollPane changelogScrollPane = new JScrollPane(changelog); 
				changelogScrollPane.setPreferredSize(new Dimension (800, 800));
				JOptionPane.showConfirmDialog(frame, changelogScrollPane, "Changelog", JOptionPane.CLOSED_OPTION , JOptionPane.PLAIN_MESSAGE, null);
				
				
			}
		});
		
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		//for adv options
		/////////////////////////////////////////////////////////////////////////////////////////////////
		JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText("Select one");
		comboBox.setBounds(176, 144, 87, 22);
		advOptionsPanel.add(comboBox);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Rename variables");
		chckbxNewCheckBox.setToolTipText("");
		chckbxNewCheckBox.setBounds(36, 143, 137, 25);
		advOptionsPanel.add(chckbxNewCheckBox);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("New check box");
		chckbxNewCheckBox_1.setBounds(36, 185, 113, 25);
		advOptionsPanel.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("New check box");
		chckbxNewCheckBox_2.setBounds(36, 229, 113, 25);
		advOptionsPanel.add(chckbxNewCheckBox_2);
		
		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("New check box");
		chckbxNewCheckBox_3.setBounds(36, 274, 113, 25);
		advOptionsPanel.add(chckbxNewCheckBox_3);
		
		JCheckBox chckbxNewCheckBox_4 = new JCheckBox("New check box");
		chckbxNewCheckBox_4.setBounds(413, 143, 113, 25);
		advOptionsPanel.add(chckbxNewCheckBox_4);
		
		JCheckBox chckbxNewCheckBox_5 = new JCheckBox("New check box");
		chckbxNewCheckBox_5.setBounds(413, 185, 113, 25);
		advOptionsPanel.add(chckbxNewCheckBox_5);
		
		JCheckBox chckbxNewCheckBox_6 = new JCheckBox("New check box");
		chckbxNewCheckBox_6.setBounds(413, 229, 113, 25);
		advOptionsPanel.add(chckbxNewCheckBox_6);
		
		JCheckBox chckbxNewCheckBox_7 = new JCheckBox("New check box");
		chckbxNewCheckBox_7.setBounds(413, 274, 113, 25);
		advOptionsPanel.add(chckbxNewCheckBox_7);
		
		
		/*
		progressBar.addPropertyChangeListener((PropertyChangeListener)this);
		progressBar.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (progressBar.getValue() == 100) {
					switchPanel(finalPanel);
				}
			}
			
		});
		progressBar.setValue(100);*/
		progressBarPanel.add(progressBar);
		
		JButton btnFinish = new JButton("Finish");
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//dump all files and close
				System.exit(0);
			}
		});
		
		inputTextfield = new JTextField();
		inputTextfield.setBounds(139, 125, 470, 22);
		inputTextfield.setEditable(false);
		browsePanel.add(inputTextfield);
		inputTextfield.setColumns(10);
		
		outputDirTextfield = new JTextField();
		outputDirTextfield.setEditable(false);
		outputDirTextfield.setColumns(10);
		outputDirTextfield.setBounds(139, 217, 470, 22);
		browsePanel.add(outputDirTextfield);
		
		btnFinish.setBounds(629, 386, 97, 25);
		finalPanel.add(btnFinish);
		
		JLabel lblInputLabel = new JLabel("Input file");
		lblInputLabel.setBounds(48, 128, 56, 16);
		browsePanel.add(lblInputLabel);
		
		JLabel lblOutputDir = new JLabel("Output dir");
		lblOutputDir.setBounds(48, 220, 59, 16);
		browsePanel.add(lblOutputDir);
		
		JLabel lblOutputFile = new JLabel("Output file");
		lblOutputFile.setBounds(48, 260, 59, 16);
		browsePanel.add(lblOutputFile);
		
		JLabel sliderOptionDescriptionLabel = new JLabel("Description 0");
		sliderOptionDescriptionLabel.setBounds(92, 243, 562, 40);
		sliderOptionPanel.add(sliderOptionDescriptionLabel);
		
		//JSlider + description label
		JSlider slider = new JSlider(0, 3, 0);
		slider.addChangeListener((ChangeListener)this);
		slider.addChangeListener(new ChangeListener() {
			
		@Override
		public void stateChanged(ChangeEvent e) {
			int level = ((JSlider)e.getSource()).getValue();
			difficulty = level;
			if(level == 0) {
				sliderOptionDescriptionLabel.setText("Comments removal | Name Obfuscation");
			}
			else if (level == 1) {
				sliderOptionDescriptionLabel.setText("Description 1");
			}
			else if (level == 2) {
				sliderOptionDescriptionLabel.setText("Description 2");
			}
			else if (level == 3) {
				sliderOptionDescriptionLabel.setText("Description 3");
			}
			
			}
		});

		/////////////////////////////////////////////////////////////////////////////////////////////////////
		//Obfuscation part, after choosing difficulty
		////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton btnNextSliderPanel = new JButton("Next");
		btnNextSliderPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//write settings to settings file 
				File file = new File ("./src/settings/easysettings.txt");
				try {
					FileWriter fr = new FileWriter(file);
					
					//-------fix
					fr.write(Integer.toString(slider.getValue()));
					fr.close();
					
					//start obsfuscation
					switchPanel(progressBarPanel);
					obfuscator.obfuscate(inputFilePath, outputFilePath, difficulty);
					

					//do delay to switch panel
					int delay = 500;
					ActionListener taskPerformer = new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							if (progressBar.getValue() < 100) {
								progressBar.setValue(progressBar.getValue()+5);
								if (progressBar.getValue() == 100) {
									switchPanel(finalPanel);
								}
							}
						}

					};
					new Timer(delay, taskPerformer).start();
				}
				catch (IOException e2)
				{
					e2.printStackTrace();
				}
			}
		});
		
		btnNextSliderPanel.setBounds(608, 380, 148, 25);
		sliderOptionPanel.add(btnNextSliderPanel);
		////////////////////////////////////////////////////////////////////////////////////////////////
		//end Slider panel
		////////////////////////////////////////////////////////////////////////////////////////////////
		JButton btnSliderDefaultSettings = new JButton("Save as default settings");
		btnSliderDefaultSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				try {
					File file = new File ("./src/settings/easysettings.txt");
					if (file.createNewFile()) {
						/*
						Process p = Runtime.getRuntime().exec("attrib +H " + file.getPath());
					   p.waitFor();*/
					}
					
					File file2 = new File ("./src/settings/advsettings.txt");
					if (file2.exists())
					{
						file2.delete();
					}
					
				}
				catch (IOException e1){
					//handle error if file does not exist
				}
			}
		});
			
		
		btnSliderDefaultSettings.setBounds(568, 25, 189, 45);
		sliderOptionPanel.add(btnSliderDefaultSettings);
		
		
		//Create the label table 
		Hashtable labelTable = new Hashtable();
		labelTable.put(new Integer(0), new JLabel("Weak"));
		labelTable.put(new Integer(1), new JLabel("Medium"));
		labelTable.put(new Integer(2), new JLabel("Strong"));
		labelTable.put(new Integer(3), new JLabel("Cray cray"));
		slider.setLabelTable(labelTable); 
		slider.setPaintLabels(true);
		slider.setBounds(92, 164, 562, 52);
		sliderOptionPanel.add(slider);
		

		
		JLabel inputFileTypeLabel = new JLabel("");
		inputFileTypeLabel.setBounds(139, 151, 470, 16);
		browsePanel.add(inputFileTypeLabel);
		
		JLabel outputFileTypeLabel = new JLabel("");
		outputFileTypeLabel.setBounds(139, 275, 470, 16);
		browsePanel.add(outputFileTypeLabel);
		
		JButton btnNextInitialPanel = new JButton("Next");
		btnNextInitialPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					if (outputDirTextfield.getText().equals("") || outputFileTextfield.getText().equals("") || inputTextfield.getText().equals("")) {	
						
						JOptionPane.showMessageDialog(frame, "All fields must be filled", "Warning", JOptionPane.OK_OPTION);
						
						}
					else if (inputFileTypeLabel.getText() != "") {
						
						JOptionPane.showMessageDialog(frame, "Please check input file", "Warning", JOptionPane.OK_OPTION);
					}
						
					else if ((outputDirTextfield.getText() != "") && (outputFileTextfield.getText() != "") && (inputTextfield.getText() != "") && (inputFileTypeLabel.getText() == "")) 
					{
						String s = outputDirTextfield.getText();
						String s2 = outputFileTextfield.getText();
						String s3 = inputTextfield.getText();
						outputFilePath = s + "\\" + s2 + ".java";
						
						//if input file dir and output file dir and file is same name
						if (inputTextfield.getText().equals(outputFilePath)) 
						{
							JOptionPane.showMessageDialog(frame, "Input and output file cannot be the same", "Warning", JOptionPane.OK_OPTION);
						}
						 //if output file ext  is not valid
						else if (outputFileTextfield.getText().indexOf(".") > 0 ) 
						{ //if "." exists
							JOptionPane.showMessageDialog(frame, "Invalid output file name. Please exclude the file extension", "Warning", JOptionPane.OK_OPTION);
						}
						else if (outputFileTextfield.getText().indexOf(" ") > 0 ) {
							JOptionPane.showMessageDialog(frame, "Invalid output file name.", "Warning", JOptionPane.OK_OPTION);
						}
						else
						{
							//check which settings file exists and direct to appropriate one
							File file = new File ("./src/settings/easysettings.txt");
							File file2 = new File ("./src/settings/advsettings.txt");

							if (file2.exists()) {
								System.out.println("here");
								switchPanel(advOptionsPanel);
							}
							else if (file.exists()) {
								try {
									Scanner scan = new Scanner(file);
									int i = Integer.parseInt(scan.nextLine());
									scan.close();
									slider.setValue(i);
									switchPanel(sliderOptionPanel);
									
								}
								catch (FileNotFoundException e2) {
									e2.printStackTrace();
								}
							}
							else {
								switchPanel(sliderOptionPanel);
							}
						}
					}
				
				
			}
		});
		
		
		btnNextInitialPanel.setBounds(607, 385, 97, 25);
		btnNextInitialPanel.setEnabled(false);
		browsePanel.add(btnNextInitialPanel);
		
		//adv default settings btn
		JButton btnAdvDefaultSettings = new JButton("Save as default settings");
		btnAdvDefaultSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File ("./src/settings/advsettings.txt");
					if (file.createNewFile()) {
						Process p = Runtime.getRuntime().exec("attrib +H " + file.getPath());
					   p.waitFor();
					}
					
					File file2 = new File ("./src/settings/easysettings.txt");
					if (file2.exists())
					{
						file2.delete();
					}
					
				}
				catch (IOException e1){
					//handle error if file does not exist
				}
				catch (InterruptedException e2) {
					//handle error
				}
			}
				
		});
		
		btnAdvDefaultSettings.setBounds(545, 36, 189, 45);
		advOptionsPanel.add(btnAdvDefaultSettings);
		
		
		//File input chooser function
		JButton btnInputBrowseInitialPanel = new JButton("Browse...");
		btnInputBrowseInitialPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnNextInitialPanel.setEnabled(false);
		        //Handle open button action.
		        if (e.getSource() == btnInputBrowseInitialPanel) {
		            int returnVal = fc.showOpenDialog(frame);

		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc.getSelectedFile();
		             
		                inputTextfield.setText(file.getAbsolutePath());
						inputFilePath = inputTextfield.getText();

		                if (checkExt(file.getName()) == true) {
		                	inputFileTypeLabel.setText("");
		                }
		                else {
		                	inputFileTypeLabel.setText("Invalid file extension");
		                }
		     
							
					}
	            } 
		        
		        
		        //outputFileTextfield
		        //inputTextfield
		        //outputDirTextfield
		        //inputFileTypeLabel
		        
		        if ((outputDirTextfield.getText() != "") && (inputTextfield.getText() != "") && (inputFileTypeLabel.getText() == "")) {
		        	btnNextInitialPanel.setEnabled(true);
		        }
		}
		});
		
		btnInputBrowseInitialPanel.setBounds(644, 124, 97, 25);
		browsePanel.add(btnInputBrowseInitialPanel);
		
		//File output chooser function
		JButton btnOutputBrowseInitialPanel = new JButton("Browse...");
		btnOutputBrowseInitialPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnNextInitialPanel.setEnabled(false);
				
		        //Handle open button action.
		        if (e.getSource() == btnOutputBrowseInitialPanel) {
		            int returnVal = fc2.showOpenDialog(frame);

		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc2.getSelectedFile();
		                
		                outputDirTextfield.setText(file.getAbsolutePath());
		            } 
		        }
		        
		        
		        if ((outputDirTextfield.getText() != "") && (inputTextfield.getText() != "") && (inputFileTypeLabel.getText() == "")) {
		        	btnNextInitialPanel.setEnabled(true);
		        }
		}
		});
				
				btnOutputBrowseInitialPanel.setBounds(644, 216, 97, 25);
				browsePanel.add(btnOutputBrowseInitialPanel);
				
				outputFileTextfield = new JTextField();
				outputFileTextfield.setText("Enter output file name (without extension)");
				outputFileTextfield.setColumns(10);
				outputFileTextfield.setBounds(139, 257, 470, 22);
				outputFileTextfield.addMouseListener(new MouseAdapter(){
		            @Override
		            public void mouseClicked(MouseEvent e){
						outputFileTextfield.setText("");
		            }
		        });
				
				
				
				outputFileTextfield.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if ((outputFileTextfield.getText() != "") && (inputTextfield.getText() != "") && (outputDirTextfield.getText() != "") && 
								 (inputFileTypeLabel.getText() == "")) 
							{
				        	
								btnNextInitialPanel.setEnabled(true);
							}
						

				}
				}); 
				browsePanel.add(outputFileTextfield);
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
}