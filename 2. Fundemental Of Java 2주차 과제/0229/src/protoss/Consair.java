package src.protoss;

import src.Interface.Flyable;
import src.abstractclass.Protoss;
import src.abstractclass.Unit;

public class Consair extends Protoss implements Flyable {
    public Consair() {
        this.offensePower = 4;
        this.defensePower = 12;
        this.name = "Consair";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}