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
        chapter0.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson1.jpg")));
        chapter0.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson2.jpg")));
        chapter0.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson3.jpg")));
        chapter0.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson4.jpg")));
        chapter0.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson5.jpg")));
        
        ArrayList<ImageIcon> chapter1 = new ArrayList<ImageIcon>();
        chapter1.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson1.jpg")));
        chapter1.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson2.jpg")));
        chapter1.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson3.jpg")));
        chapter1.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson4.jpg")));
        chapter1.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson5.jpg")));
        
        ArrayList<ImageIcon> chapter2 = new ArrayList<ImageIcon>();
        chapter2.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson1.jpg")));
        chapter2.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson2.jpg")));
        chapter2.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson3.jpg")));
        chapter2.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson4.jpg")));
        chapter2.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson5.jpg")));
        
        ArrayList<ImageIcon> chapter3 = new ArrayList<ImageIcon>();
        chapter3.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson1.jpg")));
        chapter3.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson2.jpg")));
        chapter3.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson3.jpg")));
        chapter3.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson4.jpg")));
        chapter3.add(new ImageIcon(this.getClass().getClassLoader().getResource("lesson5.jpg")));
        
        
        this.chapters.add(chapter0);
        this.chapters.add(chapter1);
        this.chapters.add(chapter2);
        this.chapters.add(chapter3);
        
    }
    

    public ArrayList<ArrayList<ImageIcon>> getChapters() {
        return chapters;
    }
}