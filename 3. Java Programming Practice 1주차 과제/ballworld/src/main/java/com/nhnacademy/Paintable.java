package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
/*
 * Paintable 인터페이스는
 * 1. 컬러를 설정하고, 가져올 수 있음.
 * 2. paint() 메서드를 통해 Frame 위에 올라갈 수 있는 준비를 완료할 수 있음.
 */

 // Ball(Regionable) -> PaintalbeBall(Paintable) -> MovableBall(Movable) -> BoundedBall(Bounded)
public interface Paintable {
    public void setColor(Color color) ;
    public Color getColor();
    public void paint(Graphics g);
}
