package com.nhneducation.protoss;

import com.nhneducation.abstractclass.Protoss;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.Flyable;

public class Consair extends Protoss implements Flyable {
    public Consair() {
        this.offensePower = 4;
        this.defensePower = 12;
        this.name = "Consair";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}