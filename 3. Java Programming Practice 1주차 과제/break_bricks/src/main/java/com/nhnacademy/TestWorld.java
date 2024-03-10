package com.nhnacademy;

import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TestWorld {
    static final int FRAME_WIDTH = 500;
    static final int FRAME_HEIGHT = 400;
    static final int MIN_RADIUS = 10;
    static final int MAX_RADIUS = 10;
    static final int MIN_WIDTH = 50;
    static final int MAX_WIDTH = 50;
    static final int MIN_HEIGHT = 20;
    static final int MAX_HEIGHT = 20;
    static final int FIXED_BALL_COUNT = 0;
    static final int FIXED_BOX_COUNT = 11;
    static final int BOUNDED_BALL_COUNT = 1;
    static final int MIN_DELTA = 5;
    static final int MAX_DELTA = 7;
    static final int MAX_MOVE_COUNT = 0;
    static final int DT = 10;
    static final Color[] COLOR_TABLE = {
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.YELLOW
    };

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setSize(FRAME_WIDTH+300, FRAME_HEIGHT+300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GameWorld world = new GameWorld(FRAME_WIDTH, FRAME_HEIGHT);

        frame.add(world);

        Random random = new Random();

        // 고정된 공
        while (world.getCount() < FIXED_BALL_COUNT) {
            try {
                PaintableBall ball = new PaintableBall(
                        new Point(random.nextInt(FRAME_WIDTH), random.nextInt(FRAME_HEIGHT)),
                        MIN_RADIUS + random.nextInt(MAX_RADIUS - MIN_RADIUS + 1),
                        COLOR_TABLE[random.nextInt(COLOR_TABLE.length)]);

                world.add(ball);
            } catch (IllegalArgumentException ignore) {
            }
        }

        // 깨지는 박스
        int i = MAX_WIDTH;
        while (world.getCount() < FIXED_BALL_COUNT + FIXED_BOX_COUNT) {
            try {
                world.add(new BrittleBox(
                        i, 60,
                        MIN_WIDTH + random.nextInt(MAX_WIDTH - MIN_WIDTH + 1),
                        MIN_HEIGHT + random.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1),
                        COLOR_TABLE[random.nextInt(COLOR_TABLE.length)]));
                        i += (MAX_WIDTH+10);
            } catch (IllegalArgumentException ignore) {
            }
        }

        // 움직이는 공
        while (world.getCount() < FIXED_BALL_COUNT + FIXED_BOX_COUNT + BOUNDED_BALL_COUNT) {
            try {
                BounceableBall ball = new BounceableBall(
                        new Point(random.nextInt(FRAME_WIDTH), random.nextInt(FRAME_HEIGHT)),
                        MIN_RADIUS + random.nextInt(MAX_RADIUS - MIN_RADIUS + 1),
                        COLOR_TABLE[random.nextInt(COLOR_TABLE.length)]);

                ball.setMotion(
                        MIN_DELTA - random.nextInt(MAX_DELTA - MIN_DELTA + 1),
                        MIN_DELTA - random.nextInt(MAX_DELTA - MIN_DELTA + 1));

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
