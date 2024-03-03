package src.abstractclass;
/*
 * [궁금한 점]
 * - 멤버변수는 강제화 할 수 있는 수단이 없나?
 * - 멤버변수를 강제화 할 필요가 없을까?
 * - 강제화 할 필요가 없다면, 추상 클래스에 멤버 변수는 왜 선언할 수 있게 만들었을까?
 * 1) 그냥 기억하기 위해서 하는 거라면 => 게터 세터까지 다 만들고
 * 2) 그런 이유도 없고 자유화 시키는 거라면 => 걍 지우면 안 되나?
 * 
 * 아니다. Unit을 Type으로 받을 수도 있으니까 가지고 있는 게 좋긴 하겠다. => 이게 결정적인 이유다.
 * 근데 필요할 때 넣어 보자. 일단 지금은 주석처리 하기
 * => 위 이유가 맞음. 캐스팅 할 때 참조변수가 참조할 수 있는 배경을 주어야 다형성의 본 목적 달성 가능.
 * * 파라미터에서 참조변수로 캐스팅하는 게 핵심임.
 * 
 * - 메서드를 상위 클래스에서 상속받았을 경우에, 왜 접근 지정자를 더 좁게 하지 못하는가?
 * 
 * [알게 된 점]
 * - 게터와 세터는 인터페이스에서 정의해도 강제하지 않는 걸로 예상 됨.
 * - 인터페이스의 추상 메서드로 강제할 것인지, 추상클래스의 추상 메서드로 강제할 것인지 생각하기.
 * 
 * [느낀 점]
 * - 실습을 하지 않으면 인터페이스나 추상클래스의 필요성에 대해 인지하지 못함.
 * - 강제적으로라도 악조건 속에서 설계해 보려는 습관을 들이는 게, 장기적으로 부족한 점을 빠르게 인지할 수 있는 배경이 되는 듯
 * - 최소한의 멤버 변수로 구현하려 하자. 멤버 변수가 많아질수록, 프로그램이 기하급수적으로 복잡해지게 된다.
 * - 최대한 확장성이 좋게 만들어야 한다.
 * - 분리할 수 있다면, 최대한 분리해 보자.
 * - 그러나, 의미 없는 값의 이동같은 분리는 하면 안 된다.
 * - 통합할 수 있으면 최대한 통합해 보자.
 * 
 * [피드백]
 * - FlyableAttack 인터페이스에 int getDefensePower(); 가 있으면 안 되는 거 아니냐...
 * => Flyable을 때려서 그렇다. 그 경우에는 Flyable의 디펜스 파워를 가져와야 한다.
 * => 그냥 Unit 때린 다음에 instanceOf 부르면 되는 거 아니냐
 * 
 * [내 고민]
 * Human 클래스는 여러 사용자에 대비하기 위해서 만들었다.
 * 이 상황에서, 나는 Unit 리스트를 만들려고 한다.
 * 나는 이게 당연하게 Human에게 있어야 한다고 생각한다.
 * User은 유닛들을 '가지고 있다'고 봐야 맞는 것 같다. 그럼 포함 관계다.
 * 하지만, 저그가 저그 종족의 유닛들을 '가지고 있다'고 보기에는 많이 어려울 것으로 보인다. 
 * 
 *

 */

import src.Exception.UnAttackableUnitException;
import src.Interface.FlyAttackable;
import src.Interface.Flyable;
import src.Interface.NonFlyable;

public abstract class Unit implements Comparable<Unit> {
    protected int offensePower;
    protected int defensePower;
    protected String tribe;
    protected String name;

    public int getOffensePower() {
        return this.offensePower;
    }

    public void setOffensePower(int offensePower) {
        this.offensePower = offensePower;
    }

    public int getDefensePower() {
        return this.defensePower;
    }

    public void setDefensePower(int defensePower) {
        this.defensePower = defensePower;
    }

    public String getTribe() {
        return this.tribe;
    }

    public void setTribe(String tribe) {
        this.tribe = tribe;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Unit() {
    }

    public void attack(Unit unit) throws Exception {
        // 공중 유닛을 때리려고 하는데, 나는 못 날면서 + 공중 때리지도 못하면
        if (unit instanceof Flyable && this instanceof NonFlyable && !(this instanceof FlyAttackable)) {
            throw new UnAttackableUnitException("공격할 수 없는 유닛입니다.");
        } else {
            unit.setDefensePower(unit.getDefensePower() - this.offensePower);
        }
    }

    @Override
    public String toString() {
        return this.name + " (현재 방어력 : " + this.defensePower + ")";
    }
    /*
     * [시나리오]
     * 1. NonFlyable + <FlyAttackable이 아닌>경우 => NonFlyable만 공격할 수 있음.
     * : 여기서 '예외'를 던져주면 됨
     * 
     * 2. 그 외에는 "예외를 던져 줄 필요"는 없음.
     * => 그냥 맞는 객체에 맞게 때려요~
     */
}