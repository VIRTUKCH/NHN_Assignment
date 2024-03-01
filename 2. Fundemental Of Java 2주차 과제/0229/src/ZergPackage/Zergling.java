package src.ZergPackage;

import src.Interface.NonFlyable;
import src.abstractclass.Unit;
import src.abstractclass.Zerg;

public class Zergling extends Zerg implements NonFlyable {
    public Zergling() {
        this.offensePower = 2;
        this.defensePower = 2;
        this.name = "Zergling";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}