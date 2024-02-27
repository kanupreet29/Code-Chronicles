package InteractingWithPlayer;

import InteractingWithPlayer.NonPlayerCharacters.NPC;
import InteractingWithPlayer.NonPlayerCharacters.Prowler;
import InteractingWithPlayer.Player.Player;

/**
 * The player uses Trust Command, if the player wishes to trust the NPC character, interestingly
 * which the player does not know is a prowler or school member.
 */

public class TrustCommand implements Command {

    private Player player;
    private NPC npc;

    /**
     * Trust Command Constructor
     * @param player
     * @param npc
     */

    public TrustCommand(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
    }

    public String executeCommand() { return showCharacterIdentity();};

    /**
     * This method reveals the identity of the NPC character when the player chooses to trust the character.
     *
     * @return indicator string
     */
    public String showCharacterIdentity() {
        if (this.npc instanceof Prowler) {
            if (this.player.getCurrentRoom().characterInRoom.getQuest().getIfWon()) {
                return "You have already interacted with this character once successfully. You are not allowed any more interactions.";
            }
            else {
                this.player.loseLife();
                this.player.getCurrentRoom().characterInRoom.setTrusted(true);
                return "Oh no! You decided to trust a Prowler and lost 1 life";
            }
        }
        else {
            if (this.npc.getTrusted()) {
                return "You have already interacted with this character once successfully. You are not allowed any more interactions.";
            }
            else {
                this.npc.setTrusted(true);
                this.player.updateCodeBytes(10);
                return "You decided to trust a School Member. \n Hi " + this.player.getPlayerName() + "! I am so happy to hear you're helping us defeat the Polymorphic prowlers. Here are 10 code bytes to help you with your mission.";
            }
        }
    }
}
