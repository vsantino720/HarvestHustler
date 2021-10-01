package tests;

import controllers.InitialConfigurationController;
import entities.Crop;
import entities.Game;
import entities.Player;
import entities.Season;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RiggyConfigTests {
    private InitialConfigurationController control = new InitialConfigurationController();

    @Test
    public void testPlayer() {
        control.nameHandler("Riggy");
        control.difficultyHandler1();
        control.cropHandler(new Crop("Soybean", 100, 1, "Crop", "brown"));
        control.seasonHandler(Season.SPRING);
        control.initializeGame();
        Player player = control.getInitialPlayer();
        assertEquals(player.getMoney(), 300, 0);
        assertEquals(player.getCreditScore(), 0);
    }

    @Test
    public void testGame() {
        control.nameHandler("Riggy");
        control.difficultyHandler1();
        control.cropHandler(new Crop("Soybean", 100, 1, "Crop", "brown"));
        control.seasonHandler(Season.SPRING);
        control.initializeGame();
        Game game = control.getGame();
        assertEquals(game.getDay(), 0);
    }

}
