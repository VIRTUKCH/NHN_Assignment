package com.nhnacademy;

import java.awt.Color;
import java.awt.Rectangle;
/*
 * [BoundedBall이 하는 일]
 * 1. 얘도 Ball이 가지고 있는 Rectangle처럼, Rectangle을 가지고 있음.
 * 
 * 2. 근데 이름은 bounds이고, 얘는 공의 크기가 아니라 *'공이 움직일 수 있는 범위'를 표현함.
 * 
 * 3. 이친구에게 move하도록 시키면 (오버라이딩)
 * 1) 일단 움직인 다음에 (조상 메서드의 move() 메서드 호출)
 * 2) 자신이 움직일 수 있는 범위 (bounds)를 벗어난다면
 * 3) bounce() 메서드를 호출함
 * 4) bounce() 메서드는 내가 박은 벽에 따라서 방향을 바꿔주는 메서드임
 * 
 * 4. 다른 공과 충돌하는 기능도 있음.
 */
public class BoundedBall extends MovableBall {
    Rectangle bounds;

    public BoundedBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
        bounds = new Rectangle(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean isOutOfBounds() {
        Rectangle region = new Rectangle(getX() - getRadius(), getY() - getRadius(), 2 * getRadius(), 2 * getRadius());
        Rectangle intersection = bounds.intersection(region);

        return (intersection.getWidth() != region.getWidth()) || (intersection.getHeight() != region.getHeight());
    }

    @Override
    public void move() {
        super.move();

        if (isOutOfBounds()) {
            bounce();
        }
    }

    /*
     * getMinX()는 Rectangle의 메서드임.
     */
    public void bounce() {
        if (this.getX() - this.getRadius() < this.getBounds().getMinX() // 1. 좌측 벽을 뚫거나
                || (getX() + getRadius() > getBounds().getMaxX())) { // 2. 우측 벽을 뚫었으면
            setDx(-getDx()); // dx 방향 바꿔줘
        }

        if ((getY() - getRadius() < getBounds().getMinY()) // 3. 상단 벽을 뚫거나
                || (getY() + getRadius() > getBounds().getMaxY())) { // 4. 하단 벽을 뚫었으면
            setDy(-getDy()); // dy 방향 바꿔줘
        }
    }

    public void bounce(Ball other) {
        Rectangle intersection = getRegion().intersection(other.getRegion());

        if (getRegion().getHeight() != intersection.getHeight()) {
            setDy(-getDy());
        }

        if (getRegion().getWidth() != intersection.getWidth()) {
            setDx(-getDx());
        }
    }
}