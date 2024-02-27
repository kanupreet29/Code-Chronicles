package GameModel.Pet;

import InteractingWithPlayer.Player.Player;

/**
 * This class keeps track of the progress
 * of the player in the game.
 */
public class VirtualVulture implements Pet {
    public boolean equipped; // is the pet chosen by the player?
    public boolean purchased; // is the pet purchased by the player?

    public String description = this.description = "Meet VirtualVulture, an enigmatic guide in your journey. " +
            "With a talent for dropping hints that unravel the mysteries " +
            "of quests, VirtualVulture's true power lies in revealing quests' " +
            "answers, becoming an indispensable ally."; // the description of the pet
    final int COST_IN_CODEBYTES = 35;

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
        if (playerPet != null && playerPet instanceof VirtualVulture) {
            return "You already have this pet equipped.";
        }
        else {
            if (player.getCodeBytes() >= COST_IN_CODEBYTES) {
                this.equipped = true;
                this.purchased = true;
                player.setPet(this);
                return "You have equipped Virtual Vulture as your pet.";
            }
            else {
                return "You have don't have enough code bytes to equip this pet.";
            }
        }
    }
}
