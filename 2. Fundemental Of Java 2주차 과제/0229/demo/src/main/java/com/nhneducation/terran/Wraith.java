package com.nhneducation.terran;

import com.nhneducation.abstractclass.Terran;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.Flyable;

public class Wraith extends Terran implements Flyable {
    public Wraith() {
        this.offensePower = 3;
        this.defensePower = 10;
        this.name = "Wraith";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}