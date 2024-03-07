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
    static final int FRAME_WIDTH = 500; // 프레임의 사이즈 -> JFrame을 상속 받은 건 World, MovableWorld임. 그리고 그걸 관리하는 게 TestWorld
    static final int FRAME_HEIGHT = 400;

    static final int MIN_RADIUS = 10; // 최소 반지름 -> 볼 생성의 권한은 TestWorld에게 있다.
    static final int MAX_RADIUS = 50; // 최대 반지름
    
    static final int FIXED_BALL_COUNT = 0; // 못 움직이는 볼
    static final int BOUNDED_BALL_COUNT = 5; // 움직이는 볼

    static final int MIN_DELTA = 5; // 속도 제한
    static final int MAX_DELTA = 7; // 속도 제한

    static final int MAX_MOVE_COUNT = 0; // 끝이 없는 반복문

    static final int DT = 10; // 속도 제한

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

        while (world.getCount() < FIXED_BALL_COUNT) { // 공 더하기
            try {
                PaintableBall ball = new PaintableBall(random.nextInt(FRAME_WIDTH),
                        random.nextInt(FRAME_HEIGHT),
                        MIN_RADIUS + random.nextInt(MAX_RADIUS - MIN_RADIUS + 1),
                        COLOR_TABLE[random.nextInt(COLOR_TABLE.length)]);

                world.add(ball);
            } catch (IllegalArgumentException ignore) {
                //
            }
        }

        while (world.getCount() < FIXED_BALL_COUNT + BOUNDED_BALL_COUNT) { // 공 돌리기
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
        world.setDT(DT);
        world.run();
    }
}