package FYP;

import java.io.*;
import java.util.Hashtable;
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
	private JTextField outputTextfield;
	private int difficulty;
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
	public void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	//check selected file ext	
	public boolean checkExt(String fileName) {
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
	
	public boolean checkFileName(String inputFile, String outputFile)
	{
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

		frame = new JFrame("Obsfuscator");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		layeredPane.setBounds(0, 0, 782, 453);
		frame.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JFileChooser fc = new JFileChooser();
		

		JPanel initialPanel = new JPanel();
		layeredPane.add(initialPanel);
		initialPanel.setLayout(null);
		
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
		//switch to desired frame for testing purposes
		//switchPanel(sliderOptionPanel);
		
		

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
				obfuscator.obfuscate(inputTextfield.getText(), outputTextfield.getText(), difficulty);
			}
		});
		btnNextAdvOptions.setBounds(620, 390, 97, 25);
		advOptionsPanel.add(btnNextAdvOptions);
		
		JButton btnBackSliderPanel = new JButton("Back");
		btnBackSliderPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(initialPanel);
			}
		});
		btnBackSliderPanel.setBounds(48, 380, 97, 25);
		sliderOptionPanel.add(btnBackSliderPanel);

		JButton btnAdvancedSettings = new JButton("Advanced settings");
		btnAdvancedSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(advOptionsPanel);
			}
		});
		btnAdvancedSettings.setBounds(608, 336, 148, 25);
		sliderOptionPanel.add(btnAdvancedSettings);
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//Progress Bar panel
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		JLabel lblProgressBarStatus = new JLabel("Scanning files...");
		lblProgressBarStatus.setBounds(88, 219, 160, 16);
		progressBarPanel.add(lblProgressBarStatus);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////
		//Changelog panel
		//////////////////////////////////////////////////////////////////////////////////////////////////

		JTextField originalCodeField = new JTextField();
		JTextField newCodeField = new JTextField();
		originalCodeField.setBounds(0, 0, 400, 500);
		newCodeField.setBounds(400, 0, 400, 500);
		changelogPanel.add(originalCodeField);
		changelogPanel.add(newCodeField);

		JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
		BoundedRangeModel brm = originalCodeField.getHorizontalVisibility();

		scrollBar.setModel(brm);
		changelogPanel.add(scrollBar);
		
		JScrollBar scrollBar2 = new JScrollBar(JScrollBar.VERTICAL);
		BoundedRangeModel brm2 = newCodeField.getHorizontalVisibility();
		
		scrollBar2.setModel(brm2);
		changelogPanel.add(scrollBar2);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		//Final panel
		//////////////////////////////////////////////////////////////////////////////////////////////////
		JButton btnViewOutput = new JButton("View output file");
		btnViewOutput.setBounds(59, 196, 143, 25);
		finalPanel.add(btnViewOutput);
		
		JButton btnViewChangelog = new JButton("View changelog");
		btnViewChangelog.setBounds(310, 196, 143, 25);
		finalPanel.add(btnViewChangelog);
		btnViewChangelog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(changelogPanel);
			}
		});
		
		JButton btnAnother = new JButton("Obfuscate another file");
		btnAnother.setBounds(526, 196, 184, 25);
		finalPanel.add(btnAnother);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		//for adv options
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
		
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setBounds(88, 167, 560, 38);
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
		initialPanel.add(inputTextfield);
		inputTextfield.setColumns(10);
		
		outputTextfield = new JTextField();
		outputTextfield.setColumns(10);
		outputTextfield.setBounds(139, 193, 470, 22);
		initialPanel.add(outputTextfield);
		
		btnFinish.setBounds(629, 386, 97, 25);
		finalPanel.add(btnFinish);
		
		JLabel lblInputLabel = new JLabel("Input file");
		lblInputLabel.setBounds(48, 128, 56, 16);
		initialPanel.add(lblInputLabel);
		
		JLabel lblOutputFile = new JLabel("Output file");
		lblOutputFile.setBounds(48, 196, 59, 16);
		initialPanel.add(lblOutputFile);
		
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
		//Obfuscation part, after choosing slider
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
					obfuscator.obfuscate(inputTextfield.getText(), outputTextfield.getText(), difficulty);

					//show statistics
					originalCodeField.setText(inputTextfield.getText());
					newCodeField.setText(outputTextfield.getText());

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
		

		
		/*
		//JSlider changes
	    public void stateChanged(ChangeEvent e) {

	        }*/
		
		JLabel inputFileTypeLabel = new JLabel("");
		inputFileTypeLabel.setBounds(139, 151, 470, 16);
		initialPanel.add(inputFileTypeLabel);
		
		JLabel outputFileTypeLabel2 = new JLabel("");
		outputFileTypeLabel2.setBounds(139, 220, 470, 16);
		initialPanel.add(outputFileTypeLabel2);
		
		JButton btnNextInitialPanel = new JButton("Next");
		btnNextInitialPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (outputTextfield.getText().equals("")) {
					
					int response = JOptionPane.showConfirmDialog(frame, "Input file will be overwritten. Proceed?", "Warning", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						switchPanel(sliderOptionPanel);
					}
				}
				else if (outputTextfield.getText() != "") {
					String s = outputTextfield.getText();
					String s2 = inputTextfield.getText();
					if (checkExt(s) == false) { //if file ext is not valid 
						outputFileTypeLabel2.setText("Invalid output file. Output file can only be a .java file");
					}
					else if (checkFileName(s, s2) == false){ //if input and output file names are different
						switchPanel(sliderOptionPanel);
					}
					else if (checkFileName(s, s2) == true) { //if input and output file names are same
						outputFileTypeLabel2.setText("Input and output file cannot have the same name");
					}
							
				}
				
				//check which settings file exists and direct to appropriate one
				File file = new File ("./src/settings/easysettings.txt");
				File file2 = new File ("./src/settings/advsettings.txt");

				if (file2.exists())
				{
					switchPanel(advOptionsPanel);
				}
				else if (file.exists())
				{
					try {
						Scanner scan = new Scanner(file);
						int i = Integer.parseInt(scan.next());
						slider.setValue(i);
						switchPanel(sliderOptionPanel);
					}
					catch (FileNotFoundException e2)
					{
						e2.printStackTrace();
					}
				}
					
				
			}
		});
		btnNextInitialPanel.setBounds(607, 385, 97, 25);
		btnNextInitialPanel.setEnabled(false);
		initialPanel.add(btnNextInitialPanel);
		
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
		
		
		//File chooser function
		JButton btnBrowseInitialPanel = new JButton("Browse...");
		btnBrowseInitialPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnNextInitialPanel.setEnabled(false);
		        //Handle open button action.
		        if (e.getSource() == btnBrowseInitialPanel) {
		            int returnVal = fc.showOpenDialog(frame);

		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc.getSelectedFile();
		                //This is where a real application would open the file.
		                inputTextfield.setText(file.getAbsolutePath());
		                
		                if (checkExt(file.getName()) == true) {
		                	inputFileTypeLabel.setText("");
		                	btnNextInitialPanel.setEnabled(true);
		                }
		                else {
		                	inputFileTypeLabel.setText("Invalid input file");
		                }
		            } 
			}
		}
		});
		
		btnBrowseInitialPanel.setBounds(644, 124, 97, 25);
		initialPanel.add(btnBrowseInitialPanel);
		
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


	

