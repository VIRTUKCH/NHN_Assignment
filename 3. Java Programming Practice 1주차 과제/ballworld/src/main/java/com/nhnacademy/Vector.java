package com.nhnacademy;

public class Vector {
    // dx, dy변수
    private int dx;
    private int dy;
    
    // dx, dy를 받아서 Vector 오브젝트 생성하기
    public Vector(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
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

    // 추가 + - 연산은 나중에~
}