package InteractingWithPlayer;
import InteractingWithPlayer.NonPlayerCharacters.NPC;
import InteractingWithPlayer.Player.Player;

/**
 * The player uses Ignore Command if the player wishes to ignore the NPC character.
 */
public class IgnoreCommand implements Command {

    private Player player;
    private NPC npc;

    /**
     * Ignore Command Constructor
     * @param player
     * @param npc
     */
    public IgnoreCommand(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
    }

    public String executeCommand() {
        if (this.player.getCodeBytes() >= 5) {
            this.player.updateCodeBytes(-5);
            return "You decided to ignore the NPC character.";
        }
        return "Sorry, you cannot ignore this NPC character, as you don't have enough " +
                "codebytes. Look for school members in other rooms.";
    }
}
