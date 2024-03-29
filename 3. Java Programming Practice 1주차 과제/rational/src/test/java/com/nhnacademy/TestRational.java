package com.nhnacademy;

/*
 * <궁금한 점>
 * 1. '모든' Integer 범위의 테스트 케이스를 통과하기 위해서는
 * => Long 멤버 변수가 필수적인 것 같다.
 * => 그러려면 당연하게 모든 클래스에서 BigInteger을 사용해야 하는 것 아닐까?
 * 질문에 대한 대답 : 당연히 그렇다. 그렇지만, 제약된 상황에서 테스트를 조정해 보라는 의미에서 그랬다.
 * 
 * 2. 그럼에도 불구하고, 내부적으로 long을 사용한다는 건 무슨 소리일까??
 * => 메모리가 소중한 상황에서 처리하는 방법인 걸까?
 * 질문에 대한 대답
 * 
 * 3. Object에 null을 넣으려고 하니 JUnit이 막던데, 어떻게 처리해야 할까?
 * 질문에 대한 대답 : 굳이 그럴 필요는 없었다.
 * 4. OverFlow가 날 수도 있다. 그건 그럴 수 있는데, JUnit이 그걸 어떻게 알았을까?
 * 
 * 5. reciprocal 메서드는 어떻게 처리해야 하는 걸까? 해결하긴 했지만, 1/32 확률로 에러가 날 수도..
 */

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestRational {
    static final int RANDOM_TEST_COUNT = 100;

    @ParameterizedTest // 외부에서 파라미터를 받는 테스트
    @MethodSource("legalIntegerArgumentProvider") // 파라미터를 주는 메서드 이름
    void testConstructorWithInteger(int n) { // 테스트 메서드 이름
        assertDoesNotThrow(() -> { // 예외가 발생하지 않을 거다
            Rational r = new Rational(n);

            assertEquals(n, r.getNumerator()); // 값이 일치할 거다.
            assertEquals(1, r.getDenominator()); // 값이 일치할 거다.
        });
    }

    public static Stream<Arguments> legalIntegerArgumentProvider() {
        List<Arguments> argumentsList = new LinkedList<>();

        argumentsList.add(Arguments.arguments(Integer.MIN_VALUE));
        argumentsList.add(Arguments.arguments(Integer.MAX_VALUE));
        argumentsList.add(Arguments.arguments(1));
        argumentsList.add(Arguments.arguments(-1));
        argumentsList.add(Arguments.arguments(0));

        return argumentsList.stream();
    }

    @ParameterizedTest
    @MethodSource("legalFractionArgumentProvider")
    void testConstructorWithFraction(int numerator, int denominator, int targetNumerator,
            int targetDenominator) {
        assertDoesNotThrow(() -> {
            Rational r = new Rational(numerator, denominator);

            assertEquals(targetNumerator, r.getNumerator());
            assertEquals(targetDenominator, r.getDenominator());
        });
    }

    public static Stream<Arguments> legalFractionArgumentProvider() {
        List<Arguments> argumentsList = new LinkedList<>();

        argumentsList.add(Arguments.arguments(0, Integer.MIN_VALUE, 0, 1));
        argumentsList.add(Arguments.arguments(0, Integer.MAX_VALUE, 0, 1));
        argumentsList.add(Arguments.arguments(Integer.MIN_VALUE, Integer.MIN_VALUE, 1, 1));
        argumentsList.add(Arguments.arguments(Integer.MAX_VALUE, Integer.MAX_VALUE, 1, 1));
        argumentsList.add(Arguments.arguments(Integer.MIN_VALUE, 1, Integer.MIN_VALUE, 1));
        argumentsList.add(Arguments.arguments(Integer.MAX_VALUE, 1, Integer.MAX_VALUE, 1));
        argumentsList.add(Arguments.arguments(Integer.MAX_VALUE, -1, -1 * Integer.MAX_VALUE, 1));

        return argumentsList.stream();
    }

    @ParameterizedTest
    @MethodSource("arithmeticExceptionProvider")
    void testCosntructorWithArithmeticExeption(int numerator, int denominator) {
        assertThrowsExactly(ArithmeticException.class, () -> {
            new Rational(numerator, denominator);
        });
    }

    public static Stream<Arguments> arithmeticExceptionProvider() {
        List<Arguments> argumentsList = new LinkedList<>();

        argumentsList.add(Arguments.arguments(1, 0));

        return argumentsList.stream();
    }

    // @ParameterizedTest
    // @MethodSource("outOfBoundsExceptionProvider")
    // void testConstructorWithOutOfBoundsException(int numerator, int denominator) {
    //     assertThrowsExactly(OutOfBoundsException.class, () -> {
    //         new Rational(numerator, denominator);
    //     });
    // }

    // public static Stream<Arguments> outOfBoundsExceptionProvider() {
    //     List<Arguments> argumentsList = new LinkedList<>();
    //     argumentsList.add(Arguments.arguments(1, Integer.MIN_VALUE));
    //     return argumentsList.stream();
    // }

    @ParameterizedTest
    @MethodSource("addProvider")
    void testAdd(Rational r1, Rational r2, Rational result) {
        assertDoesNotThrow(() -> {
            assertEquals(result, Rational.add(r1, r2));
        });
    }

    static Stream<Arguments> addProvider() {
        return Stream.of(
                Arguments.arguments(new Rational(0, 2), new Rational(1, 2), new Rational(1, 2)),
                Arguments.arguments(new Rational(1, 2), new Rational(-1, 2), new Rational(0, 1)),
                Arguments.arguments(new Rational(1, 2), new Rational(1, 2), new Rational(1)));
    }

    @ParameterizedTest
    @MethodSource("subtractProvider")
    void testSubtract(Rational r1, Rational r2, Rational result) {
        assertDoesNotThrow(() -> {
            assertEquals(result, Rational.sub(r1, r2));
        });
    }

    static Stream<Arguments> subtractProvider() {
        return Stream.of(
                Arguments.arguments(new Rational(0, 2), new Rational(1, 2), new Rational(-1, 2)),
                Arguments.arguments(new Rational(0, 2), new Rational(1, -2), new Rational(1, 2)),
                Arguments.arguments(new Rational(1, Integer.MAX_VALUE), new Rational(1),
                        new Rational(1 - Integer.MAX_VALUE, Integer.MAX_VALUE)),
                Arguments.arguments(new Rational(1), new Rational(1, Integer.MAX_VALUE),
                        new Rational(Integer.MAX_VALUE - 1, Integer.MAX_VALUE)),
                Arguments.arguments(new Rational(3, 2), new Rational(1, 2), new Rational(1)));
    }

    @ParameterizedTest
    @MethodSource("multiplyProvider")
    void testMultiply(Rational r1, Rational r2, Rational result) {
        assertDoesNotThrow(() -> {
            assertEquals(result, Rational.multiply(r1, r2));
        });
    }

    static Stream<Arguments> multiplyProvider() {
        return Stream.of(
                Arguments.arguments(new Rational(0), new Rational(Integer.MAX_VALUE), new Rational(0)),
                Arguments.arguments(new Rational(Integer.MAX_VALUE), new Rational(0), new Rational(0)),
                Arguments.arguments(new Rational(1), new Rational(Integer.MAX_VALUE), new Rational(Integer.MAX_VALUE)),
                Arguments.arguments(new Rational(Integer.MAX_VALUE), new Rational(1), new Rational(Integer.MAX_VALUE)),
                Arguments.arguments(new Rational(-1), new Rational(Integer.MAX_VALUE),
                        new Rational(Integer.MIN_VALUE + 1)),
                Arguments.arguments(new Rational(Integer.MAX_VALUE), new Rational(-1),
                        new Rational(Integer.MIN_VALUE + 1)),
                Arguments.arguments(new Rational(1, Integer.MAX_VALUE), new Rational(Integer.MAX_VALUE),
                        new Rational(1)),
                Arguments.arguments(new Rational(-1, Integer.MAX_VALUE), new Rational(-Integer.MAX_VALUE),
                        new Rational(1)),
                // Arguments.arguments(new Rational(-1, Integer.MIN_VALUE), new
                // Rational(-Integer.MIN_VALUE), new Rational(1)),
                Arguments.arguments(new Rational(Integer.MAX_VALUE / 7 * 7), new Rational(4, 7),
                        new Rational(Integer.MAX_VALUE / 7 * 4)));
    }

    // @ParameterizedTest
    // @MethodSource("multiplyWithOutOfBoundsExceptionProvider")
    // void testMultiplyWidthOutOfBoundsException(Rational r1, Rational r2) {
    //     assertThrowsExactly(OutOfBoundsException.class, () -> {
    //         Rational.multiply(r1, r2);
    //     });
    // }

    // static Stream<Arguments> multiplyWithOutOfBoundsExceptionProvider() {
    //     return Stream.of(
    //             Arguments.arguments(new Rational(1, Integer.MAX_VALUE), new Rational(1, Integer.MAX_VALUE)),
    //             Arguments.arguments(new Rational(1, Integer.MIN_VALUE + 1), new Rational(1, Integer.MAX_VALUE)),
    //             Arguments.arguments(new Rational(1, Integer.MIN_VALUE + 1), new Rational(1, Integer.MIN_VALUE + 1)),
    //             Arguments.arguments(new Rational(1, Integer.MAX_VALUE), new Rational(1, 2)),
    //             Arguments.arguments(new Rational(1, (Integer.MAX_VALUE / 2) + 1), new Rational(1, 2)));
    // }

    @ParameterizedTest
    @MethodSource("divideProvider")
    void testDivide(Rational r1, Rational r2, Rational result) {
        assertDoesNotThrow(() -> {
            assertEquals(result, Rational.divide(r1, r2));
        });
    }

    static Stream<Arguments> divideProvider() {
        return Stream.of(
                Arguments.arguments(new Rational(2, 3), new Rational(3, 2),
                        new Rational(4, 9)));
    }

    @ParameterizedTest
    @MethodSource("divideByZeroProvider")
    void testDivideByZero(Rational r1, Rational r2) {
        assertThrowsExactly(ArithmeticException.class, () -> {
            Rational.divide(r1, r2);
        });
    }

    static Stream<Arguments> divideByZeroProvider() {
        return Stream.of(
                Arguments.arguments(new Rational(0), new Rational(0)),
                Arguments.arguments(new Rational(2, 3), new Rational(0)),
                Arguments.arguments(new Rational(2, 3), new Rational(0, 2)));
    }

    @ParameterizedTest
    @MethodSource("irreducibleProvider")
    void testIrreducible(int numbrator, int denominator, int numeratorOfIrreducible, int denominatorOfIrreducible) {
        Rational r = new Rational(numbrator, denominator);

        assertEquals(numeratorOfIrreducible, r.getNumerator());
        assertEquals(denominatorOfIrreducible, r.getDenominator());
    }

    static Stream<Arguments> irreducibleProvider() {
        return Stream.of(
                Arguments.arguments(1, 2, 1, 2),
                Arguments.arguments(2, 4, 1, 2),
                Arguments.arguments(Integer.MAX_VALUE, Integer.MAX_VALUE, 1, 1),
                Arguments.arguments(Integer.MIN_VALUE, Integer.MIN_VALUE, 1, 1),
                Arguments.arguments(1, -1 * Integer.MAX_VALUE, -1, Integer.MAX_VALUE));
    }

    // @ParameterizedTest
    // @MethodSource("oppositeProvider")
    // void testOpposite(int numbrator, int denominator, int targetNumerator, int targetDenominator) {
    //     Rational r = new Rational(numbrator, denominator).opposite();

    //     assertEquals(targetNumerator, r.getNumerator());
    //     assertEquals(targetDenominator, r.getDenominator());
    // }

    // static Stream<Arguments> oppositeProvider() {
    //     return Stream.of(
    //             Arguments.arguments(1, 2, -1, 2),
    //             Arguments.arguments(2, 4, -1, 2),
    //             Arguments.arguments(Integer.MAX_VALUE, Integer.MAX_VALUE, -1, 1),
    //             Arguments.arguments(Integer.MIN_VALUE, Integer.MIN_VALUE, -1, 1),
    //             Arguments.arguments(1, -1 * Integer.MAX_VALUE, 1, Integer.MAX_VALUE));
    // }

    // @ParameterizedTest
    // @MethodSource("reciprocalProvider")
    // void testReciprocal(int numbrator, int denominator, int targetNumerator, int targetDenominator) {
    //     Rational r = new Rational(numbrator, denominator).reciprocal();

    //     assertEquals(targetNumerator, r.getNumerator());
    //     assertEquals(targetDenominator, r.getDenominator());
    // }

    // static Stream<Arguments> reciprocalProvider() {
    //     return Stream.of(
    //             Arguments.arguments(1, 2, 2, 1),
    //             Arguments.arguments(2, 4, 2, 1),
    //             Arguments.arguments(Integer.MAX_VALUE, Integer.MAX_VALUE, 1, 1),
    //             Arguments.arguments(Integer.MIN_VALUE, Integer.MIN_VALUE, 1, 1),
    //             Arguments.arguments(1, -1 * Integer.MAX_VALUE, -1 * Integer.MAX_VALUE, 1));
    // }

    @ParameterizedTest
    @MethodSource("toStringProvider")
    void testToString(int numbrator, int denominator, String output) {
        Rational r = new Rational(numbrator, denominator);

        assertEquals(output, r.toString());
    }

    static Stream<Arguments> toStringProvider() {
        List<Arguments> argumentList = new LinkedList<>();

        argumentList.add(Arguments.arguments(0, Integer.MAX_VALUE, "[0]"));
        argumentList.add(Arguments.arguments(0, Integer.MIN_VALUE, "[0]"));
        argumentList.add(Arguments.arguments(Integer.MAX_VALUE, Integer.MAX_VALUE, "[1]"));
        argumentList.add(Arguments.arguments(Integer.MIN_VALUE, Integer.MIN_VALUE, "[1]"));
        argumentList.add(Arguments.arguments(1, 2, "[1/2]"));
        argumentList.add(Arguments.arguments(-1, 2, "[-1/2]"));
        argumentList.add(Arguments.arguments(2, 4, "[1/2]"));
        argumentList.add(Arguments.arguments(2, -4, "[-1/2]"));
        argumentList.add(Arguments.arguments(-2, 4, "[-1/2]"));
        argumentList.add(Arguments.arguments(-2, -4, "[1/2]"));

        return argumentList.stream();   
    }
}