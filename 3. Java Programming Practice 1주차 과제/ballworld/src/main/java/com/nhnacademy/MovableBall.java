package com.nhnacademy;

import java.awt.Color;

public class MovableBall extends PaintableBall {
    public MovableBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
    }

    int dx;
    int dy;

    int getDx() {
        return this.dx;
    }

    void setDx(int dx) {
        this.dx = dx;
    }

    int getDy() {
        return this.dy;
    }

    void setDy(int dy) {
        this.dy = dy;
    }

    void move() {
        this.x = x + this.dx;
        this.y = y + this.dy;
    }

    void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
