package com.nhnacademy;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ball implements Bounded {
    // 참조변수에 final을 붙이면, 한 번 초기화된 후에 다른 객체를 참조할 수는 없다.
    final String id = UUID.randomUUID().toString();
    final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    final Bounds bounds;

    public Ball(Point point, int radius) { // 내부적으로 아래 생성자를 부른다.
        this(point.getX(), point.getY(), radius);
    }

    public Ball(int x, int y, int radius) { // 모든 생성은 이 메서드를 지나야만 한다.
        if (radius <= 0) {
            throw new IllegalArgumentException("반지름은 0보다 커야 합니다.");
        }

        if ((x + (long) radius > Integer.MAX_VALUE)
                || (x - (long) radius < Integer.MIN_VALUE)
                || (y + (long) radius > Integer.MAX_VALUE)
                || (y - (long) radius < Integer.MIN_VALUE)) {
            throw new IllegalArgumentException("볼이 정수 공간을 벗어납니다.");
        }

        // bounds는 공의 사각형 테두리를 의미함(내부적으로 Rectangle을 사용함).
        // final로 선언되었기 때문에, 한 번 초기화하고 나서 다른 객체로 재할당 될 수는 없음.
        // 다시 말해서, 값을 수정하는 건 가능하지만, new 연산자를 사용해 새로운 객체를 생성하는 것은 불가능함.
        bounds = new Bounds(x - radius, y - radius, 2 * radius, 2 * radius); 
        logger.trace("Ball created : {}, {}, {}", x, y, radius);
    }

    @Override
    public Bounds getBounds() {
        return this.bounds;
        // return new Bounds(bounds); // 강사님은 이렇게 코드를 작성하셨는데, 난 이렇게 쓰는 게 좋은지 모르겠음.
    }

    @Override
    public int getMinX() {
        return bounds.getMinX();
    }

    @Override
    public int getMaxX() {
        return bounds.getMaxX();
    }

    @Override
    public int getCenterX() {
        return bounds.getCenterX();
    }

    @Override
    public int getMinY() {
        return bounds.getMinY();
    }

    @Override
    public int getMaxY() {
        return bounds.getMaxY();
    }

    @Override
    public int getCenterY() {
        return bounds.getCenterY();
    }

    @Override
    public int getWidth() {
        return bounds.getWidth();
    }

    @Override
    public int getHeight() {
        return bounds.getHeight();
    }

    @Override
    public boolean isCollision(Bounds other) {
        return this.bounds.isCollision(other);
    }

    @Override
    public boolean isInclude(Bounds other) {
        return this.bounds.isInclude(other);
    }
}