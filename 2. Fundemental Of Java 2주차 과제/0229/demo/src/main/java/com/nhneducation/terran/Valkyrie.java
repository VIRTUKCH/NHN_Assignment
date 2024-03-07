package com.nhneducation.terran;

import com.nhneducation.abstractclass.Terran;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.Flyable;

public class Valkyrie extends Terran implements Flyable {
    public Valkyrie() {
        this.offensePower = 4;
        this.defensePower = 12;
        this.name = "Valkyrie";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}