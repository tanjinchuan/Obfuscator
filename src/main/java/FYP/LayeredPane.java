package FYP;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.awt.*;

@SuppressWarnings("serial")

public class LayeredPane extends JLayeredPane {
//function for next/back btns
    
    public LayeredPane() {
        
        this.setBounds(0, 0, 1024, 500);
		this.setLayout(new CardLayout(0, 0));
    }

    public void addPanel(JPanel panel) {
        this.add(panel);
    }
    public void switchPanel(JPanel panel) {
        this.removeAll();
        this.add(panel);
        this.repaint();
        this.revalidate();
    }

}