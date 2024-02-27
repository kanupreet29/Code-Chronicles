package GameModel.Pet;

import InteractingWithPlayer.Player.Player;

/**
 * This class keeps track of the progress
 * of the player in the game.
 */
public class NanoBunny implements Pet {
    public boolean equipped; // is the pet chosen by the player?
    public boolean purchased; // is the pet purchased by the player?

    public String description = "Meet NanoBunny, the trusty first companion on any quest. " +
            "NanoBunny becomes an invaluable guide throughout adventures by " +
            "aiding the journey with its " +
            "ability to provide essential clues."; // the description of the pet

    /**
     * This method provides the player with an introduction of this pet.
     *
     * @return the introduction of the pet
     */
    @Override
    public String introducePet() {
        return this.description;
    }

    /**
     * This method allows the player to equip this pet.
     *
     * @param player the player of this game
     * @return the string indicating the status of pet equipment
     */
    @Override
    public String equipPet(Player player) {
        Pet playerPet = player.getPet();
        if (playerPet != null && playerPet instanceof NanoBunny) {
            return "You already have this pet equipped.";
        }
        else {
            this.equipped = true;
            this.purchased = true;
            player.setPet(this);
            return "You have equipped Nano Bunny as your pet.";
        }
    }
}
