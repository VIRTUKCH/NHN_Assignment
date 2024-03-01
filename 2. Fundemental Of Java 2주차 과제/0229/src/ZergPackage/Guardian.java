package src.ZergPackage;

import src.Interface.Flyable;
import src.abstractclass.Unit;
import src.abstractclass.Zerg;

public class Guardian extends Zerg implements Flyable {
    public Guardian() {
        this.offensePower = 3;
        this.defensePower = 6;
        this.name = "Guardian";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}