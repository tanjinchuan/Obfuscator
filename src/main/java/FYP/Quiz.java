package FYP;

import java.util.ArrayList;

public class Quiz {
    private ArrayList<Questions> quizList = new ArrayList<Questions>();

    Questions q1 = new Questions("Which of the following code is used to retrieve method names?\n" +
    "a) MethodNames\n" +
    "b) MethodDeclaration\n" +
    "c) MethodsDeclarator\n" +
    "d) MethodsDefinition\n", "b", 4);
    
    Questions q2 = new Questions("Obfuscation is used to make source code as unreadable as possible. True or False?\n" +
    "a) True\n" + 
    "b) False\n", "a", 2);

    Questions q3 = new Questions("What is 1 + 2?\n" + 
    "a) 3\n" + 
    "b) 4\n" + 
    "c) 5\n" + 
    "d) 1", "a", 4);


    public Quiz() {
        this.quizList.add(q1);
        this.quizList.add(q2);
        this.quizList.add(q3);
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