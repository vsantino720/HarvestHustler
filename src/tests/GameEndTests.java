package tests;

import controllers.InitialConfigurationController;
import controllers.MainWindow;
import controllers.MarketplaceController;
import controllers.PlotController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameEndTests {

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
    public void testGameOver() {
        configController.getInitialPlayer().setMoney(49);
        configController.getInitialPlayer().getInventory().removeCrop("Soybean Seed");
        assertTrue(configController.getGame().gameOver());
    }

    @Test
    public void testWinGame() {
        configController.getInitialPlayer().setMoney(5001);
        for (int i = 0; i < 102; i++) {
            configController.getGame().incrementDay();
        }
        assertTrue(configController.getGame().winGame());
    }
}
