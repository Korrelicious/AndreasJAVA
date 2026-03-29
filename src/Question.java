public class Question {
    private String question;
    private String answer;
    private String correctAnswer;

    public Question(String question, String answer, String correctAnswer) {
        this.question = question;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
    }
    public String getQuestion() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    @Override
    public String toString() {
        return question + answer + "The correct answer is " + correctAnswer;
    }
}
