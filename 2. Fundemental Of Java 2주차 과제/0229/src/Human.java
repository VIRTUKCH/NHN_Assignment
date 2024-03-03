package src;

import java.util.ArrayList;
import java.util.List;

import src.Exception.UnAttackableUnitException;
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

    public List<Unit> getList() {
        return this.list;
    }

    public void setList(List<Unit> list) {
        this.list = list;
    }

    Human(int number) { // 생성자의 역할은 멤버변수 초기화.
        list = new ArrayList<>();
        list = UnitManager.getList(number);
    }

    @Override
    public void printList() {
        super.printList();
    }

    @Override
    public void orderAttack(User enemyUser, int teamIdx, int enemyTeamIdx) {
        if(enemyUser instanceof Computer) {
            Unit teamUnit = this.list.get(teamIdx);
            try {
                teamUnit.attack(enemyUser.getList().get(enemyTeamIdx));
            } catch (UnAttackableUnitException e) { // 공중 유닛 못 때리는데 때린다고 하면 어쩔 거야.
                System.out.println(e.getMessage());
                e.printStackTrace();
            } catch (Exception e) { // 리스트가 비었으면 어쩔 거야
                e.printStackTrace();
            }
        }
    }
}