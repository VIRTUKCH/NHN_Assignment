package com.nhneducation.zerg;

import com.nhneducation.abstractclass.Unit;
import com.nhneducation.abstractclass.Zerg;
import com.nhneducation.interfacei.Flyable;

public class Guardian extends Zerg implements Flyable {
    public Guardian() {
        this.offensePower = 3;
        this.defensePower = 6;
        this.name = "Guardian";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}