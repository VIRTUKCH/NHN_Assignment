package com.nhnacademy;

import java.awt.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ball {
    static int getRegionCallCount = 0;
    static int count = 0;
    int id = ++count;
    int x;
    int y;
    int radius;
    Rectangle region; // 볼의 테두리를 나타낸다.
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

        this.x = x;
        this.y = y;
        this.radius = radius;
        region = new Rectangle(x - radius, y - radius, 2 * radius, 2 * radius);
        logger.trace("Ball created : {}, {}, {}", x, y, radius);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void setX(int x) {
        this.x = x;
        region.setLocation(getX() - getRadius(), getY() - getRadius());
    }

    void setY(int y) {
        this.y = y;
        region.setLocation(getX() - getRadius(), getY() - getRadius());
    }

    public int getRadius() {
        return radius;
    }

    public Rectangle getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d,%d)", getX(), getY(), getRadius());
    }
}