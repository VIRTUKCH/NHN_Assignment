package src.TerranPackage;

import src.Interface.FlyAttackable;
import src.Interface.NonFlyable;
import src.abstractclass.Terran;
import src.abstractclass.Unit;

public class Goliath extends Terran implements NonFlyable, FlyAttackable {
    public Goliath() {
        this.offensePower = 5;
        this.defensePower = 15;
        this.name = "Goliath";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}