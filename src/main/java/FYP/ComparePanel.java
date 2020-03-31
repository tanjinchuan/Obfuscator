package FYP;


import java.awt.Dimension;
import java.awt.GridLayout;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.github.javaparser.ParseException;

public class ComparePanel extends JPanel {

    public ComparePanel(JFrame frame, Obfuscator obfuscator, String inputFilePath, String outputFilePath) {
        String originalText = "";
        String obfuscatedText = "";
        try {
            originalText = obfuscator.compileCode(inputFilePath);
            obfuscatedText = obfuscator.compileCode(outputFilePath);


        } catch (ParseException pe) {
            JOptionPane.showMessageDialog(frame, "Something wrong with your .java File, please check for syntax error", 
                    inputFilePath, JOptionPane.OK_OPTION);
            
        } catch (FileNotFoundException fe) {
            JOptionPane.showMessageDialog(frame, "Invalid File Path", inputFilePath, JOptionPane.OK_OPTION);
            
        }
        
        JPanel comparePanel = new JPanel(new GridLayout(1, 2));
                
        //Text area for original code
        JTextArea originalTextArea = new JTextArea(originalText); 
        originalTextArea.setLineWrap(true);  
        originalTextArea.setWrapStyleWord(true); 
        originalTextArea.setEditable(false);
        JScrollPane originalScrollPane = new JScrollPane(originalTextArea); 
        originalScrollPane.setPreferredSize(new Dimension(500, 500));
        
        //Text area for obfuscated code
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
}