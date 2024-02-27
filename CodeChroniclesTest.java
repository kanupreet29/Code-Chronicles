import java.io.IOException;
import GameModel.Room;
import InteractingWithPlayer.IgnoreCommand;
import InteractingWithPlayer.NonPlayerCharacters.NPC;
import InteractingWithPlayer.Player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CodeChroniclesTest  {

    /**
     * Ignore Command case, where the player
     * has enough codeBytes to ignore character that is only 5 codeBytes(initially)
     * and as the player wants to ignore the character,
     * the player is left only with 0 bytes now.
     * @throws IOException
     */
    @Test
    void IgnoreCommandTest() throws IOException{
        Room room = new Room("Coders Building", 1, 1, "You stand at the entrance of Coders building, a sleek structure of glass and steel where the distant clicks of keyboards and soft discussions envelop the surroundings.");
        Player player = new Player("Lisa",  room , "Mage", 7,7);
        NPC npc = new NPC("Olivia Peterson", "Hi my name is Olivia Peterson. Click below.");
        IgnoreCommand ignoreCommand = new IgnoreCommand(player, npc);
        String enoughBytes = ignoreCommand.executeCommand();
        assertEquals("You decided to ignore the NPC character.", enoughBytes);
        assertEquals(2, player.getCodeBytes());
    }

}
