package FYP;


class Sample {
    public static void main(String[] args) {
        

            // Obfuscator obfuscator = new Obfuscator();
            // obfuscator.obfuscate("C:\\Users\\User\\Desktop\\test.java", "C:\\Users\\User\\Desktop\\finaltest.java", 1);
        String test = "testString";
        String s = "for (int i = 0; i < 10; i++)";
        String[] split = s.split("\\s|(?<=\\()|(?=\\))|(?=[;])|(?=[.])|(?<=[.])|(^?<=[!])|(?=!)|(?<=[,])|(?=[,])|(?=\\+)\\b");
        for (String a: split){
            System.out.println(a);
        }
    }
}