package FYP;

import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;
import java.io.*;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;


import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;



public class Obfuscator {
    // private static final String inputFilePath = "C:\\Users\\User\\Desktop\\test.java";
    // private static final String newFilePath = "C:\\Users\\User\\Desktop\\testoutput.java";
    private static HashMap<String, String> hash = new HashMap<String, String>();

    public static void main(String[] args) {
        
        //save the method names into hash map
        ChangeMethodName c = new ChangeMethodName();
        try {
            System.out.println("Obfuscating Method Names...");
            
            //hardcoded file path, use your own to test
            changeMethodNames("C:\\Users\\User\\Desktop\\test.java", "C:\\Users\\User\\Desktop\\testoutput.java");
            System.out.println("Method Names Obfuscated!");

            
            
            refactorCode("C:\\Users\\User\\Desktop\\testoutput.java"); //help remove unnecessary spaces, make code look nice
            System.out.println("Refactor complete");

        } catch (IOException io) {
        
        }
        //have a hash map of method names
        //change the method names of test.java


        System.out.println("end");
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class ChangeMethodName extends VoidVisitorAdapter<Void> {
        char [] letters = new char[] {'l', 'I', '1'};
        Random rand = new Random();


        //set of the new word to change to
        int numberofLetters = 10;


        //to get method names and put into hash map
        @Override
        public void visit(MethodDeclaration md, Void arg) {

            //visit the nodes
            super.visit(md, arg);
            String s = md.getNameAsString();
            if (!s.equals("main")) { //cannot change main method name
                String newMethodName = ""; 

                
                char [] firstLetters = new char[] {'l', 'I'};
                //initialize first random character for new method name, first character cannot use '1'

                //to initialize first charcter of new method name with either l or I
                int random = rand.nextInt(2);
                char firstChar = firstLetters[random];
                newMethodName = newMethodName + firstChar;
                
                //loop to generate random method name (eg. l1l1lll1IIl)
                for (int i = 0; i < numberofLetters; i++) {
                    int randomIndex = rand.nextInt(3);
                    char letter = letters[randomIndex];
                    newMethodName = newMethodName + letter;
                }
                //put into hash map
                hash.put(s, newMethodName);

            }
            
        }

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Change method names
        public static void changeMethodNames (String inputFilePath, String outputFilePath) throws FileNotFoundException, IOException {
            CompilationUnit cu = StaticJavaParser.parse(new File(inputFilePath));
            String newText = "";
            
            File inputFile = new File(inputFilePath);

            VoidVisitor<?> methodNameVisitor = new ChangeMethodName();
            methodNameVisitor.visit(cu, null);

            Scanner scanner = new Scanner(inputFile);

            //for output file
            File outputFile = new File(outputFilePath);
            FileWriter fw = new FileWriter(outputFile, false);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] split = line.split("\\[s]|\\s|(?=[(])|(?<=[.])"); 
                //(eg. public void setName(String s) will split into ["public","void", "setName", "(", "String", "s", ")"])
                
                String newLine = "";

                //loop to check whether each seperated word is the method name, if it is, change to the new method name
                for (int i = 0; i < split.length; i++) {
                    if(hash.containsKey(split[i])) {
                        split[i] = hash.get(split[i]);
                        //changes the method name to new value name
                    }  
                }

                //loop to add the code back into the line
                for (int i = 0; i < split.length; i++) {
                    
                    newLine = newLine + split[i] + " ";
                    
                }
                
                //add the line into the new text of code
                newText = newText + newLine + "\n";
                
            }
            //write the whole code into a new output file
            fw.write(newText);
            fw.close();
            scanner.close();
            
        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //help remove unecessary spaces in code
    public static void refactorCode(String inputFilePath) throws FileNotFoundException, IOException{
        CompilationUnit cu = StaticJavaParser.parse(new File(inputFilePath));
        File outputFile = new File(inputFilePath);
        FileWriter fw = new FileWriter(outputFile, false);
        fw.write(cu.toString());
        fw.close();

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Remove of comments
    public static void removeComments(String inputFilePath) throws FileNotFoundException, IOException {
        System.out.println("Removing Comments");
        CompilationUnit cu = StaticJavaParser.parse(new File(inputFilePath));

        for (Comment c : cu.getComments()){
            System.out.println(c);
            c.remove();
        }
        File inputFile = new File(inputFilePath);
        String code = cu.toString();
        FileWriter fw = new FileWriter(inputFile, false);
        fw.write(code);
        fw.close();
        
    }


}

