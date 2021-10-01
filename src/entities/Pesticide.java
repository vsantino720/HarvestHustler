package entities;

public class Pesticide extends Item {
    public Pesticide() {
        super(25, "Pesticide");
        setColor("Purple");
    }

    @Override
    public Item clone() {
        return new Pesticide();
    }
}
