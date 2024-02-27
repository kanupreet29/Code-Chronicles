package InteractingWithPlayer;


import InteractingWithPlayer.NonPlayerCharacters.NPC;
import InteractingWithPlayer.NonPlayerCharacters.Prowler;
import InteractingWithPlayer.Player.Player;
import View.CodeChroniclesGameView;
import View.LastBattleView;
import View.QuestView;

/**
 * The player uses Hack Command, if the player wishes to hack in the NPC character's mind
 * which the player does not know is a prowler or school member.
 */
public class HackCommand implements Command {

    private Player player;
    private NPC npc;
    private CodeChroniclesGameView gameView;
    static final int MIN_BYTES = 2; //the minimum number of code bytes the player should have to hack an NPC

    /**
     * Constructor for Hack Command
     * @param player
     * @param npc
     * @param gameView
     */
    public HackCommand(Player player, NPC npc, CodeChroniclesGameView gameView) {
        this.player = player;
        this.npc = npc;
        this.gameView = gameView;
    }


    public String executeCommand() {
        return showCharacterIdentity(this.npc, this.player, gameView);
    };


    /**
     * This method reveals the identity of the NPC character when the player
     * chooses to hack the character.
     * @param player ;
     * @param character ;
     * @param gameView ;
     * @return the identity of the NPC character.
     */

    public String showCharacterIdentity(NPC character, Player player, CodeChroniclesGameView gameView) {
        // check if player has enough code bytes to hack
        if (this.countBytes() >= MIN_BYTES) {
            // check if character type is Prowler, warn the player.
            if (character instanceof Prowler) {
                return prowlerQuest(player, gameView);
            }
            // check if character type is school member, greet the player.
            else {
                player.loseLife();
                return "Oh no! You tried to hack a School Member, and lost 1 life.";
            }
        }
        else {
            return "You don't have enough code bytes to hack this person." +
                    " Look around for School Members to help you with collecting code bytes.";
        }
    }

    /**
     * This method checks if the player has the minimum required code bytes
     * to hack the prowler.
     * @return the number of code bytes the player has.
     */

    public Integer countBytes() {
        return this.player.getCodeBytes();
    }

    /**
     * This method checks if it is a prowler and the player does not have MINIMUM_BYTES to fight the prowler,
     * the player has to play the quest.
     * @param player;
     * @param gameView;
     * @return result of the quest
     */
    public String prowlerQuest(Player player, CodeChroniclesGameView gameView) {
        if (this.player.getCurrentRoom().characterInRoom.getQuest().getIfWon()) {
            return "You have already won this quest. You cannot play it again.";
        }
        player.playQuest(gameView);
        return "Click on Map to update your codebyte count.";
    }

}
