package com.nhneducation.terran;

import com.nhneducation.abstractclass.Terran;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.NonFlyable;

public class Marine extends Terran implements NonFlyable {
    public Marine() {
        this.offensePower = 3;
        this.defensePower = 10;
        this.name = "Marine";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}