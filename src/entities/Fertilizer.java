package entities;

public class Fertilizer extends Item {
    public Fertilizer() {
        super(50, "Fertilizer");
        setColor("Brown");
    }

    @Override
    public Item clone() {
        return new Fertilizer();
    }
}
