package entities.weather;

import entities.Crop;
import entities.Plot;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public interface WeatherEvent {
    public boolean currentWeather(Plot[] planted);
    public default int locustAttack(Plot[] planted, int affected) {
        int deathCount = 0;
        Random rand = new Random();
        List<Integer> plotsAff = new LinkedList();
        for (int i = 0; i < affected; i++) {
            plotsAff.add(rand.nextInt(planted.length));
        }
        for (int plot : plotsAff) {
            Crop curCrop = planted[plot].getCrop();
            if (curCrop != null && curCrop.getStage() != "Dead"
                    && !curCrop.hasPesticide()) {
                curCrop.eat();
                deathCount++;
            }
        }
        return deathCount;
    }
    public String getEventMsg();
}
