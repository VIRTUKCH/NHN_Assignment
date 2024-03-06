package com.nhnacademy;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TestBall {

    @ParameterizedTest
    @MethodSource("illegalArgumentExceptionProvider")
    void testConstructorWidthIllegalArgumentException(int x, int y, int radius) {
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            new Ball(x, y, radius);
        });
    }

    static Stream<Arguments> illegalArgumentExceptionProvider() {
        return Stream.of(
                Arguments.arguments(Integer.MAX_VALUE, Integer.MIN_VALUE, 1),
                Arguments.arguments(1, -1, Integer.MAX_VALUE));
    }

    @ParameterizedTest
    @MethodSource("toStringProvider")
    void testToString(int x, int y, int radius, String target) {
        assertDoesNotThrow(() -> {
            Ball ball = new Ball(x, y, radius);
            assertEquals(target, ball.toString());
        });
    }

    static Stream<Arguments> toStringProvider() {
        return Stream.of(
                Arguments.arguments(1, 1, 1, "(1,1,1)"),
                Arguments.arguments(-1, -2, -3, "(-1,-2,-3)"),
                Arguments.arguments(100, 2000000000, 1, "(100,2000000000,1)")
        );
    }
}