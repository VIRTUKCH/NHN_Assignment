package src.terran;

import src.Interface.Flyable;
import src.abstractclass.Terran;
import src.abstractclass.Unit;

public class Wraith extends Terran implements Flyable {
    public Wraith() {
        this.offensePower = 3;
        this.defensePower = 10;
        this.name = "Wraith";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}