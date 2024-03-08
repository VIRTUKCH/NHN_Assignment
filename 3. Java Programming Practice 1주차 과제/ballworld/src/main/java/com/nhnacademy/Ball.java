package com.nhnacademy;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ball implements Bounded {
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

        bounds = new Bounds(x - radius, y - radius, 2 * radius, 2 * radius); // 바운드는 공의 테두리
        logger.trace("Ball created : {}, {}, {}", x, y, radius);
    }

    @Override
    public Bounds getBounds() {
        return this.bounds;
        // return new Bounds(bounds); // 왜 이게 중요한지 잘 모르겠음
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