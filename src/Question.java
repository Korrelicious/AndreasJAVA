public class Question {
    String question;
    String answer;
    String correctAnswer;

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
}
