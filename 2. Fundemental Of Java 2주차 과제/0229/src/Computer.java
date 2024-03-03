package src;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import src.Interface.FlyAttackable;
import src.Interface.Flyable;
import src.Interface.NonFlyable;
import src.abstractclass.Unit;
import src.abstractclass.User;

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

    public List<Unit> getList() {
        return this.list;
    }

    public void setList(List<Unit> list) {
        this.list = list;
    }

    Computer() { // 생성자의 역할은 멤버변수 초기화.
        int number = (int) (Math.random() * 3) + 1;
        list = UnitManager.getList(number);
    }

    @Override
    public void printList() {
        System.out.println("[적군] : " + list.get(0).getTribe());

        Iterator<Unit> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            System.out.println(String.valueOf(i++) + ". " + iterator.next());
        }
        System.out.println();
    }

    public void orderAttack(User enemyUser, int teamIdx, int enemyTeamIdx) {
        Unit teamUnit = this.list.get(teamIdx);
        teamUnit.attack(enemyUser.getList().get(enemyTeamIdx));
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