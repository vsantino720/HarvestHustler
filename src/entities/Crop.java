package entities;

public class Crop extends Item {
    private int growthTime;
    private String stage;
    private double age;
    private String color;
    private int waterLevel;
    private boolean pesticide;
    public Crop(String name, int basePrice, int growthSpeed, String stage) {
        this(name, basePrice, growthSpeed, stage, "gray");
    }

    public Crop(String name, int basePrice, int growthSpeed, String stage, String color) {
        super(basePrice, name);
        this.growthTime = growthSpeed;
        this.stage = stage;
        this.color = color;
        this.waterLevel = 30;
        this.pesticide = false;
    }

    public String getType() {
        if (pesticide) {
            return "<P> " + super.getType();
        }
        return super.getType();
    }

    public int getGrowthSpeed() {
        return growthTime;
    }

    public void setGrowthTime(int growthSpeed) {
        this.growthTime = growthSpeed;
    }

    public void updateGrowth(int howManyTimes) {
        age += howManyTimes;
        if (age >= growthTime && stage != "Crop") {
            setBasePrice(getBasePrice() * 2 + 20);
            stage = "Crop";
        } else if (age >= growthTime / 2 && stage == "Seed") {
            stage = "Immature Crop";
        }
    }

    public void stallGrowth(int days) {
        if (age > days) {
            age -= days;
        }
    }

    @Override
    public boolean equals(Object obj) {
        Crop o = (Crop) obj;
        return (this.getName().equals(o.getName()));
    }

    public String getStage() {
        return this.stage;
    }

    public String getName() {
        return getType() + " " + this.stage;
    }

    public String getColor() {
        if (pesticide) {
            return "linear-gradient(purple, " + this.color + ")";
        } else if (this.getStage().equals("Seed")) {
            return "linear-gradient(brown, " + this.color + ")";
        } else if (this.getStage().equals("Immature Crop")) {
            return "linear-gradient(green, " + this.color + ")";
        } else {
            return "linear-gradient(orange, " + this.color + ")";
        }
    }

    public Crop seedOf() {
        return new Crop(getType(), getBasePrice(), growthTime, "Seed", this.color);
    }

    public Crop clone() {
        return new Crop(getType(), getBasePrice(), growthTime, stage, color);
    }

    @Override
    public String toString() {
        return this.getType();
    }

    public void setWaterLevel(int level) {
        waterLevel = level;
        if (waterLevel < 30 || waterLevel > 80) {
            stage = "Dead";
            color = "red";
            waterLevel = 0;
        }
    }

    public void freeze() {
        this.stage = "Dead";
        color = "white";
        waterLevel = 0;
    }

    public void eat() {
        this.stage = "Dead";
        color = "brown";
        waterLevel = 0;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public boolean hasPesticide() {
        return pesticide;
    }

    public void givePesticide() {
        pesticide = true;
        setBasePrice(getBasePrice() - 15);
    }

}
