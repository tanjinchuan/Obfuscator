package FYP;


class Sample {
    public static void main(String[] args) {
        
        Obfuscator obfuscator = new Obfuscator();
        obfuscator.obfuscate("C:\\Users\\User\\Desktop\\test.java", "C:\\Users\\User\\Desktop\\finaltest.java", 1);
        
        // String test = "testString";
        // String s = "System.out.println";
        // String[] split = s.split("\\s|(?<=\\()|(?=\\))|(?=[;])|(?=[.])|(?<=[.])|(?<=[!])");
        // for (String a: split){
        //     System.out.println(a);
        // }
    }
}