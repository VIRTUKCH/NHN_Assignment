package src.TerranPackage;

import src.Interface.Flyable;
import src.abstractclass.Terran;
import src.abstractclass.Unit;

public class Valkyrie extends Terran implements Flyable {
    public Valkyrie() {
        this.offensePower = 4;
        this.defensePower = 12;
        this.name = "Valkyrie";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}