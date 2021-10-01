package controllers;

import boundary.PlaySceneBuilder;
import entities.*;
import entities.weather.WeatherEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;

import java.util.List;

public class PlotController {

    private InitialConfigurationController configController = MainWindow.getConfigController();
    private Plot[] planted = configController.getInitialPlayer().getPlanted();

    public void randomizePlanted() {
        List<Crop> allCrops = configController.getAllCrops();
        for (int i = 0; i < planted.length; i++) {
            Crop randomCrop = allCrops.get(i % allCrops.size()).seedOf();
            int randomNum = (int) (Math.random() * (15 - 0 + 1) + 0);
            randomCrop.updateGrowth(randomNum);
            planted[i].setCrop(randomCrop);
        }
    }

    public void harvest(int pos) {
        Crop curCrop = planted[pos].getCrop();
        if (curCrop == null) {
            System.out.println("null if");
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Cannot harvest an empty plot!");
            a.setContentText("You have nothing growing here.");
            a.showAndWait();
        } else if (curCrop.getStage() != "Crop") {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Cannot harvest a " + curCrop.getStage() + "!");
            a.setContentText("You can only harvest fully grown crops.");
            a.showAndWait();
        } else if (configController.getInitialPlayer().getInventory().isAtCapacity()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Cannot harvest this crop!");
            a.setContentText("Your crop inventory is full. Sell some crops!");
            a.showAndWait();
        } else if (configController.getGame().getHarvestMax() == 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Cannot harvest this crop!");
            a.setContentText("You have reached your harvest maximum for today!");
            a.showAndWait();
        } else {
            //playAudio();
            double yieldChance = (planted[pos].getFertilizerLevel() - 15) / 100.0;
            if (Math.random() < yieldChance) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Harvest Info");
                a.setHeaderText("Double Yield!");
                a.setContentText("You have harvested two crops due to fertilizer!");
                a.showAndWait();
                configController.getInitialPlayer().giveCrop(curCrop.clone());
            }
            configController.getInitialPlayer().giveCrop(curCrop);
            planted[pos].setCrop(null);
            configController.getInitialPlayer().dePlantedSize();
            configController.getGame().reduceHarvestMax();
        }
    }

    public void ageCrops() {
        for (int i = 0; i < planted.length; i++) {
            Crop curCrop = planted[i].getCrop();
            if (curCrop != null) {
                if (curCrop.getStage() == "Dead") { //Check if dead crops need to be removed.
                    planted[i].setCrop(null);
                    configController.getInitialPlayer().dePlantedSize();
                    continue;
                }
                curCrop.updateGrowth(1 * planted[i].getMultiplier()); //increment growth of crops.
                curCrop.setWaterLevel(curCrop.getWaterLevel() - 10);
            }
            planted[i].reduceFertilizer();
        }
    }

    public void plantSeed(int plot, String seed) {
        if (seed == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Cannot plant seed");
            a.setContentText("Please select a seed first!");
            a.showAndWait();
        } else {
            Crop newSeed = configController.getInitialPlayer().getInventory().removeCrop(seed);
            if (newSeed != null) {
                planted[plot].setCrop(newSeed);
                configController.getInitialPlayer().upPlantedSize();
            }
        }
    }

    public void waterCrop(Integer plot) {
        double playerMoney = configController.getInitialPlayer().getMoney();
        Crop curCrop = planted[plot].getCrop();
        if (curCrop == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Cannot water crop");
            a.setContentText("There is nothing growing here!");
            a.showAndWait();
        } else if (configController.getGame().getWaterMax() == 0) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Cannot water crop");
            a.setContentText("You have reached your maximum water capacity for today.");
            a.showAndWait();
        } else {
            curCrop.setWaterLevel(curCrop.getWaterLevel() + 10);
            configController.getGame().reduceWaterMax();
        }
    }

    public void setOnActionPlotButtons() {
        Button[] plots = PlaySceneBuilder.getPlots();
        for (int i = 0; i < plots.length; i++) {
            int finalI = i;
            plots[i].setOnAction(e -> {
                if (planted[finalI].getCrop() != null) {
                    harvest(finalI);
                } else {
                    plantSeed(finalI, PlaySceneBuilder.getSeedBox().getValue());
                }
                MainWindow.updatePlots();
                MainWindow.updateDailyMaxLabels();
            });
            plots[i].setOnMousePressed(e -> {
                if (e.getButton() == MouseButton.SECONDARY) {
                    if (PlaySceneBuilder.getToolBox().getValue().equals("Water")) {
                        waterCrop((finalI));
                        MainWindow.updateMoneyLabel();
                    } else if (PlaySceneBuilder.getToolBox().getValue().equals("Pesticide")) {
                        sprayPesticide(finalI);
                    } else if (PlaySceneBuilder.getToolBox().getValue().equals("Fertilizer")) {
                        fertilizePlot(finalI);
                    }
                    MainWindow.updatePlots();
                    MainWindow.updateDailyMaxLabels();
                }
            });
        }
    }

    public void fertilizePlot(int i) {
        Inventory inv = configController.getInitialPlayer().getInventory();
        Crop curCrop = planted[i].getCrop();
        if (inv.removeItem(new Fertilizer())) {
            planted[i].addFertilizer(25);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Fertilizer Error");
            a.setHeaderText("Cannot give fertilizer to crop.");
            a.setContentText("You do not have any fertilizer in your inventory!");
            a.showAndWait();
        }
    }

    public void attemptWeather() {
        WeatherEvent weather = configController.getGame().getSeason().getWeather();
        if ((Math.random() * 100 / 100.0 <= configController.getGame().getWeatherChance())) {
            if (weather.currentWeather(planted)) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Weather Occurrence");
                a.setHeaderText("Weather has impacted your crops.");
                a.setContentText(weather.getEventMsg());
                a.showAndWait();
            }
        } else if ((Math.random() * 100 / 100.0 <= configController.getGame().getWeatherChance())) {
            int affected = configController.getGame().getDifficulty() * 2 + 2;
            int count = weather.locustAttack(planted, affected);
            if (count > 0) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Bug Occurrence");
                a.setHeaderText("Locusts have impacted your crops.");
                a.setContentText("A Locust swarm killed " + count + " crops!");
                a.showAndWait();
            }
        }
    }

    public void sprayPesticide(int i) {
        Inventory inv = configController.getInitialPlayer().getInventory();
        Crop curCrop = planted[i].getCrop();
        if (curCrop == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Pesticide Error");
            a.setHeaderText("Cannot give pesticide to crop.");
            a.setContentText("There is nothing growing here to pesticide!");
            a.showAndWait();
        } else if (inv.removeItem(new Pesticide())) {
            if (curCrop.hasPesticide()) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Pesticide Error");
                a.setHeaderText("Cannot give pesticide to crop.");
                a.setContentText("This crop already has been given pesticide!");
                a.showAndWait();
            } else {
                curCrop.givePesticide();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Pesticide Error");
            a.setHeaderText("Cannot give pesticide to crop.");
            a.setContentText("You do not have any pesticide in your inventory!");
            a.showAndWait();
        }
    }

    public void updatePlanted() {
        planted = configController.getInitialPlayer().getPlanted();
    }

    /*private static void playAudio() {
        String path = "C:/Users/vinee/CS2340/M2/src/sounds/minecraft_harvest_sound(1).mp3";
        AudioClip harvestSound = new AudioClip(new File(path).toURI().toString());
        harvestSound.play();
    }*/
}
