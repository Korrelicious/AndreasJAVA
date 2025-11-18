import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Quiz {

    public static void main(String[] args) {

        int points = 0;

        List<Question> questions = new ArrayList<Question>();

        questions.add(new Question("What is the capital of Sweden?\n", "1.Malmo;\n 2.Stockholm;\n 3.Karlskrona;\n 4.Karlshamn; \n", "2.Stockholm\n"));
        questions.add(new Question("What is the capital of United Kingdom?\n", "1.London;\n 2.Liverpool;\n 3.Cardiff;\n 4.Bradford;\n", "1.London\n"));
        questions.add(new Question("What is the capital of Ukraine?\n", "1.Lviv;\n 2.Odesa;\n 3.Kyiv;\n 4.Kharkiv\n", "3.Kyiv\n"));
        System.out.println(questions);
    }
}