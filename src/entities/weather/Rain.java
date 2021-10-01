package entities.weather;

import entities.Crop;
import entities.Plot;

import java.util.Random;

public class Rain implements WeatherEvent {

    private int waterInc;
    private Random rand = new Random();

    @Override
    public boolean currentWeather(Plot[] planted) {
        int cropAffect = 0;
        Crop curCrop;
        waterInc = (rand.nextInt(6) + 1) * 10;
        for (int i = 0; i < planted.length; i++) {
            curCrop = planted[i].getCrop();
            if (curCrop != null && curCrop.getStage() != "Dead") {
                curCrop.setWaterLevel(curCrop.getWaterLevel() + waterInc);
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
        return "A shower has increased the water levels " + waterInc + "!";
    }
}
