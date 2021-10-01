package boundary;

import controllers.MainWindow;
import entities.Crop;
import entities.Item;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class InventorySceneBuilder {

    private static HBox cropList = new HBox();
    private static HBox seedList = new HBox();
    private static HBox itemList = new HBox();

    public static Scene build() {
        BorderPane root = new BorderPane();

        Text sLabel = new Text("SEEDS");
        MainWindow.styleText(sLabel, 30, "linear-gradient(black, brown)");

        Text cLabel = new Text("CROPS");
        MainWindow.styleText(cLabel, 30, "linear-gradient(black, darkgreen)");

        Text iLabel = new Text("ITEMS");
        MainWindow.styleText(iLabel, 30, "linear-gradient(black, lightblue)");


        Text invText = new Text("INVENTORY");
        MainWindow.styleText(invText, 60, "standard");

        VBox contents = new VBox();
        VBox seeds = new VBox();
        VBox crops = new VBox();
        VBox items = new VBox();

        seeds.getChildren().addAll(sLabel, seedList);
        crops.getChildren().addAll(cLabel, cropList);
        items.getChildren().addAll(iLabel, itemList);

        contents.getChildren().addAll(seeds, crops, items);

        Button returnBtn = new Button("RETURN");
        MainWindow.styleButton(returnBtn, 30);
        returnBtn.setOnAction(e -> MainWindow.goToPlayScene());

        root.setAlignment(contents, Pos.TOP_CENTER);
        root.setAlignment(invText, Pos.TOP_CENTER);
        root.setAlignment(returnBtn, Pos.CENTER);

        root.setTop(invText);
        root.setCenter(contents);
        root.setBottom(returnBtn);
        root.setStyle("-fx-background-color: linear-gradient(black, peachpuff);");

        return new Scene(root, MainWindow.getScreenWidth(), MainWindow.getScreenHeight());
    }

    public static void generateSeedLabels(List<Crop> crops) {
        seedList.setMinHeight(100);
        styleInvList(seedList, "linear-gradient(black, brown)");
        seedList.getChildren().clear();
        for (Crop crop: crops) {
            if (crop.getStage().equals("Seed") || crop.getStage().equals("Immature Crop")) {
                Label slot = new Label(crop.getName());
                slot.setStyle("-fx-border-color: peachpuff; -fx-border-radius: 9; "
                        + "-fx-border-width: 5; -fx-font: 15px Impact; "
                        + "-fx-text-fill: black; -fx-background-color: "
                        + crop.getColor() + "; -fx-background-radius: 10;");
                slot.setMinSize(100, 100);
                slot.setMaxSize(100, 100);
                slot.setAlignment(Pos.CENTER);
                seedList.getChildren().add(slot);
            }
        }
    }

    public static void generateCropLabels(List<Crop> crops) {
        cropList.setMinHeight(100);
        styleInvList(cropList, "linear-gradient(black, forestgreen)");
        cropList.getChildren().clear();
        for (Crop crop: crops) {
            if (crop.getStage().equals("Crop")) {
                Label slot = new Label(crop.getName());
                slot.setStyle("-fx-border-color: peachpuff; -fx-border-radius: 9;"
                        + " -fx-border-width: 5; -fx-font: 15px Impact; "
                        + "-fx-text-fill: black; -fx-background-color: "
                        + crop.getColor()  + "; -fx-background-radius: 10;");
                slot.setMinSize(100, 100);
                slot.setMaxSize(100, 100);
                slot.setAlignment(Pos.CENTER);
                cropList.getChildren().add(slot);
            }
        }
    }

    public static void generateItemLabels(List<Item> items) {
        itemList.setMinHeight(100);
        styleInvList(itemList, "linear-gradient(black, lightblue)");
        itemList.getChildren().clear();
        for (Item item: items) {
            Label slot = new Label(item.getType());
            slot.setStyle("-fx-border-color: peachpuff; -fx-border-radius: 9;"
                    + " -fx-border-width: 5; -fx-font: 15px Impact; "
                    + "-fx-text-fill: black; -fx-background-color: "
                    + item.color() + "; -fx-background-radius: 10;");
            slot.setMinSize(100, 100);
            slot.setMaxSize(100, 100);
            slot.setAlignment(Pos.CENTER);
            itemList.getChildren().add(slot);
        }
    }

    private static void styleInvList(HBox list, String color) {
        list.setStyle("-fx-border-color: black; -fx-border-width: 5; -fx-border-radius: 9;"
                    + "-fx-background-color: " + color + "; -fx-background-radius: 10;");
    }

    public static HBox getCropList() {
        return cropList;
    }
    public static HBox getSeedList() {
        return seedList;
    }
    public static HBox getItemList() {
        return itemList;
    }
}
