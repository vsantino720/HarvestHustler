package tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import entities.Crop;

public class VineethTests {
    //private Plot newPlot = new Plot(2, 2, 5);
    private Crop wheat = new Crop("Wheat", 5, 3, "Crop");

    //@Test
    //public void plotTest() {
    //    assertEquals(2, newPlot.getX());
    //}

    @Test
    public void cropTest() {
        assertEquals("Wheat", wheat.getType());
        assertEquals(3, wheat.getGrowthSpeed());
    }
}
