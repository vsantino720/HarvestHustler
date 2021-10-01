package tests;

import controllers.InitialConfigurationController;
import entities.Crop;

import entities.Inventory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class VinnieConfigTests {

    private InitialConfigurationController controller = new InitialConfigurationController();
    private Inventory inventory = controller.getInitialPlayer().getInventory();

    @Test
    public void testAddCrop() {
        controller.nameHandler("Player");
        controller.initializeGame();
        List<Crop> playerCrops = controller.getInitialPlayer().getInventory().getCrops();
        assertEquals(playerCrops.size(), 1);
        controller.getInitialPlayer().getInventory().addCrop(new Crop("default", 100, 1, "Crop"));
        assertEquals(playerCrops.size(), 2);
    }

    @Test
    public void testStartingMoney() {
        controller.difficultyHandler2();
        controller.initializeGame();
        assertEquals(controller.getInitialPlayer().getMoney(), 200, 0);

        controller.difficultyHandler1();
        controller.initializeGame();
        assertEquals(controller.getInitialPlayer().getMoney(), 300, 0);
    }
}
