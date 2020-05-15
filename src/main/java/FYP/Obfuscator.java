package FYP;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;

import com.github.javaparser.ParseException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import com.github.javaparser.ast.body.Parameter;

import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.mgnt.utils.StringUnicodeEncoderDecoder;


public class Obfuscator {

    ArrayList<Statistics> statistics = new ArrayList<Statistics>();
    protected String sourceCode = "";
    protected String fileName = "";

    public void obfuscate(String inputFilePath, String outputFilePath, int difficulty, String selectedClass)
            throws ParseException, FileNotFoundException, IOException {
        String code = compileCode(inputFilePath);
        switch (difficulty) {
            // Weak difficulty
            case 0: {
                code = changeClassName(code, selectedClass);
                code = removeComments(code);
                code = changeMethodNames(code, "All");
                code = changeInterfaceNames(code);
                code = removeWhiteSpaces(code);
                break;
            }
            // Medium difficulty
            case 1: {

                code = changeClassName(code, selectedClass);
                code = removeComments(code);
                code = changeMethodNames(code, "All");
                code = changeInterfaceNames(code);
                code = changeVariableNames(code);
                code = changeParameterNames(code);
                code = removeWhiteSpaces(code);
                break;
            }

            // Strong difficulty
            case 2: {

                code = changeClassName(code, selectedClass);
                code = removeComments(code);
                code = changeMethodNames(code, "All");
                code = changeInterfaceNames(code);
                code = changeVariableNames(code);
                code = changeParameterNames(code);
                code = removeWhiteSpaces(code);

                //add string encoding and libraries and decrypt method
                code = stringEncoding(code);
                code = addLibraries(code);
                code = addDecryptMethod(code);  

                break;
            }

            // Extreme difficulty
            case 3: {
                // insert dummy code first
                code = dummyCodeInsertion(code);
                code = stringEncoding(code);
                code = addLibraries(code);
                code = addDecryptMethod(code);


                code = changeClassName(code, selectedClass);
                code = removeComments(code);
                code = changeMethodNames(code, "All");
                code = changeInterfaceNames(code);
                code = changeVariableNames(code);
                code = changeParameterNames(code);
                code = removeWhiteSpaces(code);

            }

        }

        saveObfuscatedCode(code, outputFilePath);

    }

 

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Advance obfuscate
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void advObfuscate(String inputFilePath, String outputFilePath, HashMap<String, Integer> settings,
            String selectedClass) throws ParseException, FileNotFoundException, IOException {

        // get the code
        String code = compileCode(inputFilePath);


        //if change class names not chosen, the file name will be saved as original source code class name
        if (settings.get("3_Change Class Names") == 0) {
            this.fileName = "_" + selectedClass + ".java";
        }

        // do dummy code insertion first if user wants it, as we have to obfuscate the
        // dummy code as well.
        if (settings.get("6_Insert Dummy Code") == 1) {
            code = dummyCodeInsertion(code);
        }

        

        // do string encoding first if user wants it
        if (settings.get("7_String Encoding") == 1) {
            code = stringEncoding(code);
            code = addLibraries(code);
            code = addDecryptMethod(code);
        }

        // do string to unicode before string encoding, if user ticked both encoding and
        // unicode,
        // the unicode wil be encrypted too
        if (settings.get("8_String To Unicode") == 1) {
            code = stringToUnicode(code);
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
                        code = changeClassName(code, selectedClass);
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

                    case "10_Remove Comments": { // Removing comments from code
                        code = removeComments(code);
                        break;
                    }

                }

            }

        }

        // because my methods make the code neat, Removing white spaces must put at end
        // if user wants it
        if (settings.get("9_Remove White Space") == 1) {
            code = removeWhiteSpaces(code);
        } else {
            code = prettyPrinting(code);
        }

        saveObfuscatedCode(code, outputFilePath);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Dummy Code Insertion
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class StatementAdder extends VoidVisitorAdapter<HashMap<String, String>>{
        int count = 0;

        //creates a random variable declaration
        public String randomVariableDeclarator(int count){

            //Create Variable name with count
            String varName = "dummyCode" + count;
            String dataType = "";
            String initialize = "";
            int bound = 0;

            //Randomize a Data Type e.g. int, float etc
            Random rand = new Random();
            int ranNum = rand.nextInt(8);

            //Get initializer and DataType to String
            switch(ranNum){
                //Just in case
                default:
                case 0:
                    dataType = "boolean";
                    initialize = String.valueOf(rand.nextBoolean());
                    break;
                case 1:
                    dataType = "byte";
                    bound = rand.nextInt(255) - 128;
                    initialize = String.valueOf(bound);
                    break;
                case 2:
                    dataType = "char";
                    char c = (char)(rand.nextInt(26) + 'a');
                    initialize = "'" + String.valueOf(c) + "'";
                    break;
                case 3:
                    dataType = "short";
                    bound = rand.nextInt(65535) - 32768;
                    initialize = String.valueOf(bound);
                    break;
                case 4:
                    dataType = "int";
                    initialize = String.valueOf(rand.nextInt());
                    break;
                case 5:
                    dataType = "long";
                    initialize = String.valueOf(rand.nextLong()) + "L";
                    break;
                case 6:
                    dataType = "float";
                    initialize = String.valueOf(rand.nextFloat()) + "f";
                    break;
                case 7:
                    dataType = "double";
                    initialize = String.valueOf(rand.nextDouble()) + "d";
                    break;
            }

            String varDeclare = dataType + " " + varName + "= " + initialize + ";";
            return varDeclare;


        }

        @Override
        public void visit(BlockStmt bs, HashMap<String, String> hashMap){
            super.visit(bs, hashMap);

            AtomicInteger hash = new AtomicInteger(bs.getRange().hashCode());

            //Only even hashes add dummycode
            if(hash.get() %2 == 0){
                //add Variables
                NodeList<Statement> meep = bs.getStatements();
                meep.add(0, StaticJavaParser.parseStatement(randomVariableDeclarator(count)));
                bs.setStatements(meep);
                hashMap.put(meep.get(0).toString(), ""); 

                count++;
            }
        }
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


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // change class name
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class ClassVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        @Override
        public void visit(ClassOrInterfaceDeclaration c, HashMap<String, String> classes) {
            super.visit(c, classes);

            classes.put(c.getNameAsString(), randomWord());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    // String Literal Visitor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private class StringLiteralEncodeVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        @Override
        public void visit(StringLiteralExpr ex, HashMap<String, String> hash) {

            // create encrypted literal
            String encrypted = encrypt(ex.toString().substring(1, ex.toString().length() - 1));
            encrypted = "_D(\"" + encrypted + "\")";
            hash.put(ex.toString(), encrypted);

        }
    }
    private class StringLiteralToUnicodeVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        @Override
        public void visit(StringLiteralExpr ex, HashMap<String, String> hash) {

            String stringLiteral = ex.toString();

            //remove double quotes (e.g. "FooBar" to FooBar) 
            if (stringLiteral.length() > 0) {

                stringLiteral = stringLiteral.substring(1, stringLiteral.length() - 1);
                String unicode = StringUnicodeEncoderDecoder.encodeStringToUnicodeSequence(stringLiteral);
                unicode = "\"" + unicode + "\"";
                hash.put(ex.toString(), unicode);
    
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
            if (line.startsWith("import") | line.startsWith("package")) {
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
            if (line.startsWith("import") | line.startsWith("package")) {
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
            if (line.startsWith("import") | line.startsWith("package")) {
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
            if (line.startsWith("import") | line.startsWith("package")) {
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

    private String changeClassName(String code, String selectedClass) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        HashMap<String, String> classNames = new HashMap<String, String>();

        VoidVisitorAdapter<HashMap<String, String>> classVisitor = new ClassVisitor();

        classVisitor.visit(cu, classNames);

        // set selectedClass as file name and obfuscate it, to put it as new file name
        this.fileName = classNames.get(selectedClass) + ".java";

        // initialize class name statistics
        Statistics stats = new Statistics();
        stats.setType("Class");

        Scanner scanner = new Scanner(code);
        String newCode = "";

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("import") | line.startsWith("package")) {
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

    


    private String dummyCodeInsertion(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);

        VoidVisitorAdapter<HashMap<String, String>> dummyVisitor = new StatementAdder();

        HashMap<String, String> hashMap = new HashMap<String, String>();
        
        // initialize dummy statistics
        Statistics stats = new Statistics();
        stats.setType("Dummy Code");

        dummyVisitor.visit(cu, hashMap);

        


        for (String s: hashMap.keySet()) {
            
            stats.setStats(s, "");
        }

        statistics.add(stats);

        return cu.toString();
    }


    private String stringEncoding(String code) {

        CompilationUnit cu = StaticJavaParser.parse(code);
        HashMap<String, String> stringLiterals = new HashMap<String, String>();

        VoidVisitorAdapter<HashMap<String, String>> stringVisitor = new StringLiteralEncodeVisitor();

        stringVisitor.visit(cu, stringLiterals);

        // initialize class name statistics
        Statistics stats = new Statistics();
        stats.setType("String Literals");

        String newCode = "";

        Scanner scanner = new Scanner(code);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("import") | line.startsWith("package")) {

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
                if (stringLiterals.containsKey(split.get(i))) {

                    // save the names for printing later
                    stats.setStats(split.get(i), stringLiterals.get(split.get(i)));
                    stats.increaseCount(split.get(i)); // save the number of times method changed

                    // set the variable name to new random word
                    split.set(i, stringLiterals.get(split.get(i)));
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

    private String encrypt(String value) {
        String key = "Bar12345Bar12345";
        String initVector = "RandomInitVector";

        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    private String addLibraries(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);

        String[] libraries = { "javax.crypto.Cipher", "javax.crypto.spec.IvParameterSpec",
                "javax.crypto.spec.SecretKeySpec", "java.util.Base64"};

        for (String s : libraries) {
            cu.addImport(s);
        }
        return cu.toString();
    }

    private String addDecryptMethod(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);

        String methodBody = "{String key = \"Bar12345Bar12345\";" + "String initVector = \"RandomInitVector\";" +

                "try {" + "IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(\"UTF-8\"));"
                + "SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(\"UTF-8\"), \"AES\");" +

                "Cipher cipher = Cipher.getInstance(\"AES/CBC/PKCS5PADDING\");"
                + "cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);" +

                "byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));" +

                "return new String(original);" + "} catch (Exception ex) {" + "ex.printStackTrace();" + "}"
                + "return null;}";

        for (Node childNode : cu.getChildNodes()) {
            if (childNode instanceof ClassOrInterfaceDeclaration) {

                ClassOrInterfaceDeclaration classDeclaration = (ClassOrInterfaceDeclaration) childNode;

                MethodDeclaration method = classDeclaration.addMethod("_D", Keyword.PUBLIC, Keyword.STATIC);
                method.setType(String.class);
                method.addParameter(String.class, "encrypted");
                BlockStmt blockStmt = StaticJavaParser.parseBlock(methodBody);
                method.setBody(blockStmt);

            }
        }
        return cu.toString();
    }

    
    

    private String stringToUnicode(String code) {
        CompilationUnit cu = StaticJavaParser.parse(code);
        VoidVisitor<HashMap<String, String>> stringLiteralToUnicodeVisitor = new StringLiteralToUnicodeVisitor();

        HashMap<String, String> stringLiterals = new HashMap<String, String>();

        stringLiteralToUnicodeVisitor.visit(cu, stringLiterals);

        
        // initialize class name statistics
        Statistics stats = new Statistics();
        stats.setType("String Literals");

        String newCode = "";

        Scanner scanner = new Scanner(code);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("import") | line.startsWith("package")) {

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
                if (stringLiterals.containsKey(split.get(i))) {

                    // save the names for printing later
                    stats.setStats(split.get(i), stringLiterals.get(split.get(i)));
                    stats.increaseCount(split.get(i)); // save the number of times method changed

                    // set the variable name to new random word
                    split.set(i, stringLiterals.get(split.get(i)));
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
    private void saveObfuscatedCode(String code, String outputFilePath) {
        FileWriter fw;
        try {
            fw = new FileWriter(outputFilePath + "\\" + getFileName());
            
            fw.write(code);
            fw.close();
        } catch (IOException e) {

        }
    }

    public String getFileName() {
        return this.fileName;
    }
    public String compileCode(String inputFilePath) throws ParseException, FileNotFoundException {

        CompilationUnit cu = StaticJavaParser.parse(new File(inputFilePath));

        this.sourceCode = cu.toString();
        return cu.toString();

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

    

    // for combo box to display classes of parsed java file
    public String[] getClasses() {
        ArrayList<String> list = new ArrayList<String>();
        CompilationUnit cu = StaticJavaParser.parse(this.sourceCode);
        List<Node> childNodes = cu.getChildNodes();
        for (Node n : childNodes) {
            if (n instanceof ClassOrInterfaceDeclaration) {
                ClassOrInterfaceDeclaration c = (ClassOrInterfaceDeclaration) n;
                list.add(c.getNameAsString());
                System.out.println(c.getNameAsString() + " hello");
            }
        }

        // convert to array
        String[] classNames = list.toArray(new String[list.size()]);
        return classNames;
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

    

    private String removeWhiteSpaces(String code) {
        code = prettyPrinting(code); // make nice nice first
        code = code.trim().replaceAll("\\s+", " ");
        System.out.println("Removing unecessary whitespaces");
        return code;
    }

    
    

}





