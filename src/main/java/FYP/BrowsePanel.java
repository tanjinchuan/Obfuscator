package FYP;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.*;


@SuppressWarnings("serial")

public class BrowsePanel extends JPanel {


    JLabel inputFileTypeLabel = new JLabel("");

	private String inputFilePath;
	private String outputFilePath;
    private String outputDirectory;
    private String outputFile;
    
	JTextField inputTextField = new JTextField();
	JTextField outputTextField = new JTextField();
	JTextField outputFileTextField = new JTextField();
	
    public BrowsePanel(JFrame frame) {
        
        

		inputTextField.setBounds(139, 125, 470, 22);
		inputTextField.setEditable(false);
        inputTextField.setColumns(10);
        this.add(inputTextField);

        
		
		outputTextField.setEditable(false);
		outputTextField.setColumns(10);
        outputTextField.setBounds(139, 217, 470, 22);
        this.add(outputTextField);
        
        


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
		
		btnInputBrowsePanel.setBounds(644, 124, 110, 25);
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
				
		btnOutputBrowsePanel.setBounds(644, 216, 110, 25);
		this.add(btnOutputBrowsePanel);
		
		
		outputFileTextField.setBounds(139, 257, 200, 22);
        this.add(outputFileTextField);

		

		//label beside output file name field
		JLabel lbljava = new JLabel(".java");
		lbljava.setBounds(340, 240, 50, 50);
		this.add(lbljava);

        
        
        
		JLabel lblInput = new JLabel("Select input .java file");
		lblInput.setBounds(140, 100, 300, 16);
		this.add(lblInput);

		JLabel lblInputLabel = new JLabel("File location");
		lblInputLabel.setBounds(35, 128, 150, 16);
        this.add(lblInputLabel);
		
		JLabel lblOutput = new JLabel("Choose output location");
		lblOutput.setBounds(140, 192, 300, 16);
		this.add(lblOutput);

		JLabel lblOutputDir = new JLabel("Save file to");
		lblOutputDir.setBounds(48, 220, 80, 16);
        this.add(lblOutputDir);
        
		JLabel lblOutputFile = new JLabel("File Name");
        lblOutputFile.setBounds(48, 260, 80, 16);
        this.add(lblOutputFile);

        this.setLayout(null);
        
		
	}
	
	//initiate textfields to empty
	public void setTextField() {
		inputTextField.setText("");
		outputTextField.setText("");
		outputFileTextField.setText("");
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
		
		else {
			return true;
		}
		
	}

	
}