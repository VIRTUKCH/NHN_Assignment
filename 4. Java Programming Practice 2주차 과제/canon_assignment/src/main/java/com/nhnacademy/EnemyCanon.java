package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;

public class EnemyCanon extends BrittleBox {
    public static final Color DEFAULT_COLOR = Color.BLACK;

    public EnemyCanon(int x, int y, int width, int height) {
        super(x, y, width, height, DEFAULT_COLOR);
    }

    public EnemyCanon(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    @Override
    public void hit(Bounded other) {
        super.hit(other);
    }

    @Override
    public void paint(Graphics g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }

        Color originalColor = g.getColor();

        g.setColor(getColor());

        // 탱크 아래 사각형
        g.fillRect(getBounds().getMinX() - 25, getBounds().getMinY(),
                getBounds().getWidth() + 50, getBounds().getHeight());

        // 탱크 머리 원
        g.fillOval(getCenterX() - 50, getCenterY() - 100, getWidth(), getHeight());

        // 테두리 그리기
        g.drawRect(getBounds().getMinX() - 25, getBounds().getMinY(),
                getBounds().getWidth() + 50, getBounds().getHeight());

        // 탱크 색칠하기
        g.setColor(Color.GRAY);
        g.setColor(originalColor);
    }
}