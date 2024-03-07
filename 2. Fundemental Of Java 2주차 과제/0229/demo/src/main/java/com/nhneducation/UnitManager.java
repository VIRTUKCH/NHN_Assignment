package com.nhneducation;

import java.util.ArrayList;
import java.util.List;

import com.nhneducation.abstractclass.Unit;
import com.nhneducation.exception.UnExpectedException;
import com.nhneducation.protoss.*;
import com.nhneducation.terran.*;
import com.nhneducation.zerg.*;

public final class UnitManager {
    
    private UnitManager() {}

    static final int NUMBER_OF_PROTOSS_OBJECT = 5;
    static final int NUMBER_OF_TERRAN_OBJECT = 6;
    static final int NUMBER_OF_ZERG_OBJECT = 9;
    static final int NUMBER_OF_TYPE = 6;

    public static List<Unit> getList(int number) {
        List<Unit> list = new ArrayList<>();
        int random = 0;

        if (number == 1) { // 프로토스
            for (int i = 0; i < NUMBER_OF_PROTOSS_OBJECT; i++) {
                random = (int) (Math.random() * NUMBER_OF_TYPE) + 1;
                if(random == 1) {
                    list.add(new Consair());
                } else if(random == 2) {
                    list.add(new Dragoon());
                } else if(random == 3) {
                    list.add(new HighTempler());
                } else if(random == 4){
                    list.add(new Scout());
                } else if(random == 5) {
                    list.add(new Zealot());
                } else if(random == 6) {
                    list.add(new Carrier());
                }
            }
            return list;
        }
        
        else if (number == 2) { // 테란
            for (int i = 0; i < NUMBER_OF_TERRAN_OBJECT; i++) {
                random = (int) (Math.random() * NUMBER_OF_TYPE) + 1;
                if(random == 1) {
                    list.add(new Goliath());
                } else if(random == 2) {
                    list.add(new Marine());
                } else if(random == 3) {
                    list.add(new Tank());
                } else if(random == 4){
                    list.add(new Valkyrie());
                } else if(random == 5) {
                    list.add(new Wraith());
                } else if(random == 6) {
                    list.add(new BattleCruzer());
                }
            }
            return list;
        }
        
        else if (number == 3) { // 저그
            for (int i = 0; i < NUMBER_OF_ZERG_OBJECT; i++) {
                random = (int) (Math.random() * NUMBER_OF_TYPE) + 1;
                if(random == 1) {
                    list.add(new Guardian());
                } else if(random == 2) {
                    list.add(new Hydralisk());
                } else if(random == 3) {
                    list.add(new Mutalisk());
                } else if(random == 4){
                    list.add(new Ultralisk());
                } else if(random == 5) {
                    list.add(new Zergling());
                } else if(random == 6) {
                    list.add(new Queen());
                }
            }
            return list;
        }

        else {
            try {
                throw new UnExpectedException("프로그램을 진행할 수 없는 심각한 오류입니다. 개발자에게 신고 부탁드립니다.");
            } catch (UnExpectedException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}