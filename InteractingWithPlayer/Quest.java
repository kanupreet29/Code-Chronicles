package InteractingWithPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import InteractingWithPlayer.NonPlayerCharacters.Prowler;

public class Quest implements Serializable {
    public String questName; // The quest's name.
    public String questQuestion; // The quest's multiple-choice question.
    public ArrayList<String> questionOptions; // The options for the MCQ.
    protected String questAnswer; // The answer to the question.
    protected String questHint; // The hint for the question.
    public Prowler prowler; // The prowler who proposes this question.
    private boolean withHint; // did the player solve the question with a hint?
    private boolean withAnswer; // did the player have to reveal the answer?
    private boolean ifWon; // did the player win the quest?

    /**
     * Quest Constructor
     *
     * @param name the quest's name
     * @param question the quest's question
     * @param options the quest's question's options
     * @param answer the answer to the question
     * @param hint the hint for the question
     * @param prowler the prowler who proposes this question
     */
    public Quest(String name, String question, ArrayList<String>options, String answer, String hint, Prowler prowler) {
        this.questName = name;
        this.questQuestion = question;
        this.questionOptions = options;
        this.questAnswer = answer;
        this.questHint = hint;
        this.prowler = prowler;
        this.withHint = false; this.withAnswer = false;
        this.ifWon = false;
    }

    public String getQuestAnswer() {
        return questAnswer;
    }

    public String getQuestHint() {
        return questHint;
    }

    public void usedHint() {
        this.withHint = true;
    }

    public void usedAnswer() {
        this.withAnswer = true;
    }

    public boolean isWithHint() {
        return withHint;
    }

    public boolean isWithAnswer() {
        return withAnswer;
    }
    public void setIfWon(boolean ifWon) {
        this.ifWon = ifWon;
    }
    public boolean getIfWon() {
        return this.ifWon;
    }
}