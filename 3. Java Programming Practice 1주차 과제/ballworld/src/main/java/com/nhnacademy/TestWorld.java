package com.nhnacademy;

import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
/*
 * 각종 Ball, 각종 World를 테스트하기 위한 클래스.
 * Ball을 바꿔 끼울 수도 있고, World를 바꿔 끼울 수도 있음.
 * 편하게 통합 관리하기 위해서 상수가 좀 많은 편.
 * 어떤 곳에 어떤 상수가 들어가야 할지 정확하게 알고 있다면 => 상수 만들어서 넣어도 좋다.
 */
public class TestWorld {
    static final int FRAME_WIDTH = 500;
    static final int FRAME_HEIGHT = 400;
    static final int MIN_RADIUS = 20;
    static final int MAX_RADIUS = 50;
    static final int BALL_COUNT = 5;
    static final int MIN_DELTA = 10;
    static final int MAX_DELTA = 30;
    static final int MAX_MOVE_COUNT = 0;
    static final Color[] COLOR_TABLE = {
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.YELLOW
    };

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MovableWorld world = new MovableWorld();
        world.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.add(world);

        Random random = new Random();

        while (world.getCount() < BALL_COUNT) {
            try {
                BoundedBall ball = new BoundedBall(random.nextInt(FRAME_WIDTH), random.nextInt(FRAME_HEIGHT),
                        MIN_RADIUS + random.nextInt(MAX_RADIUS - MIN_RADIUS + 1),
                        COLOR_TABLE[random.nextInt(COLOR_TABLE.length)]);

                int dx = MIN_DELTA - random.nextInt(MAX_DELTA - MIN_DELTA + 1);
                int dy = MIN_DELTA - random.nextInt(MAX_DELTA - MIN_DELTA + 1);

                ball.setDx(dx);
                ball.setDy(dy);

                world.add(ball);
            } catch (IllegalArgumentException ignore) {
                //
            }
        }

        frame.setVisible(true);

        world.setMaxMoveCount(MAX_MOVE_COUNT);
        world.setDT(100);
        world.run();
    }
}