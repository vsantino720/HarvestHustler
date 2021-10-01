package controllers;

import entities.*;

import java.util.ArrayList;
import java.util.List;

public class InitialConfigurationController {

    private Crop initialCrop;
    private Season initialSeason;
    private String name;
    private Player initialPlayer;
    private Game game;
    private List<Crop> allCrops = new ArrayList<>();
    private List<Item> allItems = new ArrayList<>();

    public InitialConfigurationController() {
        this.name = "Player";
        this.initialSeason = Season.FALL;
        initialPlayer = new Player(0, name);
        this.game = new Game(Season.FALL, initialPlayer, 1);

        allCrops.add(new Crop("Corn", 100, 9, "Seed", "yellow"));
        allCrops.add(new Crop("Wheat", 75, 6, "Seed", "goldenrod"));
        allCrops.add(new Crop("Soybean", 50, 5, "Seed", "brown"));
        allCrops.add(new Crop("Potatoes", 120, 10, "Seed", "peru"));
        allCrops.add(new Crop("Carrots", 150, 12, "Seed", "orange"));
        allCrops.add(new Crop("Green Beans", 90, 7, "Seed", "green"));

        allItems.add(new Fertilizer());
        allItems.add(new Pesticide());
    }

    public void nameHandler(String text) {
        name = text;
    }

    public void difficultyHandler1() {
        game.setDifficulty(1);
    }

    public void difficultyHandler2() {
        game.setDifficulty(2);
    }

    public void difficultyHandler3() {
        game.setDifficulty(3);
    }

    public void cropHandler(Crop crop) {
        this.initialCrop = crop;
    }

    public void seasonHandler(Season season) {
        this.initialSeason = season;
    }

    public void initializeGame() {
        initialPlayer.setName(name);
        if (this.initialCrop == null) {
            this.initialCrop = allCrops.get(2);
        }
        initialPlayer.giveCrop(initialCrop.seedOf());
        initialPlayer.setMoney((4 - getDifficulty()) * 100);
        game.setSeason(initialSeason);
    }

    public Game getGame() {
        return game;
    }

    public Player getInitialPlayer() {
        return initialPlayer;
    }

    public List<Crop> getAllCrops() {
        return allCrops;
    }

    public int getDifficulty() {
        return game.getDifficulty();
    }

    public Crop getInitialCrop() {
        return initialCrop;
    }

    public List<Item> getAllItems() {
        return allItems;
    }

}
