package GameModel;

import InteractingWithPlayer.LastBattleQuestion;
import InteractingWithPlayer.NonPlayerCharacters.NPC;
import InteractingWithPlayer.NonPlayerCharacters.Prowler;
import InteractingWithPlayer.NonPlayerCharacters.SchoolMember;
import InteractingWithPlayer.Player.Player;
import InteractingWithPlayer.Player.WarriorCharacter;
import InteractingWithPlayer.Quest;

import java.io.*;
import java.util.*;

/**
 * Class AdventureGame.  Handles all the necessary tasks to run the Adventure game.
 */
public class CodeChroniclesGame implements Serializable {
    private String helpText; //A variable to store the Help text of the game. This text is displayed when the user types "HELP" command.
    private String prologueText; //A variable to store the Prologue text of the game. This text is displayed when the user first loads the game.
    public HashMap<String, Room> rooms; //A list of all the rooms in the game.

    public Player player; //The Player of the game.
    public ArrayList<Prowler> prowlers; // The list of all the prowlers.
    public ArrayList<SchoolMember> schoolMembers; // The list of all the School Members.
    public ArrayList<Quest> quests; // The list of all the quests.
    public ArrayList<LastBattleQuestion> lastBattleQuestions; // The list of all the questions in the last battle.
    public Integer numOfProwlers;



    /**
     * Adventure Game Constructor
     * __________________________
     * Initializes attributes
     *
     */
    public CodeChroniclesGame(){
        try {
            this.rooms = new HashMap<String,Room>();
            this.prowlers = new ArrayList<Prowler>();
            this.quests = new ArrayList<Quest>();
            this.schoolMembers = new ArrayList<SchoolMember>();
            this.lastBattleQuestions = new ArrayList<LastBattleQuestion>();
            this.numOfProwlers = 7;
            setUpGame();
        } catch (IOException e) {
            throw new RuntimeException("An Error Occurred: " + e.getMessage());
        }
    }

    /**
     * setUpGame
     * __________________________
     *
     * @throws IOException in the case of a file I/O error
     */
    public void setUpGame() throws IOException {
        CodeChroniclesLoader loader = new CodeChroniclesLoader(this);
        loader.loadGame();
    }

    /**
     * getInstructions
     * __________________________
     * Getter method for instructions 
     * @return helpText
     */
    public String getInstructions() {
        return helpText;
    }

    /**
     * getPrologue
     * __________________________
     * Getter method for Prologue
     * @return prologueText
     */
    public String getPrologue() {
        return prologueText;
    }

    /**
     * getPlayer
     * __________________________
     * Getter method for Player 
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * getRooms
     * __________________________
     * Getter method for rooms 
     * @return map of key value pairs (integer to room)
     */
    public HashMap<String, Room> getRooms() {
        return this.rooms;
    }

    /**
     * setHelpText
     * __________________________
     * Setter method for helpText
     * @param help which is text to set
     */
    public void setHelpText(String help) {
        this.helpText = help;
    }

    /**
     * setPrologueText
     * __________________________
     * Setter method for helpText
     * @param prologue which is text to set
     */
    public void setPrologueText(String prologue) {
        this.prologueText = prologue;
    }

}
