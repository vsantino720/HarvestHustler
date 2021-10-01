package boundary;

import controllers.MainWindow;
import controllers.MarketplaceController;
import entities.Upgrades;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MarketplaceBuilder {

    private static Label money;
    private static HBox cropList;
    private static HBox seedList;
    private static HBox itemList;
    private static MarketplaceController marketController;
    private static Label errorMsg;
    private static ComboBox seedsToBuy = new ComboBox();
    private static ComboBox seedsToSell = new ComboBox();
    private static ComboBox cropsToSell = new ComboBox();
    private static ComboBox itemsToBuy = new ComboBox();
    private static ComboBox upgrades = new ComboBox();

    public static Scene build() {
        BorderPane root = new BorderPane();

        Font labelFont = new Font("impact", 30);
        money = new Label();
        money.textProperty().bindBidirectional(PlaySceneBuilder.getMoneyLabel().textProperty());
        money.setFont(labelFont);

        cropList = InventorySceneBuilder.getCropList();
        seedList = InventorySceneBuilder.getSeedList();
        itemList = InventorySceneBuilder.getItemList();

        marketController = MainWindow.getMarketController();

        errorMsg = new Label();

        Text buySeed = new Text("Buy Seeds");
        Button buy = new Button("Buy");
        buy.setOnAction(e -> {
            errorMsg.setText("");
            marketController.buySeed((String) seedsToBuy.getValue());
            MainWindow.updateMoneyLabel();
        });
        MainWindow.styleText(buySeed, 30, "linear-gradient(black, brown)");

        Text sellSeed = new Text("Sell Seeds");
        Button sellS = new Button("Sell");
        sellS.setOnAction(e -> {
            errorMsg.setText("");
            marketController.sellSeed((String) seedsToSell.getValue());
            MainWindow.updateMoneyLabel();
        });
        MainWindow.styleText(sellSeed, 30, "linear-gradient(black, brown)");

        Text sellCrop  = new Text("Sell Crops");
        Button sellC = new Button("Sell");
        sellC.setOnAction(e -> {
            errorMsg.setText("");
            marketController.sellCrop((String) cropsToSell.getValue());
            MainWindow.updateMoneyLabel();
        });
        MainWindow.styleText(sellCrop, 30, "linear-gradient(black, brown)");

        Text buyItem = new Text("Buy Items");
        Button item = new Button("Buy");
        item.setOnAction(e -> {
            errorMsg.setText("");
            marketController.buyItem((String) itemsToBuy.getValue());
            MainWindow.updateMoneyLabel();
        });
        MainWindow.styleText(buyItem, 30, "linear-gradient(black, brown)");

        Text upgradeText = new Text("Upgrades");
        Button upgradeButton = new Button("Upgrade");
        upgradeButton.setOnAction(e -> {
            errorMsg.setText("");
            marketController.buyUpgrade((Upgrades) upgrades.getValue());
            MainWindow.updateMoneyLabel();
            MainWindow.updateDailyMaxLabels();
        });
        MainWindow.styleText(upgradeText, 30, "linear-gradient(black, brown)");


        Text marketPlace = new Text("Marketplace");
        MainWindow.styleText(marketPlace, 60, "standard");

        HBox contents = new HBox();
        VBox buySeeds = new VBox();
        VBox sellSeeds = new VBox();
        VBox sellCrops = new VBox();
        VBox inventory = new VBox();
        VBox buyItems = new VBox();
        VBox upgradeBox = new VBox();

        seedsToBuy.setMinWidth(200);
        seedsToSell.setMinWidth(200);
        cropsToSell.setMinWidth(200);
        itemsToBuy.setMaxWidth(200);
        upgrades.setMaxWidth(150);

        buySeeds.getChildren().addAll(buySeed, seedsToBuy, buy);
        buySeeds.setAlignment(Pos.CENTER);
        sellSeeds.getChildren().addAll(sellSeed, seedsToSell, sellS);
        sellSeeds.setAlignment(Pos.CENTER);
        sellCrops.getChildren().addAll(sellCrop, cropsToSell, sellC);
        sellCrops.setAlignment(Pos.CENTER);
        buyItems.getChildren().addAll(buyItem, itemsToBuy, item);
        buyItems.setAlignment(Pos.CENTER);
        upgradeBox.getChildren().addAll(upgradeText, upgrades, upgradeButton);
        upgradeBox.setAlignment(Pos.CENTER);

        contents.getChildren().addAll(buySeeds, sellSeeds, sellCrops, buyItems, upgradeBox);
        inventory.getChildren().addAll(money, errorMsg, seedList, cropList, itemList);
        MainWindow.updateInv();
        contents.setAlignment(Pos.TOP_CENTER);
        contents.setSpacing(20);

        Button returnBtn = new Button("RETURN");
        MainWindow.styleButton(returnBtn, 30);
        returnBtn.setOnAction(e -> MainWindow.goToPlayScene());

        inventory.getChildren().add(returnBtn);

        root.setAlignment(contents, Pos.TOP_CENTER);
        root.setAlignment(marketPlace, Pos.TOP_CENTER);
        root.setAlignment(inventory, Pos.CENTER);

        root.setTop(marketPlace);
        root.setCenter(contents);
        root.setBottom(inventory);
        root.setStyle("-fx-background-color: linear-gradient(black, peachpuff);");

        return new Scene(root, MainWindow.getScreenWidth(), MainWindow.getScreenHeight());
    }

    public static Label getMoney() {
        return money;
    }

    public static Label getErrorMsg() {
        return errorMsg;
    }

    public static ComboBox getSeedsToBuy() {
        return seedsToBuy;
    }

    public static ComboBox getSeedsToSell() {
        return seedsToSell;
    }

    public static ComboBox getCropsToSell() {
        return cropsToSell;
    }

    public static ComboBox getItemsToBuy() {
        return itemsToBuy;
    }

    public static ComboBox getUpgrades() {
        return upgrades;
    }

}
