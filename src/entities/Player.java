package entities;


public class Player {
    private int creditScore;
    private double money;
    private String name;
    private Inventory inventory = new Inventory();
    private Plot[] planted;
    private int plantedSize;
    private boolean tractor;
    private boolean irrigation;

    public Player(double money, String name) {
        this.money = money;
        planted = new Plot[8];
        for (int i = 0; i < planted.length; i++) {
            planted[i] = new Plot();
        }
        this.name = name;
        tractor = false;
        irrigation = false;
        plantedSize = 0;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void giveCrop(Crop crop) {
        inventory.addCrop(crop);
    }

    public void addPlant(Crop crop) {
        // someone code this
    }

    public Plot[] getPlanted() {
        return this.planted;
    }

    public int getCreditScore() {
        return this.creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public void increaseCreditScore(int amount) {
        this.creditScore += amount;
    }

    public double getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasIrrigation() {
        return irrigation;
    }

    public boolean hasTractor() {
        return tractor;
    }

    public void addTractor() {
        this.tractor = true;
    }

    public void addIrrigation() {
        this.irrigation = true;
    }

    public void upPlantedSize() {
        if (plantedSize < planted.length) {
            plantedSize++;
        }
    }

    public void dePlantedSize() {
        if (plantedSize > 0) {
            plantedSize--;
        }
    }

    public int getPlantedSize() {
        return plantedSize;
    }

    public void upgradePlots() {
        Plot[] newPlanted = new Plot[planted.length + 1];
        for (int i = 0; i < planted.length; i++) {
            newPlanted[i] = planted[i];
        }
        newPlanted[newPlanted.length - 1] = new Plot();
        planted = newPlanted;
    }
}