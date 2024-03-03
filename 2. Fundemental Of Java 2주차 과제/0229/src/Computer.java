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

    public void orderAttack(User enemyUser) {
        if(enemyUser instanceof Human) {
            while(true) {
                try {
                    int teamIdx = (int) (Math.random() * this.getList().size()) + 1;
                    int enemyTeamIdx = (int) (Math.random() * enemyUser.getList().size()) + 1;
                    
                    Unit teamUnit = this.list.get(teamIdx);
                    teamUnit.attack(enemyUser.getList().get(enemyTeamIdx));
                } catch (Exception e) { // 공중 유닛 못 때리는데 때린다고 하면 어쩔 거야.
                    e.printStackTrace();
                    System.out.println();
                    System.out.println("적절한 대상을 다시 공격해 주세요.");
                    System.out.println();
                }
                break;
            }
        }
        enemyUser.checkDiedUnitAndPop();
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