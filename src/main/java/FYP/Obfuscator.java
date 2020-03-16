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
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;



public class Obfuscator {
    
    
    // public static void main(String[] args) {
    //     try {
    //         CompilationUnit cu = StaticJavaParser.parse(new File("C:\\Users\\User\\Desktop\\test.java"));

    //         FileWriter fw = new FileWriter("C:\\Users\\User\\Desktop\\frametest.java");
    //         String code = cu.toString();
    //         code = removeComments(code);
    //         code = changeVariableNames(code);
    //         code = changeMethodNames(code);
    //         code = refactorCode(code);
    //         fw.write(code);
    //         fw.close();
    //     } catch (FileNotFoundException fe) {

    //     } catch (IOException e) {
            
    //     }
    // }
    public void obfuscate (String inputFilePath, String outputFilePath, int difficulty) {

        String code = compileCode(inputFilePath);
        switch(difficulty) {
            //difficulty 0 is method obfuscation and comments removal
            case 0: {
                code = removeComments(code);
                code = changeMethodNames(code);
                
                break;
            }	
            //difficulty 1 is plus variable obfuscation
            case 1: {

                code = removeComments(code);
                code = changeMethodNames(code);
                code = changeVariableNames(code);
                break;
            }
            
            
        }
        try {
            FileWriter fw;
            if (outputFilePath != ""){
                fw = new FileWriter(outputFilePath);
    
            } else {
                fw = new FileWriter(inputFilePath);
            }
    
            fw.write(code);
            fw.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        
    
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class VariableNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {

        @Override
        public void visit(VariableDeclarator vd, HashMap<String, String> variables) {
            super.visit(vd, variables);
            String newWord = randomWord();
            String variable = vd.getNameAsString();
            variables.put(variable, newWord);
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String changeVariableNames(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        HashMap<String, String> variables = new HashMap<String, String>();

        VoidVisitorAdapter<HashMap<String, String>> variableNameVisitor = new VariableNameVisitor();
        variableNameVisitor.visit(cu, variables); //saves the variables into hashmap


        Scanner scanner = new Scanner(code);

        //to store the new code with new names
        String newCode = "";
        
        //loop each line of code in file
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            
            String[] split = line.split("\\s|(?=[;])|(?=[,])|(?<=[(])|(?=[)])|(?<=[.])|(?=[.])");
            String newLine = "";
            for (int i = 0; i < split.length; i++){
                if (variables.containsKey(split[i])){
                    split[i] = variables.get(split[i]); //set the variable name to new random word

                }
                
            }

            for (int i = 0; i < split.length; i++){
                //put together back the line
                newLine = newLine + split[i] + " ";
            }

            //put together the code
            newCode = newCode + newLine + "\n";
        }
            
        scanner.close();

    
        return refactorCode(newCode);
        
    
        
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class MethodNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        //to get method names and put into hash map
        @Override
        public void visit(MethodDeclaration md, HashMap<String, String> hash) {

            //visit the nodes
            super.visit(md, hash);
            if (!md.isAnnotationPresent("Override")){ //prevent changing method names that need the method to stay the same
                String s = md.getNameAsString();
                if (!s.equals("main")) { //cannot change main method name
                    String newMethodName = randomWord(); 
    
                    //put into hash map
                    hash.put(s, newMethodName);
    
                }
            }
        }

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Change method names
    public String changeMethodNames (String code)  {
        CompilationUnit cu = StaticJavaParser.parse(code);
        String newCode = "";
        
        HashMap<String, String> methods = new HashMap<String, String>();
        VoidVisitor<HashMap<String, String>> methodNameVisitor = new MethodNameVisitor();
        methodNameVisitor.visit(cu, methods);

        Scanner scanner = new Scanner(code);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] split = line.split("\\[s]|\\s|(?=[(])|(?<=[.])"); 
            //(eg. public void setName(String s) will split into ["public","void", "setName", "(", "String", "s", ")"])
            
            String newLine = "";

            //loop to check whether each seperated word is the method name, if it is, change to the new method name
            for (int i = 0; i < split.length; i++) {
                if(methods.containsKey(split[i])) {
                    split[i] = methods.get(split[i]);
                    //changes the method name to new value name
                }  
            }

            //loop to add the code back into the line
            for (int i = 0; i < split.length; i++) {
                
                newLine = newLine + split[i] + " ";
                
            }
            
            //add the line into the new text of code
            newCode = newCode + newLine + "\n";
            
        }
        
        scanner.close();
        return refactorCode(newCode);

    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //help remove unecessary spaces in code
    public String refactorCode(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        String cleanCode = cu.toString();
        return cleanCode;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Remove of comments
    public String removeComments(String code) {
        System.out.println("Removing Comments");
        CompilationUnit cu = StaticJavaParser.parse(code);

        for (Comment c : cu.getComments()){
            c.remove();
        }
        
        code = cu.toString();
        return code;
           
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private String randomWord() {
        char[] letters = new char[] {'1', 'I', '1'};
        Random rand = new Random();
        String newWord = "";
        int length = 12;

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
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    public String compileCode(String inputFilePath){
        String code = "";
        try {
            CompilationUnit cu = StaticJavaParser.parse(new File(inputFilePath));
            code = cu.toString();
        } catch (FileNotFoundException fe) {
            System.out.println(fe.toString());
        }
        return code;

    }
    public void writeFile(String outputFilePath) {

    }

}




