import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quiz {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int points = 0;

        List<Question> questions = new ArrayList<>();

        questions.add(new Question("What is the capital of Sweden?\n", "1.Malmo;\n2.Stockholm;\n3.Karlskrona;\n4.Karlshamn;\n", "Stockholm"));
        questions.add(new Question("What is the capital of United Kingdom?\n", "1.London;\n2.Liverpool;\n3.Cardiff;\n4.Bradford;\n", "London"));
        questions.add(new Question("What is the capital of Ukraine?\n", "1.Lviv;\n2.Odesa;\n3.Kyiv;\n4.Kharkiv;\n", "Kyiv"));

        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Welcome " + name);

        for (Question q : questions) {
            System.out.println(q.getQuestion());
            System.out.println(q.getAnswer());
            System.out.println("Enter your answer: ");
            String answer = scanner.nextLine();
            String answerInput = scanner.nextLine();
            if (answerInput.equalsIgnoreCase(q.getCorrectAnswer())) {
                System.out.println("Correct!");
                points++;
            } else {
                System.out.println("Wrong!");
                System.out.println("Correct answer is:" + q.getCorrectAnswer());
            }

        }


        System.out.println("Quiz finished. Your score: " + points);
    }


}