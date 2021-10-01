package controllers;

import boundary.*;
import entities.Crop;
import entities.Item;
import entities.Plot;
import entities.Upgrades;
import javafx.collections.ListChangeListener;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class MainWindow extends Application {

    private static int screenWidth = 1000;
    private static int screenHeight = 650;
    private static Stage mainStage;
    private static Scene playScene;
    private static InitialConfigurationController controller = new InitialConfigurationController();
    private static MarketplaceController marketController = new MarketplaceController();
    private static PlotController plotController = new PlotController();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        mainStage = primaryStage;
        //Initialize Screens
        WelcomeScreenBuilder wScreen = new WelcomeScreenBuilder();
        //Initialize Welcome Screen
        Scene welcomeScene = wScreen.build();
        wScreen.getPlayBtn().setOnAction(e -> {
            mainStage.setScene(ConfigSceneBuilder.build());
            addListenerCrops();
            addListenerItems();
        });
        wScreen.getExitBtn().setOnAction(e -> {
            closeStage();
        });
        primaryStage.setTitle("Harvest Hustler");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    public static void addListenerCrops() {
        ObservableList playerCrops = controller.getInitialPlayer().getInventory().getCrops();
        playerCrops.addListener(new ListChangeListener<Crop>() {
            @Override
            public void onChanged(Change<? extends Crop> change) {
                updateInv();
                updateMarketplace();
                updateSeedBox();
            }
        });
    }

    public static void addListenerItems() {
        ObservableList playerItems = controller.getInitialPlayer().getInventory().getItems();
        playerItems.addListener(new ListChangeListener<Item>() {
            @Override
            public void onChanged(Change<? extends Item> change) {
                InventorySceneBuilder.generateItemLabels(playerItems);
                updateMarketplace();
            }
        });
    }

    public static void styleButton(Button btn, int size) {
        MainMenuBtnController uiControl = new MainMenuBtnController();
        Font impact = new Font("Impact", size);
        btn.setFont(impact);
        btn.setStyle("-fx-background-color: black; -fx-text-fill: navajowhite; "
                + "-fx-background-radius: 10; -fx-border-color: navajowhite; "
                + "-fx-border-radius: 7; -fx-border-width: 5;");
        btn.setOnMouseEntered(uiControl);
        btn.setOnMouseExited(uiControl);
        btn.setOnMousePressed(uiControl);
        btn.setOnMouseReleased(uiControl);
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void goToNewPlayScene() {
        playScene = PlaySceneBuilder.build();
        PlaySceneBuilder.getPlayerLabel().setText("PLAYER: "
                + controller.getInitialPlayer().getName());
        updateMoneyLabel();
        updateDayLabel();
        updateToolBox();
        nextDay();
        PlaySceneBuilder.generatePlots(controller.getInitialPlayer().getPlanted().length);
        mainStage.setScene(playScene);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeight(500);
        a.setWidth(500);
        a.setTitle("Your Farm");
        a.setHeaderText("Welcome to your farm screen");
        a.setContentText("You can plant seeds by selecting them in the dropbox below and "
                + "then clicking a plot. If your plant water level is below 30% or above 80%,"
                + " it will die. Make sure to water them by selecting the water tool and right "
                + "clicking on your crops. \n\nYour crops will grow as you advance the day, "
                + "but lookout for weather events such as rain, droughts, and locusts!\n\n"
                + "You can harvest crops that are fully grown and sell them in the market, "
                + "but you do have a daily water and harvest limit that can be upgraded with"
                + " irrigation or a tractor! \n\nYou can also purchase pesticides to protect "
                + "crops from locusts, and fertilizer to speed up growth process! Apply "
                + "these by selecting them in tool box and right clicking your plots!"
                + "\n\nGood Luck!");
        a.showAndWait();
    }

    public static InitialConfigurationController getConfigController() {
        return controller;
    }

    public static MarketplaceController getMarketController() {
        return marketController;
    }

    public static void goToPlayScene() {
        updatePlots();
        updateMoneyLabel();
        updateDayLabel();
        mainStage.setScene(playScene);
    }

    public static void updateInv() {
        //loops through player's crops and adds them to the inventory screen.
        ObservableList playerCrops = controller.getInitialPlayer().getInventory().getCrops();
        ObservableList playerItems = controller.getInitialPlayer().getInventory().getItems();
        InventorySceneBuilder.generateSeedLabels(playerCrops);
        InventorySceneBuilder.generateCropLabels(playerCrops);
        InventorySceneBuilder.generateItemLabels(playerItems);
    }

    public static void updateMoneyLabel() {
        PlaySceneBuilder.getMoneyLabel().setText("MONEY: $"
                + Math.round(100 * controller.getInitialPlayer().getMoney()) / 100.0);
    }

    public static void updateDayLabel() {
        PlaySceneBuilder.getDayLabel().setText("DAY " + controller.getGame().getDay());
        PlaySceneBuilder.getSeasonLabel().setText(controller.getGame().getSeason().getName());
        updateDailyMaxLabels();
        PlaySceneBuilder.getRoot().setStyle("-fx-background-color: linear-gradient("
                + controller.getGame().getSeason().getColor() + ", lightgreen);");
    }

    public static void updateDailyMaxLabels() {
        PlaySceneBuilder.getWaterMax().setText("Water: " + controller.getGame().getWaterMax());
        PlaySceneBuilder.getHarvestMax().setText("Harvests: "
                + controller.getGame().getHarvestMax());
    }

    public static void nextDay() {
        if (controller.getInitialPlayer().getMoney() == 0
            && controller.getInitialPlayer().getInventory().getCrops().size() != 0) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("CANNOT ADVANCE DAY!");
            a.setHeaderText("You cannot afford to continue.");
            a.setContentText("You must sell your remaining assets to continue!");
            a.showAndWait();
            return;
        }
        controller.getGame().incrementDay();
        marketController.updateMultiplier();
        plotController.ageCrops();
        updateDayLabel();
        updatePlots();
        plotController.attemptWeather();
        updatePlots();
        updateMoneyLabel();
        if (controller.getGame().gameOver()) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("GAME OVER");
            a.setHeaderText("You have exhausted your resources!");
            a.setContentText("You do not have enough money or crops to keep your farm!"
                    + "\nTry again!");
            a.showAndWait();
            mainStage.setScene(GameOverSceneBuilder.build("GAME OVER"));
        } else if (controller.getGame().winGame()) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("YOU WON");
            a.setHeaderText("Your farm is the most successful on the planet!");
            a.setContentText("Great work!");
            a.showAndWait();
            mainStage.setScene(GameOverSceneBuilder.build("YOU WON!"));
        }
    }

    public static void newGame() {
        controller = new InitialConfigurationController();
        marketController = new MarketplaceController();
        plotController = new PlotController();
        addListenerCrops();
        addListenerItems();
        mainStage.setScene(ConfigSceneBuilder.build());
    }

    public static void goToInventory() {
        updateInv();
        mainStage.setScene(InventorySceneBuilder.build());
    }

    public static void goToMarket() {
        updateInv();
        updateMarketplace();
        mainStage.setScene(MarketplaceBuilder.build());
    }

    public static void closeStage() {
        mainStage.close();
    }

    public static void styleText(Text text, int fontSize, String color) {
        if (color.equals("standard")) {
            color = "linear-gradient(darkorange, maroon)";
        }
        text.setFont(new Font("arial black", fontSize));
        text.setStyle("-fx-fill: " + color + "; -fx-stroke: black; "
                + "-fx-stroke-width: " + (fontSize / 15) + ";");
    }

    public static void updateMarketplace() {
        ComboBox seedsToBuy = MarketplaceBuilder.getSeedsToBuy();
        ComboBox seedsToSell = MarketplaceBuilder.getSeedsToSell();
        ComboBox cropsToSell = MarketplaceBuilder.getCropsToSell();
        ComboBox itemsToBuy = MarketplaceBuilder.getItemsToBuy();
        ComboBox upgrades = MarketplaceBuilder.getUpgrades();
        seedsToBuy.getItems().clear();
        seedsToSell.getItems().clear();
        cropsToSell.getItems().clear();
        itemsToBuy.getItems().clear();
        upgrades.getItems().clear();
        for (Crop x: controller.getAllCrops()) {
            seedsToBuy.getItems().addAll(x.getType() + " " + "$"
                    + Math.round(100 * x.getBasePrice()
                    * marketController.getBuyMultiplier()) / 100.0);
        }
        for (Crop x: controller.getInitialPlayer().getInventory().getCrops()) {
            if (x.getStage().equals("Seed")) {
                seedsToSell.getItems().addAll(x.getType() + " " + "$"
                        + Math.round(100 * x.getBasePrice()
                        * marketController.getSellMultiplier()) / 100.0);
            } else {
                cropsToSell.getItems().addAll(x.getType() + " " + "$"
                        + Math.round(100 * x.getBasePrice()
                        * marketController.getSellMultiplier()) / 100.0);
            }
        }
        for (Item x: controller.getAllItems()) {
            itemsToBuy.getItems().add(x.getType() + " " + "$"
                    + Math.round(100 * x.getBasePrice()
                    * marketController.getBuyMultiplier()) / 100.0);
        }
        if (!controller.getInitialPlayer().hasTractor()) {
            upgrades.getItems().add(Upgrades.TRACTOR);
        }
        if (!controller.getInitialPlayer().hasIrrigation()) {
            upgrades.getItems().add(Upgrades.IRRIGATION);
        }
        if (controller.getInitialPlayer().getPlanted().length < 16) {
            upgrades.getItems().add(Upgrades.PLOT);
        }
    }

    public static void updatePlots() {
        Plot[] planted = controller.getInitialPlayer().getPlanted();
        Button[] plots = PlaySceneBuilder.getPlots();
        for (int i = 0; i < planted.length; i++) {
            if (planted[i] == null) {
                System.out.println(i);
            }
            if (planted[i].getCrop() != null) {
                Crop curCrop = planted[i].getCrop();
                plots[i].setText(curCrop.getType() + "\n" + curCrop.getStage()
                        + "\n Water: " + curCrop.getWaterLevel() + "%"
                        + "\n Fertilizer: " + planted[i].getFertilizerLevel() + "%");
                plots[i].setStyle("-fx-background-color: " + curCrop.getColor()
                        + "; -fx-text-fill: black; -fx-background-radius: 4; "
                        + "-fx-border-color: peachpuff; -fx-border-radius: 4;"
                        + " -fx-border-width: 4;");
            } else {
                plots[i].setText("Vacant Plot\nFertilizer: "
                        + planted[i].getFertilizerLevel() + "%");
                plots[i].setStyle("-fx-background-color: chocolate; -fx-text-fill: beige; "
                        + "-fx-background-radius: 4; -fx-border-color: beige; "
                        + "-fx-border-radius: 4; -fx-border-width: 4; -fx-font-size: 12");
            }
        }
    }

    public static void updateSeedBox() {
        ComboBox seedBox = PlaySceneBuilder.getSeedBox();
        seedBox.getItems().clear();
        for (Crop x: controller.getInitialPlayer().getInventory().getCrops()) {
            if (x.getStage().equals("Seed")) {
                seedBox.getItems().add(x.getName());
            }
        }
    }

    public static void updateToolBox() {
        ComboBox toolBox = PlaySceneBuilder.getToolBox();
        toolBox.getItems().clear();
        toolBox.getItems().add("Water");
        for (Item x : controller.getAllItems()) {
            toolBox.getItems().add(x.getType());
        }
        toolBox.setValue("Water");
    }

    public static PlotController getPlotController() {
        return plotController;
    }

    public static void setConfigController(InitialConfigurationController newController) {
        controller = newController;
    }
}


