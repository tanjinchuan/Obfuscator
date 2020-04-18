package FYP;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.javaparser.ParseException;

public class Sample {


    public static void main(String[] args) {
        Obfuscator obfuscator = new Obfuscator();
        


            try {
                String code = "for(int i = 0; i < 10; i++) { \"String boyy boy\" Person p1 = new Person(); \"003 2532\"";
                String code2 = obfuscator.compileCode("C:\\Users\\User\\Desktop\\Person.java");
                //[\\s\"']|\"[^\"]*\"|'[^']*'
                //\"([^\"]+)\"
                //String[] split = code.split("[^\\s\"']+|\"[^\"]*\"|'[^']*'|(?=[A-Za-z0-9])|(?<=[A-Za-z0-9])");
                
                List<String> matchList = new ArrayList<String>();
                Pattern regex = Pattern.compile("(\"[^\"]*\")|\\W|\\w+");
                Matcher regexMatcher = regex.matcher(code2);
                while (regexMatcher.find()) {
                    matchList.add(regexMatcher.group());
                } 
                for (String s: matchList) {
                    System.out.println(s);
                }
            } catch (IOException | ParseException e) {
                
            }
            
    
    }
}