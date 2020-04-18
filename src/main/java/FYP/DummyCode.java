package FYP;

import java.io.IOException;

import com.github.javaparser.ParseException;

public class DummyCode {
    public static void main(String[] args) {
        Obfuscator obfuscator = new Obfuscator();
        try {
            String code = obfuscator.compileCode("C:\\Users\\User\\Desktop\\Person.java");
            obfuscator.insertDummyCode(code);

        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }


}