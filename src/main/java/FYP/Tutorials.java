package FYP;

import java.util.ArrayList;


class Tutorials {

    private ArrayList<String> tutorials = new ArrayList<String>();

    public Tutorials () {
        addTutorialImages();
    }
    private void addTutorialImages() {
        tutorials.add("lesson1.jpg");
        tutorials.add("lesson2.jpg");
        tutorials.add("lesson3.jpg");
        tutorials.add("lesson4.jpg");
        tutorials.add("lesson5.jpg");
        
        
    }

    public ArrayList<String> getTutorialImages() {
        return tutorials;
    }
}