package com.nhneducation;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.nhneducation.abstractclass.Unit;
import com.nhneducation.abstractclass.User;
import com.nhneducation.interfacei.FlyAttackable;
import com.nhneducation.interfacei.NonFlyable;

public class Computer extends User {
    private List<Unit> list;

        public boolean isListHasOnlyFlyable() {
        for (Unit unit : list) {
            if(unit instanceof NonFlyable) {
                return false;
            }
        }
        return true;
    }

    public boolean isListHasOnlyNonFlyAttackable() {
        for (Unit unit : list) {
            if(unit instanceof FlyAttackable) {
                return false;
            }
        }
        return true;
    }

    public boolean isListEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public List<Unit> getList() {
        return this.list;
    }

    @Override
    public void setList(List<Unit> list) {
        this.list = list;
    }

    Computer() { // 생성자의 역할은 멤버변수 초기화.
        int number = (int) (Math.random() * 3) + 1;
        list = UnitManager.getList(number);
    }

    @Override
    public void printList() {
        Collections.sort(list);
        System.out.println("[적군] : " + list.get(0).getTribe());

        Iterator<Unit> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            System.out.println(String.valueOf(i++) + ". " + iterator.next());
        }
        System.out.println();
    }

    public void orderAttack(User enemyUser, int computerIdx, int humanIdx) {
        Unit teamUnit = this.list.get(computerIdx);
        teamUnit.attack(enemyUser.getList().get(humanIdx));
        enemyUser.checkDiedUnitAndPop();
        Collections.sort(enemyUser.getList());
    }

    @Override
    public void checkDiedUnitAndPop() {
        Collections.sort(list);
        Iterator<Unit> iterator = list.iterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if(unit.getDefensePower() <= 0) {
                iterator.remove();
            }
        }
    }
}