package GameModel;

import java.io.*;
import java.util.ArrayList;

import InteractingWithPlayer.LastBattleQuestion;
import InteractingWithPlayer.NonPlayerCharacters.Prowler;
import InteractingWithPlayer.NonPlayerCharacters.SchoolMember;
import View.ColourScheme;
import javafx.scene.image.Image;
import InteractingWithPlayer.Quest;

/**
 * Class AdventureLoader. Loads an adventure from files.
 */
public class CodeChroniclesLoader {

    private CodeChroniclesGame game; //the game to return
    private ColourScheme colourScheme;

    /**
     * Adventure Loader Constructor
     * __________________________
     * Initializes attributes
     * @param game the game that is loaded
     */
    public CodeChroniclesLoader(CodeChroniclesGame game) {
        this.game = game;
    }

     /**
     * Load game from directory
     */
    public void loadGame() throws IOException {
        parseRooms();
        parseProwlers();
        parseSchoolMembers();
        parseQuests();
        parseLastBattleQuestions();
        this.game.setHelpText(parseOtherFile("help"));
        this.game.setPrologueText(parseOtherFile("prologue"));
    }

    /**
     * Parse Rooms File
     */
    public void parseRooms() throws IOException {

        BufferedReader buff = new BufferedReader(new FileReader("OtherFiles/rooms.txt"));

        while (buff.ready()) {
            String roomName = buff.readLine();
            Integer roomXCoord = Integer.parseInt(buff.readLine());
            Integer roomYCoord = Integer.parseInt(buff.readLine());
            String roomDescription = buff.readLine();
            String separator = buff.readLine();
            if (separator != null && !separator.isEmpty())
                System.out.println("Formatting Error!");
            Room newRoom = new Room(roomName, roomXCoord, roomYCoord, roomDescription);
            this.game.rooms.put(roomName, newRoom);
        }
    }

    /**
     * Parse Prowlers File
     */
    public void parseProwlers() throws IOException {

        BufferedReader buff = new BufferedReader(new FileReader("OtherFiles/prowlers.txt"));
        while (buff.ready()) {
            String npcName = buff.readLine();
            String prowlerName = buff.readLine();
            String roomName = buff.readLine();
            String npcGreetings = buff.readLine();
            String separator = buff.readLine();
            if (separator != null && !separator.isEmpty())
                System.out.println("Formatting Error!");
            Prowler prowler = new Prowler(npcName, prowlerName, npcGreetings);
            this.game.prowlers.add(prowler);
            this.game.rooms.get(roomName).setNPC(prowler);
        }
    }

    /**
     * Parse School Members File
     */
    public void parseSchoolMembers() throws IOException {
        BufferedReader buff = new BufferedReader(new FileReader("OtherFiles/schoolmembers.txt"));
        while (buff.ready()) {
            String npcName = buff.readLine();
            String roomName = buff.readLine();
            String npcGreetings = buff.readLine();
            String separator = buff.readLine();
            if (separator != null && !separator.isEmpty())
                System.out.println("Formatting Error!");
            SchoolMember schoolMember = new SchoolMember(npcName, npcGreetings);
            this.game.schoolMembers.add(schoolMember);
            this.game.rooms.get(roomName).setNPC(schoolMember);
        }
    }

    /**
     * Parse Quests File
     */
    public void parseQuests() throws IOException {
        BufferedReader buff = new BufferedReader(new FileReader("OtherFiles/quests.txt"));
        while (buff.ready()) {
            String questName = buff.readLine();
            String questQuestion = buff.readLine();
            String optionA = buff.readLine();
            String optionB = buff.readLine();
            String optionC = buff.readLine();
            String optionD = buff.readLine();
            ArrayList<String> options = new ArrayList<String>();
            options.add(optionA);
            options.add(optionB);
            options.add(optionC);
            options.add(optionD);
            String questAnswer = buff.readLine();
            String questHint = buff.readLine();
            String prowlerName = buff.readLine();
            String separator = buff.readLine();
            Prowler questProwler = null;
            for (Prowler prowler : this.game.prowlers) {
                if (prowler.getProwlerName().equals(prowlerName)) {
                    questProwler = prowler;
                }
            }
            if (separator != null && !separator.isEmpty())
                System.out.println("Formatting Error!");
            Quest quest = new Quest(questName, questQuestion, options, questAnswer, questHint, questProwler);
            this.game.quests.add(quest);
            Integer prowlerIndex = this.game.prowlers.indexOf(questProwler);
            this.game.prowlers.get(prowlerIndex).setQuest(quest);
        }
    }

    /**
     * Parse LastBattleQuestions File
     */
    public void parseLastBattleQuestions() throws IOException {
        BufferedReader buff = new BufferedReader(new FileReader("OtherFiles/lastBattleQuestions.txt"));
        while (buff.ready()) {
            String question = buff.readLine();
            String optionA = buff.readLine();
            String optionB = buff.readLine();
            String optionC = buff.readLine();
            String optionD = buff.readLine();
            ArrayList<String> options = new ArrayList<String>();
            options.add(optionA);
            options.add(optionB);
            options.add(optionC);
            options.add(optionD);
            String answer = buff.readLine();
            String hint = buff.readLine();
            String separator = buff.readLine();

            if (separator != null && !separator.isEmpty())
                System.out.println("Formatting Error!");
            LastBattleQuestion lbQuestion = new LastBattleQuestion(question, options, answer, hint);
            this.game.lastBattleQuestions.add(lbQuestion);
        }
    }

    /**
     * Parse all other files
     *
     * @param fileName the file to parse
     */
    public String parseOtherFile(String fileName) throws IOException {
        String text = "";
        fileName = "OtherFiles" + "/" + fileName + ".txt";
        BufferedReader buff = new BufferedReader(new FileReader(fileName));
        String line = buff.readLine();
        while (line != null) { // while not EOF
            text += line+"\n";
            line = buff.readLine();
        }
        return text;
    }

}
