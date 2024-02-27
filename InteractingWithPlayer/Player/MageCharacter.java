package InteractingWithPlayer.Player;

import GameModel.Room;

import java.util.ArrayList;
import java.util.Objects;

import InteractingWithPlayer.Player.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * MageCharacter class
 * _________________________
 * The MageCharacter extends the superclass. This character and has no special powers
 * or features. If no character is chosen, this is the default character.
 *
 * This class sets the character type and assigns an image for the MageCharacter which
 * can later be used in UI. The castSpell method stays the same.
 *
 *  */
public class MageCharacter extends Player {

    String characterDesc = "As a mage, you will control the digital realms by wielding spells that manifest as intricate lines of code dancing through the air.";
    // The description of the character


    /**
     * MageCharacter Constructor
     * __________________________
     * Initializes attributes
     *
     */
    public MageCharacter(Room myCurrLocation, String playerName) {
        super(playerName, myCurrLocation, "Mage", 7, 7);
        isPlayable = true;
    }
}
