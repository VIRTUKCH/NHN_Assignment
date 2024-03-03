package src;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import src.Exception.UnAttackableUnitException;
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

    public boolean isListHasOnlyNonFlyable() {
        for (Unit unit : list) {
            if(unit instanceof Flyable) {
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

    Computer(int number) { // 생성자의 역할은 멤버변수 초기화.
        list = UnitManager.getList(number);
    }

    @Override
    public void printList() {
        Collections.sort(list); // sort는 iterator 생성 이전에 해야 함. 그렇지 않으면 java.util.ConcurrentModificationException
        if(this instanceof Computer) {
            System.out.println("[적군] : " + list.get(0).getTribe());
        } else {
            System.out.println("[아군] : " + list.get(0).getTribe());
        }

        Iterator<Unit> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            System.out.println(String.valueOf(i++) + ". " + iterator.next());
        }
        System.out.println();
    }

    @Override
    public void orderAttack(User enemyUser, int teamIdx, int enemyTeamIdx) {
        if(enemyUser instanceof Computer) {
            Unit teamUnit = this.list.get(teamIdx);
            try {
                teamUnit.attack(enemyUser.getList().get(enemyTeamIdx));
            } catch (UnAttackableUnitException e) { // 공중 유닛 못 때리는데 때린다고 하면 어쩔 거야.
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println("적절한 대상을 다시 공격해 주세요.");
                System.out.println();
            } catch (Exception e) { // 리스트가 비었으면 어쩔 거야
                e.printStackTrace();
            }
        }
        checkDiedUnitAndPop();
    }

    @Override
    public void checkDiedUnitAndPop() {
        Iterator<Unit> iterator = list.iterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if(unit.getDefensePower() <= 0) {
                iterator.remove();
            }
        }
    }
}