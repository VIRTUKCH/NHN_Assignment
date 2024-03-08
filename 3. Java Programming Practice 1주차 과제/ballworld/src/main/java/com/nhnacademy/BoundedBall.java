package com.nhnacademy;

import java.awt.Color;
import java.awt.Rectangle;

/*
 * [BoundedBall이 가지고 있는 멤버 변수와 멤버 함수]
 * 1. 멤버 변수를 따로 가지고 있지는 않음. 그저 MovableBall을 이용해서 bounce()라는 메서드를 더하고 있을 뿐임.
 * 
 * 2. Bounded 인터페이스의 bounce() 메서드를 구현하고 있음.
 * 
 * 3. bounce() 메서드는 조금 복잡하게 구성이 되어 있음.
 * : intersection이라는 '교집합 사각형'을 이용해 어느 방향으로 bounce 되어야 하는지에 대해 결정함.
 * 
 */
public class BoundedBall extends MovableBall implements Bounded {

    public BoundedBall(int x, int y, int radius, Color color) {
        super(x, y, radius, color);
    }

    public void bounce(Regionable other) {
        Rectangle intersection = getRegion().intersection(other.getRegion());

        // 세로에서 교집합의 크기와 작은 공의 크기가 일치하지 않는다면 (완전히 먹히는 상태가 아니라면)
        if ((getRegion().getHeight() != intersection.getHeight())
                && (other.getRegion().getHeight() != intersection.getHeight())) {
            setDY(-getDY()); // 진행 방향 중 상하를 반대로 꺾어라.
        }

        // 가로에서 교집합의 크기와 작은 공의 크기가 일치하지 않는다면 (완전히 먹히는 상태가 아니라면)
        if ((getRegion().getWidth() != intersection.getWidth())
                && (other.getRegion().getWidth() != intersection.getWidth())) {
            setDX(-getDX()); // 진행 방향 중 좌우를 반대로 꺾어라.
        }
    }
}