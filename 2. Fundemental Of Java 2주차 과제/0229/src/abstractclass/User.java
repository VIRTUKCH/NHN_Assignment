package src.abstractclass;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import src.Human;

public abstract class User {
    private List<Unit> list;

    public List<Unit> getList() {
        return this.list;
    }

    public void setList(List<Unit> list) {
        this.list = list;
    }

    public void printList() {
        Collections.sort(list); // sort는 iterator 생성 이전에 해야 함. 그렇지 않으면 java.util.ConcurrentModificationException
        if(this instanceof Human) {
            System.out.println("[아군] : " + list.get(0).getTribe());
        } else {
            System.out.println("[적군] : " + list.get(0).getTribe());
        }

        Iterator<Unit> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            System.out.println(String.valueOf(i++) + ". " + iterator.next());
        }
    }
    
    abstract public void orderAttack(User enemyUser, int teamIdx, int enemyTeamIdx);
}
