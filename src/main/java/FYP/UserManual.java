package FYP;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class UserManual {
    private ArrayList<ArrayList<ImageIcon>> userManual = new ArrayList<ArrayList<ImageIcon>>();

    public UserManual() {
        loadImages();
    }
    private void loadImages() {
        
        // ArrayList<ImageIcon> part1 = new ArrayList<ImageIcon>();
        // part1.add(new ImageIcon(this.getClass().getClassLoader().getResource("manual1.jpg")));
        // this.userManual.add(part1);
    }

    public ArrayList<ArrayList<ImageIcon>> getUserManual() {
        return this.userManual;
    }
}