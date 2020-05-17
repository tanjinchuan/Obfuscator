package FYP;

import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.javaparser.ParseException;

import java.awt.event.*;


@SuppressWarnings("serial")

public class BrowsePanel extends JPanel {



	protected String inputFilePath;
	protected String outputFilePath;
    protected String outputDirectory;
    //protected String outputFile;
    
	protected JTextField inputTextField = new JTextField();
	protected JTextField outputTextField = new JTextField();

    public BrowsePanel(JFrame frame, Frame frameClass, Obfuscator obfuscator, LayeredPane layeredPane) {
		
		JLabel inputFileTypeLabel = new JLabel("");
		
		inputTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		outputTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		inputTextField.setBounds(200, 155, 600, 22);
		inputTextField.setEditable(false);
        inputTextField.setColumns(10);
        this.add(inputTextField);

        
		
		outputTextField.setColumns(10);
        outputTextField.setBounds(200, 247, 600, 22);
        outputTextField.setEditable(false);
        this.add(outputTextField);
        

		inputFileTypeLabel.setBounds(200, 181, 470, 16);
	    this.add(inputFileTypeLabel);
		
		// JLabel outputFileTypeLabel = new JLabel("");
		// outputFileTypeLabel.setBounds(139, 275, 470, 16);
		// this.add(outputFileTypeLabel);
		
		//File input chooser function
		JButton btnInputBrowsePanel = new JButton("Browse...");
		btnInputBrowsePanel.setBounds(810, 154, 110, 25);
		btnInputBrowsePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		btnInputBrowsePanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception exception) {

				}
				JFileChooser fc = new JFileChooser();
				
		        //Handle open button action.
		        if (e.getSource() == btnInputBrowsePanel) {
		            int returnVal = fc.showOpenDialog(frame);

		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc.getSelectedFile();
		             
		                inputTextField.setText(file.getAbsolutePath());
                        
		                if (checkExt(file.getName()) == true) {
                            inputFileTypeLabel.setText("");
                            setInputPath(inputTextField.getText());
		                }
		                else {
							//inputFileTypeLabel.setText("Invalid file extension");
							JOptionPane.showMessageDialog(frame, "Invalid File Extension, Please choose .java file", "Warning", JOptionPane.OK_OPTION);
							inputTextField.setText(null);
						}
		     
							
					}
	            } 
				
				try {

					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		        
				} catch (Exception exception) {

				}
		        
		}
		});
		

		this.add(btnInputBrowsePanel);
		
		//File output chooser function
		JButton btnOutputBrowsePanel = new JButton("Browse...");
		btnOutputBrowsePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		btnOutputBrowsePanel.setBounds(810, 246, 110, 25);
		this.add(btnOutputBrowsePanel);

		btnOutputBrowsePanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				
				} catch (Exception exception) {
					
				}
				JFileChooser fc2 = new JFileChooser();
				fc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		        //Handle open button action.
		        if (e.getSource() == btnOutputBrowsePanel) {
		            int returnVal = fc2.showOpenDialog(frame);

		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc2.getSelectedFile();
						
						if (checkDir(file.getAbsolutePath()) == true){
							outputTextField.setText(file.getAbsolutePath());
							setOutputDir(outputTextField.getText());
						}
						else {
							JOptionPane.showMessageDialog(frame, "Invalid directory", "Warning", JOptionPane.OK_OPTION);
							outputTextField.setText(null);
						}
                        
		            } 
		        }
		        
		    
				try {

					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				
				} catch (Exception exception) {
					
				}    
			}
			
		});
				
		////////////////////////////////////////////////////////////////////////////////////////////
		//Button to go to Slider Panel on BrowsePanel
		////////////////////////////////////////////////////////////////////////////////////////////
		JButton btnBrowseNextPanel = new JButton("Next");
		btnBrowseNextPanel.setEnabled(false);
		btnBrowseNextPanel.setBounds(800, 360, 100, 60);
		btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0, true));

		this.add(btnBrowseNextPanel);
		
		btnBrowseNextPanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
                
				String inputFilePath = getInput();
				String outputFilePath = getOutput();
				if (checkFileExists(inputFilePath) == false) {
					JOptionPane.showMessageDialog(frame, "Please choose valid input .java file", "Warning", JOptionPane.OK_OPTION);

				}
				
				else if (checkDir(outputFilePath) == false){
					JOptionPane.showMessageDialog(frame, "Please choose valid output location", "Warning", JOptionPane.OK_OPTION);

				}

				else {
					//set the source code
					try {

						obfuscator.compileCode(inputFilePath);
						
						String[] array = obfuscator.getClasses();

						DefaultComboBoxModel model = new DefaultComboBoxModel(array);
						frameClass.basicSettingsPanel.comboBox.setModel(model);
						frameClass.advSettingsPanel.comboBox.setModel (model);

					} catch (FileNotFoundException fe) {

					} catch (ParseException ie) {
						
					}
					layeredPane.switchPanel(frameClass.basicSettingsPanel);

				}
				
				
			
			}
		});
        
        //Button to go back to initial Panel on browse panel
		JButton btnBrowsePanelBack = new JButton("Back");
		btnBrowsePanelBack.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		
		btnBrowsePanelBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(frameClass.initialPanel);
			}
		});
		btnBrowsePanelBack.setBounds(48, 360, 100, 60);
		this.add(btnBrowsePanelBack);

		inputTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (getInput().equals("") | getOutput().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0, true));

				}
				else {
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
					btnBrowseNextPanel.setEnabled(true);
				
				}
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				if (getInput().equals("") | getOutput().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0, true));

				}
				else {
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
					btnBrowseNextPanel.setEnabled(true);
				
				}
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (getInput().equals("") | getOutput().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0, true));

				}
				else {
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
					btnBrowseNextPanel.setEnabled(true);
				
				}				
			}

			
		});
		//check if textfield is valid
		outputTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (getInput().equals("") | getOutput().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0, true));

				}
				else {
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
					btnBrowseNextPanel.setEnabled(true);
				
				}
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				if (getInput().equals("") | getOutput().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0, true));

				}
				else {
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
					btnBrowseNextPanel.setEnabled(true);
				
				}
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (getInput().equals("") | getOutput().equals("")) {
					btnBrowseNextPanel.setEnabled(false);
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0, true));

				}
				else {
					btnBrowseNextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
					btnBrowseNextPanel.setEnabled(true);
				
				}				
			}

			
		});
        
		JLabel lblInput = new JLabel("Select input .java file");
		lblInput.setBounds(200, 130, 300, 16);
		this.add(lblInput);

		JLabel lblInputLabel = new JLabel("File location");
		lblInputLabel.setBounds(95, 158, 150, 16);
        this.add(lblInputLabel);
		
		JLabel lblOutput = new JLabel("Choose output location");
		lblOutput.setBounds(200, 212, 300, 16);
		this.add(lblOutput);

		JLabel lblOutputDir = new JLabel("Save file to");
		lblOutputDir.setBounds(100, 250, 100, 16);
        this.add(lblOutputDir);
        
		

		
        this.setLayout(null);
		
	}

	
    private void setInputPath(String path) {
        this.inputFilePath = path;
    }
    private void setOutputDir(String path) {
        this.outputDirectory = path;
    }

	private boolean checkDir(String directory) {
		File file = new File(directory);

		if (file.isDirectory() == true) {
			return true;
		} 
		else {
			return false;
		}
	}

	private boolean checkFileExists(String directory) {
		File file = new File(directory);
		if (file.isFile() == true && checkExt(directory) == true) {
			return true;
		}
		else {
			return false;
		}
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
	
	
	//initiate textfields to empty
	public void setTextField() {
		inputTextField.setText("");
		outputTextField.setText("");
	}

    public String getInput() {
        return inputTextField.getText();
    }
    public String getOutput() {
        return outputTextField.getText();
    }

	
}