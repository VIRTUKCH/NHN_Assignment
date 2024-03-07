package com.nhneducation.zerg;

import com.nhneducation.abstractclass.Unit;
import com.nhneducation.abstractclass.Zerg;
import com.nhneducation.interfacei.NonFlyable;

public class Ultralisk extends Zerg implements NonFlyable {
    public Ultralisk() {
        this.offensePower = 5;
        this.defensePower = 15;
        this.name = "Ultralisk";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}