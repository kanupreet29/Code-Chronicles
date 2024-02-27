package GameModel.Pet;

import InteractingWithPlayer.Player.Player;

/**
 * This class keeps track of the progress
 * of the player in the game.
 */
public class MechaDoodle implements Pet {
    public boolean equipped; // is the pet chosen by the player?
    public boolean purchased; // is the pet purchased by the player?

    public String description = "Meet MechaDoodle, a mystical guide for your journey. With the power to " +
            "offer invaluable hints, MechaDoodle aids " +
            "in pivotal moments, yet it is bound by a constraint—only accessible: able to assist in " +
            "the ultimate battle with Incognito Phantom, unable to assist in any other quests."; ; // the description of the pet
    final int COST_IN_CODEBYTES = 80;

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
        if (playerPet != null && playerPet instanceof MechaDoodle) {
                return "You already have this pet equipped.";
            }
        else {
            if (player.getCodeBytes() >= COST_IN_CODEBYTES) {
                this.equipped = true;
                this.purchased = true;
                player.setPet(this);
                return "You have equipped Mecha Doodle as your pet.";
            }
            else {
                return "You have don't have enough code bytes to equip this pet.";
            }
        }
    }


}
