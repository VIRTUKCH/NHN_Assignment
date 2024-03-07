package com.nhnacademy;

/*
 * 1. 기본적으로 World 클래스는 Ball을 List로 관리하고 있음.
 * 2. MoveableWorld는 dt를 정의할 뿐임.
 * 3. 그리고, n번만 이동할 수 있도록 maxMoveCount를 만들었으나 지금은 안 씀.
 */
public class MovableWorld extends World {
    static final int DEFAULT_DT = 10;
    int moveCount;
    int maxMoveCount = 0;
    int dt = DEFAULT_DT;

    public void setDT(int dt) {
        if (dt < 0) {
            throw new IllegalArgumentException();
        }
        this.dt = dt;
    }

    public int getDT() {
        return dt;
    }

    public void reset() {
        moveCount = 0;
    }

    public void move() {
        if ((getMaxMoveCount() == 0) || (getMoveCount() < getMaxMoveCount())) { // 이거는 최대 이동수 제한할 때 시절
            for (int i = 0; i < getCount(); i++) {
                Ball ball = get(i);
                if (ball instanceof MovableBall) {
                    ((MovableBall) ball).move();

                    for (int j = 0; j < getCount(); j++) {
                        Ball otherBall = get(j);

                        if ((ball != otherBall) && (ball.getRegion().intersects(otherBall.getRegion()))) {
                            logger.info("ball({})와 ball({})이 충돌하였습니다.", ball.getId(), otherBall.getId());
                        }
                    }
                }
            }

            moveCount++;
            repaint();
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            move();
            try {
                Thread.sleep(getDT());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getMaxMoveCount() {
        return maxMoveCount;
    }

    public void setMaxMoveCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }

        maxMoveCount = count;
    }

}