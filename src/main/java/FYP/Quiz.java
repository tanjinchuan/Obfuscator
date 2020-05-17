package FYP;

import java.util.ArrayList;

public class Quiz {
    private ArrayList<Questions> quizList = new ArrayList<Questions>();

    Questions q1 = new Questions("What is code obfuscation?",
        new String[] {" a) Modifying an executable so that it is useful to a third party but is unfunctional",
        " b) Maintaining an executable readable and easy to be reverse engineered",
        " c) Modifying an executable so that it is no longer useful to a third party but still fully functional",
        " d) The process of running the code on and off a platform"}, "c");
        
    Questions q2 = new Questions("Which of the following are types of obfuscation?\n" +
        "1) Dummy Code Insertion\n" +
        "2) Code Refactoring\n" +
        "3) Comment Removal\n" +
        "4) Removal of unused variables",
        new String[] {
            "a) 1,2",
            "b) 1,3", 
            "c) 2,4",
            "d) All of the above"}, "b");

    Questions q3 = new Questions("What is Anti-Tempering?",
        new String[] {"a) Inserting an application to detect the manipulation of file, resulting in crash or limit of functionality.",
        "b) Making sure the source code is not as fragile as Tempered Glass.",
        "c) Generate code to allow ease of access to threats",
        "d) Inserting gibberish code into executable file which does not affect the code logic."}, "a");

    Questions q4 = new Questions("Below is a sample of a code before and after obfuscation. What type of obfuscation was used in this code?",

        new String[] {"a) Rename Obfuscation",
        "b) String Encryption",
        "c) Dummy Code insertion",
        "d) Watermarking"}, "a");
    
 
    Questions q5 = new Questions("Which of the following is NOT advantage of obfuscation?",

        new String[] {"a) I am able to protect my code from people attempting to abuse it.",
        "b) It is easier to reverse engineer source code.",
        "c) Hackers have higher difficulty sidestepping the limitations enforced by licensing.",
        "d) Applications will be less vulnerable to threats."}, "b");

    Questions q6 = new Questions("Which of the following is a disadvantage of obfuscation",

    new String[] {"a) Hackers will take a long time to reverse engineer obfuscated code",
    "b)	Tempered code will be able to be detected by owners faster.",
    "c) Renaming of variables create higher obscurity.",
    "d) Performance will be affected by higher levels of obfuscation."}, "d");



    public Quiz() {
        this.quizList.add(q1);
        this.quizList.add(q2);
        this.quizList.add(q3);
        this.quizList.add(q4);
        this.quizList.add(q5);
        this.quizList.add(q6);
        
    }

    

    public String getQuestion(int index) {
        String question = quizList.get(index).getQuestion();
        return question;
    }


    public boolean checkAnswer(int index, String ans) {
        String answer = quizList.get(index).getAnswer();
        if (ans.equals(answer)){
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<Questions> getQuizList() {
        return quizList;
    }

    
}