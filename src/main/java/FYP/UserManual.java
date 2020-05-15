package FYP;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class UserManual {
    private ArrayList<ImageIcon> userManual = new ArrayList<ImageIcon>();

    public UserManual() {
        loadImages();
    }
    private void loadImages() {
        this.userManual.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson1.jpg")));
        this.userManual.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson2.jpg")));
        this.userManual.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson3.jpg")));
        this.userManual.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson4.jpg")));
        this.userManual.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson5.jpg")));
        
    }

    public ArrayList<ImageIcon> getUserManual() {
        return this.userManual;
    }
}