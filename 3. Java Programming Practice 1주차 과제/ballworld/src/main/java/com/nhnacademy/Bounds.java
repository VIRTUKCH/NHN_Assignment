package com.nhnacademy;

import java.awt.Rectangle;
/*
 * Bounds는 내부적으로 Rectangle을 사용한다.
 * 그리고 Rectangle은 x, y, width, height로 표현된다.
 * 여기에서는 x와 y를 Point라는 클래스로 묶어서 표현했다.
 * 
 * 요약
 * 1. Bounds는 내부적으로 Rectangle을 사용한다.
 * 2. Rectangle은 내부적으로 Point(x, y), Vector을 사용한다.
 * Point에다가 가로와 세로만 더하면 사각형이 완성될 수 있기 때문에 Point을 사용하며
 * Rectangle 또한 움직여야 할 대상 중 하나이기 때문에 Vector을 사용한다.
 * 
 * Bounds 클래스의 기능
 * 1. public Bounds intersection(Bounds other)
 * : 두 Rectangle 사이의 공통된 부분을 Bound로 반환.
 * 
 * 2. public boolean isCollision(Bounds other)
 * : 교차된 부분이 있는지 확인함.
 */
public class Bounds {
    final Rectangle rectangle;

    public Bounds(int x, int y, int width, int height) {
        rectangle = new Rectangle(x, y, width, height);
    }

    public Bounds(Point location, int width, int height) {
        rectangle = new Rectangle(location.getX(), location.getY(), width, height);
    }

    Bounds(Rectangle other) {
        rectangle = (Rectangle) other.clone();
    }

    Bounds(Bounds other) {
        this.rectangle = new Rectangle(other.getMinX(), other.getMinY(), other.getWidth(), other.getHeight());
    }

    public Point getLocation() {
        return new Point((int) rectangle.getMinX(), (int) rectangle.getMinY());
    }

    public void setLocation(Point location) {
        rectangle.setLocation(location.getX(), location.getY());
    }

    public void setLocation(int x, int y) {
        rectangle.setLocation(x, y);
    }

    public void translate(Vector motion) {
        rectangle.translate(motion.getDX(), motion.getDY());
    }

    public int getCenterX() {
        return (int) rectangle.getCenterX();
    }

    public int getCenterY() {
        return (int) rectangle.getCenterY();
    }

    public int getMinX() {
        return (int) rectangle.getMinX();
    }

    public int getMinY() {
        return (int) rectangle.getMinY();
    }

    public int getMaxX() {
        return (int) rectangle.getMaxX();
    }

    public int getMaxY() {
        return (int) rectangle.getMaxY();
    }

    public int getWidth() {
        return (int) rectangle.getWidth();
    }

    public int getHeight() {
        return (int) rectangle.getHeight();
    }

    public boolean isCollision(Bounds other) {
        return getRectangle().intersects(other.getRectangle());
    }

    public boolean isInclude(Bounds other) {
        Bounds intersection = intersection(other);

        return other.equals(intersection);
    }

    public Bounds intersection(Bounds other) {
        Rectangle intersection = getRectangle().intersection(other.getRectangle());

        return new Bounds((int) intersection.getMinX(), (int) intersection.getMinY(),
                (int) intersection.getWidth(), (int) intersection.getHeight());
    }

    Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Bounds)
                && (getLocation().equals(((Bounds) other).getLocation()))
                && (getWidth() == ((Bounds) other).getWidth())
                && (getHeight() == ((Bounds) other).getHeight());
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "[" + rectangle.getX() + "," + rectangle.getY() + "," + rectangle.getWidth() + ","
                + rectangle.getHeight() + "]";
    }
}