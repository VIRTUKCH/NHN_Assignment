package src.ProtossPackage;

import src.Interface.Flyable;
import src.abstractclass.Protoss;
import src.abstractclass.Unit;

public class Scout extends Protoss implements Flyable {
    public Scout() {
        this.offensePower = 5;
        this.defensePower = 10;
        this.name = "Scout";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}