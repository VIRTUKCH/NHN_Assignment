package com.nhneducation.terran;

import com.nhneducation.abstractclass.Terran;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.NonFlyable;

public class Tank extends Terran implements NonFlyable {
    public Tank() {
        this.offensePower = 7;
        this.defensePower = 15;
        this.name = "Tank";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}