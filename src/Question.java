public class Question {
    String question;
    String answer;
    String correctAnswer;

    public Question(String question, String answer, String correctAnswer) {
        this.question = question;
        this.answer = answer;
        this.correctAnswer = "";
    }
    public String getQuestion() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }
}
