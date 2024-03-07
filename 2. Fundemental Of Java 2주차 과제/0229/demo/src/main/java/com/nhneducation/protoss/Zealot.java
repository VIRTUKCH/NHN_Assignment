package com.nhneducation.protoss;

import com.nhneducation.abstractclass.Protoss;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.NonFlyable;

public class Zealot extends Protoss implements NonFlyable {
    public Zealot() {
        this.offensePower = 5;
        this.defensePower = 20;
        this.name = "Zealot";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}