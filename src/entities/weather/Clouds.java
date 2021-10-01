package entities.weather;

import entities.Crop;
import entities.Plot;

import java.util.Random;

public class Clouds implements WeatherEvent {

    private Random rand = new Random();
    private int setBack;

    @Override
    public boolean currentWeather(Plot[] planted) {
        setBack = rand.nextInt(3) + 1;
        int cropAffect = 0;
        Crop curCrop;
        for (int i = 0; i < planted.length; i++) {
            curCrop = planted[i].getCrop();
            if (curCrop != null && curCrop.getStage() != "Dead") {
                curCrop.stallGrowth(setBack);
                cropAffect += 1;
            }
        }
        if (cropAffect > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getEventMsg() {
        return "Cloudy skies have stalled growth by " + setBack + " days!";
    }
}
