package tests;

import controllers.InitialConfigurationController;
import controllers.MainWindow;
import controllers.MarketplaceController;
import controllers.PlotController;
import entities.Plot;
import entities.Upgrades;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpgradeTests {

    private InitialConfigurationController configController;
    private PlotController plotController;
    private MarketplaceController marketplaceController;

    @Before
    public void setup() {
        configController = new InitialConfigurationController();
        MainWindow.setConfigController(configController);
        plotController = new PlotController();
        marketplaceController = new MarketplaceController();
        configController.initializeGame();
    }

    @Test
    public void testPlotUpgrade() {
        Plot[] planted = configController.getInitialPlayer().getPlanted();
        configController.getInitialPlayer().upgradePlots();
        assertNotEquals(planted, configController.getInitialPlayer().getPlanted());
        assertEquals(9, configController.getInitialPlayer().getPlanted().length);
    }

    @Test
    public void testWaterUpgrade() {
        assertEquals(configController.getGame().getWaterMax(), 6);
        configController.getInitialPlayer().setMoney(2000);
        marketplaceController.buyUpgrade(Upgrades.IRRIGATION);
        assertNotEquals(configController.getGame().getWaterMax(), 6);
    }

    @Test
    public void testHarvestUpgrade() {
        assertEquals(configController.getGame().getHarvestMax(), 3);
        configController.getInitialPlayer().setMoney(2000);
        marketplaceController.buyUpgrade(Upgrades.TRACTOR);
        assertNotEquals(configController.getGame().getWaterMax(), 3);
    }

    @Test
    public void testWaterDecrease() {
        assertEquals(configController.getGame().getWaterMax(), 6);
        plotController.plantSeed(0, "Soybean Seed");
        plotController.waterCrop(0);
        assertNotEquals(configController.getGame().getWaterMax(), 6);
    }
}
