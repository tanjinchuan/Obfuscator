package FYP;

import java.awt.Component;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AdvOptionsPanel extends JPanel {

	HashMap<String, Integer> currentSettings = new HashMap<String, Integer>();
	ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();

	JLabel lblEliminateUnusedFields = new JLabel("Eliminate unused fields and methods");
	JLabel lblRenameFieldsAnd = new JLabel("Rename methods");
	JLabel lblMiscellaneous = new JLabel("Miscellanous");
	JCheckBox chckbxUnusedPublic = new JCheckBox("Public");
	JCheckBox chckbxUnusedProtected = new JCheckBox("Protected");
	JCheckBox chckbxUnusedPrivate = new JCheckBox("Private");
	JCheckBox chckbxRenamePublic = new JCheckBox("Public");
	JCheckBox chckbxRenameProtected = new JCheckBox("Protected");
	JCheckBox chckbxRenamePrivate = new JCheckBox("Private");

	JCheckBox chckbxChangeVariables = new JCheckBox("Change Variable Names");
	JCheckBox chckbxRemoveWhitespace = new JCheckBox("Remove White Space");
	JCheckBox chckbxInsertDummyCode = new JCheckBox("Insert Dummy Code");
	JCheckBox chckbxRemoveComments = new JCheckBox("Remove Comments");
	JCheckBox chckbxShiftMethods = new JCheckBox("Shift Methods");

	public AdvOptionsPanel() {

		this.setLayout(null);

		/////////////////////////////////////////////////////////////////////////////////////////////////
		// for adv options
		/////////////////////////////////////////////////////////////////////////////////////////////////
		lblEliminateUnusedFields.setBounds(22, 145, 216, 16);
		this.add(lblEliminateUnusedFields);

		lblRenameFieldsAnd.setBounds(286, 145, 168, 16);
		this.add(lblRenameFieldsAnd);

		lblMiscellaneous.setBounds(509, 145, 88, 16);
		this.add(lblMiscellaneous);

		// Eliminate unused fields and methods
		chckbxUnusedPublic.setBounds(22, 170, 113, 25);
		this.add(chckbxUnusedPublic);

		chckbxUnusedProtected.setBounds(22, 200, 113, 25);
		this.add(chckbxUnusedProtected);


		chckbxUnusedPrivate.setBounds(22, 230, 113, 25);
		this.add(chckbxUnusedPrivate);

		// Rename fields and methods
		chckbxRenamePublic.setBounds(286, 170, 113, 25);
		this.add(chckbxRenamePublic);

		chckbxRenameProtected.setBounds(286, 200, 113, 25);
		this.add(chckbxRenameProtected);


		chckbxRenamePrivate.setBounds(286, 230, 113, 25);
		this.add(chckbxRenamePrivate);

		// Misc
		
		chckbxChangeVariables.setBounds(509, 170, 147, 25);
		this.add(chckbxChangeVariables);

		chckbxRemoveWhitespace.setBounds(509, 200, 147, 25);
		this.add(chckbxRemoveWhitespace);

		chckbxInsertDummyCode.setBounds(509, 230, 147, 25);
		this.add(chckbxInsertDummyCode);

		chckbxRemoveComments.setBounds(509, 260, 147, 25);
		this.add(chckbxRemoveComments);

		// temp
		chckbxShiftMethods.setBounds(509, 290, 113, 25);
		this.add(chckbxShiftMethods);

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

		btnAdvDefaultSettings.setBounds(545, 36, 189, 45);
		this.add(btnAdvDefaultSettings);


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


	public void createSettingsFile() {
        //once panel initialized, create advsettings.txt
		File file = new File("src/settings/advsettings.txt");
		if (file.exists() == false) {
			int boxID = 0;
			try {
				FileWriter fr = new FileWriter(file);
				for (JCheckBox checkbox: checkboxes) {
					fr.write(boxID + "_" + checkbox.getText() + ":0\n"); //initialze all settings to unselected
					boxID++;
				}
				fr.close();
				
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
        File file = new File("./src/settings/advSettings.txt");
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

        File settingsFile = new File("./src/settings/advsettings.txt");
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