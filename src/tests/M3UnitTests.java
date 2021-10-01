package tests;

import controllers.InitialConfigurationController;
import controllers.MainWindow;
import controllers.MarketplaceController;
import controllers.PlotController;
import entities.Crop;
import entities.Inventory;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class M3UnitTests {

    private InitialConfigurationController controller;
    private MarketplaceController marketplaceController;
    private PlotController plotController;
    private Inventory inventory;

    @Before
    public void setup() {
        controller = new InitialConfigurationController();
        MainWindow.setConfigController(controller);
        plotController = new PlotController();
        marketplaceController = new MarketplaceController();
        inventory = controller.getInitialPlayer().getInventory();
    }

    @Test
    public void testInventoryAddDuplicate() {
        controller.nameHandler("Player");
        controller.initializeGame();
        controller.getInitialPlayer().giveCrop(new Crop("Soybean", 50, 3, "Seed", "brown"));
        assertEquals(2, controller.getInitialPlayer().getInventory().getCrops().size());
        assertEquals(1, controller.getInitialPlayer().getInventory().getCropNames().size());

        controller.getInitialPlayer().giveCrop(new Crop("Carrot", 50, 3, "Seed", "brown"));
        assertEquals(3, inventory.getCrops().size());
        assertEquals(2, inventory.getCropNames().size());
    }

    @Test
    public void testInventoryRemove() {
        controller.nameHandler("Player");
        controller.initializeGame();
        controller.getInitialPlayer().giveCrop(new Crop("Soybean", 50, 3, "Seed", "brown"));
        assertEquals(2, controller.getInitialPlayer().getInventory().getCrops().size());
        assertEquals(1, controller.getInitialPlayer().getInventory().getCropNames().size());

        inventory.removeCrop("Soybean Seed");
        assertEquals(1, inventory.getCrops().size());
        assertEquals(1, inventory.getCropNames().size());

        inventory.removeCrop("Soybean Seed");
        assertEquals(0, inventory.getCrops().size());
        assertEquals(0, inventory.getCropNames().size());

        assertNull(inventory.removeCrop("Soybean Seed"));

        inventory.addCrop(new Crop("Soybean", 300, 2, "Crop"));
        inventory.addCrop(new Crop("Soybean", 300, 2, "Crop"));
        inventory.removeCrop("Soybean Crop");
        assertNotNull(inventory.getCropNames().get("Soybean Crop"));
        assertEquals(inventory.getCrops().size(), 1);
        inventory.removeCrop("Soybean Crop");
        assertNull(inventory.getCropNames().get("Soybean Crop"));
        assertEquals(0, inventory.getCrops().size());
    }

    @Test
    public void testBuySeed() {
        InitialConfigurationController mainController = MainWindow.getConfigController();
        mainController.initializeGame();
        mainController.getInitialPlayer().getInventory().removeCrop("Soybean Seed");
        marketplaceController.buySeed("Wheat $90");
        assertEquals(mainController.getInitialPlayer().getInventory().removeCrop("Wheat Seed"),
                mainController.getAllCrops().get(1));
    }

    @Test
    public void testSellSeed() {
        controller.initializeGame();
        marketplaceController.sellSeed("Soybean $50");
        List<Crop> testList = new ArrayList<>();
        assertEquals((List<Crop>) controller.getInitialPlayer().getInventory().getCrops(),
                testList);
    }

    @Test
    public void testHarvest() {
        MainWindow.getConfigController().initializeGame();
        InitialConfigurationController tempController = MainWindow.getConfigController();
        tempController.getInitialPlayer().getPlanted()[0].setCrop(
                new Crop("Corn", 100, 7, "Crop", "yellow"));
        plotController.harvest(0);
        assertEquals(null, tempController.getInitialPlayer().getPlanted()[0].getCrop());
        assertEquals(2, tempController.getInitialPlayer().getInventory().getCrops().size());
    }
}
