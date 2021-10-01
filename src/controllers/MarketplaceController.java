package controllers;

import boundary.MarketplaceBuilder;
import boundary.PlaySceneBuilder;
import entities.Crop;
import entities.Inventory;
import entities.Item;
import entities.Upgrades;

import java.util.Random;

public class MarketplaceController {

    private double sellMultiplier;
    private double buyMultiplier;
    private Random random = new Random();
    private InitialConfigurationController controller = MainWindow.getConfigController();
    private Inventory playerInv = controller.getInitialPlayer().getInventory();

    public void buySeed(String cropName) {
        double money = controller.getInitialPlayer().getMoney();
        Crop crop = null;
        if (cropName == null) {
            MarketplaceBuilder.getErrorMsg().setText("Please select crop/seed.");
            return;
        }
        for (Crop x: MainWindow.getConfigController().getAllCrops()) {
            if (x.getType().equals(cropName.substring(0, cropName.indexOf(" $")))) {
                crop = x.seedOf();
                break;
            }
        }
        if (money < crop.getBasePrice() * buyMultiplier) {
            MarketplaceBuilder.getErrorMsg().setText("Not enough money.");
        } else {
            if (!MainWindow.getConfigController().getInitialPlayer().getInventory().addCrop(crop)) {
                MarketplaceBuilder.getErrorMsg().setText("Inventory Full!");
                return;
            }
            money -= crop.getBasePrice() * buyMultiplier;
            MainWindow.getConfigController().getInitialPlayer().setMoney(money);
        }
    }

    public void sellSeed(String cropName) {
        if (cropName == null) {
            MarketplaceBuilder.getErrorMsg().setText("Please select crop/seed.");
            return;
        }
        double money = controller.getInitialPlayer().getMoney();
        double price = 0;
        Crop removedCrop = playerInv.removeCrop(cropName.substring(0,
                cropName.indexOf(" $")) + " " + "Seed");
        if (removedCrop == null) {
            MarketplaceBuilder.getErrorMsg().setText("Could not find item in player inventory.");
            return;
        }
        price = removedCrop.getBasePrice() * sellMultiplier;
        controller.getInitialPlayer().setMoney(money + price);
    }

    public void sellCrop(String cropName) {
        if (cropName == null) {
            MarketplaceBuilder.getErrorMsg().setText("Please select crop/seed.");
            return;
        }
        //MarketplaceBuilder.getErrorMsg().setText(Double.toString(sellMultiplier));
        double money = controller.getInitialPlayer().getMoney();
        double price = 0;
        Crop removedCrop = playerInv.removeCrop(cropName.substring(0,
                cropName.indexOf(" $")) + " " + "Crop");
        if (removedCrop == null) {
            MarketplaceBuilder.getErrorMsg().setText("Could not find item in player inventory.");
            return;
        }
        price = removedCrop.getBasePrice() * sellMultiplier;
        controller.getInitialPlayer().setMoney(money + price);
    }

    public void buyItem(String itemName) {
        double money = controller.getInitialPlayer().getMoney();
        Item item = null;
        if (itemName == null) {
            MarketplaceBuilder.getErrorMsg().setText("Please select item.");
            return;
        }
        for (Item x: MainWindow.getConfigController().getAllItems()) {
            if (x.getType().equals(itemName.substring(0, itemName.indexOf(" $")))) {
                item = x.clone();
                break;
            }
        }
        if (money < item.getBasePrice() * buyMultiplier) {
            MarketplaceBuilder.getErrorMsg().setText("Not enough money.");
        } else {
            if (!MainWindow.getConfigController().getInitialPlayer().getInventory().addItem(item)) {
                MarketplaceBuilder.getErrorMsg().setText("Inventory Full!");
                return;
            }
            money -= item.getBasePrice() * buyMultiplier;
            MainWindow.getConfigController().getInitialPlayer().setMoney(money);
        }
    }

    public void buyUpgrade(Upgrades upgrade) {
        double money = controller.getInitialPlayer().getMoney();
        if (upgrade == null) {
            MarketplaceBuilder.getErrorMsg().setText("Please select upgrade.");
            return;
        }
        if (money < upgrade.getBasePrice()) {
            MarketplaceBuilder.getErrorMsg().setText("Not enough money.");
            return;
        }
        if (upgrade.equals(Upgrades.TRACTOR)) {
            controller.getInitialPlayer().addTractor();
            controller.getGame().updateMax();
        } else if (upgrade.equals(Upgrades.IRRIGATION)) {
            controller.getInitialPlayer().addIrrigation();
            controller.getGame().updateMax();
        } else if (upgrade.equals(Upgrades.PLOT)) {
            if (money < upgrade.getBasePrice()
                    * controller.getInitialPlayer().getPlanted().length) {
                MarketplaceBuilder.getErrorMsg().setText("Not enough money.");
                return;
            }
            controller.getInitialPlayer().upgradePlots();
            PlaySceneBuilder.generatePlots(controller.getInitialPlayer().getPlanted().length);
            MainWindow.getPlotController().updatePlanted();
            money -= upgrade.getBasePrice() * controller.getInitialPlayer().getPlanted().length;
            MainWindow.getConfigController().getInitialPlayer().setMoney(money);
            MainWindow.updateMarketplace();
            return;
        }
        money -= upgrade.getBasePrice();
        MainWindow.getConfigController().getInitialPlayer().setMoney(money);
        MainWindow.updateMarketplace();
    }

    public void updateMultiplier() {
        int difficulty = controller.getDifficulty();
        buyMultiplier = (int) (10 * (random.nextDouble() + 0.3)
                / Math.log10((4 - difficulty) + 1)) / 10.0;
        sellMultiplier = buyMultiplier * (7 - difficulty) * 0.16;
    }

    public double getSellMultiplier() {
        return sellMultiplier;
    }

    public double getBuyMultiplier() {
        return buyMultiplier;
    }

}
