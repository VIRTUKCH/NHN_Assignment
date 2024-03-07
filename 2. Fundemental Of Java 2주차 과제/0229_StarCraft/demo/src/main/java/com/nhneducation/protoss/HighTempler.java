package com.nhneducation.protoss;

import com.nhneducation.abstractclass.Protoss;
import com.nhneducation.abstractclass.Unit;
import com.nhneducation.interfacei.NonFlyable;

public class HighTempler extends Protoss implements NonFlyable {
    public HighTempler() {
        this.offensePower = 10;
        this.defensePower = 2;
        this.name = "HighTempler";
    }

    @Override
    public int compareTo(Unit o) {
        return this.defensePower - o.getDefensePower();
    }
}
