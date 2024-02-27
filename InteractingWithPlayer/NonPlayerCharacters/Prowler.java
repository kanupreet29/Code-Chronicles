package InteractingWithPlayer.NonPlayerCharacters;




// All the Prowler names were generated from ChatGpt.
public class Prowler extends NPC {

    private String prowlerName;

    /**
     * Constructor
     */
    public Prowler(String npcName, String prowlerName, String npcGreetings) {
        super(npcName, npcGreetings);
        this.prowlerName = prowlerName;

    }

    /**
     * This method gets the name of the Prowler.
     */

    public String getProwlerName() {
        return this.prowlerName;
    }

}

