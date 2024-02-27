package View;

import GameModel.CodeChroniclesGame;
import GameModel.Pet.MechaDoodle;
import GameModel.Pet.NanoBunny;
import GameModel.Pet.Pet;
import GameModel.Pet.VirtualVulture;
import GameModel.Room;
import InteractingWithPlayer.HackCommand;
import InteractingWithPlayer.IgnoreCommand;
import InteractingWithPlayer.NonPlayerCharacters.NPC;
import InteractingWithPlayer.NonPlayerCharacters.Prowler;
import InteractingWithPlayer.Player.AlchemistCharacter;
import InteractingWithPlayer.Player.MageCharacter;
import InteractingWithPlayer.Player.WarriorCharacter;
import InteractingWithPlayer.TrustCommand;
import javafx.animation.PauseTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.AccessibleRole;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javafx.scene.control.ContentDisplay.TOP;
import static jdk.dynalink.linker.support.Guards.isInstance;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize the model.
 */
public class CodeChroniclesGameView {
    public CodeChroniclesGame game; //model of the game
    public Integer fontSize;
    public ColourScheme colourScheme;
    public Stage stage; //stage on which all is rendered
    Button menuButton, instructionsButton, mapButton, petsButton; //buttons

    Boolean helpToggle = false; //is help on display?
    Boolean mapToggle = false; //is map on display?
    Boolean petToggle = false; // are pets on display?
    public Boolean music = true;
    public Boolean audio = true;
    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    ImageView roomImageView; //to hold room image
    // the media players
    private MediaPlayer backgroundMusicPlayer; // for the background music
    private MediaPlayer introductionAudioPlayer; // for the character introductions + NPC voices
    private MediaPlayer roomAudioPlayer; // for room articulations
    private MediaPlayer buttonClickPlayer; // for button sound
    private MediaPlayer commandsPlayer; // for commands audio
    private boolean backgroundMediaPlaying; //to know if the room descriptions are playing
    private boolean roomMediaPlaying; //to know if the background audio is playing
    // public boolean allAudioOn = true; // for the no audio option (true by default)
    // public boolean allAudioOn = true;


    // attributes for the background
    Long currentFrame;
    Clip clip;

    // current status of clip
    String status;

    AudioInputStream audioInputStream;
    static String filePath;

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public CodeChroniclesGameView(CodeChroniclesGame game, Stage stage) {
        this.game = game;
        this.stage = stage;
        this.colourScheme = new ColourScheme("Game Theme");
        this.fontSize = 16;
        intiUI();

        // call the method to play reduced background music indefinitely
        // but only do it if the audio feature is not turned off
        if (music && audio) {
            this.playBackgroundMusic();
        }
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {

        // SETTING UP THE STAGE
        this.stage.setTitle("Code Chronicles: Wizard's Quest");

        Button screenButton = new Button("Code Chronicles: Wizard's Quest");
        screenButton.setId("Code Chronicles: Wizard's Quest");
        screenButton.setAlignment(Pos.CENTER);
        customizeButton( screenButton, 1000, 800, this.colourScheme.backgroundColour);
        screenButton.wrapTextProperty().setValue(true);
        screenButton.setStyle("-fx-background-color: "+ this.colourScheme.backgroundColour + "; -fx-text-fill: white;");
        makeButtonAccessible( screenButton, "Code Chronicles: Wizard's Quest", "Code Chronicles: Wizard's Quest, click anywhere to continue.", "Code Chronicles: Wizard's Quest, click anywhere to continue.");
        screenButton.setOnAction(e -> {
            this.stage.setScene(this.setPrologue());
        });
        ImageView imgView = new ImageView(new Image("OtherFiles/StartScreen.png"));
        imgView.setPreserveRatio(true);
        imgView.fitWidthProperty().bind(screenButton.widthProperty());
        imgView.fitHeightProperty().bind(screenButton.heightProperty());
        screenButton.setGraphic(imgView);
        screenButton.setPadding( new Insets(0));
        Scene scene = new Scene(screenButton,  1000, 800);

        this.stage.setScene(scene);

        this.stage.setResizable(false);
        this.stage.show();
    }

    public Scene setPrologue() {
        Button prologueButton = new Button(this.game.getPrologue() + "\n\n Click anywhere to continue.");
        prologueButton.setPadding( new Insets(50));
        prologueButton.setId("Prologue");
        prologueButton.setAlignment(Pos.CENTER);
        customizeButton(prologueButton, 1000, 800, this.colourScheme.backgroundColour);
        prologueButton.wrapTextProperty().setValue(true);
        prologueButton.setStyle("-fx-background-color: "+ this.colourScheme.backgroundColour + "; -fx-text-fill: white;");
        makeButtonAccessible(prologueButton, "Prologue", "Prologue, click anywhere to continue.", this.game.getPrologue() + "\n\n Click anywhere to continue.");
        prologueButton.setOnAction(e -> {
            this.stage.setScene(this.setInstructions());
        });
        Scene scene = new Scene(prologueButton,  1000, 800);
        scene.setFill(Color.valueOf(this.colourScheme.backgroundColour));
        return scene;
    }

    public Scene setInstructions() {
        Button instructionsButton = new Button(this.game.getInstructions() + "\n\n Click anywhere to continue");
        instructionsButton.setPadding( new Insets(50));
        instructionsButton.setId("Instructions");
        instructionsButton.setAlignment(Pos.CENTER);
        customizeButton(instructionsButton, 1000, 800, this.colourScheme.backgroundColour);
        instructionsButton.wrapTextProperty().setValue(true);
        instructionsButton.setStyle("-fx-background-color: "+ this.colourScheme.backgroundColour + "; -fx-text-fill: white;");
        makeButtonAccessible(instructionsButton, "Instructions", "Instructions, click anywhere to Continue", this.game.getInstructions() + "\n\n Click anywhere to continue.");
        instructionsButton.setOnAction(e -> {
            this.stage.setScene(this.setCharacterCustomizationScene());
        });
        Scene scene = new Scene(instructionsButton,  1000, 800);
        scene.setFill(Color.valueOf(this.colourScheme.backgroundColour));
        return scene;
    }

    public Scene setCharacterCustomizationScene() {

        // SETUP GRID PANE
        GridPane characterGridPane = new GridPane();
        characterGridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf(this.colourScheme.backgroundColour),
                new CornerRadii(0),
                new Insets(0)
        )));
        // Row and Column Constraints
        ColumnConstraints column1 = new ColumnConstraints(50);
        ColumnConstraints column2 = new ColumnConstraints(300);
        ColumnConstraints column3 = new ColumnConstraints(300);
        ColumnConstraints column4 = new ColumnConstraints(300);
        ColumnConstraints column5 = new ColumnConstraints(50);
        RowConstraints row1 = new RowConstraints(50);
        RowConstraints row2 = new RowConstraints( 30);
        RowConstraints row3 = new RowConstraints(600);
        RowConstraints row4 = new RowConstraints(120);
        characterGridPane.getColumnConstraints().addAll(column1 , column2 , column3, column4, column5);
        characterGridPane.getRowConstraints().addAll(row1 , row2 , row3, row4);

        //SETUP OTHER BUTTONS AND LABELS

        // "Character Customization" Label
        Label characterCustomization = new Label("Character Customization");
        characterCustomization.setFont(new Font("Helvetica", 25));
        characterCustomization.setTextFill(Color.web(this.colourScheme.regularFontColour));
        characterCustomization.setAlignment(Pos.CENTER);
        characterGridPane.add(characterCustomization, 2, 0, 1, 1);
        characterGridPane.setHalignment(characterCustomization, HPos.CENTER);

        // Character Selection Label
        Label selectedPlayerLabel = new Label("Please select a player, and enter a name.");
        selectedPlayerLabel.setFont(new Font("Helvetica", this.fontSize));
        selectedPlayerLabel.setTextFill(Color.web(this.colourScheme.regularFontColour));
        selectedPlayerLabel.setAlignment(Pos.CENTER);
        characterGridPane.add(selectedPlayerLabel, 2, 1, 1, 1);
        characterGridPane.setHalignment(selectedPlayerLabel, HPos.CENTER);

        // Name Label
        Label nameLabel = new Label("Player Name: ");
        nameLabel.setFont(new Font("Helvetica", this.fontSize));
        nameLabel.setTextFill(Color.web(this.colourScheme.regularFontColour));
        nameLabel.setAlignment(Pos.CENTER);
        characterGridPane.add(nameLabel, 1, 3, 1, 1);
        characterGridPane.setHalignment(nameLabel, HPos.CENTER);

        // Input Text Field for Player Name
        TextField name = new TextField();
        name.setFont(new Font("Helvetica", this.fontSize));
        name.setFocusTraversable(true);
        name.setAccessibleRole(AccessibleRole.TEXT_AREA);
        name.setAccessibleRoleDescription("Player Name");
        name.setAccessibleText("Enter a name for you player in the box.");
        name.setAccessibleHelp("Enter a name for you player in the box.");
        characterGridPane.add(name, 2, 3, 1, 1);
        characterGridPane.setHalignment(name, HPos.LEFT);

        // Play Game Button
        Button playButton = new Button("Play");
        playButton.setId("Play");
        playButton.setAlignment(Pos.CENTER);
        customizeButton(playButton,100, 50, this.colourScheme.buttonColour2);
        makeButtonAccessible(playButton, "Play", "Play Game", "Click to play game with selected character.");
        playButton.setOnAction(e -> {
            if (audio) {
                playButtonClick(); // plays the button click sound effect when pressed
            }
            if (this.game.player != null) {
                try {
                    showPets();
                    stopIntroductionAudio(); // add this to stop the introduction audio before transitioning
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        characterGridPane.add(playButton, 3, 3, 1, 1);
        characterGridPane.setHalignment(playButton, HPos.RIGHT);

        // CHARACTER SELECTION BUTTONS

        // Alchemist Player
        Button alchemistButton = new Button("ALCHEMIST CHARACTER \n \n As an alchemist, you will use the alchemy of programming languages to brew potions and concoct coding elixirs that unravel the secrets of the digital universe. \n \n Lives: 5 \n Code Bytes: 10");
        alchemistButton.setTextAlignment(TextAlignment.CENTER);
        alchemistButton.setId("Alchemist Character");
        alchemistButton.setAlignment(Pos.TOP_CENTER);
        customizeButton(alchemistButton, 280, 550, this.colourScheme.buttonColour2);
        alchemistButton.wrapTextProperty().setValue(true);
        Image alchemistImage = new Image("OtherFiles/Images/" + this.colourScheme.colourSchemeName + "/characterImages/alchemistCharacter.png");
        ImageView alchemistView = new ImageView(alchemistImage);
        alchemistView.setFitHeight(300);
        alchemistView.setFitWidth(250);
        alchemistView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        alchemistView.setAccessibleText("As an alchemist, you will use the alchemy of programming languages to brew potions and concoct coding elixirs that unravel the secrets of the digital universe.");
        alchemistButton.setGraphic(alchemistView);
        alchemistButton.setContentDisplay(TOP);
        alchemistButton.setStyle("-fx-background-color: "+ this.colourScheme.buttonColour1 + "; -fx-text-fill: white;");
        makeButtonAccessible(alchemistButton, "Alchemist", "Alchemist Character", "As an alchemist, you will use the alchemy of programming languages to brew potions and concoct coding elixirs that unravel the secrets of the digital universe.");
        alchemistButton.setOnAction(e -> {
            if (audio) {
                playButtonClick(); // plays the button click sound effect when pressed
            }
            selectedPlayerLabel.setText("You have selected: Alchemist");
            this.game.player = new AlchemistCharacter(this.game.rooms.get("Front Gate"), name.getText().trim());
            // play introduction audio if selected by passing audio file to method
            playIntroductionAudio("alchemistDescription.wav");
        });

        // Mage Player
        Button mageButton = new Button("MAGE CHARACTER \n \n As a mage, you will control the digital realms by wielding spells that manifest as intricate lines of code dancing through the air. \n \n \n Lives: 7 \n Code Bytes: 7");
        mageButton.setTextAlignment(TextAlignment.CENTER);
        mageButton.setId("Mage Character");
        mageButton.setAlignment(Pos.TOP_CENTER);
        customizeButton(mageButton, 280, 550, this.colourScheme.buttonColour2);
        mageButton.wrapTextProperty().setValue(true);
        Image mageImage = new Image("OtherFiles/Images/" + this.colourScheme.colourSchemeName + "/characterImages/mageCharacter.png");
        ImageView mageView = new ImageView(mageImage);
        mageView.setFitHeight(300);
        mageView.setFitWidth(250);
        mageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        mageView.setAccessibleText("As a mage, you will control the digital realms by wielding spells that manifest as intricate lines of code dancing through the air.");
        mageButton.setGraphic(mageView);
        mageButton.setContentDisplay(TOP);
        mageButton.setStyle("-fx-background-color: "+ this.colourScheme.buttonColour1 + "; -fx-text-fill: white;");
        makeButtonAccessible(mageButton, "Mage", "Mage Character", "As a mage, you will control the digital realms by wielding spells that manifest as intricate lines of code dancing through the air.");
        mageButton.setOnAction(e -> {
            if (audio) {
                playButtonClick(); // plays the button click sound effect when pressed
            }
            selectedPlayerLabel.setText("You have selected: Mage");
            this.game.player = new MageCharacter(this.game.rooms.get("Front Gate"), name.getText().trim());
            // play introduction audio if selected by passing audio file to method
            playIntroductionAudio("mageDescription.wav");
        });

        // Warrior Player
        Button warriorButton = new Button("WARRIOR CHARACTER \n \n As a warrior, you will use your digital blade to embody strength, resilience, and martial prowess as you fight coding battles. \n \n \n Lives: 10 \n Code Bytes: 5");
        warriorButton.setTextAlignment(TextAlignment.CENTER);
        warriorButton.setId("Warrior Character");
        warriorButton.setAlignment(Pos.TOP_CENTER);
        customizeButton(warriorButton, 280, 550, this.colourScheme.buttonColour2);
        warriorButton.wrapTextProperty().setValue(true);
        Image warriorImage = new Image("OtherFiles/Images/" + this.colourScheme.colourSchemeName + "/characterImages/warriorCharacter.png");
        ImageView warriorView = new ImageView(warriorImage);
        warriorView.setFitHeight(300);
        warriorView.setFitWidth(250);
        warriorView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        warriorView.setAccessibleText("As a warrior, you will use your digital blade to embody strength, resilience, and martial prowess as you fight coding battles.");
        warriorButton.setGraphic(warriorView);
        warriorButton.setContentDisplay(TOP);
        warriorButton.setStyle("-fx-background-color: "+ this.colourScheme.buttonColour1 + "; -fx-text-fill: white;");
        makeButtonAccessible(warriorButton, "Warrior Character", "Warrior Character", "As a warrior, you will use your digital blade to embody strength, resilience, and martial prowess as you fight coding battles.");
        warriorButton.setOnAction(e -> {
            if (audio) {
                playButtonClick(); // plays the button click sound effect when pressed
            }
            selectedPlayerLabel.setText("You have selected: Warrior");
            this.game.player = new WarriorCharacter(this.game.rooms.get("Front Gate"), name.getText().trim());
            // play introduction audio if selected by passing audio file to method
            playIntroductionAudio("warriorDescription.wav");
        });

        this.game.rooms.get("Front Gate").visit();

        // Add character buttons to grid pane.
        characterGridPane.add(alchemistButton, 1, 2, 1, 1 );
        characterGridPane.setHalignment(alchemistButton, HPos.CENTER);
        characterGridPane.add(mageButton, 2, 2, 1, 1 );
        characterGridPane.setHalignment(mageButton, HPos.CENTER);
        characterGridPane.add(warriorButton, 3, 2, 1, 1 );
        characterGridPane.setHalignment(warriorButton, HPos.CENTER);

        // SETUP SCENE
        Scene scene = new Scene(characterGridPane ,  1000, 800);
        scene.setFill(Color.valueOf(this.colourScheme.backgroundColour));
        return scene;
    }

    public void showAnimalAlert() {

        // SETUP GRID PANE
        GridPane animalGridPane = new GridPane();
        animalGridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf(this.colourScheme.backgroundColour),
                new CornerRadii(0),
                new Insets(0)
        )));
        // Row and Column Constraints
        ColumnConstraints column1 = new ColumnConstraints(50);
        ColumnConstraints column2 = new ColumnConstraints(300);
        ColumnConstraints column3 = new ColumnConstraints(300);
        ColumnConstraints column4 = new ColumnConstraints(300);
        ColumnConstraints column5 = new ColumnConstraints(50);
        RowConstraints row1 = new RowConstraints(50);
        RowConstraints row2 = new RowConstraints( 30);
        RowConstraints row3 = new RowConstraints(600);
        RowConstraints row4 = new RowConstraints(120);
        animalGridPane.getColumnConstraints().addAll(column1 , column2 , column3, column4, column5);
        animalGridPane.getRowConstraints().addAll(row1 , row2 , row3, row4);

        // Play Game Button
        Button playButton = new Button("Play");
        playButton.setId("Play");
        playButton.setAlignment(Pos.CENTER);
        customizeButton(playButton,100, 50, this.colourScheme.buttonColour2);
        makeButtonAccessible(playButton, "Play", "Play Game", "Click to play game with selected character.");
        playButton.setOnAction(e -> {
            if (audio) {
                playButtonClick(); // plays the button click sound effect when pressed
            }
            if (this.game.player != null) {
                try {
                    setRoomScene();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        animalGridPane.add(playButton, 3, 3, 1, 1);
        animalGridPane.setHalignment(playButton, HPos.RIGHT);

        // SETUP SCENE
        Scene scene = new Scene(animalGridPane ,  1000, 800);
        scene.setFill(Color.valueOf(this.colourScheme.backgroundColour));
        // return scene;
    }

    public void setRoomScene() throws FileNotFoundException {
        stopIntroductionAudio();
        stopArticulation();

        GridPane roomPane = new GridPane();
        this.setupGridPane(roomPane);
        this.addGameHeader(roomPane);

        // add characters and NPCs to the GridPane
        ImageView characterView = this.getCharacterImageView();

        roomPane.add(characterView, 3, 2);

        roomPane.setValignment(characterView, VPos.CENTER);
        roomPane.setHalignment(characterView, HPos.CENTER);

        Button NPCButton = new Button();

        if (!this.game.player.getCurrentRoom().getNPC().getDefeated()) {
            stopIntroductionAudio();
            ImageView NPCView = this.getNPCImageView(this.game.player.getCurrentRoom().getNPC());
            NPCButton.setGraphic(NPCView);
            NPCButton.setBackground(null);
            roomPane.add(NPCButton, 1, 2);
            roomPane.setValignment(NPCButton, VPos.CENTER);
            roomPane.setHalignment(NPCButton, HPos.CENTER);
            // What happens when the character clicks on the other character in the room?
            NPCButton.setOnAction(e -> {
                this.roomDescLabel.setText(this.game.player.getCurrentRoom().getNPC().getIntro());
                this.addInteractionCommands();
            });
        }

        articulateRoomDescription(); // try this rn

        // add room description to GridPane
        this.roomDescLabel.setText(this.game.player.getCurrentRoom().getRoomDescription());
        this.roomDescLabel.setWrapText(true);
        this.roomDescLabel.setPadding(new Insets(30));
        this.roomDescLabel.setAlignment(Pos.TOP_CENTER);
        this.roomDescLabel.setFont(new Font("Helvetica", this.fontSize));
        this.roomDescLabel.setTextFill(Color.web(this.colourScheme.regularFontColour));
        this.roomDescLabel.setMinWidth(1000);
        this.roomDescLabel.setMinHeight(350);
        this.roomDescLabel.setBackground(new Background(new BackgroundFill(Color.valueOf(this.colourScheme.backgroundColour), CornerRadii.EMPTY, Insets.EMPTY)));
        roomPane.add(this.roomDescLabel, 0, 3, 5, 1);

        // add background
        String roomName = this.game.player.getCurrentRoom().getRoomName().replaceAll("\\s", "");
        roomPane.setStyle("-fx-background-image: url('OtherFiles/Images/" + this.colourScheme.colourSchemeName + "/roomImages/" + roomName + ".jpg');");

        this.gridPane = roomPane;
        var scene = new Scene( this.gridPane ,  1000, 800);
        scene.setFill(Color.valueOf(this.colourScheme.backgroundColour));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();

        // What happens when the character clicks on the other character in the room?
        NPCButton.setOnAction(e -> {

            // it should say that voice line

            if (audio) {
                playButtonClick(); // plays the button click sound effect when pressed
            }
            String theNpcName = this.game.player.getCurrentRoom().getNPC().getName();
            // formatting is different, change that (remove all spaces)
            theNpcName = theNpcName.replace(" ","");
            System.out.println("nameeee " + theNpcName);
            playNpcAudio(theNpcName); // play that npc audio

            this.roomDescLabel.setText(this.game.player.getCurrentRoom().getNPC().getIntro());

            this.addInteractionCommands();
        });
    }

    public void addInteractionCommands() {
        // Create Buttons
        Button ignoreButton = new Button("Ignore");
        ignoreButton.setId("Ignore");
        customizeButton(ignoreButton, 150, 50, this.colourScheme.buttonColour1);
        makeButtonAccessible(ignoreButton, "Ignore button", "This button loads the ignore interaction.", "This button loads the ignore interaction. Click it in order to ignore the character.");
        ignoreButton.setOnAction(e -> {
            playButtonClick(); // adds button click sound effect
            // also stop ANY current audio, if playing
            stopIntroductionAudio();
            stopArticulation();
            // ignore success
            if (this.game.getPlayer().getCodeBytes() >= 5) {
                playCommandsAudio("ignoreSuccess");
            }
            // ignore failure
            if (this.game.getPlayer().getCodeBytes() < 5) {
                playCommandsAudio("ignoreFailure");
            }
            IgnoreCommand command1 = new IgnoreCommand(this.game.getPlayer(), this.game.getPlayer().getCurrentRoom().getNPC());
            this.roomDescLabel.setText(command1.executeCommand());
            this.addGameHeader(this.gridPane);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
        });

        Button hackButton = new Button("Hack");

        Button trustButton = new Button("Trust");
        trustButton.setId("Trust");
        customizeButton(trustButton, 150, 50, this.colourScheme.buttonColour1);

        trustButton.setOnAction(e -> {
            playButtonClick(); // adds button click sound effect
            // also stop ANY current audio, if playing
            stopIntroductionAudio();
            stopArticulation();
            // if the player wrongfully trusts a prowler, play wrongTrust.wav audio:
            if (this.game.getPlayer().getCurrentRoom().getNPC() instanceof Prowler) {
                playCommandsAudio("wrongTrust");
            }
            else if (! this.game.getPlayer().getCurrentRoom().getNPC().getTrusted()) {
                playCommandsAudio("correctTrust");
            }

            TrustCommand command2 = new TrustCommand(this.game.getPlayer(), this.game.getPlayer().getCurrentRoom().getNPC());
            this.roomDescLabel.setText(command2.executeCommand());

            this.addGameHeader(this.gridPane);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));

            if (this.game.player.getLives() <= 0) {
                this.roomDescLabel.setText("Oh no! You lost all your lives! You lost the game now.");
                PauseTransition pause2 = new PauseTransition(Duration.seconds(5));
                this.stage.close();
            }
        });

        hackButton.setId("Hack");
        customizeButton(hackButton, 150, 50, this.colourScheme.buttonColour1);
        makeButtonAccessible(hackButton, "Map Button", "This button loads the game map.", "This button loads the game map. Click on it to see where you are and navigate to other rooms.");
        addMapEvent();
        hackButton.setOnAction(e -> {
            playButtonClick(); // adds button click sound effect
            // also stop ANY current audio, if playing
            stopIntroductionAudio();
            stopArticulation();
            // if the NPC is an innocent student play wrongHack.wav audio:
            if (!(this.game.getPlayer().getCurrentRoom().getNPC() instanceof Prowler)) {
                playCommandsAudio("wrongHack");
            }
            // if the NPC has less than 2 code bytes, play hackFailure.wac audio
            if (this.game.getPlayer().getCodeBytes() < 2) {
                playCommandsAudio("hackFailure");
            }

            HackCommand command3 = new HackCommand(this.game.getPlayer(), this.game.getPlayer().getCurrentRoom().getNPC(), this);
            this.roomDescLabel.setText(command3.executeCommand());

            this.addGameHeader(this.gridPane);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
        });


        HBox commandButtons = new HBox();
        commandButtons.getChildren().addAll(ignoreButton, trustButton, hackButton);
        commandButtons.setSpacing(10);
        commandButtons.setAlignment(Pos.CENTER);
        makeButtonAccessible(trustButton, "Trust Button",  "This button loads the trust interaction.", "This button loads the trust interaction. Click the button to trust the character.");
        addInstructionEvent();
        //add all the widgets to the GridPane
        this.gridPane.add(commandButtons, 1, 3, 3, 1 );  // Add buttons
        this.gridPane.setHalignment(commandButtons, HPos.CENTER);
    }

    public void setupGridPane(GridPane gridPane) {
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf(this.colourScheme.backgroundColour),
                new CornerRadii(0),
                new Insets(0)
        )));
        // Row and Column Constraints
        ColumnConstraints column1 = new ColumnConstraints(30);
        ColumnConstraints column2 = new ColumnConstraints(320);
        ColumnConstraints column3 = new ColumnConstraints(300);
        ColumnConstraints column4 = new ColumnConstraints(320);
        ColumnConstraints column5 = new ColumnConstraints(30);
        RowConstraints row1 = new RowConstraints(80);
        RowConstraints row2 = new RowConstraints( 20);
        RowConstraints row3 = new RowConstraints(500);
        RowConstraints row4 = new RowConstraints(200);
        gridPane.getColumnConstraints().addAll(column1 , column2 , column3, column4, column5);
        gridPane.getRowConstraints().addAll(row1 , row2 , row3, row4);
    }

    public void addGameHeader(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof HBox && gridPane.getRowIndex(node) == 0 && gridPane.getColumnIndex(node) == 1) {
                gridPane.getChildren().remove(node);
                break;
            }
        }
        // Create Buttons
        menuButton = new Button("Menu");
        menuButton.setId("Save");
        customizeButton(menuButton, 175, 50, this.colourScheme.buttonColour2);
        makeButtonAccessible(menuButton, "Menu Button", "This button loads the menu.", "This button loads the menu and settings. Click it in order to change your settings.");
        addMenuEvent();

        instructionsButton = new Button("Instructions");
        instructionsButton.setId("Instructions");
        customizeButton(instructionsButton, 175, 50, this.colourScheme.buttonColour2);
        makeButtonAccessible(instructionsButton, "Instructions Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        // play instructions audio
        // also CREATE audio for prologue
        // also do all of the quest audio
        addInstructionEvent();

        mapButton = new Button("Map");
        mapButton.setId("Map");
        customizeButton(mapButton, 175, 50, this.colourScheme.buttonColour2);
        makeButtonAccessible(mapButton, "Map Button", "This button loads the game map.", "This button loads the game map. Click on it to see where you are and navigate to other rooms.");
        addMapEvent();

        petsButton = new Button("Pets");
        petsButton.setId("Pets");
        customizeButton(petsButton, 175, 50, this.colourScheme.buttonColour2);
        makeButtonAccessible(petsButton, "Pets Button", "Pets button, click to view pets.", "Pets button, click to see an overview of the pet you have currently equipped.");
        petsButton.setOnAction(e -> {
            try {
                showPets();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Player Stats
        Label stats = new Label("Lives: " + this.game.player.getLives() + "\nCode Bytes: " + this.game.player.getCodeBytes());
        stats.setFont(new Font("Helvetica", this.fontSize));
        stats.setTextFill(Color.web(this.colourScheme.regularFontColour));

        HBox header = new HBox();
        header.getChildren().addAll(menuButton, instructionsButton, mapButton, petsButton, stats);
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER);

        //add all the widgets to the GridPane
        gridPane.add(header, 1, 0, 3, 1 );  // Add buttons
        gridPane.setHalignment(header, HPos.CENTER);
    }

    public ImageView getCharacterImageView() {
        if (this.game.player instanceof AlchemistCharacter) {
            Image playerImage = new Image("OtherFiles/Images/" + this.colourScheme.colourSchemeName + "/characterImages/alchemistCharacter.png");
            ImageView imageView = new ImageView(playerImage);
            imageView.setFitWidth(400);
            imageView.setFitHeight(500);
            return imageView;
        } else if (this.game.player instanceof MageCharacter) {
            Image playerImage = new Image("OtherFiles/Images/" + this.colourScheme.colourSchemeName + "/characterImages/mageCharacter.png");
            ImageView imageView = new ImageView(playerImage);
            imageView.setFitWidth(400);
            imageView.setFitHeight(500);
            return imageView;
        } else {
            Image playerImage = new Image("OtherFiles/Images/" + this.colourScheme.colourSchemeName + "/characterImages/warriorCharacter.png");
            ImageView imageView = new ImageView(playerImage);
            imageView.setFitWidth(400);
            imageView.setFitHeight(500);
            return imageView;
        }
    }

    public ImageView getNPCImageView(NPC character) throws FileNotFoundException {
        FileInputStream path = new FileInputStream("OtherFiles/Images/" + this.colourScheme.colourSchemeName + "/npcImages/" + this.game.player.getCurrentRoom().getNPC().getName() + ".png");
        Image image = new Image(path);
        ImageView view = new ImageView(image);
        view.setFitWidth(400);
        view.setFitHeight(500);
        view.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        view.setAccessibleText(character.getIntro());
        return view;
    }

    public ImageView getPetImageView(String petType) throws FileNotFoundException {
        FileInputStream path = new FileInputStream("OtherFiles/Images/" + this.colourScheme.colourSchemeName + "/petImages/" + petType + ".jpg");
        Image image = new Image(path);
        ImageView view = new ImageView(image);
        view.setFitWidth(220);
        view.setFitHeight(220);
        view.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        view.setAccessibleText(petType);
        return view;
    }

    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h, String colour) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", this.fontSize));
        inputButton.setStyle("-fx-background-color: " + colour + "; -fx-text-fill: white;");
    }

    /*
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() throws FileNotFoundException {
        // If helpToggle is false, add instructions to the grid pane.
        if (!this.helpToggle) {
            this.gridPane = new GridPane();
            this.setupGridPane(this.gridPane);
            this.addGameHeader(this.gridPane);

            Label instructionsLabel = new Label(this.game.getInstructions());
            instructionsLabel.wrapTextProperty().setValue(true);
            instructionsLabel.setFont(new Font("Helvetica", this.fontSize));
            instructionsLabel.setTextFill(Color.web(this.colourScheme.regularFontColour));

            this.gridPane.add(instructionsLabel, 1, 2, 5, 2);
            var scene = new Scene( this.gridPane ,  1000, 800);
            scene.setFill(Color.valueOf(this.colourScheme.backgroundColour));
            this.stage.setScene(scene);
            this.helpToggle = true;
        }
        else {
            this.setRoomScene();
            this.helpToggle = false;
        }
    }

    public void showMap() throws FileNotFoundException {
        if (this.game.numOfProwlers.equals(6)) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {stopArticulation();
                stopIntroductionAudio();
                LastBattleView view = new LastBattleView(this, this.game.player);
                view.adhereToMenuSettings(fontSize, audio, music, this.colourScheme.colourSchemeName);
            });
            pause.play();
        }
        else if (this.game.player.getLives() <= 0) {
            this.roomDescLabel.setText("Oh no! You lost all your lives! You lost the game now.");
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {this.stage.close();});
            pause.play();
        }
        // If the mapToggle is false, show the map on the grid pane.
        if (!this.mapToggle) {
            // Create map background.
            this.gridPane = new GridPane();
            this.setupGridPane(this.gridPane);
            this.addGameHeader(this.gridPane);

            // Populate the room icons.
            HBox roomsRow0 = new HBox();
            HBox roomsRow1 = new HBox();
            HBox roomsRow2 = new HBox();
            VBox allRooms = new VBox();
            roomsRow0.setAlignment(Pos.CENTER);
            roomsRow1.setAlignment(Pos.CENTER);
            roomsRow2.setAlignment(Pos.CENTER);
            roomsRow0.setSpacing(10);
            roomsRow1.setSpacing(10);
            roomsRow2.setSpacing(10);
            allRooms.setSpacing(10);
            for (String roomName : this.game.rooms.keySet()) {
                Room room = this.game.rooms.get(roomName);
                // create room icon
                RoomIcon roomIcon = new UnvisitedRoomIcon(this.game, this, room);
                if (room.getVisited()) {
                    roomIcon = new VisitedRoomIcon(this.game, this, room);
                }
                // add button to map box
                if (room.yCoord == 0) {
                    roomsRow0.getChildren().add(roomIcon.getRoomButton());
                } else if (room.yCoord == 1) {
                    roomsRow1.getChildren().add(roomIcon.getRoomButton());
                } else if (room.yCoord == 2) {
                    roomsRow2.getChildren().add(roomIcon.getRoomButton());
                }
            } allRooms.getChildren().addAll(roomsRow0, roomsRow1, roomsRow2);
            this.gridPane.add(allRooms, 1, 2, 5, 2);
            this.gridPane.setHalignment(allRooms, HPos.CENTER);
            var scene = new Scene( this.gridPane ,  1000, 800);
            scene.setFill(Color.valueOf(this.colourScheme.backgroundColour));
            this.stage.setScene(scene);
            this.mapToggle = true;
        }
        // If mapToggle is true, show the room scene again.
        else {
            this.setRoomScene();
            this.mapToggle = false;
        }
    }

    public void showPets() throws FileNotFoundException {
        // If helpToggle is false, add instructions to the grid pane.
        if (!this.petToggle) {
            this.gridPane = new GridPane();
            setupGridPane(this.gridPane);

            // "Meet Your Pets Label to Display at Top"
            Label meetPetsLabel = new Label("Meet The Pets");
            meetPetsLabel.setFont(new Font("Helvetica", 25));
            meetPetsLabel.setTextFill(Color.web(this.colourScheme.regularFontColour));
            meetPetsLabel.setAlignment(Pos.CENTER);
            this.gridPane.add(meetPetsLabel, 2, 0, 1, 1);
            this.gridPane.setHalignment(meetPetsLabel, HPos.CENTER);

            // PET SELECTION BUTTONS

            // NanoBunny
            NanoBunny nanoBunny = new NanoBunny();
            Button nanoBunnyButton = new Button("NanoBunny \n \n" + nanoBunny.description);
            nanoBunnyButton.setId("NanoBunny");
            nanoBunnyButton.setFont(new Font(16));
            nanoBunnyButton.setTextAlignment(TextAlignment.CENTER);
            nanoBunnyButton.setAlignment(Pos.CENTER);
            nanoBunnyButton.setStyle("-fx-background-color: " + this.colourScheme.buttonColour2 + ";");
            nanoBunnyButton.setMinHeight(500);
            nanoBunnyButton.setMinWidth(300);
            nanoBunnyButton.wrapTextProperty().setValue(true);
            nanoBunnyButton.setGraphic(getPetImageView("NanoBunny"));
            nanoBunnyButton.setContentDisplay(TOP);
            nanoBunnyButton.setStyle("-fx-background-color: "+ this.colourScheme.buttonColour1 + "; -fx-text-fill: white;");
            makeButtonAccessible(nanoBunnyButton, "NanoBunny", "Meet NanoBunny", nanoBunny.description);
            nanoBunnyButton.setOnAction(e -> {
                String string = nanoBunny.equipPet(this.game.player);
                nanoBunnyButton.setText("NanoBunny \n \n" + string);
                playButtonClick();
            });

            // VirtualVulture
            VirtualVulture virtualVulture = new VirtualVulture();
            Button virtualVultureButton = new Button("VirtualVulture \n \n" + virtualVulture.description);
            virtualVultureButton.setId("Virtual Vulture");
            virtualVultureButton.setFont(new Font(16));
            virtualVultureButton.setAlignment(Pos.CENTER);
            virtualVultureButton.setTextAlignment(TextAlignment.CENTER);
            virtualVultureButton.setStyle("-fx-background-color: " + this.colourScheme.buttonColour2 + ";");
            virtualVultureButton.setMinHeight(500);
            virtualVultureButton.setMinWidth(300);
            virtualVultureButton.wrapTextProperty().setValue(true);
            virtualVultureButton.setGraphic(getPetImageView("VirtualVulture"));
            virtualVultureButton.setContentDisplay(TOP);
            virtualVultureButton.setStyle("-fx-background-color: "+ this.colourScheme.buttonColour1 + "; -fx-text-fill: white;");
            makeButtonAccessible(virtualVultureButton, "VirtualVulture", "Meet VirtualVulture", virtualVulture.description);
            virtualVultureButton.setOnAction(e -> {
                String string = virtualVulture.equipPet(this.game.player);
                virtualVultureButton.setText("Virtual Vulture \n \n" + string);
                playButtonClick();
            });

            // MechaDoodle
            MechaDoodle mechaDoodle = new MechaDoodle();
            Button mechaDoodleButton = new Button("MechaDoodle \n \n" + mechaDoodle.description);
            mechaDoodleButton.setId("MechaDoodle");
            mechaDoodleButton.setFont(new Font(16));
            mechaDoodleButton.setAlignment(Pos.CENTER);
            mechaDoodleButton.setTextAlignment(TextAlignment.CENTER);
            mechaDoodleButton.setStyle("-fx-background-color: " + this.colourScheme.buttonColour2 + ";");
            mechaDoodleButton.setMinHeight(500);
            mechaDoodleButton.setMinWidth(300);
            mechaDoodleButton.wrapTextProperty().setValue(true);
            mechaDoodleButton.setGraphic(getPetImageView("MechaDoodle"));
            mechaDoodleButton.setContentDisplay(TOP);
            mechaDoodleButton.setStyle("-fx-background-color: "+ this.colourScheme.buttonColour1 + "; -fx-text-fill: white;");
            makeButtonAccessible(mechaDoodleButton, "MechaDoodle", "Meet MechaDoodle", mechaDoodle.description);
            mechaDoodleButton.setOnAction(e -> {
                String string = mechaDoodle.equipPet(this.game.player);
                mechaDoodleButton.setText("MechaDoodle \n \n" + string);
                playButtonClick();
            });

            // Label
            Label selectedPet = new Label("Please select a pet.");
            selectedPet.setFont(new Font("Helvetica", this.fontSize));
            selectedPet.setTextFill(Color.web(this.colourScheme.regularFontColour));
            selectedPet.setAlignment(Pos.CENTER);

            HBox pets = new HBox();
            pets.getChildren().addAll(nanoBunnyButton, virtualVultureButton, mechaDoodleButton);
            pets.setSpacing(10);
            pets.setAlignment(Pos.CENTER);

            // Continue Game Button
            Button playButton = new Button("Continue Game");
            playButton.setId("Continue Game");
            playButton.setAlignment(Pos.CENTER);
            customizeButton(playButton,250, 100, this.colourScheme.buttonColour2);
            makeButtonAccessible(playButton, "Continue", "Continue Game", "Click to view the room and continue playing game.");
            playButton.setOnAction(e -> {
                playButtonClick(); // plays the button click sound effect when pressed
                if (this.game.player != null) {
                    try {
                        showPets();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            this.gridPane.add(playButton, 3, 3, 1, 1);
            this.gridPane.setHalignment(playButton, HPos.CENTER);

            VBox petView = new VBox();
            petView.getChildren().addAll(meetPetsLabel, selectedPet, pets, playButton);
            petView.setSpacing(30);
            petView.setAlignment(Pos.CENTER);

            this.gridPane.add(petView, 2, 2);
            this.gridPane.setHalignment(petView, HPos.CENTER);

            Scene scene = new Scene(this.gridPane,  1000, 800);
            scene.setFill(Color.valueOf(this.colourScheme.backgroundColour));
            this.stage.setScene(scene);
            this.petToggle = true;
        }
        // If helpToggle is true, show the room scene again.
        else {
            if (this.game.player.getPet() instanceof NanoBunny) {
                this.setRoomScene();
                this.petToggle = false;
            }
        }
    }


    private void addMenuEvent() {
        menuButton.setOnAction(e -> {
            playButtonClick(); // add button click audio
            stopArticulation();
            gridPane.requestFocus();
//            QuestView view = new QuestView(this, this.game.quests.get(0), this.game.player);
            GameMenu menu = new GameMenu(this, music, audio, fontSize, colourScheme.colourSchemeName);
//            LastBattleView view = new LastBattleView(this, this.game.player);
        });
    }

    /**
     * This method handles the event related to the instructions button.
     */
    public void addInstructionEvent() {
        instructionsButton.setOnAction(e -> {
            playButtonClick(); // add button click audio
            stopArticulation();
            try {
                showInstructions();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void addMapEvent() {
        mapButton.setOnAction(e -> {
            stopArticulation();
            playButtonClick(); // add button click audio
            try {
                showMap();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    // these method is for playing the room descriptions (long and short)
    /**
     * This method articulates Room Descriptions
     * ____________________
     * Each room has a "short" and "long" audio that can be found
     * in the sub folders audio --> roomDescriptions
     */
    public void articulateRoomDescription() {
        if (audio) {

            String musicFile;
            String roomName = this.game.getPlayer().getCurrentRoom().getRoomName();
            System.out.println("room name " + roomName);

            musicFile = "audio/roomDescriptionAudio/" + roomName.toLowerCase() + "-long.wav";
            // System.out.println("room name again" + roomName);
            musicFile = musicFile.replace(" ", "-");

            Media sound = new Media(new File(musicFile).toURI().toString());

            roomAudioPlayer = new MediaPlayer(sound);
            roomAudioPlayer.setVolume(0.2);
            roomAudioPlayer.play();
            roomMediaPlaying = true;
        }
    }

    /**
     * stopArticulation()
     * ______________________
     * This method stops articulations 
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (roomMediaPlaying) {
            roomAudioPlayer.stop(); //shush!
            roomMediaPlaying = false;
        }
    }

    // these methods are for the main background music
    /**
     * playBackgroundMusic
     * ______________________
     * This method controls the main background music for the game.
     * It plays at 50% volume and runs indefinitely for the duration of the game.
     * The background music should be found in audio -> backgroundMusic -> backgroundMusic.wav
     */
    public void playBackgroundMusic() {
        if (audio) {
            //later switched to a "try/catch" format to fix MediaException errors
            try {
                if (!music) {
                    return;  // exit if audio has been turned off before playback starts
                }

                String musicFile = "audio/backgroundMusic/backgroundMusic.wav";

                //create a media object and media player
                Media sound = new Media(new File(musicFile).toURI().toString());
                backgroundMusicPlayer = new MediaPlayer(sound);

                // stop any existing background music before starting a new one
                stopBackgroundMusic();

                //self volume to 50% and play in a loop while the view is up
                backgroundMusicPlayer.setVolume(0.1);
                backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                backgroundMusicPlayer.play();

            } catch (Exception e) {
                System.out.println("Error loading background music: " + e.getMessage());
            }
        }
    }

    /**
     * stopBackgroundMusic
     * ______________________
     * This method stops the background music. This is useful for players that want a no audio option.
     */
    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            backgroundMusicPlayer.stop();
        }
    }

    // these methods are for the character introduction audio (during character selection)
    /**
     * playIntroductionAudio
     * ______________________
     * This method plays the character description audio for a character when they are
     * selected in the character customization screen.
     *
     */
    private void playIntroductionAudio(String audioFileName) {
        if (audio) {
            // changed to a try/catch format to avoid errors
            try {
                String musicFile = "audio/characterDescriptionAudio/" + audioFileName;
                Media sound = new Media(new File(musicFile).toURI().toString());

                // UPDATE: now the audio's should not overlap!
                // check to see if there is any audio playing and stop it
                if (introductionAudioPlayer != null && introductionAudioPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    introductionAudioPlayer.stop();
                }

                introductionAudioPlayer = new MediaPlayer(sound);
                introductionAudioPlayer.setVolume(0.2);
                // ^^ that creates a new media player
                introductionAudioPlayer.play();
                // ^^ that plays the new media player
            } catch (Exception e) {
                System.out.println("There's an error with the audio: " + e.getMessage());
            }
        }
    }

    /**
     * stopIntroductionAudio
     * ______________________
     * this method is to stop the introduction audio. It is useful for when the screen
     * is done, or the player has moved on from the "choose character" view.
     *
     */
    public void stopIntroductionAudio() {
        if (introductionAudioPlayer != null && introductionAudioPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            introductionAudioPlayer.stop();
        }
    }

    /* playButtonClick
     * ______________________
     * This method controls the button click sound effect
     */
    public void playButtonClick() {
        if (audio) {
            //later switched to a "try/catch" format to fix MediaException errors
            try {
                String musicFile = "audio/buttonClick.wav";

                //create a media object and media player
                Media sound = new Media(new File(musicFile).toURI().toString());
                buttonClickPlayer = new MediaPlayer(sound);
                buttonClickPlayer.setVolume(0.2);
                buttonClickPlayer.play();

            } catch (Exception e) {
                System.out.println("Error loading button click audio: " + e.getMessage());
            }
        }
    }

    /**
     * playNpcAudio
     * ______________________
     * This method plays the character description audio for a character when they are
     * selected in the character customization screen.
     *
     */
    private void playNpcAudio(String audioFileName) {
        if (audio) {
            // changed to a try/catch format to avoid errors
            try {
                String musicFile = "audio/npcAudio/" + audioFileName + ".wav";
                Media sound = new Media(new File(musicFile).toURI().toString());

                // check to see if there is any audio "room" playing and stop it before playing
                // the NPC audio
                if (roomAudioPlayer != null && roomAudioPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    roomAudioPlayer.stop();
                }
                if (introductionAudioPlayer != null && introductionAudioPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    introductionAudioPlayer.stop();
                }

                introductionAudioPlayer = new MediaPlayer(sound);
                introductionAudioPlayer.setVolume(0.2);
                // ^^ that creates a new media player
                introductionAudioPlayer.play();
                // ^^ that plays the new media player
            } catch (Exception e) {
                System.out.println("There's an error with the audio: " + e.getMessage());
            }
        }
    }


    // these methods are to play audio for the commands
    /**
     * playCommandsAudip
     * ______________________
     * This method plays the audio for the commands (i.e. the buttons pressed when the
     * user interacts with an NPC character).
     *
     */
    private void playCommandsAudio(String audioFileName) {
        if (audio) {
            // changed to a try/catch format to avoid errors
            try {
                String musicFile = "audio/commandsAudio/" + audioFileName + ".wav";
                Media sound = new Media(new File(musicFile).toURI().toString());

                // check to see if there is any previous commands audio playing and stop that
                if (commandsPlayer != null && commandsPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    commandsPlayer.stop();
                }

                // check to see if there are any room descriptions playing and stop that
                if (roomAudioPlayer != null && roomAudioPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    roomAudioPlayer.stop();
                }

                // check to see if there are any character instructions playing and stop that
                if (introductionAudioPlayer != null && introductionAudioPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    introductionAudioPlayer.stop();
                }

                commandsPlayer = new MediaPlayer(sound);
                commandsPlayer.setVolume(0.2);
                // ^^ that creates a new media player
                commandsPlayer.play();
                // ^^ that plays the new media player
            } catch (Exception e) {
                System.out.println("There's an error with the audio: " + e.getMessage());
            }
        }
    }

}

