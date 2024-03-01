enum Direction {
    EAST(1),
    SOUTH(2),
    WEST(3),
    NORTH(4);

    private int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

public class Main {
    public static void main(String[] args) {
        Direction d;

        System.out.println(Direction.valueOf("WEST").name()); // WEST
        System.out.println(Direction.valueOf("WEST")); // WEST
        System.out.println(Direction.WEST.ordinal()); // 2
        System.out.println(Direction.WEST.getValue()); // 3

        d = Direction.valueOf("WEST");
        System.out.println(d); // 객체에는 어떤 한 상태만 담을 수 있다.

        // 그렇지 않으면, 스태틱 메서드처럼 써야 한다.

    }
}