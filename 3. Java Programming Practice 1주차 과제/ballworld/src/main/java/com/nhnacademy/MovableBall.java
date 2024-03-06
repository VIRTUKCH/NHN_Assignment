package com.nhnacademy;

import java.awt.Color;

public class MovableBall extends PaintableBall {
    int dx = 0;
    int dy = 0;

    public MovableBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
    }

    public int getDx() {
        return this.dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return this.dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void move() {
        moveTo(getX() + getDx(), getY() + getDy());
    }

    public void moveTo(int x, int y) { // 서터를 불러서 처리
        setX(x);
        setY(y);
    }
}
