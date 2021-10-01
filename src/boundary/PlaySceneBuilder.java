package boundary;

import controllers.MainWindow;
import controllers.PlotController;
import controllers.PlotUIController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PlaySceneBuilder {

    private static Button[] plots;
    private static Label moneyLabel = new Label();
    private static Label dayLabel = new Label();
    private static Label seasonLabel = new Label();
    private static Label playerLabel = new Label();
    private static Label waterMax = new Label();
    private static Label harvestMax = new Label();
    private static ComboBox<String> seedBox = new ComboBox();
    private static ComboBox<String> toolBox = new ComboBox<>();
    private static Text waterInfo = new Text();
    private static PlotUIController plotUI = new PlotUIController();
    private static PlotController plotController;
    private static BorderPane rootP;
    private static GridPane farm = new GridPane();

    public static Scene build() {
        plotController = MainWindow.getPlotController();
        rootP = new BorderPane();

        VBox right = new VBox();
        right.setMaxWidth(450);

        farm.setHgap(5);
        farm.setVgap(5);

        createPlots(farm);

        HBox farmDisplay = new HBox();
        farmDisplay.setAlignment(Pos.CENTER);
        farmDisplay.getChildren().add(farm);

        VBox info = new VBox();
        info.setSpacing(20);
        info.setAlignment(Pos.BASELINE_CENTER);
        info.setStyle("-fx-border-width: 10; -fx-border-color: peachpuff; -fx-border-radius: 5;"
                + "-fx-background-color: linear-gradient(darkorange, brown);"
                + "-fx-background-radius: 5;");

        Button dayButton = new Button("Advance Day");
        MainWindow.styleButton(dayButton, 20);
        dayButton.setOnAction(e -> MainWindow.nextDay());

        Font labelFont = new Font("impact", 20);
        dayLabel.setFont(labelFont);
        dayLabel.setStyle("-fx-text-fill: black;");
        moneyLabel.setFont(labelFont);
        moneyLabel.setStyle("-fx-text-fill: black;");
        seasonLabel.setFont(labelFont);
        seasonLabel.setStyle("-fx-text-fill: black;");
        playerLabel.setFont(labelFont);
        playerLabel.setStyle("-fx-text-fill: black;");
        waterMax.setFont(labelFont);
        waterMax.setStyle("-fx-text-fill: black;");
        harvestMax.setFont(labelFont);
        harvestMax.setStyle("-fx-text-fill: black;");

        info.getChildren().add(dayButton);
        info.getChildren().add(playerLabel);
        info.getChildren().add(moneyLabel);
        info.getChildren().add(dayLabel);
        info.getChildren().add(seasonLabel);
        info.getChildren().add(waterMax);
        info.getChildren().add(harvestMax);

        right.setAlignment(Pos.CENTER);
        right.setSpacing(10);
        right.getChildren().add(waterInfo);
        right.getChildren().add(farmDisplay);

        //Plant ComboBox for when the player needs to plant new crop.
        HBox plantBox = new HBox();
        Label plantLabel = new Label("PLANT:");
        Label pestLabel = new Label("TOOLS:");
        plantLabel.setFont(labelFont);
        pestLabel.setFont(labelFont);
        plantBox.getChildren().add(plantLabel);
        plantBox.getChildren().add(seedBox);
        plantBox.getChildren().add(pestLabel);
        plantBox.getChildren().add(toolBox);

        plantBox.setAlignment(Pos.CENTER);
        plantBox.setSpacing(20);
        right.getChildren().add(plantBox);

        Button invButton = new Button("Inventory");
        MainWindow.styleButton(invButton, 30);
        invButton.setOnAction(e -> MainWindow.goToInventory());
        right.getChildren().add(invButton);

        Button marketButton = new Button("Market");
        MainWindow.styleButton(marketButton, 30);
        marketButton.setOnAction(e -> MainWindow.goToMarket());
        right.getChildren().add(marketButton);

        rootP.setCenter(right);
        rootP.setLeft(info);
        //rootP.setAlignment(info, Pos.CENTER);

        Scene playScene = new Scene(rootP, MainWindow.getScreenWidth(),
                                    MainWindow.getScreenHeight());
        return playScene;
    }

    private static void createPlots(GridPane farm) {
        Button plot1 = new Button("Plot 1");
        Button plot2 = new Button("Plot 2");
        Button plot3 = new Button("Plot 3");
        Button plot4 = new Button("Plot 4");
        Button plot5 = new Button("Plot 5");
        Button plot6 = new Button("Plot 6");
        Button plot7 = new Button("Plot 7");
        Button plot8 = new Button("Plot 8");
        Button plot9 = new Button("Plot 9");
        Button plot10 = new Button("Plot 10");
        Button plot11 = new Button("Plot 11");
        Button plot12 = new Button("Plot 12");
        Button plot13 = new Button("Plot 13");
        Button plot14 = new Button("Plot 14");
        Button plot15 = new Button("Plot 15");
        Button plot16 = new Button("Plot 16");

        plots = new Button[]{plot1, plot2, plot3, plot4, plot5,
                             plot6, plot7, plot8, plot9, plot10,
                             plot11, plot12, plot13, plot14, plot15, plot16};

        int plotSize = 110;

        //Set button behavior and style
        for (int i = 0; i < plots.length; i++) {
            styleButton(plots[i]);
            plots[i].setPrefSize(plotSize, plotSize);
        }
        plotController.setOnActionPlotButtons();
    }

    public static void generatePlots(int size) {
        farm.getChildren().clear();
        for (int i = 0; i < size; i++) {
            int row = i / 4;
            int column = i % 4;
            farm.add(plots[i], row, column);
        }
    }

    public static Label getMoneyLabel() {
        return moneyLabel;
    }
    public static Label getDayLabel() {
        return dayLabel;
    }
    public static Button[] getPlots() {
        return plots;
    }

    public static void styleButton(Button plot) {
        plot.setStyle("-fx-background-color: chocolate; "
                + "-fx-text-fill: darkgreen; -fx-background-radius: 4; "
                + "-fx-border-color: darkgreen; -fx-border-radius: 4; "
                + "-fx-border-width: 4; -fx-font-size: 10");
        plot.setOnMouseEntered(plotUI);
        plot.setOnMouseExited(plotUI);
    }

    public static Text getWaterInfo() {
        return waterInfo;
    }

    public static ComboBox<String> getSeedBox() {
        return seedBox;
    }

    public static BorderPane getRoot() {
        return rootP;
    }

    public static Label getSeasonLabel() {
        return seasonLabel;
    }

    public static Label getPlayerLabel() {
        return playerLabel;
    }

    public static ComboBox<String> getToolBox() {
        return toolBox;
    }

    public static Label getWaterMax() {
        return waterMax;
    }

    public static Label getHarvestMax() {
        return harvestMax;
    }
}
