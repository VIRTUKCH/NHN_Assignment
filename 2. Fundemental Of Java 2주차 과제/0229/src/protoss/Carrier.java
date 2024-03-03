package src.protoss;

import src.Interface.Flyable;
import src.abstractclass.Protoss;
import src.abstractclass.Unit;

public class Carrier extends Protoss implements Flyable {
    public Carrier() {
        this.offensePower = 25;
        this.defensePower = 40;
        this.name = "Carrier";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}