package com.nhneducation.protoss;

import com.nhneducation.abstractclass.Protoss;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.FlyAttackable;

public class Carrier extends Protoss implements FlyAttackable {
    public Carrier() {
        this.offensePower = 25;
        this.defensePower = 40;
        this.name = "Carrier";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}