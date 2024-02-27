package GameModel.Pet;

import InteractingWithPlayer.Player.Player;

/**
 * This class keeps track of the progress
 * of the pet in the game.
 */
public interface Pet {

    /**
     * This method provides the player with an introduction of this pet.
     *
     * @return the introduction of the pet
     */
    public String introducePet();

    /**
     * This method allows the player to equip this pet.
     *
     * @param player the player of this game
     * @return the string indicating the status of pet equipment
     */
    public String equipPet(Player player);
}