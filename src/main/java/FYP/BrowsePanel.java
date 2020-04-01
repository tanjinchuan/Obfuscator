package FYP;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.*;


@SuppressWarnings("serial")

public class BrowsePanel extends JPanel {


    JLabel inputFileTypeLabel = new JLabel("");

    private String inputFilePath;
    private String outputDirectory;
    private String outputFile;
    private String outputFilePath;

    public BrowsePanel(JFrame frame, LayeredPane layeredPane, JPanel initialPanel, JPanel sliderOptionPanel) {
        
        JTextField inputTextField = new JTextField();
        JTextField outputTextField = new JTextField();
        JTextField outputFileTextField = new JTextField();

		inputTextField.setBounds(139, 125, 470, 22);
		inputTextField.setEditable(false);
        inputTextField.setColumns(10);
        this.add(inputTextField);

        
		
		outputTextField.setEditable(false);
		outputTextField.setColumns(10);
        outputTextField.setBounds(139, 217, 470, 22);
        this.add(outputTextField);
        
        JButton btnBrowseNextPanel = new JButton("Next");
		btnBrowseNextPanel.setEnabled(false);


        JFileChooser fc = new JFileChooser();
		JFileChooser fc2 = new JFileChooser();
		fc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		inputFileTypeLabel.setBounds(139, 151, 470, 16);
	    this.add(inputFileTypeLabel);
		
		JLabel outputFileTypeLabel = new JLabel("");
		outputFileTypeLabel.setBounds(139, 275, 470, 16);
		this.add(outputFileTypeLabel);
		
		//File input chooser function
		JButton btnInputBrowsePanel = new JButton("Browse...");
		btnInputBrowsePanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
		                	inputFileTypeLabel.setText("Invalid file extension");
		                }
		     
							
					}
	            } 
		        
		        
		        
		}
		});
		
		btnInputBrowsePanel.setBounds(644, 124, 97, 25);
		this.add(btnInputBrowsePanel);
		
		//File output chooser function
		JButton btnOutputBrowsePanel = new JButton("Browse...");
		btnOutputBrowsePanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
		        //Handle open button action.
		        if (e.getSource() == btnOutputBrowsePanel) {
		            int returnVal = fc2.showOpenDialog(frame);

		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc2.getSelectedFile();
		                
                        outputTextField.setText(file.getAbsolutePath());
                        setOutputDir(outputTextField.getText());
		            } 
		        }
		        
		        
	    	}
		});
				
		btnOutputBrowsePanel.setBounds(644, 216, 97, 25);
		this.add(btnOutputBrowsePanel);
		
		outputFileTextField.setText("Enter output file name (without extension)");
		outputFileTextField.setColumns(10);
		outputFileTextField.setBounds(139, 257, 470, 22);
		outputFileTextField.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
                outputFileTextField.setText("");
			}
		});
        this.add(outputFileTextField);

		outputFileTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				btnBrowseNextPanel.setEnabled(true);
				setOutputFile(outputFileTextField.getText());
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				btnBrowseNextPanel.setEnabled(true);
				setOutputFile(outputFileTextField.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				btnBrowseNextPanel.setEnabled(true);
				setOutputFile(outputFileTextField.getText());
			}
			
		});
        
        
        //next button function
		btnBrowseNextPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String outputDir = getOutput();
                File checkDir = new File(outputDir); // check if output directory valid
				if (checkDir.exists() == true){
                    String fullPath = outputDir + "\\" + getFileName() + ".java";
                    setOutputPath(fullPath);
                    

					if(checkFileExists(frame, outputFilePath) == true) {
						//file path correct
						layeredPane.switchPanel(sliderOptionPanel);

                    }
                    else {
                        System.out.println("wtf");
                    }
					
				
                } else { //check for valid directory
					JOptionPane.showMessageDialog(frame, "Invalid directory.", "Warning", JOptionPane.OK_OPTION);

				}
			
			}
		});
        btnBrowseNextPanel.setBounds(607, 385, 97, 25);
		btnBrowseNextPanel.setEnabled(false);
        this.add(btnBrowseNextPanel);

        //back button function
		JButton btnBrowsePanelBack = new JButton("Back");
		btnBrowsePanelBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(initialPanel);
			}
		});
		btnBrowsePanelBack.setBounds(55, 390, 97, 25);
		this.add(btnBrowsePanelBack);
		
		

		JLabel lblInputLabel = new JLabel("Input file");
		lblInputLabel.setBounds(48, 128, 56, 16);
        this.add(lblInputLabel);
        
		JLabel lblOutputDir = new JLabel("Output dir");
		lblOutputDir.setBounds(48, 220, 59, 16);
        this.add(lblOutputDir);
        
		JLabel lblOutputFile = new JLabel("Output file");
        lblOutputFile.setBounds(48, 260, 59, 16);
        this.add(lblOutputFile);

        this.setLayout(null);
        
		
    }

    public void setInputPath(String path) {
        this.inputFilePath = path;
    }
    public void setOutputDir(String path) {
        this.outputDirectory = path;
    }

    public void setOutputFile(String file) {
        this.outputFile = file;
    }

    public void setOutputPath(String path) {
        this.outputFilePath = path;
    }
    public String getInput() {
        return inputFilePath;
    }
    public String getOutput() {
        return outputDirectory;
    }

    public String getFileName() {
        return outputFile;
    }

    public String getFullOutput() {
        return outputFilePath;
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
    
    //to check file name errors
    public boolean checkFileExists(JFrame frame, String fullPath) {

		//if output file name empty
		if (getFileName().equals("")) {
			JOptionPane.showMessageDialog(frame, "Output file name empty", "Warning", JOptionPane.OK_OPTION);
			return false;
		}
		//i)f input file dir and output file dir and file is same name
		if (getInput().equals(fullPath)) 
		{
			JOptionPane.showMessageDialog(frame, "Input and output file cannot be the same", "Warning", JOptionPane.OK_OPTION);
			return false;
		}
		//if output file ext  is not valid
		else if (getFileName().indexOf(".") > 0 ) 
		{ //if "." exists
			JOptionPane.showMessageDialog(frame, "Invalid output file name. Please exclude the file extension", "Warning", JOptionPane.OK_OPTION);
			return false;
		}
		else if (getFileName().indexOf(" ") > 0 ) {
			JOptionPane.showMessageDialog(frame, "Invalid output file name.", "Warning", JOptionPane.OK_OPTION);
			return false;
		}
		else {
			return true;
		}
		
	}

	
}