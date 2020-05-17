package FYP;

import java.awt.Component;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AdvSettingsPanel extends JPanel {

	private HashMap<String, Integer> currentSettings = new HashMap<String, Integer>();
	private ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
	JComboBox<String> comboBox = new JComboBox<>();


	public AdvSettingsPanel(Frame frameClass, Obfuscator obfuscator, LayeredPane layeredPane) {

		this.setLayout(null);
		
		//add help label
		JButton helpButton = new JButton("Help");
		helpButton.setBounds(50, 50, 100, 25);
		helpButton.setVisible(true);
		this.add(helpButton);
		helpButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				Help help = new Help();
				help.openPDF();
			}
		});

		JLabel lblRenameMethods = new JLabel("Rename Methods");
		JLabel lblRenameIdentifiers = new JLabel("Rename Identifiers");
		JLabel lblMiscellaneous = new JLabel("Misceallanous");
		JLabel lblFlow = new JLabel("Flow Obfuscation");
		JLabel lblEncrypt = new JLabel("String Encoding");
		
		JCheckBox chckbxRenamePublic = new JCheckBox("Public");
		JCheckBox chckbxRenameProtected = new JCheckBox("Protected");
		JCheckBox chckbxRenamePrivate = new JCheckBox("Private");

		JCheckBox chckbxChangeClassName = new JCheckBox("Change Class Names");
		JCheckBox chckbxChangeVariables = new JCheckBox("Change Variable Names");
		JCheckBox chckbxChangeParameters = new JCheckBox("Change Parameter Names");
		JCheckBox chckbxRemoveWhitespace = new JCheckBox("Compact Code");
		JCheckBox chckbxInsertDummyCode = new JCheckBox("Insert Dummy Code");
		JCheckBox chckbxRemoveComments = new JCheckBox("Remove Comments");
		JCheckBox chckbxStringEncoding = new JCheckBox("String Encoding");
		JCheckBox chckbxUnicode = new JCheckBox("String To Unicode");
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		// for adv options
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		Font lblFont = new Font("Helvetica", Font.BOLD, 16);

		//Icons
		ImageIcon infoImg = new ImageIcon(this.getClass().getClassLoader().getResource("infoIcon.png"));
		

		////////////////////////////////////////////////////////////////////////////////////////////
		//Back and Next button for advanced settings panel
		///////////////////////////////////////////////////////////////////////////////////////////
		JButton btnBackAdvOptions = new JButton("Back");
		btnBackAdvOptions.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

		btnBackAdvOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layeredPane.switchPanel(frameClass.basicSettingsPanel);
			}
		});
		btnBackAdvOptions.setBounds(48, 360, 100, 60);
		this.add(btnBackAdvOptions);

		//JLabel for combobox
		JLabel comboBoxLabel = new JLabel("Choose main class");
		comboBoxLabel.setBounds(422, 275, 200, 20);
		comboBoxLabel.setFont(lblFont);
		this.add(comboBoxLabel);
		
		//JComboBox on advSettingsPanel
		comboBox.setBounds(422, 300, 200, 20);
		this.add(comboBox);
		
		//next button for advanced settings panel
		JButton btnNextAdvOptions = new JButton("Start Obfuscating");
		btnNextAdvOptions.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		btnNextAdvOptions.setBounds(800, 350, 180, 80);
		this.add(btnNextAdvOptions);

		
		btnNextAdvOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				//obfuscate the file
				try {
					//set the file paths
					frameClass.inputFilePath = frameClass.browsePanel.getInput();
					frameClass.outputFilePath = frameClass.browsePanel.getOutput();

					//get selected main class
					String selectedClass = String.valueOf(comboBox.getSelectedItem());

					obfuscator.advObfuscate(frameClass.inputFilePath, frameClass.outputFilePath, getSettings(), selectedClass);
					//start obsfuscation
					
					layeredPane.switchPanel(frameClass.progressBarPanel);
					frameClass.progressBarPanel.update(layeredPane, frameClass.browsePanel, frameClass.finalPanel, obfuscator);
					
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		

		lblRenameMethods.setBounds(22, 140, 200, 20);
		lblRenameMethods.setFont(lblFont);
		this.add(lblRenameMethods);

		JLabel methodToolTip = new JLabel(infoImg);
		methodToolTip.setBounds(160, 140, 20, 20);
		this.add(methodToolTip);
		methodToolTip.setToolTipText("<html>Obfuscates method names, select desired modifiers<br><br>*Will not change \"main\" method<br>*Will not change overriden methods</html>");
		

		lblRenameIdentifiers.setBounds(200, 140, 200, 20);
		lblRenameIdentifiers.setFont(lblFont);
		this.add(lblRenameIdentifiers);

		JLabel identifierToolTip = new JLabel(infoImg);
		identifierToolTip.setBounds(350, 140, 20, 20);
		this.add(identifierToolTip);
		identifierToolTip.setToolTipText("<html>Obfuscates identifiers, Variables and Parameters<html>");

		lblFlow.setBounds(422, 140, 150, 20);
		lblFlow.setFont(lblFont);
		this.add(lblFlow);

		JLabel flowToolTip = new JLabel(infoImg);
		flowToolTip.setBounds(560, 140, 20, 20);
		this.add(flowToolTip);
		flowToolTip.setToolTipText("<html>Obfuscates flow of the code by inserting dummy code<br><br>*Will not affect the original functionality of the source code</html>");

		lblEncrypt.setBounds(622, 140, 200, 20);
		lblEncrypt.setFont(lblFont);
		this.add(lblEncrypt);

		JLabel encryptToolTip = new JLabel(infoImg);
		encryptToolTip.setBounds(750, 140, 20, 20);
		this.add(encryptToolTip);
		encryptToolTip.setToolTipText("<html>Obfuscates String literals to prevent information gathering</html>");

		lblMiscellaneous.setBounds(822, 140, 200, 20);
		lblMiscellaneous.setFont(lblFont);
		this.add(lblMiscellaneous);

		JLabel miscToolTip = new JLabel(infoImg);
		miscToolTip.setBounds(940, 140, 20, 20);
		miscToolTip.setToolTipText("<html>Remove comments removes all comments from source code<br>Remove white spaces removes empty lines and wraps the code together</html>");
		this.add(miscToolTip);

		
		// Rename Methods
		chckbxRenamePublic.setBounds(22, 170, 150, 25);
		this.add(chckbxRenamePublic);

		chckbxRenameProtected.setBounds(22, 195, 150, 25);
		this.add(chckbxRenameProtected);

		chckbxRenamePrivate.setBounds(22, 220, 150, 25);
		this.add(chckbxRenamePrivate);

		//Rename Identifiers
		chckbxChangeClassName.setBounds(200, 170, 220, 25);
		this.add(chckbxChangeClassName);

		chckbxChangeVariables.setBounds(200, 195, 220, 25);
		this.add(chckbxChangeVariables);

		chckbxChangeParameters.setBounds(200, 220, 230, 25);
		this.add(chckbxChangeParameters);

		
		//Flow
		chckbxInsertDummyCode.setBounds(422, 170, 200, 25);
		this.add(chckbxInsertDummyCode);


		// Encrypt
		chckbxStringEncoding.setBounds(622, 170, 200, 25);
		this.add(chckbxStringEncoding);

		chckbxUnicode.setBounds(622, 195, 200, 25);
		this.add(chckbxUnicode);

		// Misc
		chckbxRemoveWhitespace.setBounds(822, 170, 220, 25);
		this.add(chckbxRemoveWhitespace);


		chckbxRemoveComments.setBounds(822, 195, 220, 25);
		this.add(chckbxRemoveComments);

		// adv default settings btn
		JButton btnAdvDefaultSettings = new JButton("Save as default settings");
		btnAdvDefaultSettings.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		btnAdvDefaultSettings.setBounds(800, 36, 200, 45);
		this.add(btnAdvDefaultSettings);

		btnAdvDefaultSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getCurrentOptions();
				writeSettings(currentSettings);
				JOptionPane.showMessageDialog(btnAdvDefaultSettings, "Settings have been saved!", 
				"Message", JOptionPane.PLAIN_MESSAGE);
					
			}
		});

		


		//add the checkboxes into collection
		for (Component comp : this.getComponents()) {
			if( comp instanceof JCheckBox) checkboxes.add( (JCheckBox)comp );
			
		}

		

	}

	//for saving default settings
	public void getCurrentOptions() {
		int boxID = 0;
		
		for (JCheckBox box: checkboxes) {
			//get the state of each box and save into current settings hashmap
			String boxName = boxID + "_" + box.getText();
			int check_state;
			if (box.isSelected()) {
				check_state = 1;
			}
			else {
				check_state = 0;
			}
			boxID++;
			currentSettings.put(boxName, check_state);
		}
	}

		
	//use to set the options from settings file
	public void setOptions() {
		
		int boxID = 0;
		for (JCheckBox box: checkboxes) {
			String boxName = boxID + "_" + box.getText();
			if (currentSettings.get(boxName) == 1) {
				box.setSelected(true);
			}
			else {
				box.setSelected(false);
			}
			boxID++;
		}
	
	}

	
	public HashMap<String, Integer> getSettings() {
		getCurrentOptions();
		return currentSettings;
	}


	public void createSettingsFile() {
        //once panel initialized, create advsettings.txt
		File file = new File("./settings/advsettings.txt");
		if (file.exists() == false) {
			int boxID = 0;
			try {
				FileWriter fr = new FileWriter(file);
				for (JCheckBox checkbox: checkboxes) {
					fr.write(boxID + "_" + checkbox.getText() + ":0\n"); //initialze all settings to unselected
					boxID++;
				}
				fr.close();
				System.out.println("Settings file created!");
				
			} catch (IOException ie) {
				System.out.println("Empty string");
			}
			
        } 
        else {
            System.out.println("");
        }
        
	}
	
	//read the settings file once go into advanced settings
    public void readSettingsFile() {
        File file = new File("./settings/advSettings.txt");
        if (file.exists() == true) {
            //read into hashmap
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] split = line.split(":");
                    currentSettings.put(split[0], Integer.parseInt(split[1]));
                    
                }
                scanner.close();
            } catch(FileNotFoundException fe) {
                System.out.println("File missing");
            }
            
        } 
	}
	
	//write hashmap into advsettings.txt
    
    private void writeSettings(HashMap<String, Integer> currentSettings) {

        File settingsFile = new File("./settings/advsettings.txt");
        FileWriter fw;
        try {
            fw = new FileWriter(settingsFile, false);
            for (String key: currentSettings.keySet()) {
                fw.write(key  + ":" + currentSettings.get(key) + "\n");
            }
            fw.close();
        } catch (IOException ie) {
            System.out.println("Writing to Settings file IO error");
        }
        
	}

	
    

    
}