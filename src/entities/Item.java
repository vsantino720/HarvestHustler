package entities;

public abstract class Item {

    private int basePrice;
    private String type;
    private String color;

    public Item(int basePrice, String type) {
        this.basePrice = basePrice;
        this.type = type;
    }

    public String color() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public int getBasePrice() {
        return this.basePrice;
    }

    @Override
    public boolean equals(Object obj) {
        Item o = (Item) obj;
        return (this.type.equals(o.type));
    }

    public String getType() {
        return this.type;
    }

    public abstract Item clone();
}
