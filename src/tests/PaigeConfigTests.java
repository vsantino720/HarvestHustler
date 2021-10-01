package tests;

import controllers.InitialConfigurationController;
import entities.Crop;
import entities.Game;
import entities.Player;
import entities.Season;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PaigeConfigTests {
    private InitialConfigurationController control = new InitialConfigurationController();

    @Test
    public void testName() {
        control.nameHandler("Paige");
        control.difficultyHandler1();
        control.cropHandler(new Crop("Blackbean", 120, 1, "Crop", "black"));
        control.seasonHandler(Season.FALL);
        control.initializeGame();
        Player player = control.getInitialPlayer();
        assertEquals(player.getName(), "Paige");
    }

    @Test
    public void testSeason() {
        control.nameHandler("Paige");
        control.difficultyHandler1();
        control.cropHandler(new Crop("Blackbean", 120, 1, "Crop", "black"));
        control.seasonHandler(Season.FALL);
        control.initializeGame();
        Game game = control.getGame();
        assertEquals(game.getSeason(), Season.FALL);
    }
}