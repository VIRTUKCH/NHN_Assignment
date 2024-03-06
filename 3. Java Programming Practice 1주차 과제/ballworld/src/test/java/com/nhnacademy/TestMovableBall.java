package com.nhnacademy;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.awt.Color;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/*
 * 확장 후 추가되거나 변경된 기능에 대해서만 테스트
 */

class TestMovableBall {
    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> {
            MovableBall ball = new MovableBall(1, 1, 1, Color.BLACK);

            assertEquals(MovableBall.DEFAULT_DX, ball.getDX());
            assertEquals(MovableBall.DEFAULT_DY, ball.getDY());
        });
    }

    @ParameterizedTest
    @MethodSource("deltaProvider")
    void testDeltaXY(int x, int y, int radius, int dx, int dy) {
        assertDoesNotThrow(() -> {
            MovableBall ball = new MovableBall(x, y, radius, Color.RED);

            ball.setDX(dx);
            ball.setDY(dy);
            assertEquals(dx, ball.getDX());
            assertEquals(dy, ball.getDY());
        });
    }

    static Stream<Arguments> deltaProvider() {
        return Stream.of(
                Arguments.arguments(0, 0, 10, 0, 0),
                Arguments.arguments(0, 0, 10, 1, -1),
                Arguments.arguments(0, 0, 10, 1, 1),
                Arguments.arguments(0, 0, 10, 123, 123),
                Arguments.arguments(0, 0, 10, -123, -123),
                Arguments.arguments(0, 0, 10, 0, 0));
    }

    @ParameterizedTest
    @MethodSource("moveProvider")
    void testMove(int x, int y, int radius, int dx, int dy, int count) {
        assertDoesNotThrow(() -> {
            MovableBall ball = new MovableBall(x, y, radius, Color.BLUE);

            ball.setDX(dx);
            ball.setDY(dy);

            int currentX = x;
            int currentY = y;

            for (int i = 0; i < count; i++) {
                ball.move();
                currentX += dx;
                currentY += dy;

                assertEquals(currentX, ball.getX());
                assertEquals(currentY, ball.getY());
            }
        });
    }

    static Stream<Arguments> moveProvider() {
        return Stream.of(
                Arguments.arguments(10, 20, 10, 5, 10, 1),
                Arguments.arguments(10, 20, 10, 5, 10, 10),
                Arguments.arguments(10, 20, 10, -5, 10, 100),
                Arguments.arguments(10, 20, 10, 5, 10, 1000),
                Arguments.arguments(10, 20, 10, -5, 10, 10000),
                Arguments.arguments(10, 20, 10, 5, 10, 100000)
        );
    }

    // MovableBall ball;
    // int startX = 0;
    // int startY = 0;
    // int deltaX = 10;
    // int deltaY = 10;

    // @BeforeEach
    // void beforTestRepeatedMove() {
    //     ball = new MovableBall(startX, startY, 10, Color.RED);
    //     ball.setDx(deltaX);
    //     ball.setDy(deltaY);
    // }

    // @RepeatedTest(10)
    // void testRepeatedMove(RepetitionInfo repetitionInfo) {
    //     assertEquals(startX + deltaX * repetitionInfo.getCurrentRepetition(), ball.getX());
    //     assertEquals(startX + deltaY * repetitionInfo.getCurrentRepetition(), ball.getY());
    // }
}
