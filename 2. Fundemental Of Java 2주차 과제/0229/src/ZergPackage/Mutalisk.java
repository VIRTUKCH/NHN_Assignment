package src.ZergPackage;

import src.Interface.Flyable;
import src.abstractclass.Unit;
import src.abstractclass.Zerg;

public class Mutalisk extends Zerg implements Flyable {
    public Mutalisk() {
        this.offensePower = 2;
        this.defensePower = 8;
        this.name = "Mutalisk";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}