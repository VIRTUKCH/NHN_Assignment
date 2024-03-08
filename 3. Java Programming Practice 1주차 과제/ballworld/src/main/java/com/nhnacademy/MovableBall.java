package com.nhnacademy;

import java.awt.Color;

/*
 * [MovableBall이 가지고 있는 멤버 변수, 멤버 함수]
 * 1. Vector을 가지고 있음.
 * : 내부적으로 dx, 그리고 dy를 이용하여 방향을 관리할 수 있음.
 * 
 * 2. 게터와 세터를 통해 dx와 dy를 관리할 수 있음.
 * 단, 내부적으로 Vector motion을 통해 관리하고 있음.
 * 호출하려면 motion.setDX(dx); 이렇게 호출해서 세팅해야 할 것.
 * 
 * 3. move() 메서드를 Movable 인터페이스에서 가져와서 구현함.
 * 단, 내부적으로 moveTo() 메서드를 이용해서 dx, dy 만큼 이동하게 구현하였음.
 */

public class MovableBall extends PaintableBall implements Movable {
    public static final int DEFAULT_DX = 0;
    public static final int DEFAULT_DY = 0;

    final Vector motion = new Vector();

    public MovableBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
    }

    public Vector getMotion() {
        return motion;
    }

    public int getDX() {
        return motion.getDX();
    }

    public int getDY() {
        return motion.getDY();
    }

    public void setDX(int dx) {
        motion.setDX(dx);
    }

    public void setDY(int dy) {
        motion.setDY(dy);
    }

    @Override
    public void move() {
        moveTo(getX() + getDX(), getY() + getDY());
        logger.trace("{} : {}, {}, {}, {}", getId(), getX(), getY(), getRegion().getX(), getRegion().getY());
    }

    public void moveTo(int x, int y) {
        setX(x);
        setY(y);
    }
}
