package FYP;

class Questions {

    private String question;
    private String answer;
    private int options; // to show the amount of options for the question

    public Questions(String ques, String ans, int num){
        this.question = ques;
        this.answer = ans;
        this.options = num;
    }

    public String getAnswer() {
        return answer;
    }
    public String getQuestion() {
        return question;
    }
    public int getOptions() {
        return options;
    }

    
}