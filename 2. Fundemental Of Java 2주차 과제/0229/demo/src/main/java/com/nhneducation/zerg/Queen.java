package com.nhneducation.zerg;

import com.nhneducation.abstractclass.Unit;
import com.nhneducation.abstractclass.Zerg;
import com.nhneducation.interfacei.Flyable;

public class Queen extends Zerg implements Flyable {
    public Queen() {
        this.offensePower = 15;
        this.defensePower = 25;
        this.name = "Queen";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}