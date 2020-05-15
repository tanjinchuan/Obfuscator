package FYP;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;


public class ComparePanel extends JPanel {

    public ComparePanel(JFrame frame, Obfuscator obfuscator, String inputFilePath, String outputFilePath) {

        
        String originalText = "";
        String obfuscatedText = "";
        originalText = obfuscator.printCode(inputFilePath);
            
        obfuscatedText = obfuscator.printCode(outputFilePath);

        
        JPanel comparePanel = new JPanel();
                
        //Text area for original code
        JTextArea originalTextArea = new JTextArea(originalText); 
        originalTextArea.setLineWrap(true);  
        originalTextArea.setWrapStyleWord(true); 
        originalTextArea.setEditable(false);
        JScrollPane originalScrollPane = new JScrollPane(originalTextArea); 
        originalScrollPane.setPreferredSize(new Dimension(500, 600));
        
        //Text area for obfuscated code
        JTextArea obfuscatedTextArea = new JTextArea(obfuscatedText);
        obfuscatedTextArea.setLineWrap(true);  
        obfuscatedTextArea.setWrapStyleWord(true);  

        obfuscatedTextArea.setEditable(false);
        
        JScrollPane obfuscatedScrollPane = new JScrollPane(obfuscatedTextArea);  
        obfuscatedScrollPane.setPreferredSize(new Dimension(500, 600));
        
        comparePanel.add(originalScrollPane);
        comparePanel.add(obfuscatedScrollPane);
        
        UIManager.put("OptionPane.minimumSize",new Dimension(700,700)); 
        JOptionPane.showConfirmDialog(frame, comparePanel, "Comparing files", JOptionPane.CLOSED_OPTION , JOptionPane.PLAIN_MESSAGE, null);
        
    
    }
}