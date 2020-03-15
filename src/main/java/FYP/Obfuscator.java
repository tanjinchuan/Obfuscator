package FYP;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;
import java.io.*;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;



public class Obfuscator {
    // private static final String inputFilePath = "C:\\Users\\User\\Desktop\\test.java";
    // private static final String newFilePath = "C:\\Users\\User\\Desktop\\testoutput.java";

    public static void main(String[] args) {
        
        //save the method names into hash map
        // ChangeMethodName c = new ChangeMethodName();
        // System.out.println("Obfuscating Method Names...");
            
        // //hardcoded file path, use your own to test
        // changeMethodNames("C:\\Users\\User\\Desktop\\test.java", "C:\\Users\\User\\Desktop\\testoutput.java");
        // System.out.println("Method Names Obfuscated!");

            
            
        // refactorCode("C:\\Users\\User\\Desktop\\testoutput.java"); //help remove unnecessary spaces, make code look nice
        // System.out.println("Refactor complete");

        //have a hash map of method names
        //change the method names of test.java

        VoidVisitor<?> visitor = new ChangeVariableName();
        try {
            CompilationUnit cu = StaticJavaParser.parse(new File("C:\\Users\\User\\Desktop\\testoutput.java"));
            visitor.visit(cu, null);

        } catch(FileNotFoundException fe){

        }
        
        System.out.println("end");
    }

    public static class ChangeVariableName extends VoidVisitorAdapter<HashMap<String, String>> {

        @Override
        public void visit(VariableDeclarator vd, HashMap<String, String> variables) {
            super.visit(vd, variables);

            String newWord = randomWord();
            String variable = vd.toString();
            variables.put(variable, newWord);
        }
    }

   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class ChangeMethodName extends VoidVisitorAdapter<HashMap<String, String>> {
        //to get method names and put into hash map
        @Override
        public void visit(MethodDeclaration md, HashMap<String, String> hash) {

            //visit the nodes
            super.visit(md, hash);
            String s = md.getNameAsString();
            if (!s.equals("main")) { //cannot change main method name
                String newMethodName = randomWord(); 

                //put into hash map
                hash.put(s, newMethodName);

            }
            
        }

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Change method names
        public static void changeMethodNames (String inputFilePath, String outputFilePath)  {
            try {
                CompilationUnit cu = StaticJavaParser.parse(new File(inputFilePath));
                String newText = "";
                
                File inputFile = new File(inputFilePath);
                HashMap<String, String> hash = new HashMap<String, String>();
                VoidVisitor<HashMap<String, String>> methodNameVisitor = new ChangeMethodName();
                methodNameVisitor.visit(cu, hash);

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
                
            } catch (FileNotFoundException fe) {
                System.out.println(fe.getMessage());
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
            
        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //help remove unecessary spaces in code
    public static void refactorCode(String inputFilePath) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(new File(inputFilePath));
            File outputFile = new File(inputFilePath);
            FileWriter fw = new FileWriter(outputFile, false);
            fw.write(cu.toString());
            fw.close();
    
        } catch (FileNotFoundException fe) {
            System.out.println(fe.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Remove of comments
    public static void removeComments(String inputFilePath) {
        System.out.println("Removing Comments");
        try {
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
             
        } catch (FileNotFoundException fe){
            System.out.println(fe.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
           
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private static String randomWord() {
        char[] letters = new char[] {'1', 'I', '1'};
        Random rand = new Random();
        String newWord = "";
        int length = 10;

        char [] firstLetters = new char[] {'l', 'I'};
            //initialize first random character for new method name, first character cannot use '1'

            //to initialize first charcter of new method name with either l or I
            int random = rand.nextInt(2);
            char firstChar = firstLetters[random];
            newWord = newWord + firstChar;
            
            //loop to generate random method name (eg. l1l1lll1IIl)
            for (int i = 0; i < length; i++) {
                int randomIndex = rand.nextInt(3);
                char letter = letters[randomIndex];
                newWord = newWord + letter;
            }
        return newWord;
    }
}




