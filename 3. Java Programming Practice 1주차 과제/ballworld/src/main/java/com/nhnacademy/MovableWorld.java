package com.nhnacademy;

/*
 * 1. 기본적으로 World 클래스는 Ball을 List로 관리하고 있음. + 판넬에 집어 넣는 것.
 * 2. MovableWorld 클래스는 World라는 클래스를 상속 받은 친구임.
 * 3. MovableWorld가 World의 기존 기능에서 추가하고 있는 것?
 * : (각 볼이 동시에 움직이게 만든다는 전제 하에) 몇 번 움직였는지, 최대 몇 번까지 움직이게 할 건지 통합해서 관리하는 기능
 * 4. 또한, MoveableWorld는 dt를 정의할 뿐임 (단위 시간을 가지고 있다.)
 * 5. 처음에는 각 공을 모두 통합 관리하기 위해 n번만 이동할 수 있도록 maxMoveCount를 만들었으나 지금은 딱히 쓰지는 않음.
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
        if ((getMaxMoveCount() == 0) || (getMoveCount() < getMaxMoveCount())) {
            for (int i = 0; i < getCount(); i++) {
                Ball ball = get(i);
                if (ball instanceof MovableBall) {
                    ((MovableBall) ball).move();

                    if (ball instanceof BoundedBall) {
                        for (int j = 0; j < getCount(); j++) {
                            Ball otherBall = get(j);

                            if ((ball != otherBall) && (ball.getRegion().intersects(otherBall.getRegion()))) {
                                ((BoundedBall) ball).bounce(otherBall);
                                logger.info("ball({})와 ball({})이 충돌하였습니다.", ball.getId(), otherBall.getId());
                            }
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