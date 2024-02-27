package View;
import GameModel.Pet.VirtualVulture;
import InteractingWithPlayer.Quest;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import InteractingWithPlayer.Player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class QuestView {
    BorderPane borderPane = new BorderPane();
    private CodeChroniclesGameView gameView;
    private Stage stage = new Stage();
    private Button continueButton = new Button("Continue");
    private Button challengeButton = new Button("Accept the challenge!");
    private Button submitButton = new Button("Submit");
    private Button exitButton = new Button("Exit");
    private Quest quest;
    private Player player;
    private Integer increaseFont=0;
    private boolean ifAudio=true;
    private boolean ifMusic=true;
    private String colorScheme="GameTheme";
    private MediaPlayer questMusicPlayer; // for the quest background music


    public QuestView(CodeChroniclesGameView gameView, Quest quest, Player player) {
        this.gameView = gameView;
        this.quest = quest;
        this.player = player;

        // test to see if the quest audio still plays if the no audio setting
        // is selected in the menu
    }
    public void intiUI() {
        // SET THE STAGE UP
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.initOwner(gameView.stage);
        this.stage.setTitle(this.quest.questName);
        this.stage.initStyle(StageStyle.UNDECORATED);

        // boolean allAudioOn2 = this.gameView.allAudioOn;

        // also stop the old background music
        this.gameView.stopBackgroundMusic();

        // call the method to play reduced background music until
        // the quest is over and this screen can finally be exited


        if (ifMusic) {
            this.playBackgroundMusic();
        }

        // Set up the borderPane
        this.borderPane.setPadding(new Insets(10));

        // Create alertLabel
        Label alertLabel = new Label("You have decided to hack " + this.quest.prowler.getProwlerName() + "... A PROWLER!");
        alertLabel.setPadding(new Insets(20));
        alertLabel.setFont(new Font("Georgia", 22 + increaseFont));
        alertLabel.setTextFill(Color.valueOf("white"));
        alertLabel.setBackground(new Background(new BackgroundFill(Color.valueOf("#000000"), CornerRadii.EMPTY, Insets.EMPTY)));
        BorderPane.setAlignment(alertLabel, Pos.TOP_CENTER);
        this.borderPane.setTop(alertLabel);

        // Create continueButton
        this.continueButton.setId("Continue");
        this.continueButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        this.continueButton.setFont(new Font("Georgia", 16 + increaseFont));
        makeButtonAccessible(continueButton, "Continue", "Continue", "Continue to the next screen.");
        this.continueButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick(); // plays the button click sound effect when pressed
            }
            setInstructionScene();
        });

        BorderPane.setAlignment(continueButton, Pos.BOTTOM_RIGHT);
        this.borderPane.setBottom(continueButton);

        // Set up borderPane
        try {
            Image image = new Image(new FileInputStream("OtherFiles/Images/" + this.colorScheme + "/prowlerImages/" + this.quest.prowler.getProwlerName() + ".png"));
            borderPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(730, 730, true, true, true, false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // CREATE A SCREEN
        var questScene1 = new Scene(borderPane, 730, 730);
        this.stage.setScene(questScene1);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public void setInstructionScene() {
        // Set up the borderPane
        BorderPane borderPane2 = new BorderPane();

        borderPane2.setPadding(new Insets(10));

        try {
            Image image = new Image(new FileInputStream("OtherFiles/Images/" + this.colorScheme + "/questScreens/instruction screen.png"));
            borderPane2.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(730, 730, true, true, true, false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create challengeButton
        this.challengeButton.setId("Accept the Challenge!");
        this.challengeButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        this.challengeButton.setFont(new Font("Georgia", 16 + increaseFont));
        makeButtonAccessible(challengeButton, "Challenge", "Accept the challenge!", "Accept the challenge and see the question.");
        challengeButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick();
            }
            setQuestionScene(false);
        });
        borderPane2.setAlignment(challengeButton, Pos.BOTTOM_RIGHT);
        borderPane2.setBottom(challengeButton);

        // Create questIntroLabel
        Label questIntroLabel = new Label("Now, you must play their Quest!");
        questIntroLabel.setFont(new Font("Georgia", 30 + increaseFont));
        questIntroLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        borderPane2.setAlignment(questIntroLabel, Pos.TOP_CENTER);
        borderPane2.setTop(questIntroLabel);

        // Create instructionsLabel
        Label instructionsLabel =
                new Label("Welcome to " + this.quest.prowler.getProwlerName() + "'s Quest, " + this.quest.questName +". Here are the instructions:\n" +
                "1. Objective: To win, you must get the correct answer in two attempts or less.\n" +
                        "2. Game Format: You'll be presented with one multiple-choice question (MCQ) where you have to choose the correct answer out of the four provided.\n" +
                        "3. Attempts:\n" +
                        "- First Attempt: Answer the question. If you get it right on the first try, you will win and earn 10 code bytes.\n" +
                        "- Second Attempt: If you don't answer correctly in the first try, you get a hint from your pet. But this will only " +
                        "get you 6 code bytes, if you answer correctly.\n" +
                        "- Virtual Vulture: If you have the virtual vulture, you can use it to reveal the answer, but this will only get you " +
                        "2 code bytes, if you answer correctly.\n" +
                        "4. Winning and losing: If you get the right answer, you win, but if you don't get it even after 2 attempts, you lose " +
                        "a life and get 0 codebytes.\n" +
                        "Remember to use your attempts wisely and make appropriate choices. You may now proceed.");
        instructionsLabel.setWrapText(true);
        instructionsLabel.setFont(new Font("Georgia", 18 + increaseFont));
        instructionsLabel.setTextFill(Color.valueOf("black"));
        if (this.colorScheme.equals("GameTheme")) {
            instructionsLabel.setBackground(new Background(new BackgroundFill(Color.valueOf("#37a6a4"), CornerRadii.EMPTY, Insets.EMPTY)));
            instructionsLabel.setStyle("-fx-border-color: yellow;");
            instructionsLabel.setOpacity(0.90);
        }
        else {
            instructionsLabel.setStyle("-fx-border-color: black;");
            instructionsLabel.setBackground(new Background(new BackgroundFill(Color.valueOf("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY)));
            instructionsLabel.setOpacity(0.40);
        }


        instructionsLabel.setPadding(new Insets(10));
        borderPane2.setAlignment(instructionsLabel, Pos.CENTER_LEFT);
        borderPane2.setCenter(instructionsLabel);
        var questScene2 = new Scene(borderPane2, 730, 730);
        this.stage.setScene(questScene2);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public void setQuestionScene(boolean doneTwoAttempts) {
        BorderPane borderPane3 = new BorderPane();
        // Set up borderPane
        borderPane3.setPadding(new Insets(10));

        try {
            Image image = new Image(new FileInputStream("OtherFiles/Images/" + this.colorScheme + "/questScreens/question screen.png"));
            borderPane3.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(730, 730, true, true, true, false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create submitButton
        this.submitButton.setId("Submit Answer");
        this.submitButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        this.submitButton.setFont(new Font("Georgia", 16 + increaseFont));
        makeButtonAccessible(submitButton, "Submit", "Submit answer", "Submit your answer to be evaluated.");
        borderPane3.setAlignment(submitButton, Pos.BOTTOM_RIGHT);
        borderPane3.setBottom(submitButton);

        // Create Options Toggle Group
        final ToggleGroup optionsGroup = new ToggleGroup();

        RadioButton optionA = new RadioButton(this.quest.questionOptions.get(0)); RadioButton optionB = new RadioButton(this.quest.questionOptions.get(1));

        optionA.setToggleGroup(optionsGroup); optionB.setToggleGroup(optionsGroup);

        RadioButton optionC = new RadioButton(this.quest.questionOptions.get(2));  RadioButton optionD = new RadioButton(this.quest.questionOptions.get(3));

        optionC.setToggleGroup(optionsGroup); optionD.setToggleGroup(optionsGroup);

        ArrayList<RadioButton> radioButtons = new ArrayList<RadioButton>();
        radioButtons.add(optionA); radioButtons.add(optionB); radioButtons.add(optionC); radioButtons.add(optionD);

        for (RadioButton radioButton : radioButtons) {
            if (increaseFont > 0) {
                radioButton.setMinHeight(180);
                radioButton.setMinWidth(330);
                radioButton.setMaxHeight(180);
                radioButton.setMaxWidth(330);
            }
            else {
                radioButton.setMinHeight(145);
                radioButton.setMinWidth(250);
                radioButton.setMaxHeight(145);
                radioButton.setMaxWidth(250);
            }
            radioButton.setFont(new Font("Georgia", 16 + increaseFont));
            if (this.colorScheme.equals("GameTheme")) {
                radioButton.setStyle("-fx-background-color: #7286b8; -fx-text-fill: white; -fx-border-color: black;");
            }
            else {
                radioButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-color: black;");
            }
            radioButton.setPadding(new Insets(10));
            radioButton.setOpacity(0.9);
            radioButton.setWrapText(true);
        }

        // Create VBox for options A and C
        VBox optionsLeft = new VBox();
        optionsLeft.setSpacing(10);
        optionsLeft.getChildren().addAll(optionA, optionC);
        optionsLeft.setAlignment(Pos.CENTER_LEFT);

        // Create VBox for options B and D
        VBox optionsRight = new VBox();
        optionsRight.setSpacing(10);
        optionsRight.getChildren().addAll(optionB, optionD);
        optionsRight.setAlignment(Pos.CENTER_RIGHT);

        // Create HBox to hold optionsLeft and optionsRight
        HBox options = new HBox();
        options.setSpacing(10);
        options.getChildren().addAll(optionsLeft, optionsRight);
        options.setAlignment(Pos.CENTER);

        borderPane3.setAlignment(options, Pos.CENTER);
        borderPane3.setCenter(options);

        // Create questionLabel
        Label questionLabel = new Label(this.quest.questQuestion);
        questionLabel.setFont(new Font("Georgia", 22 + increaseFont));
        questionLabel.setWrapText(true);
        questionLabel.setTextFill(Color.valueOf("white"));
        questionLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        borderPane3.setAlignment(questionLabel, Pos.TOP_CENTER);
        borderPane3.setTop(questionLabel);

        this.submitButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick(); // plays the button click sound effect when pressed
            }
            RadioButton selectedRadioButton = (RadioButton) optionsGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                if (selectedRadioButton.getText().equals(this.quest.getQuestAnswer())) {
                    setEndScene("won");
                    this.quest.setIfWon(true);
                    this.gameView.game.numOfProwlers -= 1;
                    this.quest.prowler.setDefeated(true);
                    if (this.quest.isWithHint()) {
                        this.player.updateCodeBytes(6);
                    }
                    else if (this.quest.isWithAnswer()) {
                        this.player.updateCodeBytes(2);
                    }
                    else {
                        this.player.updateCodeBytes(10);
                    }
                }
                else if (! selectedRadioButton.getText().equals(this.quest.getQuestAnswer()) & ! doneTwoAttempts) {
                    setHintScene();
                }
                else if (! selectedRadioButton.getText().equals(this.quest.getQuestAnswer()) & doneTwoAttempts) {
                    setEndScene("lost");
                    this.quest.setIfWon(false);
                }
            }
        });

        var questScene3 = new Scene(borderPane3, 730, 730);
        this.stage.setScene(questScene3);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public void setEndScene(String status) {
        BorderPane borderPane4 = new BorderPane();
        // Set up the borderPane
        borderPane4.setPadding(new Insets(10));

        try {
            Image image = new Image(new FileInputStream("OtherFiles/Images/" + this.colorScheme + "/questScreens/question screen.png"));
            borderPane4.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(730, 730, true, true, true, false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create questOutroLabel
        String endLine = (status.equals("won")) ? "You won the Quest!" : "You lost the Quest!";
        Label questOutroLabel = new Label(endLine);
        questOutroLabel.setFont(new Font("Georgia", 55 + increaseFont));
        questOutroLabel.setStyle("-fx-background-color: #000000;");
        questOutroLabel.setTextFill(Color.valueOf("white"));
        borderPane4.setAlignment(questOutroLabel, Pos.CENTER);
        borderPane4.setCenter(questOutroLabel);

        //Create prowlerDialogueLabel
//        Label prowlerDialogueLabel = new Label("I can't believe this! How dare those pathetic humans\n" +
//                                                "defeat me? Me, a prowler of unparalleled power! They\n" +
//                                                "think they can just come in with their feeble tricks\n" +
//                                                "and take me down? I'll show them! I'll rise again, fiercer\n" +
//                                                "and stronger than ever before, and crush their puny\n" +
//                                                "existence into dust beneath my claws! They'll regret\n" +
//                                                "the day they dared to challenge me!");

        // Create exitButton
        this.exitButton.setId("Exit");
        this.exitButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        this.exitButton.setFont(new Font("Georgia", 16 + increaseFont));
        makeButtonAccessible(exitButton, "Exit", "Exit the quest", "Exit the quest and return to the room.");
        this.exitButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick(); // plays the button click sound effect when pressed
            }
            // stop the quest background music
            stopBackgroundMusic();
            // here, after clicking the button and returning, begin playing the background music again
            if (ifAudio) {
                this.gameView.playBackgroundMusic();
            }
            this.stage.close();
        });

        borderPane4.setAlignment(this.exitButton, Pos.BOTTOM_RIGHT);
        borderPane4.setBottom(this.exitButton);

        var questScene4 = new Scene(borderPane4, 730, 730);
        this.stage.setScene(questScene4);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public void setHintScene() {
        BorderPane borderPane5 = new BorderPane();
        // Set up the borderPane
        borderPane5.setPadding(new Insets(10));

        try {
            Image image = new Image(new FileInputStream("OtherFiles/Images/" + this.colorScheme + "/questScreens/question screen.png"));
            borderPane5.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(730, 730, true, true, true, false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create a hintLabel
        Label hintLabel = new Label("Oops! Wrong Answer. Please select one of the following options to help you " +
                "in the next attempt.");
        hintLabel.setFont(new Font("Georgia", 30 + increaseFont));
        hintLabel.setWrapText(true);
        hintLabel.setTextFill(Color.valueOf("white"));
        hintLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #000000");
        hintLabel.setPadding(new Insets(10));
        borderPane5.setAlignment(hintLabel, Pos.TOP_CENTER);
        borderPane5.setTop(hintLabel);

        // Create a hintButton
        Button hintButton = new Button("Hint");
        makeButtonAccessible(hintButton, "Hint", "This is a hint to the question.", this.quest.getQuestHint());
        hintButton.setMinWidth(250);
        hintButton.setFont(new Font("Georgia", 18 + increaseFont));
        if (this.colorScheme.equals("GameTheme")) {
            hintButton.setStyle("-fx-background-color: #7286b8; -fx-text-fill: white; -fx-border-color: black;");
        }
        else {
            hintButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-color: black;");
        }
        hintButton.setPadding(new Insets(10));
        hintButton.setOpacity(0.9);
        hintButton.setWrapText(true);
        hintButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick(); // plays the button click sound effect when pressed
            }
            hintButton.setText(this.quest.getQuestHint());
            this.quest.usedHint();
        });


        // Create a revealAnswerButton
        Button revealAnswerButton = new Button("Reveal Answer");
        makeButtonAccessible(revealAnswerButton, "Reveal Answer", "This is the answer to the question", "Please click on the button to see the answer");
        revealAnswerButton.setMinWidth(250);
        revealAnswerButton.setFont(new Font("Georgia", 18 + increaseFont));

        if (this.colorScheme.equals("GameTheme")) {
            revealAnswerButton.setStyle("-fx-background-color: #7286b8; -fx-text-fill: white; -fx-border-color: black;");
        }
        else {
            revealAnswerButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-color: black;");
        }

        revealAnswerButton.setPadding(new Insets(10));
        revealAnswerButton.setOpacity(0.9);
        revealAnswerButton.setWrapText(true);
        hintButton.setWrapText(true);
        revealAnswerButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick(); // plays the button click sound effect when pressed
            }
            if (this.player.getPet() instanceof VirtualVulture) {
                revealAnswerButton.setText(this.quest.getQuestAnswer());
                this.quest.usedAnswer();
            }
            else {
                revealAnswerButton.setText("Option not available; you don't have a Virtual Vulture as a pet.");
            }
        });

        // Create HBox for the buttons
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(hintButton, revealAnswerButton);
        buttons.setAlignment(Pos.CENTER);

        borderPane5.setAlignment(buttons, Pos.CENTER);
        borderPane5.setCenter(buttons);

        // Create Back button
        Button back = new Button("Back to the Question");
        back.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        back.setFont(new Font("Georgia", 16 + increaseFont));
        makeButtonAccessible(back, "Back", "Back to the question", "Go back to the question using this button.");
        back.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick(); // plays the button click sound effect when pressed
            }
            if (this.quest.isWithHint() || this.quest.isWithAnswer()) {
                setQuestionScene(true);
            }
        });
        borderPane5.setAlignment(back, Pos.BOTTOM_RIGHT);
        borderPane5.setBottom(back);

        var questScene5 = new Scene(borderPane5, 730, 730);
        this.stage.setScene(questScene5);
        this.stage.setResizable(false);
        this.stage.show();
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


    // these methods are for the quest
    /**
     * playBackgroundMusic
     * ______________________
     * This method controls the main background music for the game.
     * It plays at 50% volume and runs indefinitely for the duration of the game.
     * The background music should be found in audio -> backgroundMusic -> backgroundMusic.wav
     */
    public void playBackgroundMusic() {
        // if (allAudioOn2) {
            //later switched to a "try/catch" format to fix MediaException errors
            try {
                String musicFile = "audio/backgroundMusic/hackingMusic.wav";

                //create a media object and media player
                Media sound = new Media(new File(musicFile).toURI().toString());
                questMusicPlayer = new MediaPlayer(sound);

                //self volume to 50% and play in a loop while the view is up
                questMusicPlayer.setVolume(0.1);
                questMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                questMusicPlayer.play();

            } catch (Exception e) {
                System.out.println("Error loading background music: " + e.getMessage());
            }
        }
    // }
    public void adhereToMenuSettings(Integer fontIncrease, boolean audio, boolean music, String colorScheme) {
        this.increaseFont = fontIncrease;
        this.ifAudio = audio;
        this.ifMusic = music;
        this.colorScheme = colorScheme;
        intiUI();
    }

    /**
     * stopBackgroundMusic
     * ______________________
     * This method stops the background music. This is useful for players that want a no audio option.
     */
    public void stopBackgroundMusic() {
        if (questMusicPlayer != null && questMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            questMusicPlayer.stop();
        }
    }
}



