package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Canon extends MovableBox {
    int angle;

    public int getAngle() {
        return this.angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public static final Color DEFAULT_COLOR = Color.BLACK;

    public Canon(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (g == null || g2d == null) {
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

        int canonBarrelX = getCenterX();
        int canonBarrelY = getCenterY() - 80;
        // 탱크 포 회전하기 + 그리기
        g2d.rotate(Math.toRadians(-angle), canonBarrelX, canonBarrelY);
        g2d.fillRect(getCenterX(), getCenterY() - 80, 100, 20);
        g2d.rotate(Math.toRadians(angle), canonBarrelX, canonBarrelY);

        // 탱크 색칠하기
        g.setColor(Color.GRAY);
        g.setColor(originalColor);
    }

    @Override
    public void run() {
        thread = Thread.currentThread();
        stopped = false;

        if (startedActionListener != null) {
            startedActionListener.action();
        }

        while (!stopped) {
            try {
                move();
                Thread.sleep(dt);
            } catch (InterruptedException ignore) {
                Thread.currentThread().interrupt();
            }
        }
    }
}