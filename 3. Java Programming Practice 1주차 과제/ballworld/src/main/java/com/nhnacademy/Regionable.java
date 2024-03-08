package com.nhnacademy;

import java.awt.Rectangle;
/*
 * Regionable 인터페이스는
 * 1. 아이디를 얻어 올 수 있는 기능이 있고
 * 2. x, y좌표를 얻어올 수 있는 기능이 있고
 * 3. Rectangle을 얻어 올 수 있는 기능이 있음.
 */
public interface Regionable {
    public int getId();

    public int getX();

    public int getY();

    public Rectangle getRegion();
}
