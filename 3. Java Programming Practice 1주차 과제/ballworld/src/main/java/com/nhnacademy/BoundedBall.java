package com.nhnacademy;

import java.awt.Color;
import java.awt.Rectangle;

public class BoundedBall extends MovableBall {
    private Rectangle bounds;

    static final int FRAME_WIDTH = 500;
    static final int FRAME_HEIGHT = 400;

    public BoundedBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
        this.bounds = new Rectangle(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
    }

    Rectangle getBounds() {
        return this.bounds;
    }

    void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    /*
     * Q. 공마다 벽이 다른 듯한 느낌을 받는 이유는 무엇일까?
     * A. dx, dy의 크기가 공마다 다르기 때문이다.
     * 단위 시간 당 움직이는 속도의 크기에 따라, bounce() 메서드를 호출하는 공의 좌표가 다르다.
     */
    boolean isOutOfBounds() {
        if (x + radius >= FRAME_WIDTH) { // 1. 우측 충돌 - 반만 담궈도 됨
            return true;
        } else if (x - radius <= 0) { // 2. 좌측 충돌 - 반지름 만큼 거리 남았는데도 튕겨버림
            return true;
        } else if (y + radius >= FRAME_HEIGHT) { // 3. 하단 충돌 - 맵에서 사라져야 인정됨.
            return true;
        } else if (y - radius <= 0) { // 4. 상단 충돌
            return true;
        } else {
            return false;
        }
    }

    public void move() {
        super.move();
        if(isOutOfBounds()) {
            bounce();
        }
    }

    void bounce() {
        if (x - radius <= 0 || x + radius >= FRAME_WIDTH) {
            dx = -dx;
        } else if (y - radius <= 0 || y + radius >= FRAME_HEIGHT) {
            dy = -dy;
        }
    }
}