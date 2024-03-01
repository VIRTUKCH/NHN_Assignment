package src;

import java.util.ArrayList;
import java.util.List;

import src.Exception.UnExpectedException;
import src.ProtossPackage.Consair;
import src.ProtossPackage.Dragoon;
import src.ProtossPackage.HighTempler;
import src.ProtossPackage.Scout;
import src.ProtossPackage.Zealot;
import src.TerranPackage.Goliath;
import src.TerranPackage.Marine;
import src.TerranPackage.Tank;
import src.TerranPackage.Valkyrie;
import src.TerranPackage.Wraith;
import src.ZergPackage.Guardian;
import src.ZergPackage.Hydralisk;
import src.ZergPackage.Mutalisk;
import src.ZergPackage.Ultralisk;
import src.ZergPackage.Zergling;
import src.abstractclass.Protoss;
import src.abstractclass.Terran;
import src.abstractclass.Unit;
import src.abstractclass.Zerg;

public final class UnitManager {
    
    private UnitManager() {}

    static final int NUMBER_OF_PROTOSS_OBJECT = 4;
    static final int NUMBER_OF_TERRAN_OBJECT = 5;
    static final int NUMBER_OF_ZERG_OBJECT = 8;
    static final int NUMBER_OF_TYPE = 5;

    public static List<Unit> getList(int number) {
        List<Unit> list = new ArrayList<>();
        int random = 0;

        if (number == 1) { // 프로토스
            Protoss[] protossArray = new Protoss[] { new Consair(), new Dragoon(), new HighTempler(), new Scout(), new Zealot() };
            for (int i = 0; i < NUMBER_OF_PROTOSS_OBJECT; i++) {
                random = (int) (Math.random() * NUMBER_OF_TYPE);
                list.add(protossArray[random]);
            }
            return list;
        }
        
        else if (number == 2) { // 테란
            Terran[] terranArray = new Terran[] { new Goliath(), new Marine(), new Tank(), new Valkyrie(), new Wraith() };
            for (int i = 0; i < NUMBER_OF_TERRAN_OBJECT; i++) {
                random = (int) (Math.random() * NUMBER_OF_TYPE);
                list.add(terranArray[random]);
            }
            return list;
        }
        
        else if (number == 3) { // 저그
            Zerg[] zergArray = new Zerg[] { new Guardian(), new Hydralisk(), new Mutalisk(), new Ultralisk(), new Zergling() };
            for (int i = 0; i < NUMBER_OF_ZERG_OBJECT; i++) {
                random = (int) (Math.random() * NUMBER_OF_TYPE);
                list.add(zergArray[random]);
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
        return list = null;
    }
}
