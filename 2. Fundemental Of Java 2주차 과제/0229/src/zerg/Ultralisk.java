package src.zerg;

import src.Interface.NonFlyable;
import src.abstractclass.Unit;
import src.abstractclass.Zerg;

public class Ultralisk extends Zerg implements NonFlyable {
    public Ultralisk() {
        this.offensePower = 5;
        this.defensePower = 15;
        this.name = "Ultralisk";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}