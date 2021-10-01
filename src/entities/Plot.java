package entities;

public class Plot {
    private Crop crop;
    private int fertilizerLevel;
    private int growthMultiplier;

    public Plot() {
        growthMultiplier = 1;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public Crop getCrop() {
        return this.crop;
    }

    public int getFertilizerLevel() {
        return fertilizerLevel;
    }

    public void addFertilizer(int quantity) {
        if (fertilizerLevel < 100) {
            fertilizerLevel += quantity;
        }
        if (fertilizerLevel > 100) {
            fertilizerLevel = 100;
        }
        growthMultiplier = (1 + (fertilizerLevel * 2 / 100));
    }

    public void reduceFertilizer() {
        if (fertilizerLevel > 10) {
            fertilizerLevel -= 10;
        } else {
            fertilizerLevel = 0;
        }
        growthMultiplier = (1 + (fertilizerLevel * 2 / 100));
    }

    public int getMultiplier() {
        return growthMultiplier;
    }
}
