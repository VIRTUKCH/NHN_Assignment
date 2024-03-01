package src.TerranPackage;

import src.Interface.NonFlyable;
import src.abstractclass.Terran;
import src.abstractclass.Unit;

public class Tank extends Terran implements NonFlyable {
    public Tank() {
        this.offensePower = 7;
        this.defensePower = 15;
        this.name = "Tank";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}