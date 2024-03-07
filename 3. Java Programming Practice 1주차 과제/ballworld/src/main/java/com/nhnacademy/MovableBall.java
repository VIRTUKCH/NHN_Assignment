package com.nhnacademy;

import java.awt.Color;

public class MovableBall extends PaintableBall {
    public static final int DEFAULT_DX = 0;
    public static final int DEFAULT_DY = 0;
    
    final Vector motion = new Vector();

    public MovableBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
    }

    public Vector getMotion() {
        return this.motion;
    }

    public int getDx() {
        return this.motion.getDx();
    }

    public void setDx(int dx) {
        motion.setDx(dx);
    }

    public int getDy() {
        return this.motion.getDy();
    }

    public void setDy(int dy) {
        this.motion.setDy(dy);
    }
    
    public void move() {
        moveTo(getX() + getDx(), getY() + getDy());
        logger.trace("{} : {}, {}, {}, {}", getId(), getX(), getY(), getRegion().getX(), getRegion().getY());
    }

    public void moveTo(int x, int y) {
        setX(x);
        setY(y);
    }
}