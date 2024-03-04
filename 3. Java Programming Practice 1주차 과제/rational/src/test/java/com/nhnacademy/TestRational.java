package com.nhnacademy;

/*
 * <궁금한 점>
 * 1. '모든' Integer 범위의 테스트 케이스를 통과하기 위해서는
 * => Long 멤버 변수가 필수적인 것 같다.
 * => 그러려면 당연하게 모든 클래스에서 BigInteger을 사용해야 하는 것 아닐까?
 * 
 * 2. 그럼에도 불구하고, 내부적으로 long을 사용한다는 건 무슨 소리일까??
 * => 메모리가 소중한 상황에서 처리하는 방법인 걸까?
 * 
 * 3. Object에 null을 넣으려고 하니 JUnit이 막던데, 어떻게 처리해야 할까?
 * 
 * 4. OverFlow가 날 수도 있다. 그건 그럴 수 있는데, JUnit이 그걸 어떻게 알았을까?
 * 
 * 5. reciprocal 메서드는 어떻게 처리해야 하는 걸까? 해결하긴 했지만, 1/32 확률로 에러가 날 수도..
 */
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestRational {

    // 1) 메서드의 선언분에 파라미터가 존재하지는 않음.
    // => 그러므로, @ParameterizedTest 애너테이션을 붙이지 않음.
    // 2) 예외가 발생하지 않아야 테스트를 통화함.
    // => 그러므로, assertDoesNotThrow 메서드를 람다식으로 호출함.
    @Test
    void testValidConstructor() {
        assertDoesNotThrow(() -> { // 반드시 에러가 발생하면 안 된다.
            new Rational(1, 2); // 내가 손으로 파라미터를 넘김
        });
    }

    // 1) 메서드의 선언부에 파라미터가 존재하지는 않음. => 파라미터 애너테이션 없음.
    // 2) 예외가 발생해야 테스트를 통과 => assertThrowsExactly(() ->
    @Test
    void testInvalidConstructor() {
        assertThrowsExactly(ArithmeticException.class, () -> { // 반드시 에러가 발생해야 한다.
            new Rational(1, 0);
        });
    }

    // 1) 메서드의 선언부에 파라미터가 존재하니 => @ParameterizedTest
    // 2) 예외가 발생하지 않아야 테스트를 통과 => assertDoesNotThrow(() ->
    // 3) 파라미터는 다른 함수에서 가져옴 => @MethodSources("Stream을 가져오는 함수 이름")
    @ParameterizedTest
    @MethodSource("validConstructorProvider") // ValidConstructorProvider라는 메서드를 데이터 소스로 사용한다
    void testValidConstructor2(int numeartor, int denominator) {
        assertDoesNotThrow(() -> {
            new Rational(numeartor, denominator); // 내가 손으로 파라미터를 넘기지는 않음.
        });
    }

    // 이 함수는 메서드의 파라미터를 내가 만들어서 던져줄 때 쓰는 함수임.
    // 위의 @MethodSource("validConstructorProvider")와 한 쌍임.
    static Stream<Arguments> validConstructorProvider() {
        return Stream.of(
                Arguments.arguments(1, 2),
                Arguments.arguments(100, 10),
                Arguments.arguments(12, 45),
                Arguments.arguments(-10, -4));
    }

    // 1. 메서드의 선언부에 파라미터가 존재하므로 => @ParameterizedTest
    // 2. 예외가 발생하지 않아야 테스트를 통과 => assertDoesNotThrow(() ->
    // 3. 파라미터는 다른 메서드에서 가져옴 => @MethodSource("Stream을 가져오는 함수 이름")
    @ParameterizedTest
    @MethodSource("irreducibleProvider")
    void testIrreducible(int numerator, int denominator, Rational target) {
        assertDoesNotThrow(() -> {
            assertEquals(target, new Rational(numerator, denominator));
        });
    }

    // 위 함수와 한 쌍임
    static Stream<Arguments> irreducibleProvider() {
        return Stream.of(
                Arguments.arguments(1, 2, new Rational(1, 2)),
                Arguments.arguments(100, 10, new Rational(10)),
                Arguments.arguments(12, 45, new Rational(4, 15)),
                Arguments.arguments(-10, -4, new Rational(5, 2)));
    }

    // -------- 여기서부터 내가 짠 코드임 --------
    // -------- Equals() --------

    @ParameterizedTest
    @MethodSource("validEqualsMethodProvider") // ValidConstructorProvider라는 메서드를 데이터 소스로 사용한다
    void validEqualsMethod(Object other) {
        assertDoesNotThrow(() -> {
            Rational r1 = new Rational(Integer.MAX_VALUE, 1);
            r1.equals(other);
        });
    }

    static Stream<Arguments> validEqualsMethodProvider() {
        return Stream.of(
                Arguments.arguments(""),
                Arguments.arguments(new Rational(Integer.MAX_VALUE, 1)),
                Arguments.arguments(new Object()));
    }

    // null Check - JUnit이 막아버림
    // @ParameterizedTest
    // @MethodSource("validEqualsMethodProvider2") // ValidConstructorProvider라는
    // 메서드를 데이터 소스로 사용한다
    // void validEqualsMethod2(Object other) {
    // assertThrowsExactly(NullPointerException.class, () -> {
    // Rational r1 = new Rational(Integer.MAX_VALUE, 1);
    // r1.equals(other);
    // });
    // }

    // static Stream<Arguments> validEqualsMethodProvider2() {
    // return Stream.of(Arguments.arguments(null));
    // }

    // -------- add() --------

    @ParameterizedTest
    @MethodSource("validAddMethodProvider")
    void validAddMethod(int a, int b) {
        assertDoesNotThrow(() -> {
            Rational r1 = new Rational(Integer.MAX_VALUE, a);
            Rational r2 = new Rational(Integer.MAX_VALUE, b);

            Rational result = Rational.add(r1, r2);

            System.out.println("result : " + result);
        });
    }

    static Stream<Arguments> validAddMethodProvider() {
        return Stream.of(
                Arguments.arguments(Integer.MAX_VALUE, 1), // 이게 문제인지 어떻게 알았지?
                Arguments.arguments(Integer.MAX_VALUE, -1),
                Arguments.arguments(Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.arguments(Integer.MAX_VALUE, -Integer.MAX_VALUE),
                Arguments.arguments(-Integer.MAX_VALUE, 1),
                Arguments.arguments(-Integer.MAX_VALUE, -1),
                Arguments.arguments(-Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.arguments(-Integer.MAX_VALUE, -Integer.MAX_VALUE),
                Arguments.arguments(1, 1),
                Arguments.arguments(1, -1),
                Arguments.arguments(1, Integer.MAX_VALUE),
                Arguments.arguments(1, -Integer.MAX_VALUE),
                Arguments.arguments(-1, 1),
                Arguments.arguments(-1, -1),
                Arguments.arguments(-1, Integer.MAX_VALUE),
                Arguments.arguments(-1, -Integer.MAX_VALUE));
    }

    // -------- sub() --------

    @ParameterizedTest
    @MethodSource("validSubMethodProvider")
    void validSubMethod(int x, int y) {
        assertDoesNotThrow(() -> {
            Rational r1 = new Rational(Integer.MAX_VALUE, x);
            Rational r2 = new Rational(Integer.MAX_VALUE, y);

            Rational result = Rational.sub(r1, r2);

            System.out.println("result : " + result);
        });
    }

    static Stream<Arguments> validSubMethodProvider() {
        return Stream.of(
                Arguments.arguments(Integer.MAX_VALUE, 1),
                Arguments.arguments(Integer.MAX_VALUE, -1),
                Arguments.arguments(Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.arguments(Integer.MAX_VALUE, -Integer.MAX_VALUE),
                Arguments.arguments(-Integer.MAX_VALUE, 1),
                Arguments.arguments(-Integer.MAX_VALUE, -1),
                Arguments.arguments(-Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.arguments(-Integer.MAX_VALUE, -Integer.MAX_VALUE),
                Arguments.arguments(1, 1),
                Arguments.arguments(1, -1),
                Arguments.arguments(1, Integer.MAX_VALUE),
                Arguments.arguments(1, -Integer.MAX_VALUE),
                Arguments.arguments(-1, 1),
                Arguments.arguments(-1, -1),
                Arguments.arguments(-1, Integer.MAX_VALUE),
                Arguments.arguments(-1, -Integer.MAX_VALUE));
    }

    // -------- multiply() --------

    @ParameterizedTest
    @MethodSource("validMultiplyMethodProvider")
    void validMultiplyMethod(int x, int y) {
        assertDoesNotThrow(() -> {
            Rational r1 = new Rational(Integer.MAX_VALUE, x);
            Rational r2 = new Rational(Integer.MAX_VALUE, y);

            Rational result = Rational.multiply(r1, r2);

            System.out.println("result : " + result);
        });
    }

    static Stream<Arguments> validMultiplyMethodProvider() {
        return Stream.of(
                Arguments.arguments(Integer.MAX_VALUE, 1),
                Arguments.arguments(Integer.MAX_VALUE, -1),
                Arguments.arguments(Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.arguments(Integer.MAX_VALUE, -Integer.MAX_VALUE),
                Arguments.arguments(-Integer.MAX_VALUE, 1),
                Arguments.arguments(-Integer.MAX_VALUE, -1),
                Arguments.arguments(-Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.arguments(-Integer.MAX_VALUE, -Integer.MAX_VALUE),
                Arguments.arguments(1, 1),
                Arguments.arguments(1, -1),
                Arguments.arguments(1, Integer.MAX_VALUE),
                Arguments.arguments(1, -Integer.MAX_VALUE),
                Arguments.arguments(-1, 1),
                Arguments.arguments(-1, -1),
                Arguments.arguments(-1, Integer.MAX_VALUE),
                Arguments.arguments(-1, -Integer.MAX_VALUE));
    }

    // -------- divide() --------

    @ParameterizedTest
    @MethodSource("validDivideMethodProvider")
    void validDivideMethod(int x, int y) {
        assertDoesNotThrow(() -> {
            Rational r1 = new Rational(x, Integer.MAX_VALUE);
            Rational r2 = new Rational(Integer.MAX_VALUE, y);

            Rational result = Rational.divide(r1, r2);

            System.out.println("result : " + result);
        });
    }

    static Stream<Arguments> validDivideMethodProvider() {
        return Stream.of(
                Arguments.arguments(Integer.MAX_VALUE, 1),
                Arguments.arguments(Integer.MAX_VALUE, -1),
                Arguments.arguments(Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.arguments(Integer.MAX_VALUE, -Integer.MAX_VALUE),
                Arguments.arguments(-Integer.MAX_VALUE, 1),
                Arguments.arguments(-Integer.MAX_VALUE, -1),
                Arguments.arguments(-Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.arguments(-Integer.MAX_VALUE, -Integer.MAX_VALUE),
                Arguments.arguments(1, 1),
                Arguments.arguments(1, -1),
                Arguments.arguments(1, Integer.MAX_VALUE),
                Arguments.arguments(1, -Integer.MAX_VALUE),
                Arguments.arguments(-1, 1),
                Arguments.arguments(-1, -1),
                Arguments.arguments(-1, Integer.MAX_VALUE),
                Arguments.arguments(-1, -Integer.MAX_VALUE));
    }

    // -------- inverse() --------

    @ParameterizedTest
    @MethodSource("validInverseMethodProvider")
    void validInverseMethod(int x, int y) {
        assertDoesNotThrow(() -> {
            Rational r1 = new Rational(x, y);
            Rational result = r1.inverse();
            System.out.println("result : " + result);
        });
    }

    static Stream<Arguments> validInverseMethodProvider() {
        return Stream.of(
                Arguments.arguments(Integer.MAX_VALUE, 1),
                Arguments.arguments(Integer.MAX_VALUE, -1),
                Arguments.arguments(Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.arguments(Integer.MAX_VALUE, -Integer.MAX_VALUE),
                Arguments.arguments(-Integer.MAX_VALUE, 1),
                Arguments.arguments(-Integer.MAX_VALUE, -1),
                Arguments.arguments(-Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.arguments(-Integer.MAX_VALUE, -Integer.MAX_VALUE),
                Arguments.arguments(1, 1),
                Arguments.arguments(1, -1),
                Arguments.arguments(1, Integer.MAX_VALUE),
                Arguments.arguments(1, -Integer.MAX_VALUE),
                Arguments.arguments(-1, 1),
                Arguments.arguments(-1, -1),
                Arguments.arguments(-1, Integer.MAX_VALUE),
                Arguments.arguments(-1, -Integer.MAX_VALUE));
    }

    // -------- reciprocal() --------

    @ParameterizedTest
    @MethodSource("validReciprocalMethodProvider")
    void validReciprocalMethod(int x, int y) {
        assertThrowsExactly(ArithmeticException.class, () -> {
            Rational r1 = new Rational(x, y);
            Rational result = r1.reciprocal(x);
            System.out.println("result : " + result);
        });
    }

    static Stream<Arguments> validReciprocalMethodProvider() {
        return Stream.of(
                Arguments.arguments(-1, Integer.MAX_VALUE),
                Arguments.arguments(-1, -Integer.MAX_VALUE)
            );
    }

    // ----- 여기까지는 이상 없음 -----

    @ParameterizedTest
    @MethodSource("validReciprocalMethodProvider2")
    void validReciprocalMethod2(int x, int y, int n) {
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            Rational r1 = new Rational(x, y);
            Rational result = r1.reciprocal(n);
            System.out.println("result : " + result);
        });
    }

    static Stream<Arguments> validReciprocalMethodProvider2() {
        return Stream.of(
                Arguments.arguments(1, Integer.MAX_VALUE, Integer.MAX_VALUE), // 이건 예외가 됨.
                Arguments.arguments(-1, Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.arguments(Integer.MAX_VALUE, 1, Integer.MAX_VALUE),
                Arguments.arguments(-Integer.MAX_VALUE, 1, Integer.MAX_VALUE), // 이건 예외가 안 됨. -> Math.abs로 처리
                Arguments.arguments(Integer.MAX_VALUE, -1, Integer.MAX_VALUE), // 이건 예외가 안 됨. -> Math.abs로 처리
                Arguments.arguments(3, 2, Integer.MAX_VALUE),
                Arguments.arguments(40, 41, Integer.MAX_VALUE)
            );
    }
}