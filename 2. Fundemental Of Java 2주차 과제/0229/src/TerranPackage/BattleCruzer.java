package src.TerranPackage;

import src.Interface.Flyable;
import src.abstractclass.Terran;
import src.abstractclass.Unit;

public class BattleCruzer extends Terran implements Flyable {
    public BattleCruzer() {
        this.offensePower = 20;
        this.defensePower = 30;
        this.name = "BattleCruzer";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}