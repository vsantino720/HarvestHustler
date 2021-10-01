package boundary;

import controllers.MainWindow;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class WelcomeScreenBuilder {

    private Button playBtn;
    private Button exitBtn;

    public Scene build() {
        BorderPane rootW = new BorderPane();
        VBox top = new VBox();
        top.setAlignment(Pos.CENTER);
        Text title = new Text("HARVEST HUSTLER");
        MainWindow.styleText(title, 70, "linear-gradient(darkorange, maroon)");
        top.getChildren().add(title);
        VBox buttons = new VBox();
        buttons.setSpacing(30);
        buttons.setAlignment(Pos.CENTER);
        playBtn = new Button("PLAY");
        exitBtn = new Button("QUIT");

        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.TOP_CENTER);
        infoBox.setStyle("-fx-border-width: 10; -fx-border-color: black; -fx-border-radius: 2;"
                + "-fx-background-color: linear-gradient(black, chocolate);"
                + "-fx-background-radius: 7;");
        Text info = new Text(" Welcome to Harvest Hustler,\n the game where young\n "
                + "entrepreneurial farmers can test\n their business acumen! "
                + "The goal\n is to try and build up a\n prosperous farm to make as"
                + "\n much money as you can! The\n game will end when you no\n longer "
                + "have enough liquid\n money or crops to continue!\n"
                + " You will have a variety of tools\n at your disposal: Pesticide,"
                + "\n Fertilizer, Tractors, Irrigation\n and most importantly:\n your MIND!");
        MainWindow.styleText(info, 20, "peachpuff");
        infoBox.getChildren().add(info);

        MainWindow.styleButton(playBtn, 40);
        MainWindow.styleButton(exitBtn, 40);

        buttons.getChildren().addAll(playBtn, exitBtn);
        //top.getChildren().add(info);
        rootW.setTop(top);
        rootW.setAlignment(top, Pos.CENTER);
        rootW.setCenter(buttons);
        rootW.setRight(infoBox);
        rootW.setAlignment(info, Pos.CENTER);
        rootW.setStyle("-fx-background-color: linear-gradient(black, peachpuff);");
        return new Scene(rootW, MainWindow.getScreenWidth(), MainWindow.getScreenHeight());
    }

    public Button getPlayBtn() {
        return playBtn;
    }
    public Button getExitBtn() {
        return exitBtn;
    }
}
