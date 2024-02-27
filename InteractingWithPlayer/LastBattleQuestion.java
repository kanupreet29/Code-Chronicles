package InteractingWithPlayer;

import View.LastBattleView;

import java.util.ArrayList;

public class LastBattleQuestion {
    public String question; // The multiple-choice question
    public ArrayList<String> options; // The options for the MCQ.
    protected String answer; // The answer to the question.
    protected String hint; // The hint for the question.


    public LastBattleQuestion(String question, ArrayList<String> options, String answer, String hint) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.hint = hint;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getHint() {
        return this.hint;
    }
}
