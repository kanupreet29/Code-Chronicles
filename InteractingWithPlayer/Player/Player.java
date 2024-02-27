package InteractingWithPlayer.Player;

import GameModel.Pet.Pet;
import GameModel.Room;
import View.CodeChroniclesGameView;
import View.QuestView;
import javafx.scene.image.Image;

/**
 * Character class
 * _________________________
 * This class keeps track of the characters in the game.
 * There are three types of playable characters: Mage, Alchemist, Warrior.
 * And two non-playing characters: Prowler, NPC.
 *
 * In order to maintain the single responsibility principal, it only
 * handles actions directly relating to the character (i.e. their room, objects)
 *
 * At the start of the game the player will be able to choose the
 * character "type" as indicated by the subclasses.
 *  */
public class Player {

    String characterDesc;
    // The description of the character
    String playerName;
    // The name of the character, allow the character to choose
    Room currLocation;
    // The current location of the character. Must be a Room object.
    String playerType;
    // The type of character [Either Mage, Alchemist or, Warrior]
    boolean isPlayable;
    // Attribute that determines if character is playable
    private Integer codeBytes;
    // keeps track of the current number of code bytes
    private Integer lives;
    private Pet pet;


    /**
     * Player Constructor
     * __________________________
     * Initializes attributes
     *
     */
    public Player(String playerName, Room myCurrLocation, String myPlayerType, Integer codeBytes, Integer lives) {
        this.playerName = playerName; // BEFORE the player customizes their name
        this.currLocation = myCurrLocation;
        this.playerType = myPlayerType;
        this.codeBytes = codeBytes; // initial value
        this.lives = lives; //default value
    }

    /**
     * getPlayerName
     * _________________________
     * Getter method for the character name.
     *
     * This is useful for instances in the story in which the
     * Characters name is said by another character.
     *
     * @return the name of the character.
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * getCurrentLocation
     * _________________________
     * Getter method for the current location attribute.
     *
     * @return current room the player is in.
     */
    public Room getCurrentRoom() {
        return this.currLocation;
    }

    public void setCurrentRoom(Room room) {
        this.currLocation = room;
        if (!room.getVisited()) {
            this.updateCodeBytes(-1);
        } room.visit();
    }

    /**
     * setCharacterName
     * _________________________
     * Setter method for the character name.
     *
     * This is useful for instances in the story in which the
     * Characters name is said by another character.
     */
    public void setPlayerName(String name) {
        this.playerName = name;
    }

    /**
     * getPlayerType
     */
    public String getPlayerType() {
        return this.playerType;
    }

    /**
     * getCodeBytes
     */
    public Integer getCodeBytes() {
        return this.codeBytes;
    }

    /**
     * getLives
     */
    public Integer getLives() {return this.lives;
    }

    /**
     * updateCodeBytes
     * _________________________
     This method updates the attribute based on the provided number of code bytes
     */
    public void updateCodeBytes(int codeBytes) {
        this.codeBytes += codeBytes;
    }

    /**
     * loseLife
     * _________________________
     This method updates the attribute based on the provided number of lives
     */
    public void loseLife() {
        this.lives -= 1;
    }

    /**
     * playQuest
     */
    public boolean playQuest(CodeChroniclesGameView gameView) {
        QuestView questView = new QuestView(gameView, this.currLocation.characterInRoom.getQuest(), this);
        questView.adhereToMenuSettings(gameView.fontSize - 16, gameView.audio, gameView.music,
                gameView.colourScheme.colourSchemeName);
        return this.getCurrentRoom().characterInRoom.getQuest().getIfWon();
    }

    public void setPet(Pet pet) {this.pet = pet;}
    public Pet getPet() {return this.pet;}
}


