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
    void testConstructor() { // movableBall의 생성자 테스트.
        assertDoesNotThrow(() -> {
            MovableBall ball = new MovableBall(1, 1, 1, Color.BLACK);
            assertEquals(MovableBall.DEFAULT_DX, ball.getDX()); // dx는 별개로 추가하지는 않았음. - 그래서 DEFAULT_DX와 같음.
            assertEquals(MovableBall.DEFAULT_DY, ball.getDY()); // assertEquals()는 두 값이 서로 일치하는지 확인함.
        });
    }

    @ParameterizedTest
    @MethodSource("deltaProvider") // dx를 setter를 통해 setting하고, assertEqulas 메서드를 통해 바뀌었는지 확인하는 테스트 코드.
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

            for (int i = 0; i < count; i++) { // 반복적으로 움직이고 그 결과를 확인.
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

    // static int startX = 0;
    // static int startY = 0;
    // static int deltaX = 10;
    // static int deltaY = 10;
    // static MovableBall ball = new MovableBall(startX, deltaY, 10, Color.RED);

    // @BeforeAll
    // static void testRepeatedMove(RepetitionInfo RepetitionInfo) {
    //     ball.move();
    //     assertEquals(startX + deltaX, null);
    // }
}
