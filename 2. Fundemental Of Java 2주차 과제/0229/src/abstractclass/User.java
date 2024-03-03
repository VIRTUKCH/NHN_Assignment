package src.abstractclass;

import java.util.List;

public abstract class User {
    private List<Unit> list;

    public List<Unit> getList() {
        return this.list;
    }

    public void setList(List<Unit> list) {
        this.list = list;
    }

    public void printList() {
        
    }

    public void orderAttack(User enemyUser, int teamIdx, int enemyTeamIdx) {
    }
}
