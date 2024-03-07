package com.nhneducation.terran;

import com.nhneducation.abstractclass.Terran;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.Flyable;

public class BattleCruzer extends Terran implements Flyable {
    public BattleCruzer() {
        this.offensePower = 20;
        this.defensePower = 30;
        this.name = "BattleCruzer";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}