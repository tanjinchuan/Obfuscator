package FYP;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import com.github.javaparser.ast.body.Parameter;

import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class Obfuscator {

    ArrayList<Statistics> statistics = new ArrayList<Statistics>();

    public void obfuscate(String inputFilePath, String outputFilePath, int difficulty)
            throws ParseException, FileNotFoundException, IOException {

        String code = compileCode(inputFilePath);
        switch (difficulty) {
            // difficulty 0 is method obfuscation and comments removal
            case 0: {
                code = removeComments(code);
                code = changeMethodNames(code, "All");
                code = changeInterfaceNames(code);
                code = removeWhiteSpaces(code);
                break;
            }
            // difficulty 1 is plus variable obfuscation
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
        if (outputFilePath != "") {
            fw = new FileWriter(outputFilePath);

        } else {
            fw = new FileWriter(inputFilePath);
        }

        fw.write(code);
        fw.close();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Advance obfuscate
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void advObfuscate(String inputFilePath, String outputFilePath, AdvSettingsPanel advSettingsPanel)
            throws ParseException, FileNotFoundException, IOException {

        // get the code
        String code = compileCode(inputFilePath);

        advSettingsPanel.getCurrentOptions(); // get the current check box values
        HashMap<String, Integer> settings = advSettingsPanel.getSettings(); // get the hashmap of values

        //do dummy code insertion first if user wants it, as we have to obfuscate the dummy code as well.
        if (settings.get("7_Insert Dummy Code") == 1) {
            //code = insertDummyCode(code);
        }
        
        for (String key : settings.keySet()) {
            
            int value = settings.get(key);
            if (value == 1) { // if the box is ticked, do that obfuscation technique
                switch (key) {
                    

                    case "0_Public": { // Rename public methods
                        code = changeMethodNames(code, "Public");
                        break;
                    }

                    case "1_Protected": { // Rename protected methods
                        code = changeMethodNames(code, "Protected");
                        break;
                    }

                    case "2_Private": { // Rename private methods
                        code = changeMethodNames(code, "Private");
                        break;
                    }

                    case "3_Change Class Names": {
                        code = changeClassName(code);
                        break;
                    }

                    case "4_Change Variable Names": { // Change variable names
                        code = changeVariableNames(code);
                        break;
                    }

                    case "5_Change Parameter Names": { // Change parameter names
                        code = changeParameterNames(code);
                        break;
                    }

                    case "6_Remove White Space": { // Removing white spaces from code
                        break;
                    }

                    case "7_Insert Dummy Code": { // Insert dummy code
                        break;
                    }

                    case "8_Remove Comments": { // Removing comments from code
                        code = removeComments(code);
                        break;
                    }

                    case "9_Flow Obfuscation": {
                        // code = flowObfuscate(code);
                        break;
                    }
                }

            }

        }

        //because my methods make the code neat, Removing white spaces must put at end if user wants it
        if (settings.get("6_Remove White Space") == 1) { 
            code = removeWhiteSpaces(code);
        } else {
            code = prettyPrinting(code);
        }
        // end of advance obfuscation
        // write to output
        FileWriter fw;
        if (outputFilePath != "") {
            fw = new FileWriter(outputFilePath);

        } else {
            fw = new FileWriter(inputFilePath);
        }

        fw.write(code);
        fw.close();
        System.out.println("ok");

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Methods
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class MethodNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        // to get method names and put into hash map
        @Override
        public void visit(MethodDeclaration md, HashMap<String, String> hash) {

            // visit the nodes
            super.visit(md, hash);
            if (!md.getClass().isInterface() && !md.isAnnotationPresent("Override")) {
                if (!md.getNameAsString().equals("main")) {
                    String method = md.getNameAsString();
                    String newMethodName = randomWord();
                    hash.put(method, newMethodName);

                }

                // put into hash map
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class PublicMethodNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        // to get method names and put into hash map
        @Override
        public void visit(MethodDeclaration md, HashMap<String, String> hash) {

            // visit the nodes
            super.visit(md, hash);
            if (md.isPublic() && !md.isAnnotationPresent("Override")) {
                if (!md.getNameAsString().equals("main")) {
                    String method = md.getNameAsString();
                    String newMethodName = randomWord();
                    hash.put(method, newMethodName);

                }

                // put into hash map
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class ProtectedMethodNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        // to get method names and put into hash map
        @Override
        public void visit(MethodDeclaration md, HashMap<String, String> hash) {

            // visit the nodes
            super.visit(md, hash);
            if (md.isProtected() && !md.isAnnotationPresent("Override")) { // prevent changing method names that need
                                                                           // the method to stay the same
                if (!md.getNameAsString().equals("main")) {
                    String method = md.getNameAsString();
                    String newMethodName = randomWord();
                    hash.put(method, newMethodName);

                }

                // put into hash map
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class PrivateMethodNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        // to get method names and put into hash map
        @Override
        public void visit(MethodDeclaration md, HashMap<String, String> hash) {

            // visit the nodes
            super.visit(md, hash);
            if (md.isPrivate() && !md.isAnnotationPresent("Override")) { // prevent changing method names that are
                if (!md.getNameAsString().equals("main")) {
                    String method = md.getNameAsString();
                    String newMethodName = randomWord();
                    hash.put(method, newMethodName);

                }

                // put into hash map
            }
        }

    }

    // Function to call for changing method names using basic slider, methodType =
    // e.g "Public"
    private String changeMethodNames(String code, String methodType) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        String newCode = "";

        // initialize method visitor
        HashMap<String, String> methods = new HashMap<String, String>();

        // if empty, do all method names
        if (methodType.equals("All")) {

            VoidVisitor<HashMap<String, String>> methodNameVisitor = new MethodNameVisitor();
            methodNameVisitor.visit(cu, methods);

        }
        // if public
        else if (methodType.equals("Public")) {

            VoidVisitor<HashMap<String, String>> methodNameVisitor = new PublicMethodNameVisitor();
            methodNameVisitor.visit(cu, methods);

        }
        // if protected
        else if (methodType.equals("Protected")) {

            VoidVisitor<HashMap<String, String>> methodNameVisitor = new ProtectedMethodNameVisitor();
            methodNameVisitor.visit(cu, methods);

        }
        // if private
        else if (methodType.equals("Private")) {

            VoidVisitor<HashMap<String, String>> methodNameVisitor = new PrivateMethodNameVisitor();
            methodNameVisitor.visit(cu, methods);

        }

        // initialize method statistics
        Statistics stats = new Statistics();
        stats.setType("Method");

        Scanner scanner = new Scanner(code);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("import")) {
                newCode = newCode + line + "\n";

                continue;
            }

            List<String> split = new ArrayList<String>();
            Pattern regex = Pattern.compile("(\"[^\"]*\")|\\W|\\w+");
            Matcher regexMatcher = regex.matcher(line);
            while (regexMatcher.find()) {
                split.add(regexMatcher.group());
            } 
            
            // (eg. public void setName(String s) will split into ["public","void",
            // "setName", "(", "String", "s", ")"])

            String newLine = "";

            // loop to check whether each seperated word is the method name, if it is,
            // change to the new method name
            for (int i = 0; i < split.size(); i++) {
                if (methods.containsKey(split.get(i))) {

                    stats.setStats(split.get(i), methods.get(split.get(i)));
                    stats.increaseCount(split.get(i)); // save the number of times method changed
                    // save the names to statistics for printing

                    // changes the method name to new value name
                    split.set(i, methods.get(split.get(i)));
                }
            }

            // loop to add the code back into the line
            for (int i = 0; i < split.size(); i++) {

                newLine = newLine + split.get(i);

            }

            // add the line into the new text of code
            newCode = newCode + newLine + "\n";

        }
        statistics.add(stats);

        scanner.close();
        return newCode;

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Parameters
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class ParameterNameVisitor extends VoidVisitorAdapter<HashMap<String, String>> {

        @Override
        public void visit(Parameter p, HashMap<String, String> parameters) {
            super.visit(p, parameters);
            String parameter = p.getNameAsString();

            String newWord = randomWord();
            if (!parameter.equals("args")) {
                parameters.put(parameter, newWord);
            }

        }
    }

    private String changeParameterNames(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        HashMap<String, String> parameters = new HashMap<String, String>();

        VoidVisitorAdapter<HashMap<String, String>> parameterNameVisitor = new ParameterNameVisitor();
        parameterNameVisitor.visit(cu, parameters); // saves the parameters into hashmap

        // initialize parameter statistics
        Statistics stats = new Statistics();
        stats.setType("Parameter");

        Scanner scanner = new Scanner(code);

        // to store the new code with new names
        String newCode = "";

        // loop each line of code in file
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("import")) {
                newCode = newCode + line + "\n";

                continue;
            }            
            String newLine = "";

            List<String> split = new ArrayList<String>();
            Pattern regex = Pattern.compile("(\"[^\"]*\")|\\W|\\w+");
            Matcher regexMatcher = regex.matcher(line);
            while (regexMatcher.find()) {
                split.add(regexMatcher.group());
            } 

            for (int i = 0; i < split.size(); i++) {
                if (parameters.containsKey(split.get(i))) {

                    // save the names for printing later
                    stats.setStats(split.get(i), parameters.get(split.get(i)));
                    stats.increaseCount(split.get(i)); // save the number of times method changed

                     // set the variable name to new random word
                    split.set(i, parameters.get(split.get(i)));
                }

            }

            for (int i = 0; i < split.size(); i++) {
                // put together back the line
                newLine = newLine + split.get(i);
            }

            // put together the code
            newCode = newCode + newLine + "\n";
        }

        statistics.add(stats);
        scanner.close();

        return newCode;

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Variables
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

    private String changeVariableNames(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        HashMap<String, String> variables = new HashMap<String, String>();

        VoidVisitorAdapter<HashMap<String, String>> variableNameVisitor = new VariableNameVisitor();
        variableNameVisitor.visit(cu, variables); // saves the variables into hashmap

        // initialize variable statistics
        Statistics stats = new Statistics();
        stats.setType("Variable");

        Scanner scanner = new Scanner(code);

        // to store the new code with new names
        String newCode = "";

        // loop each line of code in file
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("import")) {
                newCode = newCode + line + "\n";

                continue;
            } 
            String newLine = "";
            List<String> split = new ArrayList<String>();
            Pattern regex = Pattern.compile("(\"[^\"]*\")|\\W|\\w+");
            Matcher regexMatcher = regex.matcher(line);
            while (regexMatcher.find()) {
                split.add(regexMatcher.group());
            } 

            for (int i = 0; i < split.size(); i++) {
                if (variables.containsKey(split.get(i))) {

                    // save the names for printing later
                    stats.setStats(split.get(i), variables.get(split.get(i)));
                    stats.increaseCount(split.get(i)); // save the number of times method changed

                     // set the variable name to new random word
                    split.set(i, variables.get(split.get(i)));
                }

            }

            for (int i = 0; i < split.size(); i++) {
                // put together back the line
                newLine = newLine + split.get(i);
            }

            // put together the code
            newCode = newCode + newLine + "\n";
        }

        scanner.close();

        statistics.add(stats);

        return newCode;

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

    private String changeInterfaceNames(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        HashMap<String, String> interfaceNames = new HashMap<String, String>();

        VoidVisitorAdapter<HashMap<String, String>> InterfaceVisitor = new InterfaceVisitor();

        InterfaceVisitor.visit(cu, interfaceNames);

        // initialize interface statistics
        Statistics stats = new Statistics();
        stats.setType("Interface");

        Scanner scanner = new Scanner(code);
        String newCode = "";

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("import")) {
                newCode = newCode + line + "\n";
                continue;
            }            
            String newLine = "";

            List<String> split = new ArrayList<String>();
            Pattern regex = Pattern.compile("(\"[^\"]*\")|\\W|\\w+");
            Matcher regexMatcher = regex.matcher(line);
            while (regexMatcher.find()) {
                split.add(regexMatcher.group());
            } 

            for (int i = 0; i < split.size(); i++) {
                if (interfaceNames.containsKey(split.get(i))) {

                    // save the names for printing later
                    stats.setStats(split.get(i), interfaceNames.get(split.get(i)));
                    stats.increaseCount(split.get(i)); // save the number of times method changed

                     // set the variable name to new random word
                    split.set(i, interfaceNames.get(split.get(i)));
                }

            }

            for (int i = 0; i < split.size(); i++) {
                // put together back the line
                newLine = newLine + split.get(i);
            }

            // put together the code
            newCode = newCode + newLine + "\n";
        }

        scanner.close();

        statistics.add(stats);
        return newCode;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //change class name
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class ClassVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        @Override
        public void visit(ClassOrInterfaceDeclaration c, HashMap<String, String> classes) {
            super.visit(c, classes);
            classes.put(c.getNameAsString(), randomWord());
        }
    }

    private String changeClassName(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        HashMap<String, String> classNames = new HashMap<String, String>();

        VoidVisitorAdapter<HashMap<String, String>> classVisitor = new ClassVisitor();

        classVisitor.visit(cu, classNames);
        
        
        // initialize class name statistics
        Statistics stats = new Statistics();
        stats.setType("Class");

        Scanner scanner = new Scanner(code);
        String newCode = "";

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("import")) {
                newCode = newCode + line + "\n";
                continue;
            }            
            String newLine = "";

            List<String> split = new ArrayList<String>();
            Pattern regex = Pattern.compile("(\"[^\"]*\")|\\W|\\w+");
            Matcher regexMatcher = regex.matcher(line);
            while (regexMatcher.find()) {
                split.add(regexMatcher.group());
            } 

            for (int i = 0; i < split.size(); i++) {
                if (classNames.containsKey(split.get(i))) {

                    // save the names for printing later
                    stats.setStats(split.get(i), classNames.get(split.get(i)));
                    stats.increaseCount(split.get(i)); // save the number of times method changed

                     // set the variable name to new random word
                    split.set(i, classNames.get(split.get(i)));
                }

            }

            for (int i = 0; i < split.size(); i++) {
                // put together back the line
                newLine = newLine + split.get(i);
            }

            // put together the code
            newCode = newCode + newLine + "\n";
        }

        scanner.close();

        statistics.add(stats);
        return newCode;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // to use for printing all the stats in the frame.java
    public String getStatistics() {
        String text = "";

        for (Statistics s : statistics) {
            text = text + s.printStats();
        }
        return text;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // help remove unecessary spaces in code
    private String prettyPrinting(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        String cleanCode = cu.toString();
        return cleanCode;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Remove of comments
    private String removeComments(String code) {

        StaticJavaParser.getConfiguration().setAttributeComments(false);
        CompilationUnit cu = StaticJavaParser.parse(code);

        code = cu.toString();
        return code;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String randomWord() {
        char[] letters = new char[] { '1', 'I', 'l' };
        Random rand = new Random();
        String newWord = "";
        int wordLength = 12;

        char[] firstLetters = new char[] { 'l', 'I' };
        // initialize first random character for new method name, first character cannot
        // use '1'

        // to initialize first charcter of new method name with either l or I
        int random = rand.nextInt(2);
        char firstChar = firstLetters[random];
        newWord = newWord + firstChar;

        // loop to generate random method name (eg. l1l1lll1IIl)
        for (int i = 0; i < wordLength; i++) {
            int randomIndex = rand.nextInt(3);
            char letter = letters[randomIndex];
            newWord = newWord + letter;
        }
        return newWord;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String compileCode(String inputFilePath) throws ParseException, FileNotFoundException {
        String code = "";
        StaticJavaParser.getConfiguration().setLexicalPreservationEnabled(true);
        CompilationUnit cu = StaticJavaParser.parse(new File(inputFilePath));
        code = cu.toString();

        return code;

    }

    // this is for printing during comparison
    public String printCode(String inputFilePath) {
        File file = new File(inputFilePath);
        String code = "";
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                code = code + scanner.nextLine() + "\n";
            }
            scanner.close();
        } catch (FileNotFoundException fe) {

        }

        return code;

    }

    
    private String removeWhiteSpaces(String code) {
        code = prettyPrinting(code); // make nice nice first
        code = code.trim().replaceAll("\\s+", " ");
        System.out.println("Removing unecessary whitespaces");
        return code;
    }




    // // /*not done yet
    // private class ClassVisitor extends VoidVisitorAdapter<Void> {
    //     @Override
    //     public void visit(ClassOrInterfaceDeclaration c, Void arg) {
    //         super.visit(c, arg);
    //         //add dummy methods here
    //         BlockStmt blockStmt = new BlockStmt();
            
			
    //         blockStmt.addStatement(new ExpressionStmt(new NameExpr("String firstName = \"John\"")));
            
    //         blockStmt.addStatement(new ExpressionStmt(new NameExpr("String secondName = \"Mary\"")));
    //         c.addMethod("getNames", Keyword.PRIVATE).setBody(
    //             blockStmt
                
    //         );

    //     }  
    // }

    // public String insertDummyCode(String code) {
    //     CompilationUnit cu = StaticJavaParser.parse(code);

    //     VoidVisitorAdapter<?> classvisitor = new ClassVisitor();
    //     classvisitor.visit(cu, null);
        

    //     String newCode = cu.toString();
    //     return newCode;
    // }
    
}





