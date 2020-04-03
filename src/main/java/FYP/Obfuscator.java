package FYP;

import java.util.*;


import java.io.*;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import com.github.javaparser.ast.body.Parameter;

import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;




public class Obfuscator {

    ArrayList<Statistics> statistics = new ArrayList<Statistics>();

    public void obfuscate (String inputFilePath, String outputFilePath, int difficulty) throws ParseException, FileNotFoundException, IOException{

        String code = compileCode(inputFilePath);
        switch(difficulty) {
            //difficulty 0 is method obfuscation and comments removal
            case 0: {
                code = removeComments(code);
                code = changeMethodNames(code, "All");
                code = changeInterfaceNames(code);
                code = removeWhiteSpaces(code);
                break;
            }	
            //difficulty 1 is plus variable obfuscation
            case 1: {

                code = removeComments(code);
                code = changeMethodNames(code, "All");
                code = changeInterfaceNames(code);
                code = changeVariableNames(code);
                code = changeParameterNames(code);
                code = removeWhiteSpaces(code);
                break;
            }

            

        }

    
        FileWriter fw;
        if (outputFilePath != ""){
            fw = new FileWriter(outputFilePath);

        } else {
            fw = new FileWriter(inputFilePath);
        }

        fw.write(code);
        fw.close();
    
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Advance obfuscate
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void advObfuscate(String inputFilePath, String outputFilePath, AdvOptionsPanel advOptionsPanel) throws ParseException, FileNotFoundException, IOException {

        //get the code
        String code = compileCode(inputFilePath);


        advOptionsPanel.getCurrentOptions(); //get the current check box values
        HashMap<String, Integer> settings = advOptionsPanel.getSettings(); //get the hashmap of values
        for (String key: settings.keySet()) {
            
            int value = settings.get(key);
            if (value == 1) { //if the box is ticked, do that obfuscation technique
                switch (key) {
                    case "0_Public": { //Eliminating unused public methods
                        //code = eliminatePublic(code);
                        break;
                    }

                    case "1_Protected": { //Eliminating unused protected methods
                        //code = eliminateProtected(code);
                        break;
                    }

                    case "2_Private": { //Elminating unused private methods
                        //code = eliminatePrivate(code);
                        break;
                    }

                    case "3_Public": { //Rename public methods
                        code = changeMethodNames(code, "Public");
                        break;
                    }

                    case "4_Protected": { //Rename protected methods
                        code = changeMethodNames(code, "Protected");
                        break;
                    }
                    
                    case "5_Private": { //Rename private methods
                        code = changeMethodNames(code, "Private");
                        break;
                    }

                    case "6_Change Variable Names": { //Change variable names
                        code = changeVariableNames(code);
                        break;
                    }

                    case "7_Remove White Space": { //Removing white spaces from code
                        code = removeWhiteSpaces(code);
                        break;
                    }

                    case "8_Insert Dummy Code": { //Insert dummy code
                        //code = insertDummyCode(code);
                        break;
                    }

                    case "9_Remove Comments": { //Removing comments from code
                        code = removeComments(code);
                        break;
                    }

                    case "10_Shift Methods": {
                        //code = shiftMethods(code);
                        break;
                    }
                }

                
            }

            //end of advance obfuscation
            //write to output 
            FileWriter fw;
            if (outputFilePath != ""){
                fw = new FileWriter(outputFilePath);

            } else {
                fw = new FileWriter(inputFilePath);
            }

            fw.write(code);
            fw.close();
            }
            System.out.println("ok");

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//For Methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private class MethodNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        //to get method names and put into hash map
        @Override
        public void visit(MethodDeclaration md, HashMap<String, String> hash) {

            //visit the nodes
            super.visit(md, hash);
            if (!md.getClass().isInterface() && !md.isAnnotationPresent("Override")){ //prevent changing method names that need the method to stay the same
                if (!md.getNameAsString().equals("main")){
                    String method = md.getNameAsString();
                    String newMethodName = randomWord(); 
                    hash.put(method, newMethodName);

                }
                
                //put into hash map
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private class PublicMethodNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        //to get method names and put into hash map
        @Override
        public void visit(MethodDeclaration md, HashMap<String, String> hash) {

            //visit the nodes
            super.visit(md, hash);
            if (md.isPublic() && !md.isAnnotationPresent("Override")){ 
                if (!md.getNameAsString().equals("main")){
                    String method = md.getNameAsString();
                    String newMethodName = randomWord(); 
                    hash.put(method, newMethodName);

                }
                
                //put into hash map
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private class ProtectedMethodNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        //to get method names and put into hash map
        @Override
        public void visit(MethodDeclaration md, HashMap<String, String> hash) {

            //visit the nodes
            super.visit(md, hash);
            if (md.isProtected() && !md.isAnnotationPresent("Override")){ //prevent changing method names that need the method to stay the same
                if (!md.getNameAsString().equals("main")){
                    String method = md.getNameAsString();
                    String newMethodName = randomWord(); 
                    hash.put(method, newMethodName);

                }
                
                //put into hash map
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private class PrivateMethodNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        //to get method names and put into hash map
        @Override
        public void visit(MethodDeclaration md, HashMap<String, String> hash) {

            //visit the nodes
            super.visit(md, hash);
            if (md.isPrivate() && !md.isAnnotationPresent("Override")){ //prevent changing method names that are
                if (!md.getNameAsString().equals("main")){
                    String method = md.getNameAsString();
                    String newMethodName = randomWord(); 
                    hash.put(method, newMethodName);

                }
                
                //put into hash map
            }
        }

    }

    //Function to call for changing method names using basic slider
    public String changeMethodNames (String code, String methodType)  {
        CompilationUnit cu = StaticJavaParser.parse(code);
        String newCode = "";
        
        //initialize method visitor
        HashMap<String, String> methods = new HashMap<String, String>();

        //if empty, do all method names
        if (methodType.equals("All")) {

            VoidVisitor<HashMap<String, String>> methodNameVisitor = new MethodNameVisitor();
            methodNameVisitor.visit(cu, methods);
    
        }
        //if public
        else if (methodType.equals("Public")) {
            
            VoidVisitor<HashMap<String, String>> methodNameVisitor = new PublicMethodNameVisitor();
            methodNameVisitor.visit(cu, methods);

        }
        //if protected
        else if (methodType.equals("Protected")) {
            
            VoidVisitor<HashMap<String, String>> methodNameVisitor = new ProtectedMethodNameVisitor();
            methodNameVisitor.visit(cu, methods);

        }
        //if private
        else if (methodType.equals("Private")) {
            
            VoidVisitor<HashMap<String, String>> methodNameVisitor = new PrivateMethodNameVisitor();
            methodNameVisitor.visit(cu, methods);

        }

        //initialize method statistics
        Statistics stats = new Statistics();
        stats.setType("Method");


        Scanner scanner = new Scanner(code);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("import")) {
                newCode = newCode + line + "\n";

                continue;
            }
            String[] split = line.split("\\s|(?=[(])|(?<=[.])"); 
            //(eg. public void setName(String s) will split into ["public","void", "setName", "(", "String", "s", ")"])
            
            String newLine = "";

            //loop to check whether each seperated word is the method name, if it is, change to the new method name
            for (int i = 0; i < split.length; i++) {
                if(methods.containsKey(split[i])) {

                    stats.setStats(split[i], methods.get(split[i]));
                    stats.increaseCount(split[i]); // save the number of times method changed
                    //save the names to statistics for printing

                    //changes the method name to new value name
                    split[i] = methods.get(split[i]);

                }  
            }

            //loop to add the code back into the line
            for (int i = 0; i < split.length; i++) {
                
                newLine = newLine + split[i] + " ";
                
            }
            
            //add the line into the new text of code
            newCode = newCode + newLine + "\n";
            
        }
        statistics.add(stats);

        scanner.close();
        return refactorCode(newCode);

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//For Parameters
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class ParameterNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        
        @Override
        public void visit(Parameter p, HashMap<String, String> parameters) {
            super.visit(p, parameters);
            String parameter = p.getNameAsString();

            String newWord = randomWord();
            if (!parameter.equals("args")){
                parameters.put(parameter, newWord);
            }

        }
    }

    public String changeParameterNames(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        HashMap<String, String> parameters = new HashMap<String, String>();

        VoidVisitorAdapter<HashMap<String, String>> parameterNameVisitor = new ParameterNameVisitor();
        parameterNameVisitor.visit(cu, parameters); //saves the variables into hashmap


        //initialize parameter statistics
        Statistics stats = new Statistics();
        stats.setType("Parameter");
        
        
        Scanner scanner = new Scanner(code);

        //to store the new code with new names
        String newCode = "";
        
        //loop each line of code in file
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (line.startsWith("import")) {
                newCode = newCode + line + "\n";

                continue;
            }
            String[] split = line.split("\\s|(?<=\\()|(?=\\))|(?=[;])|(?=[.])|(?<=[.])|(^?<=[!])|(?=!)|(?<=[,])|(?=[,])");
            String newLine = "";
            for (int i = 0; i < split.length; i++){
                if (parameters.containsKey(split[i])){

                    //save the names for printing later
                    stats.setStats(split[i], parameters.get(split[i]));
                    stats.increaseCount(split[i]); // save the number of times method changed

                    split[i] = parameters.get(split[i]); //set the variable name to new random word
                }
                
            }

            for (int i = 0; i < split.length; i++){
                //put together back the line
                newLine = newLine + split[i] + " ";
            }

            //put together the code
            newCode = newCode + newLine + "\n";
        }
            

        statistics.add(stats);
        scanner.close();

    
        return refactorCode(newCode);
        
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//For Variables
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

    public String changeVariableNames(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        HashMap<String, String> variables = new HashMap<String, String>();

        VoidVisitorAdapter<HashMap<String, String>> variableNameVisitor = new VariableNameVisitor();
        variableNameVisitor.visit(cu, variables); //saves the variables into hashmap

        //initialize variable statistics
        Statistics stats = new Statistics();
        stats.setType("Variable");

        

        Scanner scanner = new Scanner(code);

        //to store the new code with new names
        String newCode = "";
        
        //loop each line of code in file
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (line.startsWith("import")) {
                newCode = newCode + line + "\n";

                continue;
            }
            String[] split = line.split("\\s|(?<=\\()|(?=\\))|(?=[;])|(?=[.])|(?<=[.])|(^?<=[!])|(?=!)|(?<=[,])|(?=[,])|(?=\\+)\\b");
            String newLine = "";
            for (int i = 0; i < split.length; i++){
                if (variables.containsKey(split[i])){

                    //save the names to statistics for printing
                    stats.setStats(split[i], variables.get(split[i])); 
                    stats.increaseCount(split[i]); // save the number of times method changed

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

        statistics.add(stats);

    
        return refactorCode(newCode);
        
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private class InterfaceVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        @Override
        public void visit(ClassOrInterfaceDeclaration c, HashMap<String, String> interfaceNames) {
            super.visit(c, null);
            if (c.isInterface() == true) {
                interfaceNames.put(c.getNameAsString(), randomWord());
            } 
        }
    }

    public String changeInterfaceNames(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        HashMap<String, String> interfaceNames = new HashMap<String, String>();


        VoidVisitorAdapter<HashMap<String, String>> InterfaceVisitor = new InterfaceVisitor();

        InterfaceVisitor.visit(cu, interfaceNames);
        
        //initialize interface statistics
        Statistics stats = new Statistics();
        stats.setType("Interface");

        Scanner scanner = new Scanner(code);
        String newCode = "";

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (line.startsWith("import")) {
                newCode = newCode + line + "\n";
                continue;
            }
            String[] split = line.split("\\s|(?<=\\()|(?=\\))|(?=[;])|(?=[.])|(?<=[.])|(^?<=[!])|(?=!)|(?<=[,])|(?=[,])");
            String newLine = "";
            for (int i = 0; i < split.length; i++){
                if (interfaceNames.containsKey(split[i])){

                    //save the names to statistics
                    stats.setStats(split[i], interfaceNames.get(split[i])); 
                    stats.increaseCount(split[i]); // save the number of times method changed

                    split[i] = interfaceNames.get(split[i]); //set the variable name to new random word
                    
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

        statistics.add(stats);
        return refactorCode(newCode);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //to use for printing all the stats in the frame.java
    public String getStatistics() {
        String text = "";

        for (Statistics s: statistics) {
            text = text + s.printStats();
        }
        return text;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

        StaticJavaParser.getConfiguration().setAttributeComments(false);
        CompilationUnit cu = StaticJavaParser.parse(code);

        code = cu.toString();
        return code;
           
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private String randomWord() {
        char[] letters = new char[] {'1', 'I', 'l'};
        Random rand = new Random();
        String newWord = "";
        int wordLength = 12;

        char [] firstLetters = new char[] {'l', 'I'};
            //initialize first random character for new method name, first character cannot use '1'

            //to initialize first charcter of new method name with either l or I
            int random = rand.nextInt(2);
            char firstChar = firstLetters[random];
            newWord = newWord + firstChar;
            
            //loop to generate random method name (eg. l1l1lll1IIl)
            for (int i = 0; i < wordLength; i++) {
                int randomIndex = rand.nextInt(3);
                char letter = letters[randomIndex];
                newWord = newWord + letter;
            }
        return newWord;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    public String compileCode(String inputFilePath) throws ParseException, FileNotFoundException{
        String code = "";
        
        CompilationUnit cu = StaticJavaParser.parse(new File(inputFilePath));
        code = cu.toString();
    
        return code;

    }

    public String removeWhiteSpaces(String code) {
        code = code.replaceAll("\\s", " ");
        System.out.println("Removing unecessary whitespaces");
        return code;
    }

}





