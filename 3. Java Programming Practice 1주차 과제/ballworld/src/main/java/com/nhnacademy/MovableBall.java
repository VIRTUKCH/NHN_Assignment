package com.nhnacademy;

import java.awt.Color;

public class MovableBall extends PaintableBall {
    public static final int DEFAULT_DX = 0;
    public static final int DEFAULT_DY = 0;

    int dx = DEFAULT_DX; // 단위 시간 당 x값의 변화량
    int dy = DEFAULT_DY; // 단위 시간 당 y값의 변화량

    public MovableBall(int x, int y, int radius, Color color) { // dx, dy는 생성자를 통해 초기화하지 않고, 세터를 통해 받음.
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
            System.out.println("위치 재조정");
        }
    }

    public void moveTo(int x, int y) { // 서터를 불러서 처리
        setX(x);
        setY(y);
    }
}
