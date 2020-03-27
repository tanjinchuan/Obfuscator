package FYP;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;





class Lessons {

    
    public ArrayList<String> getTutorialImages(String textFile) {
        ArrayList<String> lessons = new ArrayList<String>();
        
        File file = new File(textFile);

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                    
                lessons.add(line);  //add the lesson into arraylist
               
            }
            scanner.close();
        } catch (FileNotFoundException fe) {

        }
        return lessons;
    }
}