package entities.weather;

import entities.Crop;
import entities.Plot;

import java.util.Random;

public class Drought implements WeatherEvent {

    private int waterDec;
    private Random rand = new Random();

    @Override
    public boolean currentWeather(Plot[] planted) {
        int cropAffect = 0;
        Crop curCrop;
        waterDec = (rand.nextInt(3) + 1) * 10;
        for (int i = 0; i < planted.length; i++) {
            curCrop = planted[i].getCrop();
            if (curCrop != null && curCrop.getStage() != "Dead") {
                curCrop.setWaterLevel(curCrop.getWaterLevel() - waterDec);
                cropAffect += 1;
            }
        }
        //Return true if the weather impacted any crops.
        if (cropAffect > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String getEventMsg() {
        return "A draught has lowered plant water levels by " + waterDec + "!";
    }
}
