package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;

/*
 * [PaintableBall 클래스는]
 * 1. Ball을 가지고 있음. -> x, y, radius를 가져올 수 있음.
 * x, r, radius가 실질적으로 멤버변수로 존재하지는 않으나, Rectangle을 내부적으로 이용해서 가져옴.
 * 
 * 2. Color을 세팅하고 가져올 수 있음. (Paintable 인터페이스)
 * 
 * 3. paint(Graphics g) 메서드를 통해 그래픽 상에 그림을 그릴 수 있음.
 * 
 * [PaintableBall에서 추가하고 있는 속성, 기능]
 * <속성>
 * - Color : awt.Color을 import하여 색을 관리함.
 * 
 * <기능>
 * - Paint(Graphics g) : 색을 설정하고, 칠하고, Rect를 표현하는 등의 절차를 통해 Frame 위에 올릴 준비를 완료함.
 */
public class PaintableBall extends Ball implements Paintable {
    public static final Color DEFAULT_COLOR = Color.BLACK;

    Color color;

    public PaintableBall(int x, int y, int radius) {
        this(x, y, radius, DEFAULT_COLOR);
    }

    public PaintableBall(int x, int y, int radius, Color color) {
        super(x, y, radius);

        if (color == null) {
            throw new IllegalArgumentException();
        }

        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color
     * @throws IllegalArgumentException color는 null 허용하지 않습니다
     */
    public void setColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException();
        }

        this.color = color;
    }

    public void paint(Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }

        Color originalColor = g.getColor();

        g.setColor(getColor());

        g.fillOval(getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
        g.setColor(Color.GRAY);
        g.drawRect((int) getRegion().getX(), (int) getRegion().getY(), (int) getRegion().getWidth(),
                (int) getRegion().getHeight());

        g.setColor(originalColor);
    }
}
