package View;

import GameModel.CodeChroniclesGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GameMenu {

    GridPane gridPane = new GridPane(); //to hold images and buttons
    private Stage stage = new Stage();
    private ComboBox<String> musicBox;
    private ComboBox<String> audioBox;
    private ComboBox<String> colourModeBox = new ComboBox<String>();
    private Spinner<Integer> fontSizeBox;
    private Button restartButton = new Button("Restart Game");;
    private Button saveChangesButton = new Button("Save Changes");

    private CodeChroniclesGameView gameView;
    private String saveAudio;
    private String saveMusic;
    private Integer saveFontSize;
    private String saveColourScheme;

    public GameMenu(CodeChroniclesGameView gameView, boolean ifMusic, boolean ifAudio, Integer sizeOfFont, String colorScheme) {
        this.gameView = gameView;
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.initOwner(gameView.stage);

        // Create Menu Label
        Label menu = new Label("Menu");
        menu.setAlignment(Pos.BASELINE_CENTER);
        menu.setFont(new Font("Helvetica", 30));
        menu.setMaxHeight(50);
        menu.setTextFill(Color.web(this.gameView.colourScheme.buttonColour1));

        // Create Colour Mode label
        Label colourTheme = new Label("Theme");
        colourTheme.setTextFill(Color.web(this.gameView.colourScheme.regularFontColour));
        colourTheme.setFont(new Font("Helvetica", this.gameView.fontSize));

        // Create colour mode box
        this.colourModeBox = new ComboBox<String>();
        this.colourModeBox.getItems().add("Monochrome");
        this.colourModeBox.getItems().add("Game Theme");
        this.colourModeBox.setValue(colorScheme);
        this.colourModeBox.setMaxWidth(150);

        // Create Font Size Label
        Label fontSize = new Label("Font Size");
        fontSize.setTextFill(Color.web(this.gameView.colourScheme.regularFontColour));
        fontSize.setFont(new Font("Helvetica", this.gameView.fontSize));

        // Create font size box
        this.fontSizeBox = new Spinner<Integer>(12, 20, 16);
        this.fontSizeBox.getValueFactory().setValue(sizeOfFont);

        // Create Music Label
        Label music = new Label("Music");
        music.setTextFill(Color.web(this.gameView.colourScheme.regularFontColour));
        menu.setFont(new Font("Helvetica", this.gameView.fontSize));

        // Create music box
        this.musicBox = new ComboBox<String>();
        this.musicBox.getItems().add("On");
        this.musicBox.getItems().add("Off");
        this.musicBox.setValue((ifMusic) ? "On" : "Off");

        // Create Audio Label
        Label audio = new Label("Sounds");
        audio.setTextFill(Color.web(this.gameView.colourScheme.regularFontColour));
        audio.setFont(new Font("Helvetica", this.gameView.fontSize));

        // Create audio box
        this.audioBox = new ComboBox<String>();
        this.audioBox.getItems().add("On");
        this.audioBox.getItems().add("Off");
        this.audioBox.setValue((ifAudio) ? "On" : "Off");

        // Create Restart Button
        this.restartButton.setId("Restart Game");
        this.restartButton.setStyle("-fx-background-color:" + this.gameView.colourScheme.buttonColour1 + "; -fx-text-fill: white;");
        this.restartButton.setPrefSize(200, 50);
        this.restartButton.setFont(new Font(16));
        this.restartButton.setOnAction(e -> {
            this.gameView.playButtonClick(); // plays the button click sound effect when pressed
            try {
                this.restart();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        CodeChroniclesGameView.makeButtonAccessible(this.restartButton, "Restart Game", "This is a button to restart the game", "Use this button to restart the game. It will load the game from the beginning, and you will lose your progress.");

        // Create Save Changes Button
        this.saveChangesButton.setId("Save Changes");
        this.saveChangesButton.setStyle("-fx-background-color:" + this.gameView.colourScheme.buttonColour1 + "; -fx-text-fill: white;");
        this.saveChangesButton.setPrefSize(200, 50);
        this.saveChangesButton.setFont(new Font(16));
        this.saveChangesButton.setOnAction(e ->this.save_changes());
        CodeChroniclesGameView.makeButtonAccessible(this.saveChangesButton, "Save Changes", "This is a button to save your game's settings", "Use this button to save your game's settings. It will update the game based on the changes you have made.");

        // Add Buttons to Horizontal Box
        HBox buttons = new HBox();
        buttons.getChildren().addAll(restartButton, saveChangesButton);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);

        //Set Up GridPane
        ColumnConstraints column = new ColumnConstraints(100);
        RowConstraints row = new RowConstraints(100);
        this.gridPane.getColumnConstraints().addAll(column, column, column, column, column, column, column);
        this.gridPane.getRowConstraints().addAll(row, row, row, row);
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf(this.gameView.colourScheme.backgroundColour),
                new CornerRadii(0),
                new Insets(0)
        )));
        // Add Everything to GridPane
        // GridPane.add(columnIndex, rowIndex, columnSpan, rowSpan)
        this.gridPane.add(menu, 3, 0, 1, 1);
        this.gridPane.add(buttons, 0, 3, 7, 1);
        this.gridPane.add(colourTheme, 1, 1, 2, 1);
        this.gridPane.add(this.colourModeBox, 2, 1, 2, 1);
        this.gridPane.add(fontSize, 1, 2, 2, 1);
        this.gridPane.add(this.fontSizeBox, 2, 2, 2, 1);
        this.gridPane.add(audio, 4, 1, 2, 1);
        this.gridPane.add(this.audioBox, 5, 1, 2, 1);
        this.gridPane.add(music, 4, 2, 2, 1);
        this.gridPane.add(this.musicBox, 5, 2, 2, 1);

        Scene dialogScene = new Scene(gridPane, 700, 400);
        this.stage.setScene(dialogScene);
        this.stage.setResizable(false);
        this.stage.show();
    }
    // getter methods for music, audio, font
    public ComboBox<String> getMusicBox(){
        return this.musicBox;
    }
    public ComboBox<String> getAudioBox(){
        return this.audioBox;
    }
    public ComboBox<String> getColourModeBox(){
        return this.colourModeBox;
    }
    public Spinner<Integer> getFontSizeBox(){
        return this.fontSizeBox;
    }

    /**
     * This method restarts the whole game but does not change the menu settings done by the player.
     * @throws IOException
     */


    public void restart() throws IOException {
        //restart the game
        this.gameView.stopBackgroundMusic();
        this.saveMusic = this.musicBox.getValue();
        this.saveAudio = this.audioBox.getValue();
        String saveColourScheme = this.colourModeBox.getValue();
        this.saveFontSize = this.fontSizeBox.getValue();
        CodeChroniclesGame newGame = new CodeChroniclesGame(); //change the name of the game if you want to try something bigger!
        CodeChroniclesGameView gameView = new CodeChroniclesGameView(newGame, new Stage());
//        this.gameView = gameView;
//        this.musicBox.setValue(this.saveMusic);
//        this.audioBox.setValue(this.saveAudio);
//        this.colourModeBox.setValue(saveColourScheme);
//        this.fontSizeBox.getValueFactory().setValue(this.saveFontSize);
    }

    /**
     * This method saves the changes after the player selects the menu
     */

    public void save_changes() {
        // check value of music box, update this.gameView.music
        if (this.musicBox.getValue().equals("On")){
            // fix the functionality of this
            this.gameView.music = true;
            this.gameView.playBackgroundMusic();
        }
        else {
            this.gameView.music = false;
            this.gameView.stopBackgroundMusic();
        }

        // check value of audio box, update this.gameView.audio
        // problem rn: off turns off only voice not background music
        if (this.audioBox.getValue().equals("On")) {
            // Start or resume playing music when audio is turned on
            this.gameView.audio = true;
            this.gameView.playBackgroundMusic();
        } else {
            this.gameView.audio = false;
            // Stop music when audio is turned off
            this.gameView.stopBackgroundMusic();
        }

        // check value of colour scheme box, update this.colourScheme.music
        this.gameView.colourScheme = new ColourScheme(this.colourModeBox.getValue());

        // check value of font size box, update this.colourScheme.font size
        this.gameView.fontSize = this.fontSizeBox.getValue();

        try {
            this.gameView.setRoomScene();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.stage.close(); // close the stage after saving the changes
    }

}
