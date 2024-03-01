package src;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import src.abstractclass.Unit;
import src.abstractclass.User;

public class Computer extends User {
    private List<Unit> list;

    Computer(int number) { // 생성자의 역할은 멤버변수 초기화.
        list = new LinkedList<>();
        list = UnitManager.getList(number);
    }

    public void printComputer() {
        System.out.println("적군 : " + list.get(0).getTribe());
        Iterator<Unit> iterator = list.iterator();
        int i = 0;
        Collections.sort(list);
        while (iterator.hasNext()) {
            System.out.println(String.valueOf(i++) + ". " + iterator.next());
        }
        System.out.println();
    }
}