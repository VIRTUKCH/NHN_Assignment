package com.nhneducation.protoss;

import com.nhneducation.abstractclass.Protoss;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.FlyAttackable;
import com.nhneducation.interfacei.NonFlyable;

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