package com.nhnacademy;

public class MovableWorld extends World {
    static final int DEFUALT_MAX_MOVE_COUNT = 10;

    private int moveCount;
    private int maxMoveCount = DEFUALT_MAX_MOVE_COUNT;

    void reset() {
        this.moveCount = 0;
    }

    void move() {
        if(moveCount < maxMoveCount) {
            for (Ball ball : ballList) {
                if(ball instanceof MovableBall) {
                    ((MovableBall) ball).move();
                }
                this.moveCount++;
            }
        }
        repaint();
    }

    void run() {
        if(maxMoveCount == 0) {
            while (moveCount < Integer.MAX_VALUE) {
                move();
            }
        } else {
            while (moveCount < maxMoveCount) {
                move();
            }
        }
    }

    int getMovementCount() {
        return this.moveCount;
    }

    int getMaxMoveCount() {
        return this.maxMoveCount;
    }

    void setMaxMoveCount(int count) {
        this.maxMoveCount = count;
    }
}