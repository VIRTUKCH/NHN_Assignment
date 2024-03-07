package com.nhnacademy;

import java.awt.Color;
import java.awt.Rectangle;

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
}