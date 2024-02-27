package View;

import GameModel.Pet.MechaDoodle;
import InteractingWithPlayer.Player.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LastBattleView {
    BorderPane borderPane = new BorderPane();
    private CodeChroniclesGameView gameView;
    private Stage stage = new Stage();
    private Button continueButton = new Button("Continue");
    private Button challengeButton = new Button("Accept the challenge!");
    private Button submitButton = new Button("Submit");
    private Button exitButton = new Button("Exit");
    private Player player;
    private boolean ifAudio=true;
    private boolean ifMusic=true;
    private Integer increaseFont=0;
    private String colorScheme="GameTheme";
    private Integer current_question=0; // the current question in the battle (first -> 0, second -> 1, third -> 2)
    private MediaPlayer battleMusicPlayer; // for the battle background music

    public LastBattleView(CodeChroniclesGameView gameView, Player player) {
        this.gameView = gameView;
        this.player = player;
    }
    public void intiUI() {

        // SET THE STAGE UP
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.initOwner(gameView.stage);
        this.stage.setTitle("Battle with the Incognito Phantom!");
        this.stage.initStyle(StageStyle.UNDECORATED);

        // also stop the old background music
        this.gameView.stopBackgroundMusic();

        // call the method to play reduced background music until
        // the quest is over and this screen can finally be exited
        if (ifMusic) {
            this.playBackgroundMusic();
        }

        // Set up the borderPane
        this.borderPane.setPadding(new Insets(10));
        try {
            Image image = new Image(new FileInputStream("OtherFiles/Images/" + this.colorScheme + "/lastBattleImages/Incognito Phantom.png"));
            borderPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(600, 600, true, true, true, false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create alertLabel
        Label alertLabel = new Label("It is I, the Inconginto Phantom. I am here to defeat you.");
        alertLabel.setPadding(new Insets(5));
        alertLabel.setMaxWidth(450);
        alertLabel.setFont(new Font("Georgia", 20));
        alertLabel.setTextFill(Color.valueOf("white"));
        alertLabel.setBackground(new Background(new BackgroundFill(Color.valueOf("navy"), CornerRadii.EMPTY, Insets.EMPTY)));
        alertLabel.setWrapText(true);
        alertLabel.setOpacity(0.85);

        // Create continueButton
        this.continueButton.setId("Continue");
        this.continueButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        this.continueButton.setMaxWidth(90);
        this.continueButton.setPrefSize(90, 30);
        this.continueButton.setFont(new Font("Georgia", 16));
        makeButtonAccessible(continueButton, "Continue", "Continue", "Continue to the next screen.");
        this.continueButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick();
            }
            setInstructionScene();
        });

        // Create HBox for label and button
        HBox bottomItems = new HBox();
        bottomItems.setSpacing(10);
        bottomItems.getChildren().addAll(alertLabel, this.continueButton);
        bottomItems.setAlignment(Pos.CENTER);

        this.borderPane.setAlignment(bottomItems, Pos.BOTTOM_CENTER);
        this.borderPane.setBottom(bottomItems);

        var battleScene1 = new Scene(borderPane, 600, 600);
        this.stage.setScene(battleScene1);
        this.stage.setResizable(false);
        this.stage.show();
    }

    private void setInstructionScene() {
        BorderPane borderPane2 = new BorderPane();
        // Set up the borderPane
        borderPane2.setPadding(new Insets(10));
        try {
            Image image = new Image(new FileInputStream("OtherFiles/Images/" + this.colorScheme + "/lastBattleImages/instruction screen.png"));
            borderPane2.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(600, 600, true, true, true, false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create challengeButton
        this.challengeButton.setId("Accept the Challenge!");
        this.challengeButton.setPrefSize(200, 30);
        this.challengeButton.setStyle("-fx-background-color: navy; -fx-text-fill: white;");
        this.challengeButton.setFont(new Font("Georgia", 16));
        makeButtonAccessible(challengeButton, "Challenge", "Accept the challenge!", "Accept the challenge and see the question.");

        this.challengeButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick();
            }
            setQuestionScene(false);
        });

        borderPane2.setAlignment(challengeButton, Pos.BOTTOM_RIGHT);
        borderPane2.setBottom(challengeButton);

        // Create questIntroLabel
        Label questIntroLabel = new Label("Now, you must battle with me to win this game!");
        questIntroLabel.setFont(new Font("Georgia", 22));
        questIntroLabel.setWrapText(true);
        questIntroLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        borderPane2.setAlignment(questIntroLabel, Pos.TOP_CENTER);
        borderPane2.setTop(questIntroLabel);

        Label instructionsLabel =
                new Label("Welcome to the Incognito Prowler's Quest, the Final Conquest. The rules are the same as before, except:\n" +
                        "1. Objective: To win, you must get the correct answer to all the 3 questions in two attempts or less, each.\n" +
                        "2. Game Format: You'll be presented with 3 multiple-choice questions (MCQ) where you will have to choose the correct answer out of the four provided.\n" +
                        "3. Attempts:\n" +
                        "- First Attempt: Answer the question. If you get it right on the first try, you will move to the next question.\n" +
                        "- Second Attempt: If you don't answer correctly in the first try, you get a hint from your pet, but only if you have " +
                        "the Mecha Doodle.\n" +
                        "- Virtual Vulture and Nano Bunny are not allowed in this battle. Therefore, answers cannot be revealed either.\n" +
                        "4. Winning and losing: If you answer all the questions correctly, you win the game. If you answer even a single question" +
                        " wrong after your two attempts for it, you lose the game.\n" +
                        "Remember to use your attempts wisely and make appropriate choices. You may now proceed.");
        instructionsLabel.setWrapText(true);
        instructionsLabel.setFont(new Font("Georgia", 18));
        instructionsLabel.setTextFill(Color.valueOf("black"));
        instructionsLabel.setBackground(new Background(new BackgroundFill(Color.valueOf("#37a6a4"), CornerRadii.EMPTY, Insets.EMPTY)));
        instructionsLabel.setOpacity(0.90);
        instructionsLabel.setStyle("-fx-border-color: yellow;");
        instructionsLabel.setPadding(new Insets(10));
        borderPane2.setAlignment(instructionsLabel, Pos.CENTER_LEFT);
        borderPane2.setCenter(instructionsLabel);

        var questScene2 = new Scene(borderPane2, 600, 600);
        this.stage.setScene(questScene2);
        this.stage.setResizable(false);
        this.stage.show();
    }

    private void setQuestionScene(boolean doneTwoAttempts) {
        BorderPane borderPane3 = new BorderPane();
        // Set up borderPane
        borderPane3.setPadding(new Insets(10));

        try {
            Image image = new Image(new FileInputStream("OtherFiles/Images/" + this.colorScheme + "/lastBattleImages/question screen.png"));
            borderPane3.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(600, 600, true, true, true, false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create submitButton
        this.submitButton.setId("Submit Answer");
        this.submitButton.setPrefSize(200, 30);
        this.submitButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        this.submitButton.setFont(new Font("Georgia", 16));
        makeButtonAccessible(submitButton, "Submit", "Submit answer", "Submit your answer to be evaluated.");
        borderPane3.setAlignment(submitButton, Pos.BOTTOM_RIGHT);
        borderPane3.setBottom(submitButton);

        // Create Options Toggle Group
        final ToggleGroup optionsGroup = new ToggleGroup();

        RadioButton optionA = new RadioButton(this.gameView.game.lastBattleQuestions.get(current_question).options.get(0));
        RadioButton optionB = new RadioButton(this.gameView.game.lastBattleQuestions.get(current_question).options.get(1));

        optionA.setToggleGroup(optionsGroup); optionB.setToggleGroup(optionsGroup);

        RadioButton optionC = new RadioButton(this.gameView.game.lastBattleQuestions.get(current_question).options.get(2));
        RadioButton optionD = new RadioButton(this.gameView.game.lastBattleQuestions.get(current_question).options.get(3));

        optionC.setToggleGroup(optionsGroup); optionD.setToggleGroup(optionsGroup);

        ArrayList<RadioButton> radioButtons = new ArrayList<RadioButton>();
        radioButtons.add(optionA); radioButtons.add(optionB); radioButtons.add(optionC); radioButtons.add(optionD);

        for (RadioButton radioButton : radioButtons) {
            radioButton.setMinHeight(80);
            radioButton.setMinWidth(250);
            radioButton.setMaxHeight(80);
            radioButton.setMaxWidth(250);
            radioButton.setFont(new Font("Georgia", 16));
            radioButton.setStyle("-fx-background-color: #7286b8; -fx-text-fill: white; -fx-border-color: black;");
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
        Label questionLabel = new Label(this.gameView.game.lastBattleQuestions.get(current_question).question);
        questionLabel.setFont(new Font("Georgia", 30));
        questionLabel.setWrapText(true);
        questionLabel.setTextFill(Color.valueOf("white"));
        questionLabel.setPadding(new Insets(10));
        questionLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: black;");
        borderPane3.setAlignment(questionLabel, Pos.TOP_CENTER);
        borderPane3.setTop(questionLabel);

        this.submitButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick();
            }
            RadioButton selectedRadioButton = (RadioButton) optionsGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                if (selectedRadioButton.getText().equals(this.gameView.game.lastBattleQuestions.get(current_question).getAnswer())) {
                    if (this.current_question.equals(2)) {
                        setEndScene("won");
                    }
                    else {
                        setTransitionScene();
                        this.current_question += 1;
                    }
                }
                else if (! selectedRadioButton.getText().equals(this.gameView.game.lastBattleQuestions.get(current_question).getAnswer()) & ! doneTwoAttempts) {
                    setHintScene();
                }
                else if (! selectedRadioButton.getText().equals(this.gameView.game.lastBattleQuestions.get(current_question).getAnswer()) & doneTwoAttempts) {
                    setEndScene("lost");
                }
            }
        });

        var questScene3 = new Scene(borderPane3, 600, 600);
        this.stage.setScene(questScene3);
        this.stage.setResizable(false);
        this.stage.show();
    }

    private void setTransitionScene() {
        BorderPane borderPane4 = new BorderPane();
        // Set up borderPane
        borderPane4.setPadding(new Insets(10));

        try {
            Image image = new Image(new FileInputStream("OtherFiles/Images/" + this.colorScheme + "/lastBattleImages/question screen.png"));
            borderPane4.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(600, 600, true, true, true, false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create transitionLabel
        Label transitionLabel = new Label("Correct! Click next to go to the\nnext question.");
        transitionLabel.setFont(new Font("Georgia", 40));
        transitionLabel.setWrapText(true);
        transitionLabel.setTextAlignment(TextAlignment.CENTER);
        transitionLabel.setPadding(new Insets(10));
        transitionLabel.setStyle("-fx-background-color: #000000;");
        transitionLabel.setTextFill(Color.valueOf("white"));
        borderPane4.setAlignment(transitionLabel, Pos.CENTER);
        borderPane4.setCenter(transitionLabel);

        // Create nextButton
        Button nextButton = new Button("Next");
        nextButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        nextButton.setPrefSize(90, 30);
        nextButton.setFont(new Font("Georgia", 16));
        makeButtonAccessible(nextButton, "Next", "Next question", "CLick this button to go to the next question.");
        nextButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick();
            }
            setQuestionScene(false);
        });

        borderPane4.setAlignment(nextButton, Pos.BOTTOM_RIGHT);
        borderPane4.setBottom(nextButton);

        var questScene4 = new Scene(borderPane4, 600, 600);
        this.stage.setScene(questScene4);
        this.stage.setResizable(false);
        this.stage.show();
    }

    private void setHintScene() {
        BorderPane borderPane5 = new BorderPane();
        /// Set up borderPane
        borderPane5.setPadding(new Insets(10));

        try {
            Image image = new Image(new FileInputStream("OtherFiles/Images/" + this.colorScheme + "/lastBattleImages/question screen.png"));
            borderPane5.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(600, 600, true, true, true, false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create a hintLabel
        Label hintLabel = new Label("Oops! Wrong Answer. Please click on the Hint button to see a hint.");
        hintLabel.setFont(new Font("Georgia", 30));
        hintLabel.setWrapText(true);
        hintLabel.setTextFill(Color.valueOf("white"));
        hintLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #000000");
        hintLabel.setPadding(new Insets(10));
        borderPane5.setAlignment(hintLabel, Pos.TOP_CENTER);
        borderPane5.setTop(hintLabel);

        // Create a hintButton
        Button hintButton = new Button("Hint");
        makeButtonAccessible(hintButton, "Hint", "This is a hint to the question.", "Click on the button to see the hint.");
        hintButton.setMinHeight(80);
        hintButton.setMinWidth(250);
        hintButton.setFont(new Font("Georgia", 18));
        hintButton.setStyle("-fx-background-color: #7286b8; -fx-text-fill: white; -fx-border-color: black;");
        hintButton.setPadding(new Insets(10));
        hintButton.setOpacity(0.9);
        hintButton.setWrapText(true);
        hintButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick();
            }
            if (this.player.getPet() instanceof MechaDoodle) {
                hintButton.setText(this.gameView.game.lastBattleQuestions.get(current_question).getHint());
            }
            else {
                hintButton.setText("Option not available; you don't have a Mecha Doodle as a pet.");
            }
        });
        borderPane5.setAlignment(hintButton, Pos.CENTER);
        borderPane5.setCenter(hintButton);

        // Create Back button
        Button back = new Button("Back to the Question");
        back.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        back.setMinWidth(100);
        back.setMinHeight(30);
        back.setFont(new Font("Georgia", 16));
        makeButtonAccessible(back, "Back", "Back to the question", "Go back to the question using this button.");
        back.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick();
            }
            setQuestionScene(true);
        });
        borderPane5.setAlignment(back, Pos.BOTTOM_RIGHT);
        borderPane5.setBottom(back);

        var questScene5 = new Scene(borderPane5, 600, 600);
        this.stage.setScene(questScene5);
        this.stage.setResizable(false);
        this.stage.show();
    }

    private void setEndScene(String status) {
        BorderPane borderPane6 = new BorderPane();
        // Set up borderPane
        borderPane6.setPadding(new Insets(10));

        try {
            Image image = new Image(new FileInputStream("OtherFiles/Images/" + this.colorScheme + "/lastBattleImages/end screen.png"));
            borderPane6.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(600, 600, true, true, true, false))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create questOutroLabel
        String endLine = (status.equals("won")) ? "You won the game!" : "You lost the game!";
        Label questOutroLabel = new Label(endLine);
        questOutroLabel.setFont(new Font("Georgia", 60));
        questOutroLabel.setStyle("-fx-background-color: #000000;");
        questOutroLabel.setTextFill(Color.valueOf("white"));
        borderPane6.setAlignment(questOutroLabel, Pos.CENTER);
        borderPane6.setCenter(questOutroLabel);

        // Create exitButton
        this.exitButton.setId("Exit");
        this.exitButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
        this.exitButton.setPrefSize(90, 30);
        this.exitButton.setFont(new Font("Georgia", 16));
        makeButtonAccessible(exitButton, "Exit", "Exit the battle window", "Exit the battle window and return to the previous screen.");
        this.exitButton.setOnAction(e -> {
            if (ifAudio) {
                this.gameView.playButtonClick();
            }
            this.gameView.stage.close();
        });

        borderPane6.setAlignment(this.exitButton, Pos.BOTTOM_RIGHT);
        borderPane6.setBottom(this.exitButton);

        var questScene4 = new Scene(borderPane6, 600, 600);
        this.stage.setScene(questScene4);
        this.stage.setResizable(false);
        this.stage.show();
    }

    public void adhereToMenuSettings(Integer fontIncrease, boolean audio, boolean music, String colorScheme) {
        this.increaseFont = fontIncrease;
        this.ifAudio = audio;
        this.ifMusic = music;
        this.colorScheme = colorScheme;
        intiUI();
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
            battleMusicPlayer = new MediaPlayer(sound);

            //self volume to 50% and play in a loop while the view is up
            battleMusicPlayer.setVolume(0.1);
            battleMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            battleMusicPlayer.play();

        } catch (Exception e) {
            System.out.println("Error loading background music: " + e.getMessage());
        }
    }
    // }

    /**
     * stopBackgroundMusic
     * ______________________
     * This method stops the background music. This is useful for players that want a no audio option.
     */
    public void stopBackgroundMusic() {
        if (battleMusicPlayer != null && battleMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            battleMusicPlayer.stop();
        }
    }
}