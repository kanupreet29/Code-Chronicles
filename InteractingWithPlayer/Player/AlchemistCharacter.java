package InteractingWithPlayer.Player;

import GameModel.Room;

import java.util.ArrayList;
import java.util.Objects;

import InteractingWithPlayer.Player.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * AlchemistCharacter class
 * _________________________
 * The AlchemistCharacter extends the superclass. This character and has no special powers
 * or features. If no character is chosen, this is the default character.
 * This class sets the character type and assigns an image for the AlchemistCharacter which can later
 * be used in UI
 *
 * AlchemistCharacter objects have an elementalAffinity attribute (i.e. fire, water, wind, earth).
 * It can be used in battle once during the game using castAffinity().
 *
 * Its usage is tracked through the affinityUsed.
 *
 * The castSpell() method is overridden to reflect the change in AlchemistCharacter’s lines when in battle.
 *
 *  */
public class AlchemistCharacter extends Player {

    String characterDesc = "As an alchemist, you will use the alchemy of programming languages to brew potions and concoct coding elixirs that unravel the secrets of the digital universe.";
    // The description of the character

    /**
     * AlchemistCharacter Constructor
     * __________________________
     * Initializes attributes
     *
     */
    public AlchemistCharacter(Room myCurrLocation, String playerName) {
        super(playerName, myCurrLocation, "Alchemist", 10, 5);
        isPlayable = true;
        // change playerType
        playerType = "Alchemist";
    }
}

