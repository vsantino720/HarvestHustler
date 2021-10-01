package entities;


public class Game {
    private Season[] seasons;
    private int currSeason;
    private Player player;
    private int day;
    private int difficulty;
    private int harvestMax;
    private int waterMax;

    public Game(Season season, Player player, int difficulty) {
        seasons = Season.values();
        this.player = player;
        this.day = 0;
        this.difficulty = difficulty;
        setSeason(season);
        waterMax = 6;
        harvestMax = 3;
    }

    public Season getSeason() {
        return seasons[currSeason];
    }

    public void setSeason(Season season) {
        for (int i = 0; i < seasons.length; i++) {
            if (seasons[i].equals(season)) {
                currSeason = i;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public int getDay() {
        return day;
    }

    public void incrementDay() {
        this.day++;
        if (day % 20 == 0) {
            currSeason = (currSeason + 1) % seasons.length;
        }
        updateMax();
    }

    public void updateMax() {
        if (player.hasTractor()) {
            harvestMax = 10;
        } else {
            harvestMax = 3;
        }
        if (player.hasIrrigation()) {
            waterMax = 16;
        } else {
            waterMax = 6;
        }
        if (day != 1) {
            if (player.getMoney() >= 10) {
                player.setMoney(player.getMoney() - 10);
            } else {
                player.setMoney(0);
            }
        }
    }

    public void setDifficulty(int diff) {
        difficulty = diff;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public double getWeatherChance() {
        if (day > 2) {
            return 0.05 * difficulty;
        } else {
            return 0;
        }
    }

    public int getHarvestMax() {
        return harvestMax;
    }

    public int getWaterMax() {
        return waterMax;
    }

    public void reduceWaterMax() {
        if (waterMax > 0) {
            waterMax--;
        }
    }

    public void reduceHarvestMax() {
        if (harvestMax > 0) {
            harvestMax--;
        }
    }

    public boolean gameOver() {
        if (player.getPlantedSize() == 0 && player.getMoney() < 80
                && player.getInventory().getCrops().size() == 0) {
            return true;
        }
        return false;
    }

    public boolean winGame() {
        if (day > 100 && player.getMoney() >= 2500) {
            return true;
        }
        return false;
    }
}
