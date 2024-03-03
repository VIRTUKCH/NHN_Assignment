package src.protoss;

import src.Interface.NonFlyable;
import src.abstractclass.Protoss;
import src.abstractclass.Unit;

public class HighTempler extends Protoss implements NonFlyable {
    public HighTempler() {
        this.offensePower = 10;
        this.defensePower = 2;
        this.name = "HighTempler";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}
