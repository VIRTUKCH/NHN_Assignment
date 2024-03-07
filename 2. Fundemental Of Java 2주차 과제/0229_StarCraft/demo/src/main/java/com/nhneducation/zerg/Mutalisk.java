package com.nhneducation.zerg;

import com.nhneducation.abstractclass.Unit;
import com.nhneducation.abstractclass.Zerg;
import com.nhneducation.interfacei.Flyable;

public class Mutalisk extends Zerg implements Flyable {
    public Mutalisk() {
        this.offensePower = 2;
        this.defensePower = 8;
        this.name = "Mutalisk";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}