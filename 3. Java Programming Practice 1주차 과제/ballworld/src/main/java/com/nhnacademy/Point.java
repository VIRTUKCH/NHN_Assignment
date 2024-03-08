package com.nhnacademy;

/*
 * Point는 움직일 수 있는 대상이다.
 * 내부적으로 Vector을 이용하여, 속도를 얻어 낼 수 있다.
 */
public class Point {
    int x;
    int y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        this.x = other.getX();
        this.y = other.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    public void set(Point other) {
        setX(other.getX());
        setY(other.getY());
    }

    public void set(int x, int y) {
        setX(x);
        setY(y);
    }

    public void translate(int dx, int dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public void translate(Vector motion) {
        set(getX() + motion.getDX(), getY() + motion.getDY());
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Point)
                && (getX() == ((Point) other).getX())
                && (getY() == ((Point) other).getY());
    }

    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }
}