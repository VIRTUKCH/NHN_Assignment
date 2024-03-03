package src.protoss;

import src.Interface.NonFlyable;
import src.abstractclass.Protoss;
import src.abstractclass.Unit;

public class Zealot extends Protoss implements NonFlyable {
    public Zealot() {
        this.offensePower = 5;
        this.defensePower = 20;
        this.name = "Zealot";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}