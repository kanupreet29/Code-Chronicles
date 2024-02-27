import GameModel.CodeChroniclesGame;
import javafx.application.Application;
import javafx.stage.Stage;
import View.CodeChroniclesGameView;

import java.io.IOException;
import java.util.Map;

/**
 * Class AdventureGameApp.
 */
public class GameApp extends Application {

    CodeChroniclesGame model;
    CodeChroniclesGameView view;

    public static void main(String[] args) {
        launch(args);
    }

    /*
    * JavaFX is a Framework, and to use it we will have to
    * respect its control flow!  To start the game, we need
    * to call "launch" which will in turn call "start" ...
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.model = new CodeChroniclesGame(); //change the name of the game if you want to try something bigger!
        this.view = new CodeChroniclesGameView(model, primaryStage);
    }

}
