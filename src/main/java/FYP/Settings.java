package FYP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Settings {
    
    private HashMap<String, Integer> advSettings = new HashMap<String, Integer>();


    public void readSettingsFile(String settingsFilePath) throws IOException {
        File file = new File(settingsFilePath);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(":");
                //Unusedpublic:0
                advSettings.put(split[0], Integer.parseInt(split[1]));
                
            }
            scanner.close();
        } catch (FileNotFoundException fe) {
            //if no file, create file
            File newfile = new File ("./src/settings/advsettings.txt");
            FileWriter fr = new FileWriter(newfile);

                
            newfile.createNewFile();
            //Eliminate unused fields and methods
        
                fr.write("UnusedPublic:0\n");
                fr.write("UnusedProtected:0\n");
                fr.write("UnusedPackage:0\n");
                fr.write("UnusedPrivate:0\n");
            
            
            //Rename fields and methods
                fr.write("RenamePublic:0\n");
                fr.write("RenameProtected:0\n");
                fr.write("RenamePackage:0\n");
                fr.write("RenamePrivate:0\n");
            
            //Misc
                fr.write("RemoveWhitespace:0\n");
                fr.write("InsertDummyCode:0\n");
                fr.write("RemoveComments:0\n");
                fr.write("ShiftMethods:0\n");
                fr.close();

        } 
            
        
    }
}