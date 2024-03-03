package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import src.abstractclass.Unit;
import src.abstractclass.User;

/*
 * 최대한 확장성이 좋게 만들어야 한다.
 * 분리할 수 있다면, 최대한 분리해 보자.
 * 그러나, 의미 없는 값의 이동같은 분리는 하면 안 된다.
 * + 통합할 수 있으면 최대한 통합해 보자.
 */

public class Human extends User {
    private List<Unit> list;

    Human(int number) { // 생성자의 역할은 멤버변수 초기화.
        list = new ArrayList<>();
        list = UnitManager.getList(number);
    }

    public void printHuman() {
        Collections.sort(list);
        System.out.println("아군 : " + list.get(0).getTribe());
        Iterator<Unit> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            System.out.println(String.valueOf(i++) + ". " + iterator.next());
        }
    }
}