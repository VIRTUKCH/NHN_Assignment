package com.nhnacademy;

public class Vector {
    // dx, dy변수
    private int dx;
    private int dy;

    public Vector() {
        this.dx = 0;
        this.dy = 0;
    }

    public Vector(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // dx, dy를 받아서 Vector 오브젝트 생성하기
    public Vector createVector(int dx, int dy) {
        return new Vector(dx, dy);
    }

    // dx/dy를 위한 getter
    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }

    // dx/dy의 setter
    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void set(int dx, int dy) {
        this.setDx(dx);
        this.setDy(dy);
    }
    public void set(Vector other) {
        this.setDx(other.dx);
        this.setDy(other.dy);
    }

    // 추가 + - 연산은 나중에~
    public void add(Vector other) {
        setDx(getDx() + other.getDx());
        setDy(getDy() + other.getDy());
    }

    public void sub(Vector other) {
        setDx(getDx() - other.getDx());
        setDy(getDy() - other.getDy());
    }

    public void turnDx() {
        this.setDx(-this.getDx());
    }

    public void turnDy() {
        this.setDy(-this.getDy());
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Vector) && (((Vector)other).getDx() == this.getDx()) && (((Vector)other).getDy() == this.getDy());
    }
}