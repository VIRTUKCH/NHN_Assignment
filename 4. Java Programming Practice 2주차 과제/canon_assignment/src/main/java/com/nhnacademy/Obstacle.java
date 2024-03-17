package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Obstacle extends BounceableBox {
    public static final Color DEFAULT_COLOR = Color.YELLOW;

    public Obstacle(Point location, int width, int height, Color color) {
        this(location.getX(), location.getY(), width, height, color);
    }

    public Obstacle(int x, int y, int width, int height, Color color) {
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


        g2d.fillRect(getCenterX(), getCenterY() - 80, 100, 20);
        g.fillRect(getBounds().getMinX() - 25, getBounds().getMinY(),
        getBounds().getWidth() + 50, getBounds().getHeight());
        
        // 테두리 그리기
        g.drawRect(getBounds().getMinX() - 25, getBounds().getMinY(),
        getBounds().getWidth() + 50, getBounds().getHeight());
        
        System.out.println(getCenterX());
        System.out.println(getCenterY());
        


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

    public void bounce(Bounded other) {
        if (isCollision(other.getBounds())) {
            Bounds intersection = getBounds().intersection(other.getBounds());

            Vector newMotion = getMotion();

            if ((getBounds().getHeight() != intersection.getHeight())
                    && (other.getHeight() != intersection.getHeight())) {

                if (getMinY() < other.getMinY()) {
                    setLocation(new Point(getX(), other.getMinY() - getHeight() / 2));
                } else {
                    setLocation(new Point(getX(), other.getMaxY() + getHeight() / 2));
                }

                newMotion.turnDY();
            }

            if ((getBounds().getWidth() != intersection.getWidth())
                    && (other.getWidth() != intersection.getWidth())) {

                if (getMinX() < other.getMinX()) {
                    setLocation(new Point(other.getMinX() - getWidth() / 2, getY()));
                } else {
                    setLocation(new Point(other.getMaxX() + getWidth() / 2, getY()));
                }

                newMotion.turnDX();
            }

            if (!getMotion().equals(newMotion)) {
                setMotion(newMotion);
            }
        }
    }
}