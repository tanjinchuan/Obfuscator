package FYP;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
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
	JTextArea quizTextArea = new JTextArea();

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

		Lessons lessons = new Lessons();
		ArrayList<String> tutorials = lessons.getTutorialImages("src\\main\\java\\FYP\\imagesURL.txt");
		JDialog tutorialDialog = new JDialog();

		JLabel imgLabel = new JLabel();

		//initialize first image
		File imageFile = new File(tutorials.get(0));
		URL url = null;
		try {
			url = imageFile.toURI().toURL();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(url);
		imgLabel.setIcon(icon);
		imgLabel.setBounds(0, 0, 1000, 650);
		JButton btnTutorialEnd = new JButton("End tutorial");
		btnTutorialEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tutorialDialog.dispose();
			}
		});
		
		JButton btnTutorialBack = new JButton("Back");
		JButton btnTutorialNext = new JButton("Next");

		btnTutorialBack.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				
				if (currIndex > 0) {
					currIndex--;
					File imageFile = new File(tutorials.get(currIndex));
					URL url = null;
					try {
						url = imageFile.toURI().toURL();
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon(url);
					imgLabel.setIcon(icon);

					btnTutorialNext.setVisible(true);
				}
			}
		});
		
		btnTutorialNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (currIndex < tutorials.size() -1) {
					currIndex++;
					File imageFile = new File(tutorials.get(currIndex));
					URL url = null;
					try {
						url = imageFile.toURI().toURL();
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon(url);
					imgLabel.setIcon(icon);

				}
				if (currIndex == tutorials.size()-1) {
					btnTutorialNext.setVisible(false);
				}
			}
		});
		
		JPanel tutorialPanel = new JPanel();
		tutorialPanel.add(imgLabel);
		tutorialPanel.setLayout(null);
		tutorialPanel.setPreferredSize(new Dimension(1200, 700));

		btnTutorialBack.setBounds(30, 710, 97, 25);
		btnTutorialNext.setBounds(870, 710, 97, 25);
		btnTutorialEnd.setBounds(440, 710, 97, 25);
		
		tutorialPanel.add(btnTutorialBack);
		tutorialPanel.add(btnTutorialNext);
		tutorialPanel.add(btnTutorialEnd);
		
		tutorialDialog.setLayout(null);
		tutorialDialog.setSize(1050,800);
		tutorialDialog.add(btnTutorialBack);
		tutorialDialog.add(btnTutorialNext);
		tutorialDialog.add(btnTutorialEnd);
		tutorialDialog.setTitle("Tutorial");
		tutorialDialog.setLocationRelativeTo(null);
		tutorialDialog.add(imgLabel);

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
		switchPanel(advOptionsPanel);
		
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

				//obfuscate the file
				try {
					obfuscator.obfuscate(inputTextfield.getText(), outputFilePath, difficulty);
					switchPanel(progressBarPanel);

				} catch(Exception ex) {
					switchPanel(initialPanel);
				}
			}
		});
		
		btnNextAdvOptions.setBounds(620, 390, 97, 25);
		
		advOptionsPanel.add(btnNextAdvOptions);
		
		
	

		////////////////////////////////////////////////////////////////////////////////////////////////////
		//Initial Panel
		////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton btnViewTutorial = new JButton("View tutorial");
		btnViewTutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTutorial();
			}
		});
		btnViewTutorial.setBounds(120, 180, 131, 63);
		
		JButton btnQuiz = new JButton("Take quiz");
		btnQuiz.setBounds(300, 180, 131, 63);
		btnQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(quizPanel);
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
			}
		});

		
		JButton btnObfuscateFile = new JButton("Obfuscate a file");
		btnObfuscateFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(browsePanel);
			}
		});
		btnObfuscateFile.setBounds(480, 180, 170, 63);
	
		initialPanel.add(btnViewTutorial);
		initialPanel.add(btnObfuscateFile);
		initialPanel.add(btnQuiz);
		
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
		
		
		
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		//Final panel
		//////////////////////////////////////////////////////////////////////////////////////////////////
		JButton btnViewOutput = new JButton("View output file");
		btnViewOutput.setBounds(59, 196, 143, 25);
		finalPanel.add(btnViewOutput);
		btnViewOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
			}
		});
		finalPanel.add(btnTakeTutorial);

	
		
		
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
		JLabel lblEliminateUnusedFields = new JLabel("Eliminate unused fields and methods");
		lblEliminateUnusedFields.setBounds(22, 145, 216, 16);
		advOptionsPanel.add(lblEliminateUnusedFields);
		
		JLabel lblRenameFieldsAnd = new JLabel("Rename fields and methods");
		lblRenameFieldsAnd.setBounds(286, 145, 168, 16);
		advOptionsPanel.add(lblRenameFieldsAnd);
		
		JLabel lblMiscellaneous = new JLabel("Miscellanous");
		lblMiscellaneous.setBounds(509, 145, 88, 16);
		advOptionsPanel.add(lblMiscellaneous);
		
		//Eliminate unused fields and methods
		JCheckBox chckbxUnusedPublic = new JCheckBox("Public");
		chckbxUnusedPublic.setBounds(22, 170, 113, 25);
		advOptionsPanel.add(chckbxUnusedPublic);
		
		JCheckBox chckbxUnusedProtected = new JCheckBox("Protected");
		chckbxUnusedProtected.setBounds(22, 200, 113, 25);
		advOptionsPanel.add(chckbxUnusedProtected);
		
		JCheckBox chckbxUnusedPackage = new JCheckBox("Package");
		chckbxUnusedPackage.setBounds(22, 230, 113, 25);
		advOptionsPanel.add(chckbxUnusedPackage);
		
		JCheckBox chckbxUnusedPrivate = new JCheckBox("Private");
		chckbxUnusedPrivate.setBounds(22, 260, 113, 25);
		advOptionsPanel.add(chckbxUnusedPrivate);
		
		
		//Rename fields and methods
		JCheckBox chckbxRenamePublic = new JCheckBox("Public");
		chckbxRenamePublic.setBounds(286, 170, 113, 25);
		advOptionsPanel.add(chckbxRenamePublic);
		
		JCheckBox chckbxRenameProtected = new JCheckBox("Protected");
		chckbxRenameProtected.setBounds(286, 200, 113, 25);
		advOptionsPanel.add(chckbxRenameProtected);
		
		JCheckBox chckbxRenamePackage = new JCheckBox("Package");
		chckbxRenamePackage.setBounds(286, 230, 137, 25);
		advOptionsPanel.add(chckbxRenamePackage);
		
		JCheckBox chckbxRenamePrivate = new JCheckBox("Private");
		chckbxRenamePrivate.setBounds(286, 260, 113, 25);
		advOptionsPanel.add(chckbxRenamePrivate);
		
		//Misc
		JCheckBox chckbxRemoveWhitespace = new JCheckBox("Remove white space");
		chckbxRemoveWhitespace.setBounds(509, 170, 147, 25);
		advOptionsPanel.add(chckbxRemoveWhitespace);
		
		JCheckBox chckbxInsertDummyCode = new JCheckBox("Insert dummy code");
		chckbxInsertDummyCode.setBounds(509, 200, 147, 25);
		advOptionsPanel.add(chckbxInsertDummyCode);
		
		JCheckBox chckbxRemoveComments = new JCheckBox("Remove comments");
		chckbxRemoveComments.setBounds(509, 230, 147, 25);
		advOptionsPanel.add(chckbxRemoveComments);
		
		//temp
		JCheckBox chckbxShiftMethods = new JCheckBox("Shift methods");
		chckbxShiftMethods.setBounds(509, 260, 113, 25);
		advOptionsPanel.add(chckbxShiftMethods);
		
		
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
		btnNextSliderPanel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
					
				//start obsfuscation
				switchPanel(progressBarPanel);
				try 
				{

					obfuscator.obfuscate(inputFilePath, outputFilePath, difficulty);
				
				} catch (Exception ex) {
					
					switchPanel(browsePanel);
					JOptionPane.showMessageDialog(frame, "Something wrong with your .java File, please check for syntax error", 
							inputFilePath, JOptionPane.OK_OPTION);
					
				}

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
					FileWriter fr = new FileWriter(file);
					
					if (file.exists()) {
					  	
						file.delete();
						
					}
					
					file.createNewFile();
					fr.write(Integer.toString(slider.getValue()));
					fr.flush();
					fr.close();
						
					
					
					File file2 = new File ("./src/settings/advsettings.txt");
					if (file2.exists())
					{
						file2.delete();
					}
					
				}
				catch (IOException e1) {
					
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
					FileWriter fr = new FileWriter(file);
					
					if (file.createNewFile() == false) { //if file already exists
											
						file.delete();
						
					}
						
					file.createNewFile();
					
						
					//Eliminate unused fields and methods
					if (chckbxUnusedPublic.isSelected()) {
						fr.write("UnusedPublic:\"1\"\n");
					}
					else {
						fr.write("UnusedPublic:\"0\"\n");
					}
					
					
					if (chckbxUnusedProtected.isSelected()) {
						fr.write("UnusedProtected:\"1\"\n");
					}
					else {
						fr.write("UnusedProtected:\"0\"\n");
					}
					
					if (chckbxUnusedPackage.isSelected()) {
						fr.write("UnusedPackage:\"1\"\n");
					}
					else {
						fr.write("UnusedPackage:\"0\"\n");
					}
					
					if (chckbxUnusedPrivate.isSelected()) {
						fr.write("UnusedPrivate:\"1\"\n");
					}
					else {
						fr.write("UnusedPrivate:\"0\"\n");
					}
					
					
					//Rename fields and methods
					if (chckbxRenamePublic.isSelected()) {
						fr.write("RenamePublic:\"1\"\n");
					}
					else {
						fr.write("RenamePublic:\"0\"\n");
					}
					
					if (chckbxRenameProtected.isSelected()) {
						fr.write("RenameProtected:\"1\"\n");
					}
					else {
						fr.write("RenameProtected:\"0\"\n");
					}
					
					if (chckbxRenamePackage.isSelected()) {
						fr.write("RenamePackage:\"1\"\n");
					}
					else {
						fr.write("RenamePackage:\"0\"\n");
					}
					
					if (chckbxRenamePrivate.isSelected()) {
						fr.write("RenamePrivate:\"1\"\n");
					}
					else {
						fr.write("RenamePrivate:\"0\"\n");
					}
					
					//Misc
					if (chckbxRemoveWhitespace.isSelected()) {
						fr.write("RemoveWhitespace:\"1\"\n");
					}
					else {
						fr.write("RemoveWhitespace:\"0\"\n");
					}
					
					if (chckbxInsertDummyCode.isSelected()) {
						fr.write("InsertDummyCode:\"1\"\n");
					}
					else {
						fr.write("InsertDummyCode:\"0\"\n");
					}
					
					if (chckbxRemoveComments.isSelected()) {
						fr.write("RemoveComments:\"1\"\n");
					}
					else {
						fr.write("RemoveComments:\"0\"\n");
					}
					
					if (chckbxShiftMethods.isSelected()) {
						fr.write("ShiftMethods:\"1\"");
					}
					else {
						fr.write("ShiftMethods:\"0\"");
					}
					
					fr.close();
						
				
					
					File file2 = new File ("./src/settings/easysettings.txt");
					if (file2.exists())
					{
						file2.delete();
					}
					
				}
				catch (IOException e1){
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

		JButton btnBrowsePanelBack = new JButton("Back");
		btnBrowsePanelBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(initialPanel);
			}
		});
		btnBrowsePanelBack.setBounds(55, 390, 97, 25);
		browsePanel.add(btnBrowsePanelBack);
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		//Quiz panel
		///////////////////////////////////////////////////////////////////////////////////////////////		
		Quiz quiz = new Quiz();

		Collections.shuffle(quiz.quizList);

		Questions firstQuestion = quiz.quizList.get(0);
		quizTextArea.setText("Question 1 of " + quiz.quizList.size() + "\n" + firstQuestion.getQuestion());
	
		quizTextArea.setBounds(8, 31, 758, 249);
		quizTextArea.setEditable(false);

		JRadioButton rdbtn1 = new JRadioButton("a");
		rdbtn1.setBounds(113, 297, 127, 25);
		rdbtn1.setActionCommand("a");

		JRadioButton rdbtn2 = new JRadioButton("b");
		rdbtn2.setBounds(113, 332, 127, 25);
		rdbtn2.setActionCommand("b");
		
		JRadioButton rdbtn3 = new JRadioButton("c");
		rdbtn3.setBounds(113, 367, 127, 25);
		rdbtn3.setActionCommand("c");
		
		JRadioButton rdbtn4 = new JRadioButton("d");
		rdbtn4.setBounds(113, 402, 127, 25);
		rdbtn4.setActionCommand("d");
		
		
		ButtonGroup rdbtnGroup = new ButtonGroup();
		rdbtnGroup.add(rdbtn1);
		rdbtnGroup.add(rdbtn2);
		rdbtnGroup.add(rdbtn3);
		rdbtnGroup.add(rdbtn4);
	
		int options = firstQuestion.getOptions();

		if (options == 2) {
			rdbtn3.setVisible(false);
			rdbtn4.setVisible(false);
		}

		JLabel answerLabel = new JLabel();
		answerLabel.setBounds(450, 387, 100,50);
		quizPanel.add(answerLabel);

		
		JButton btnQuizSubmit = new JButton("Submit");

		btnQuizSubmit.setBounds(560, 399, 97, 25);

		JButton btnQuizNext = new JButton("Next");
		btnQuizNext.setBounds(660, 399, 97, 25);

		btnQuizNext.setVisible(false);

		btnQuizSubmit.addActionListener(new ActionListener() {
			int quizIndex = 0;
			public void actionPerformed(ActionEvent e) {
				
				String answer = rdbtnGroup.getSelection().getActionCommand();
				boolean check = quiz.checkAnswer(quizIndex, answer);
				
				if (check == true) {
					answerLabel.setText("Correct!");
				}
				else {
					answerLabel.setText("Wrong");
				}
				btnQuizNext.setVisible(true);
				btnQuizSubmit.setEnabled(false);
				answerLabel.setVisible(true);
				quizIndex++; //increase index in question arraylist
				
			}
		});
		
		btnQuizNext.addActionListener(new ActionListener() {
			int quizIndex = 0;
			public void actionPerformed(ActionEvent e) {
				quizIndex++;
				btnQuizNext.setVisible(false);
				btnQuizSubmit.setEnabled(true);
				answerLabel.setVisible(false);
				if (quizIndex == quiz.quizList.size()) { // if no more questions, close window
					switchPanel(initialPanel);
				}
				else { //else set the next question
					Questions question = quiz.quizList.get(quizIndex);
					int options = question.getOptions();
					quizTextArea.setText("Question " + (quizIndex+1) + " of " + quiz.quizList.size() + "\n" + question.getQuestion());
					rdbtnGroup.clearSelection();
					if (options == 2) {
						rdbtn3.setVisible(false);
						rdbtn4.setVisible(false);
					}
					else {
						rdbtn3.setVisible(true);
						rdbtn4.setVisible(true);
					}
					
				}
			}
		});
		quizPanel.add(quizTextArea);
		quizPanel.add(btnQuizSubmit);
		quizPanel.add(btnQuizNext);
		quizPanel.add(rdbtn1);
		quizPanel.add(rdbtn2);
		quizPanel.add(rdbtn3);
		quizPanel.add(rdbtn4);

		
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