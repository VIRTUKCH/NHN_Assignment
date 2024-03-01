package src.ProtossPackage;

import src.Interface.FlyAttackable;
import src.Interface.NonFlyable;
import src.abstractclass.Protoss;
import src.abstractclass.Unit;

public class Dragoon extends Protoss implements NonFlyable, FlyAttackable {
    public Dragoon() {
        this.offensePower = 3;
        this.defensePower = 15;
        this.name = "Dragoon";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}