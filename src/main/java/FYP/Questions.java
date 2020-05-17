package FYP;

class Questions {

    private String question;
    private String answer;
    private String[] options; // to show the amount of options for the question

    public Questions(String ques, String[] answers, String ans){
        this.question = ques;
        this.answer = ans;
        this.options = answers;
    }

    public String getAnswer() {
        return answer;
    }
    public String getQuestion() {
        return question;
    }
    public String[] getOptions() {
        return options;
    }

    
}