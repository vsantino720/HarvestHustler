package entities.weather;

import entities.Crop;
import entities.Plot;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SnowStorm implements WeatherEvent {

    private int deathCount;
    private Random rand = new Random();

    @Override
    public boolean currentWeather(Plot[] planted) {
        Crop curCrop;
        deathCount = 0;
        List<Integer> plotsAff = new LinkedList();
        for (int i = 0; i < planted.length / 3; i++) {
            plotsAff.add(rand.nextInt(planted.length));
        }
        for (int plot : plotsAff) {
            curCrop = planted[plot].getCrop();
            if (curCrop != null && curCrop.getStage() != "Dead") {
                curCrop.freeze();
                deathCount++;
            }
        }
        if (deathCount > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getEventMsg() {
        return "A Snowstorm has killed " + deathCount + " of your crops!";
    }
}
