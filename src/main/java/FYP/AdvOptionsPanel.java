package FYP;

import java.io.*;


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

        this.setLayout(null);
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
        File file2 = new File("./src/settings/easysettings.txt");
        if (file2.exists()) {
            file2.delete();
        }
    }
}