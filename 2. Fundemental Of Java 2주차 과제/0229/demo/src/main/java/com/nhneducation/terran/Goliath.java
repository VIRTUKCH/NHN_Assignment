package com.nhneducation.terran;

import com.nhneducation.abstractclass.Terran;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.FlyAttackable;
import com.nhneducation.interfacei.NonFlyable;

public class Goliath extends Terran implements NonFlyable, FlyAttackable {
    public Goliath() {
        this.offensePower = 5;
        this.defensePower = 15;
        this.name = "Goliath";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}