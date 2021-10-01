package boundary;

import controllers.InitialConfigurationController;
import controllers.MainWindow;
import entities.Crop;
import entities.Season;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ConfigSceneBuilder {
    public static Scene build() {
        InitialConfigurationController controller = MainWindow.getConfigController();

        VBox rootC = new VBox();
        HBox nameList = new HBox();

        Text namePrompt = new Text("Enter your name:");
        namePrompt.setFont(new Font("arial black", 20));
        TextField nameEnter = new TextField();
        Text errorMsg = new Text();
        nameList.getChildren().addAll(namePrompt, nameEnter, errorMsg);
        nameList.setSpacing(15);

        HBox difficultyList = new HBox();

        Button button1 = new Button("Easy");
        button1.setOnAction(e -> controller.difficultyHandler1());
        Button button2 = new Button("Medium");
        button2.setOnAction(e -> controller.difficultyHandler2());
        Button button3 = new Button("Hard");
        button3.setOnAction(e -> controller.difficultyHandler3());
        difficultyList.getChildren().addAll(button1, button2, button3);
        difficultyList.setSpacing(10);
        MainWindow.styleButton(button1, 20);
        MainWindow.styleButton(button2, 20);
        MainWindow.styleButton(button3, 20);

        HBox cropList = new HBox();

        for (Crop crop : controller.getAllCrops()) {
            Button button = new Button(crop.getName());
            cropList.getChildren().add(button);
            button.setOnAction(e -> {
                controller.cropHandler(crop);
            });
            MainWindow.styleButton(button, 20);
        }
        cropList.setSpacing(10);

        HBox seasonList = new HBox();

        Season[] seasons = Season.values();
        for (Season season : seasons) {
            Button button = new Button(season.getName());
            seasonList.getChildren().add(button);
            button.setOnAction(e -> {
                controller.seasonHandler(season);
            });
            MainWindow.styleButton(button, 20);
        }
        seasonList.setSpacing(10);

        Button next = new Button("Next");
        next.setOnAction(e -> {
            if (nameEnter.getText() == null || nameEnter.getText().equals("")) {
                errorMsg.setText("Please enter valid name");
                errorMsg.setStyle("-fx-fill: red; -fx-font-weight: bold;");
            } else {
                String name = nameEnter.getText();
                if (name.length() > 15) {
                    name = name.substring(0, 16);
                }
                controller.nameHandler(name);
                controller.initializeGame();
                MainWindow.goToNewPlayScene();
            }
        });
        MainWindow.styleButton(next, 30);
        rootC.getChildren().addAll(nameList, difficultyList, cropList, seasonList, next);
        rootC.setAlignment(Pos.CENTER);
        rootC.setSpacing(20);
        rootC.setStyle("-fx-background-color: linear-gradient(black, peachpuff);");

        return new Scene(rootC, MainWindow.getScreenWidth(), MainWindow.getScreenHeight());
    }
}
