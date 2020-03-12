package FYP;

import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.io.*;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import com.github.javaparser.ast.expr.*;

import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;

import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.google.common.io.Files;
import com.github.javaparser.ast.Node;


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
            c.changeMethodNames("C:\\Users\\User\\Desktop\\test.java", "C:\\Users\\User\\Desktop\\testoutput.java");
            
            System.out.println("Method Names Obfuscated!");
        } catch (IOException io) {
        
        }
        //have a hash map of method names
        //change the method names of test.java


        System.out.println("end");
    }

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

        public void changeMethodNames (String inputFilePath, String outputFilePath) throws FileNotFoundException, IOException {
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

                String[] split = line.split("\\s|(?=[(])|(?<=[.])"); 
                //(eg. public void setName(String s) will split into ["public","void", "setName", "(", "String", "s", ")"])
                
                String newLine = "";
                for (int i = 0; i < split.length; i++) {
                    
                    if(hash.containsKey(split[i])) {
                        split[i] = hash.get(split[i]);
                        //changes the method name to new value name
                        //put back together the string
                    }  
                }
                for (String s: split) {
                    newLine = newLine + " " + s;
                }
                //write to new Text
                newText = newText + newLine + "\n";

            }
            fw.write(newText);
            fw.close();
            scanner.close();
        }
    }

    public void removeComments(String inputFilePath) throws FileNotFoundException, IOException {
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

    private static void copyFile(String inputDirectory, String outputDirectory) throws IOException{
        InputStream is = null;
        OutputStream os = null;

        File source = new File(inputDirectory);
        File dest = new File(outputDirectory);
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }

    }

    private static class FieldNamePrinter extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(FieldDeclaration fd, Void arg) {
            super.visit(fd, arg);
            List<VariableDeclarator> nodes = fd.getVariables();
            List<String> list = new ArrayList<String>();
            for (VariableDeclarator v: nodes) {
                list.add(v.toString());
            }
            for (String s: list) {
                System.out.println("Variable names in class: " + s);
            }
            
        }
    }
    
}

