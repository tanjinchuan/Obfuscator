package FYP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class Sample {


    public static void main(String[] args) {
        try {

            CompilationUnit cu = StaticJavaParser.parse(new File("C:\\Users\\User\\Desktop\\Person.java"));
            VoidVisitor<HashMap<String, String>> visitor = new StringLiteralVisitor();
            HashMap<String, String> hashMap = new HashMap<String, String>();
            visitor.visit(cu, hashMap);

            for (String s: hashMap.keySet()) {
                System.out.println(hashMap.get(s));
            }

        } catch (FileNotFoundException fe) {
            
        }
    }


    private static class StringLiteralVisitor extends VoidVisitorAdapter<HashMap<String, String>> {
        @Override
        public void visit(StringLiteralExpr ex, HashMap<String, String> hashMap) {
            
            //create encrypted literal
            String encrypted = encrypt(ex.toString().substring(1, ex.toString().length()-1));
            encrypted = "decrypt(\"" + encrypted + "\")"; 
            hashMap.put(ex.toString(), encrypted);
            
        } 
    }

    private static String encrypt(String value) {
        String key = "Bar12345Bar12345";
        String initVector = "RandomInitVector";

        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
           
            return DatatypeConverter.printBase64Binary(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    
}

