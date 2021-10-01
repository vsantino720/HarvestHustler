package tests;

import static org.junit.Assert.assertEquals;

import entities.Game;
import entities.Player;
import entities.Season;
import org.junit.Test;

public class RitvikConfigTests {
    private Game newgame = new Game(Season.SPRING, new Player(20, "Player"), 1);
    @Test
    public void gameNameTest() {
        assertEquals(Season.SPRING, newgame.getSeason());
    }
    @Test
    public void gameMonTest() {
        assertEquals(20, newgame.getPlayer().getMoney(), 0);
    }

}
