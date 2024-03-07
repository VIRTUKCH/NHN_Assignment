package com.nhneducation.zerg;

import com.nhneducation.abstractclass.Unit;
import com.nhneducation.abstractclass.Zerg;
import com.nhneducation.interfacei.FlyAttackable;
import com.nhneducation.interfacei.NonFlyable;

public class Hydralisk extends Zerg implements NonFlyable, FlyAttackable {
    public Hydralisk() {
        this.offensePower = 3;
        this.defensePower = 7;
        this.name = "Hydralisk";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}