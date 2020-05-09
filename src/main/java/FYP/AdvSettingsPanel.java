package FYP;

import java.awt.Component;
import java.awt.event.*;
import java.awt.Font;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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


	public AdvSettingsPanel() {

		this.setLayout(null);

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
		JCheckBox chckbxRemoveWhitespace = new JCheckBox("Remove White Space");
		JCheckBox chckbxInsertDummyCode = new JCheckBox("Insert Dummy Code");
		JCheckBox chckbxRemoveComments = new JCheckBox("Remove Comments");
		JCheckBox chckbxStringEncoding = new JCheckBox("String Encoding");
		JCheckBox chckbxUnicode = new JCheckBox("String To Unicode");
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		// for adv options
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		Font lblFont = new Font("Helvetica", Font.BOLD, 16);

		lblRenameMethods.setBounds(22, 140, 200, 20);
		lblRenameMethods.setFont(lblFont);
		this.add(lblRenameMethods);

		lblRenameIdentifiers.setBounds(200, 140, 200, 20);
		lblRenameIdentifiers.setFont(lblFont);
		this.add(lblRenameIdentifiers);

		lblFlow.setBounds(422, 140, 150, 20);
		lblFlow.setFont(lblFont);
		this.add(lblFlow);

		lblEncrypt.setBounds(622, 140, 200, 20);
		lblEncrypt.setFont(lblFont);
		this.add(lblEncrypt);

		lblMiscellaneous.setBounds(822, 140, 200, 20);
		lblMiscellaneous.setFont(lblFont);
		this.add(lblMiscellaneous);
		
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

		chckbxChangeParameters.setBounds(200, 220, 220, 25);
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
		btnAdvDefaultSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getCurrentOptions();
				writeSettings(currentSettings);
				JOptionPane.showMessageDialog(btnAdvDefaultSettings, "Settings have been saved!", 
				"Message", JOptionPane.PLAIN_MESSAGE);
					
			}
		});

		btnAdvDefaultSettings.setBounds(700, 36, 189, 45);
		this.add(btnAdvDefaultSettings);


		//add the checkboxes into collection
		for (Component comp : this.getComponents()) {
			if( comp instanceof JCheckBox) checkboxes.add( (JCheckBox)comp );
			
		}

		//JLabel for combobox
		JLabel comboBoxLabel = new JLabel("Choose main class");
		comboBoxLabel.setBounds(300, 280, 200, 20);
		comboBoxLabel.setFont(lblFont);
		this.add(comboBoxLabel);
		
		//JComboBox on advSettingsPanel
		comboBox.setBounds(300, 300, 200, 20);
		this.add(comboBox);

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
            System.out.println("Settings File found");
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

	public HashMap<String, Integer> getSettings() {
		return currentSettings;
	}
    

    
}