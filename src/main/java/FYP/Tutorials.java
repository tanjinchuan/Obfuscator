package FYP;

import java.util.ArrayList;

import javax.swing.ImageIcon;


class Tutorials {

    private ArrayList<ArrayList<ImageIcon>> chapters = new ArrayList<ArrayList<ImageIcon>>();
    
    public Tutorials() {
        loadTutorialImages();
    }
    private void loadTutorialImages() {
        ArrayList<ImageIcon> chapter0 = new ArrayList<ImageIcon>();
        chapter0.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide1.PNG")));
        chapter0.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide2.PNG")));
        chapter0.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide3.PNG")));
        
        ArrayList<ImageIcon> chapter1 = new ArrayList<ImageIcon>();
        chapter1.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide4.PNG")));
        chapter1.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide5.PNG")));
        chapter1.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide6.PNG")));
        
        ArrayList<ImageIcon> chapter2 = new ArrayList<ImageIcon>();
        chapter2.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide7.PNG")));
        chapter2.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide8.PNG")));
        chapter2.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide9.PNG")));
        chapter2.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide10.PNG")));
        chapter2.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide11.PNG")));
        chapter2.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide12.PNG")));
        
        ArrayList<ImageIcon> chapter3 = new ArrayList<ImageIcon>();
        chapter3.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide13.PNG")));
        chapter3.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide14.PNG")));
        chapter3.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide15.PNG")));
        chapter3.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide16.PNG")));
        chapter3.add(new ImageIcon(this.getClass().getClassLoader().getResource("Slide17.PNG")));
        
        this.chapters.add(chapter0);
        this.chapters.add(chapter1);
        this.chapters.add(chapter2);
        this.chapters.add(chapter3);
        
    }
    

    public ArrayList<ArrayList<ImageIcon>> getChapters() {
        return chapters;
    }
}