package tests;

import controllers.InitialConfigurationController;
import controllers.MainWindow;
import controllers.PlotController;
import entities.Crop;
import entities.Plot;
import entities.Season;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SeasonTest {
    private InitialConfigurationController configController;
    private PlotController plotController;
    //NEW FOR M5***
    @Before
    public void setup() {
        configController = new InitialConfigurationController();
        MainWindow.setConfigController(configController);
        plotController = new PlotController();
        configController.initializeGame();
    }

    @Test
    public void testSeasonChange() {
        assertEquals(Season.FALL, configController.getGame().getSeason());
        for (int i = 0; i < 20; i++) {
            configController.getGame().incrementDay();
        }
        assertEquals(Season.WINTER, configController.getGame().getSeason());
    }

    @Test
    public void testRain() {
        for (int i = 0; i < 40; i++) {
            configController.getGame().incrementDay();
        }
        assertEquals(Season.SPRING, configController.getGame().getSeason());
        Plot[] planted = configController.getInitialPlayer().getPlanted();
        plotController.plantSeed(0, "Soybean Seed");
        Crop crop = planted[0].getCrop();
        assertEquals(30, crop.getWaterLevel());
        Season.SPRING.getWeather().currentWeather(planted);
        assertNotEquals(30, crop.getWaterLevel());
    }

    @Test
    public void testDraught() {
        for (int i = 0; i < 60; i++) {
            configController.getGame().incrementDay();
        }
        assertEquals(Season.SUMMER, configController.getGame().getSeason());
        Plot[] planted = configController.getInitialPlayer().getPlanted();
        plotController.plantSeed(0, "Soybean Seed");
        Crop crop = planted[0].getCrop();
        assertEquals(30, crop.getWaterLevel());
        Season.SUMMER.getWeather().currentWeather(planted);
        assertEquals("Dead", crop.getStage());
    }
}
