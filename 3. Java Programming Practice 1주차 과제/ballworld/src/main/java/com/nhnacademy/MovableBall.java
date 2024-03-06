package com.nhnacademy;

import java.awt.Color;

public class MovableBall extends PaintableBall {
    public static final int DEFAULT_DX = 0;
    public static final int DEFAULT_DY = 0;

    int dx = DEFAULT_DX;
    int dy = DEFAULT_DY;

    public MovableBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
    }

    public int getDX() {
        return this.dx;
    }

    public void setDX(int dx) {
        this.dx = dx;
    }

    public int getDY() {
        return this.dy;
    }

    public void setDY(int dy) {
        this.dy = dy;
    }

    public void move() {
        try {
            moveTo(getX() + getDX(), getY() + getDY());
        } catch (IllegalArgumentException e) {
        }
    }

    public void moveTo(int x, int y) { // 서터를 불러서 처리
        setX(x);
        setY(y);
    }
}
