package com.nhnacademy;

import java.awt.Color;
/*
 * [MovableBall이 하는 일]
 * 벡터를 통해 dx, dy와 같은 방향을 가짐.
 * 
 * '움직이는 기능'은 move() 메서드를 통해 구현 됨.
 * move() 메서드는 내부적으로 moveTo() 메서드를 부름
 * Vector 클래스가 가지고 있는 dx와 dy로 방향과 크기를 가지고 있음.
 * moveTo() 메서드는 해당하는 방향과 크기 만큼 다음 좌표를 찾아 이동함.
 * 로그 표현하여, 한 번 이동하면 이동한 결과를 표현했음.
 */
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