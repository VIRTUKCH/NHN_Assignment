package src.ZergPackage;

import src.Interface.Flyable;
import src.abstractclass.Unit;
import src.abstractclass.Zerg;

public class Queen extends Zerg implements Flyable {
    public Queen() {
        this.offensePower = 15;
        this.defensePower = 25;
        this.name = "Queen";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}