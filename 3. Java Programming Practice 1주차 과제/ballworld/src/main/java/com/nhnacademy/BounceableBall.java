package com.nhnacademy;

import java.awt.Color;
import java.util.List;

public class BounceableBall extends MovableBall implements Bounceable {
    public BounceableBall(Point location, int radius, Color color) {
        super(location, radius, color);
    }

    public void move(List<Bounded> boundedList) {
        super.move();

        for (Bounded bounded : boundedList) {
            if (bounded != this) {
                bounce(bounded);
            }
        }
    }

    // Bounded(테두리 처리 되어 있는 것)의 상태인 무언가를 파라미터를 받아야 함.
    public void bounce(Bounded other) {
        if (isCollision(other.getBounds())) {
            // 1. 겹치는 부위 파악
            Bounds intersection = getBounds().intersection(other.getBounds());

            // 2. 벡터 얻어오기
            Vector newMotion = getMotion();

            // 부딪힌 물체와 나의 bound의 크기가 완전히 일치하지 않는다면
            if ((getBounds().getHeight() != intersection.getHeight())
                    && (other.getHeight() != intersection.getHeight())) {

                // 동시에, 내 공의 중점 좌표의 y값이 더 작다면
                if (getMinY() < other.getMinY()) {
                    // 
                    setLocation(new Point(getX(), other.getMinY() - getRadius()));
                } else {
                    setLocation(new Point(getX(), other.getMaxY() + getRadius()));
                }

                newMotion.turnDY();
            }

            if ((getBounds().getWidth() != intersection.getWidth())
                    && (other.getWidth() != intersection.getWidth())) {

                if (getMinX() < other.getMinX()) {
                    setLocation(new Point(other.getMinX() - getRadius(), getY()));
                } else {
                    setLocation(new Point(other.getMaxX() + getRadius(), getY()));
                }

                newMotion.turnDX();
            }

            if (!getMotion().equals(newMotion)) {
                setMotion(newMotion);
            }
        }

    }
}