package com.nhneducation.protoss;

import com.nhneducation.abstractclass.Protoss;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.Flyable;

public class Scout extends Protoss implements Flyable {
    public Scout() {
        this.offensePower = 5;
        this.defensePower = 10;
        this.name = "Scout";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}