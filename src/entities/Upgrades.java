package entities;

import controllers.MainWindow;

public enum Upgrades {
    TRACTOR("Tractor", 500),
    IRRIGATION("Irrigation", 400),
    PLOT("+1 Plot", 10);

    private final int basePrice;
    private final String name;

    Upgrades(String name, int basePrice) {
        this.basePrice = basePrice;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getBasePrice() {
        return this.basePrice;
    }

    @Override
    public String toString() {
        if (this == Upgrades.PLOT) {
            return name + " " + "$" + basePrice
                    * MainWindow.getConfigController().getInitialPlayer().getPlanted().length;
        }
        return name + " " + "$" + basePrice;
    }

}
