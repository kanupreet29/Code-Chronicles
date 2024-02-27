package View;

import GameModel.CodeChroniclesGame;
import GameModel.Room;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.AccessibleRole;

import java.io.File;

import static javafx.scene.control.ContentDisplay.TOP;
import static javafx.scene.layout.GridPane.getColumnIndex;
import static javafx.scene.layout.GridPane.getRowIndex;
import static jdk.dynalink.linker.support.Guards.isInstance;

public class VisitedRoomIcon implements RoomIcon{
    Room room;
    CodeChroniclesGame game;
    CodeChroniclesGameView gameView;
    Button iconButton;
    Image roomImage;
    String previewText;

    public VisitedRoomIcon(CodeChroniclesGame game, CodeChroniclesGameView gameView, Room room) {
        this.room = room;
        this.game = game;
        this.gameView = gameView;
        this.iconButton = new Button(room.getRoomName());
        this.previewText = room.getRoomDescription();
        String roomName = room.getRoomName().replaceAll("\\s", "");
        this.roomImage = new Image("OtherFiles/Images/" + this.gameView.colourScheme.colourSchemeName + "/roomImages/" + roomName + ".jpg");
        this.formatIcon();
    }

    public void formatIcon() {
        // Format Visuals
        this.iconButton.setMinWidth(175);
        this.iconButton.setMinHeight(200);
        this.iconButton.setId(this.room.getRoomName());
        this.iconButton.setGraphic(this.getRoomImage());
        this.iconButton.setContentDisplay(TOP);
        this.iconButton.setStyle("-fx-background-color: "+ this.gameView.colourScheme.buttonColour1 + "; -fx-text-fill: white;");
        this.iconButton.setAlignment(Pos.CENTER);
        // Create preview of room is player clicks on room button.
        this.iconButton.setOnAction(e -> {
            RoomPreview preview = new RoomPreview(this, this.gameView);
        });
    }

    public Button getRoomButton() {
        return this.iconButton;
    }

    public ImageView getRoomImage() {
        ImageView view = new ImageView(this.roomImage);
        view.setFitWidth(170);
        view.setFitHeight(150);
        return view;
    };

    public Room getRoom() {
        return this.room;
    };

    public String getPreviewName() {
        return this.room.getRoomName();
    };

    public String getPreviewText() {
        return this.previewText;
    };

    public void movePlayerToRoom() {

    }

}
