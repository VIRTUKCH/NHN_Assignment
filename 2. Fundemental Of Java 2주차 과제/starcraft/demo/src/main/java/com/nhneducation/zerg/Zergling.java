package com.nhneducation.zerg;

import com.nhneducation.abstractclass.Unit;
import com.nhneducation.abstractclass.Zerg;
import com.nhneducation.interfacei.NonFlyable;

public class Zergling extends Zerg implements NonFlyable {
    public Zergling() {
        this.offensePower = 2;
        this.defensePower = 2;
        this.name = "Zergling";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}