package src.zerg;

import src.Interface.FlyAttackable;
import src.Interface.NonFlyable;
import src.abstractclass.Unit;
import src.abstractclass.Zerg;

public class Hydralisk extends Zerg implements NonFlyable, FlyAttackable {
    public Hydralisk() {
        this.offensePower = 3;
        this.defensePower = 7;
        this.name = "Hydralisk";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}