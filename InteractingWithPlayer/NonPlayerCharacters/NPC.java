package InteractingWithPlayer.NonPlayerCharacters;

import GameModel.Room;
import InteractingWithPlayer.Quest;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// All the NPC character names were taken as a reference from ChatGpt except CrownMark Markus and we added a few names on our own.
/**
 * NPC
 * ___________________
 * This class represents the NPC, or the other students in
 * the school.
 *
 * There are 5 NPCs to face throughout the game. Their images are in the
 * folder characterImages --> NPCimages
 */
public class NPC {

    // the name of the NPC character
    ///String IntroText;
    // a short description in case the user would like to know more about the NPC
    String NPCGreeting;
    // a short description in case the user would like to know more about the NPC
    String npcName;
    String npcGreetings;
    Room currLocation;
    // the location of the NPC (the room where they are found)

    private boolean defeated;

    Quest quest;
    private boolean trusted;
    /**
     * NPC Character Constructor
     * __________________________
     * Initializes attributes
     */
    public NPC(String npcName, String npcGreetings) {
        this.npcName = npcName;
        this.NPCGreeting = npcGreetings;
        this.defeated = false;
        this.trusted = false;
    }

    public String getName(){
        return this.npcName;
    } // getting name of the NPC characters

    public String getIntro(){
        return this.NPCGreeting;
    }
    public void setQuest(Quest quest) {this.quest = quest;}

    public boolean getDefeated() {return this.defeated;}

    public void setDefeated(boolean defeated) {this.defeated = defeated;}
    public Quest getQuest() {return this.quest;}
    public void setTrusted(boolean trust) {this.trusted = true;}
    public boolean getTrusted() {return this.trusted;}
}



