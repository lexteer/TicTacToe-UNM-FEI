package si.unm_fei.logic;

import si.unm_fei.core.*;
import java.util.*;

public class QuestionManager {
    private ArrayList<Question> remainingQuestions;
    private Random random = new Random();

    public QuestionManager(Kategorija kategorija) {
        remainingQuestions = new ArrayList<> ( QuestionLoader.load().get(kategorija));
    }

    public Question getRandomQuestion() {

        if (remainingQuestions.isEmpty()) {
            return null; // ali vrzi exception, ali reset
        }
        int index = random.nextInt(remainingQuestions.size());
        return remainingQuestions.remove(index);
    }
}

