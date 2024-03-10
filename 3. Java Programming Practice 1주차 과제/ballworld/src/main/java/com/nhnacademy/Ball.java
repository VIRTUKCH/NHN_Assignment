package com.nhnacademy;

import java.awt.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * <Ball은>
 * 1. id, Rectangle, count를 멤버 변수로 가지고 있음.
 * 2. Regionable 인터페이스는 좌표로 나타내기 위한 메서드들
 * 3. 좌표는 Rectangle을 통해 나타내고 있고, 참조 변수의 이름은 region.
 * 
 * <Ball의 기능>
 * 1. 아이디, x, y좌표(Region) 등을 가지고 있음
 * 2. 반지름을 나타낼 수 있는데, Rectangle의 width/2를 내부적으로 return함.
 * 실제로 radius라는 멤버 변수를 가지고 있지은 않음.
 */

// Ball(Regionable) -> PaintalbeBall(Paintable) -> MovableBall(Movable) -> BoundedBall(Bounded)
public class Ball implements Regionable {
    static int getRegionCallCount = 0;
    static int count = 0;
    int id = ++count;
    Rectangle region;
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    public Ball(int x, int y, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("반지름은 0보다 커야 합니다.");
        }

        if ((x + (long) radius > Integer.MAX_VALUE)
                || (x - (long) radius < Integer.MIN_VALUE)
                || (y + (long) radius > Integer.MAX_VALUE)
                || (y - (long) radius < Integer.MIN_VALUE)) {
            throw new IllegalArgumentException("볼이 정수 공간을 벗어납니다.");
        }

        region = new Rectangle(x - radius, y - radius, 2 * radius, 2 * radius);
        logger.trace("Ball created : {}, {}, {}", x, y, radius);
    }

    // -- 아래는 Regionable 인터페이스의 메서드를 오버라이딩 함 --

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getX() {
        return (int) region.getCenterX();
    }

    @Override
    public int getY() {
        return (int) region.getCenterY();
    }

    @Override
    public Rectangle getRegion() {
        return region;
    }

    void setX(int x) {
        region.setLocation(x - getRadius(), getY() - getRadius());
    }

    void setY(int y) {
        region.setLocation(getX() - getRadius(), y - getRadius());
    }

    public int getRadius() {
        return (int) region.getWidth() / 2;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d,%d)", getX(), getY(), getRadius());
    }
}
