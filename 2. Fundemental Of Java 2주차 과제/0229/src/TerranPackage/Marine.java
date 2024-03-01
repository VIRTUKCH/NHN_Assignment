package src.TerranPackage;

import src.Interface.NonFlyable;
import src.abstractclass.Terran;
import src.abstractclass.Unit;

public class Marine extends Terran implements NonFlyable {
    public Marine() {
        this.offensePower = 3;
        this.defensePower = 10;
        this.name = "Marine";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}