package InteractingWithPlayer.Player;

import GameModel.Room;

/**
 * WarriorCharacter class
 * _________________________
 * The WarriorCharacter extends the superclass. This character and has no special powers
 * or features. If no character is chosen, this is the default character.
 * This class sets the character type and assigns an image for the WarriorCharacter which can later
 * be used in UI
 *
 * WarriorCharacter objects hold a diamond shield (guarantees victory in a battle when used).
 * The shield can be used in battle once during the game using useShield().
 *
 * Its usage is tracked through the shieldUsed attribute.
 *
 * The castSpell() method is overridden to reflect the change in WarriorCharacter’s lines when in battle.
 *
 *  */
public class WarriorCharacter extends Player {

    String characterDesc = "As a warrior, you will use your digital blade to embody strength, resilience, and martial prowess as you fight coding battles.";
    // The description of the character

    /**
     * WarriorCharacter Constructor
     * __________________________
     * Initializes attributes
     *
     */
    public WarriorCharacter(Room myCurrLocation, String playerName) {
        super(playerName, myCurrLocation, "Warrior", 5, 10);
        this.isPlayable = true;
    }
}
