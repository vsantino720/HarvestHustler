package tests;

import controllers.InitialConfigurationController;
import controllers.MainWindow;
import controllers.PlotController;
import entities.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GrowCropTests {

    private InitialConfigurationController configController;
    private PlotController plotController;

    @Before
    public void setup() {
        configController = new InitialConfigurationController();
        MainWindow.setConfigController(configController);
        plotController = new PlotController();
    }

    @Test
    public void plantTest() {
        configController.initializeGame();
        plotController.plantSeed(0, "Soybean Seed");
        assertEquals(0, configController.getInitialPlayer().getInventory().getCrops().size());
        assertNotNull(configController.getInitialPlayer().getPlanted()[0]);
    }

    @Test
    public void testPlantNullCrop() {
        configController.initializeGame();
        plotController.plantSeed(0, "Garbonza Bean Seed");
        assertNull(configController.getInitialPlayer().getPlanted()[0].getCrop());
    }

    @Test
    public void waterCrop() {
        configController.initializeGame();
        plotController.plantSeed(0, "Soybean Seed");
        Crop planted = configController.getInitialPlayer().getPlanted()[0].getCrop();
        assertEquals(30, planted.getWaterLevel());
        plotController.waterCrop(0);
        assertEquals(40, planted.getWaterLevel());
    }

    @Test
    public void testOverWaterCrop() {
        configController.initializeGame();
        plotController.plantSeed(0, "Soybean Seed");
        Crop planted = configController.getInitialPlayer().getPlanted()[0].getCrop();
        assertEquals(30, planted.getWaterLevel());
        //Water too many times
        plotController.waterCrop(0);
        plotController.waterCrop(0);
        plotController.waterCrop(0);
        plotController.waterCrop(0);
        plotController.waterCrop(0);
        plotController.waterCrop(0);
        assertEquals("Dead", planted.getStage());
    }

    @Test
    public void testAgeCropImmature() {
        configController.initializeGame();
        plotController.plantSeed(0, "Soybean Seed");
        Crop planted = configController.getInitialPlayer().getPlanted()[0].getCrop();
        assertEquals("Seed", planted.getStage());
        for (int i = 0; i < planted.getGrowthSpeed() / 2; i++) {
            plotController.waterCrop(0);
            plotController.ageCrops();
        }
        assertEquals("Immature Crop", planted.getStage());
        assertEquals(50, planted.getBasePrice());
    }

    @Test public void testAgeCropMature() {
        configController.initializeGame();
        plotController.plantSeed(0, "Soybean Seed");
        Crop planted = configController.getInitialPlayer().getPlanted()[0].getCrop();
        for (int i = 0; i < planted.getGrowthSpeed(); i++) {
            plotController.waterCrop(0);
            plotController.ageCrops();
        }
        assertEquals("Crop", planted.getStage());
        assertNotEquals(50, planted.getBasePrice());
    }

    //NEW FOR M5***
    @Test
    public void testFertilizeLevel() {
        configController.initializeGame();
        Inventory inv = configController.getInitialPlayer().getInventory();
        Plot[] planted = configController.getInitialPlayer().getPlanted();
        Crop plantedCrop = planted[0].getCrop();
        plotController.plantSeed(0, "Soybean Seed");
        inv.addItem(new Fertilizer());
        inv.addItem(new Fertilizer());
        inv.addItem(new Fertilizer());
        inv.addItem(new Fertilizer());
        plotController.fertilizePlot(0);
        plotController.fertilizePlot(0);
        plotController.fertilizePlot(0);
        plotController.fertilizePlot(0);
        assertEquals(100, planted[0].getFertilizerLevel());
    }

    @Test
    public void testPesticide() {
        configController.initializeGame();
        Inventory inv = configController.getInitialPlayer().getInventory();
        Plot[] planted = configController.getInitialPlayer().getPlanted();
        plotController.plantSeed(0, "Soybean Seed");
        Crop plantedCrop = planted[0].getCrop();
        inv.addItem(new Pesticide());
        plotController.sprayPesticide(0);
        assertEquals(true, plantedCrop.hasPesticide());
        assertNotEquals(50, plantedCrop.getBasePrice());
    }
}
