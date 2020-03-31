package FYP;

import java.io.*;

import java.io.FileWriter;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;




@SuppressWarnings("serial")
public class AdvOptionsPanel extends JPanel {
    JLabel lblEliminateUnusedFields = new JLabel("Eliminate unused fields and methods");
    JLabel lblRenameFieldsAnd = new JLabel("Rename fields and methods");
    JLabel lblMiscellaneous = new JLabel("Miscellanous");
    JCheckBox chckbxUnusedPublic = new JCheckBox("Public");
    JCheckBox chckbxUnusedProtected = new JCheckBox("Protected");
    JCheckBox chckbxUnusedPackage = new JCheckBox("Package");
    JCheckBox chckbxUnusedPrivate = new JCheckBox("Private");
    JCheckBox chckbxRenamePublic = new JCheckBox("Public");
    JCheckBox chckbxRenameProtected = new JCheckBox("Protected");
    JCheckBox chckbxRenamePackage = new JCheckBox("Package");
    JCheckBox chckbxRenamePrivate = new JCheckBox("Private");
    JCheckBox chckbxRemoveWhitespace = new JCheckBox("Remove white space");
    JCheckBox chckbxInsertDummyCode = new JCheckBox("Insert dummy code");
    JCheckBox chckbxRemoveComments = new JCheckBox("Remove comments");
    JCheckBox chckbxShiftMethods = new JCheckBox("Shift methods");

    public AdvOptionsPanel() {

        
		/////////////////////////////////////////////////////////////////////////////////////////////////
		//for adv options
		/////////////////////////////////////////////////////////////////////////////////////////////////
		lblEliminateUnusedFields.setBounds(22, 145, 216, 16);
		this.add(lblEliminateUnusedFields);
		
		lblRenameFieldsAnd.setBounds(286, 145, 168, 16);
		this.add(lblRenameFieldsAnd);
		
		lblMiscellaneous.setBounds(509, 145, 88, 16);
		this.add(lblMiscellaneous);
		
		//Eliminate unused fields and methods
		chckbxUnusedPublic.setBounds(22, 170, 113, 25);
		this.add(chckbxUnusedPublic);
		
		chckbxUnusedProtected.setBounds(22, 200, 113, 25);
		this.add(chckbxUnusedProtected);
		
		chckbxUnusedPackage.setBounds(22, 230, 113, 25);
		this.add(chckbxUnusedPackage);
		
		chckbxUnusedPrivate.setBounds(22, 260, 113, 25);
		this.add(chckbxUnusedPrivate);
		
		
		//Rename fields and methods
		chckbxRenamePublic.setBounds(286, 170, 113, 25);
		this.add(chckbxRenamePublic);
		
		chckbxRenameProtected.setBounds(286, 200, 113, 25);
		this.add(chckbxRenameProtected);
		
		chckbxRenamePackage.setBounds(286, 230, 137, 25);
		this.add(chckbxRenamePackage);
		
		chckbxRenamePrivate.setBounds(286, 260, 113, 25);
		this.add(chckbxRenamePrivate);
		
		//Misc
		chckbxRemoveWhitespace.setBounds(509, 170, 147, 25);
		this.add(chckbxRemoveWhitespace);
		
		chckbxInsertDummyCode.setBounds(509, 200, 147, 25);
		this.add(chckbxInsertDummyCode);
		
		chckbxRemoveComments.setBounds(509, 230, 147, 25);
		this.add(chckbxRemoveComments);
		
		//temp
		chckbxShiftMethods.setBounds(509, 260, 113, 25);
		this.add(chckbxShiftMethods);
		
		
    }


    public void writeSettings() {
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
}