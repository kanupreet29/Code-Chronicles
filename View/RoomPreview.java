package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static View.CodeChroniclesGameView.makeButtonAccessible;

public class RoomPreview {

    private Label previewText;
    private CodeChroniclesGameView gameView;

    public RoomPreview(RoomIcon icon, CodeChroniclesGameView gameView) {

        // Setting up scene.
        this.gameView = gameView;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.gameView.stage);
        dialog.setResizable(false);
        GridPane pane = new GridPane();
        pane.setBackground(new Background(new BackgroundFill(
                Color.valueOf(gameView.colourScheme.backgroundColour),
                new CornerRadii(0),
                new Insets(0)
        )));
        pane.setAlignment(Pos.CENTER);

        // Creating Preview Text Content
        Label previewTitle = new Label(icon.getPreviewName());
        previewTitle.setFont(new Font("Helvetica", 25));
        previewTitle.setTextFill(Color.web(gameView.colourScheme.headingsFontColour));
        this.previewText = new Label(icon.getPreviewText());
        this.previewText.setWrapText(true);
        this.previewText.setMinHeight(100);
        this.previewText.setFont(new Font("Helvetica", gameView.fontSize));
        this.previewText.setTextFill(Color.web(gameView.colourScheme.regularFontColour));
        this.previewText.setPadding(new Insets(10));

        VBox dialogVbox = new VBox();
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: " + this.gameView.colourScheme.backgroundColour + ";");

        // Creating "Go Here" Button
        // Play Game Button
        Button goButton = new Button("Go Here");
        goButton.setId("Go");
        goButton.setAlignment(Pos.CENTER);
        goButton.setFont(new Font("Arial", this.gameView.fontSize));
        goButton.setStyle("-fx-background-color: " + this.gameView.colourScheme.buttonColour2 + "; -fx-text-fill: white;");
        makeButtonAccessible(goButton, "Go", "Go Here", "Click to go to the place previewed.");
        goButton.setOnAction(e -> {
            if (gameView.game.getPlayer().getCodeBytes() < 1) {
                this.previewText.setText("You don't have enough CodeBytes to unlock a new room.");
            } else {
                this.gameView.game.getPlayer().setCurrentRoom(icon.getRoom());
                try {
                    this.gameView.setRoomScene();
                    dialog.close();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Adding Content to VBox
        VBox preview = new VBox();
        preview.getChildren().addAll(previewTitle, icon.getRoomImage(), this.previewText, goButton);
        preview.setAlignment(Pos.CENTER);
        preview.setSpacing(20);
        pane.add(preview, 0, 0);

        // Setting up Scene
        Scene dialogScene = new Scene(pane, 600, 600);
        dialogScene.setFill(Color.valueOf(this.gameView.colourScheme.backgroundColour));
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
