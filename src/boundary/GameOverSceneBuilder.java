package boundary;

import controllers.MainWindow;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameOverSceneBuilder {

    public static Scene build(String endMsg) {
        BorderPane rootW = new BorderPane();
        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        Text title = new Text(endMsg);
        MainWindow.styleText(title, 70, "linear-gradient(darkorange, maroon)");
        top.getChildren().add(title);
        VBox buttons = new VBox();
        buttons.setSpacing(30);
        buttons.setAlignment(Pos.CENTER);
        Button playBtn = new Button("NEW GAME");
        playBtn.setOnAction(e -> {
            MainWindow.newGame();
        });
        Button exitBtn = new Button("QUIT");
        exitBtn.setOnAction(e -> {
            MainWindow.closeStage();
        });

        MainWindow.styleButton(playBtn, 40);
        MainWindow.styleButton(exitBtn, 40);

        buttons.getChildren().addAll(playBtn, exitBtn);
        rootW.setTop(top);
        rootW.setAlignment(top, Pos.CENTER);
        rootW.setCenter(buttons);
        rootW.setStyle("-fx-background-color: linear-gradient(black, peachpuff);");
        return new Scene(rootW, MainWindow.getScreenWidth(), MainWindow.getScreenHeight());
    }
}
