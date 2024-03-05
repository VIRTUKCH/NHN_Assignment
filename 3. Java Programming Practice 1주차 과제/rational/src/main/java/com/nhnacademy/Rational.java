package com.nhnacademy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; // core를 import하면 안 됨.
// import org.apache.logging.log4j.core.Logger;

// '모든' Integer 범위의 테스트 케이스를 통과하기 위해서는 => Long 멤버 변수가 필수적인 것 같다.
// 그렇게 따지면 무조건 BigInteger을 해야 하는 거 아닐까?

/**
 * 유리수
 * @author VIRTUKCH
 */
public class Rational {
    long numerator; // 분자
    long denominator; // 분모

    // 로그에 이름을 주면 특정한 로그만 가져올 수 있다.
    // getSimpleName을 하면 클래스 이름만 나옴.
    Logger logger = LogManager.getLogger(this.getClass().getName());
    // 이렇게 하면 static하게 관리할 수 있음.
    // static Logger logger = LogManager.getLogger(Rational.class.getName());

    public long getNumerator() {
        return this.numerator;
    }

    public void setNumerator(long numerator) {
        this.numerator = numerator;
    }

    public long getDenominator() {
        return this.denominator;
    }

    public void setDenominator(long denominator) {
        this.denominator = denominator;
    }

    public Rational(int n) {
        numerator = n;
        denominator = 1;
        logger.trace("new rational : [{}/{}]", numerator, denominator);
    }

    /**
     * @param numerator
     * @param denominator
     * @throws ArithmeticException 분모가 0인 경우
     * @throws OutOfBoundsException 분모가 Integer.MAX_VALUE를 넘을 경우
     */
    public Rational(long numerator, long denominator) {
        if (denominator == 0) {
            throw new ArithmeticException();
        }

        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }

        long g = gCD(Math.abs(numerator), Math.abs(denominator));

        this.numerator = numerator / g;
        this.denominator = denominator / g;
    }

    public Rational(Rational rational) {
        this.numerator = rational.getNumerator();
        this.denominator = rational.getDenominator();
    }

    @Override
    public String toString() {
        if (this.denominator == 1) {
            return "[" + this.getNumerator() + "]";
        } else {
            return "[" + this.getNumerator() + "/" + this.getDenominator() + "]";
        }
    }

    long gCD(long x, long y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException();
        }

        if (y == 0) {
            return x;
        } else {
            return gCD(y, x % y);
        }
    }

    // ------------- 여기서부터 테스트코드 작성하기 -------------

    @Override
    public boolean equals(Object other) {
        return (other instanceof Rational)
                && (getNumerator() == ((Rational) other).getNumerator())
                && (getDenominator() == ((Rational) other).getDenominator());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // 더하기
    public static Rational add(Rational x, Rational y) {
        return new Rational(
                x.getNumerator() * y.getDenominator() + y.getNumerator() * x.getDenominator(),
                x.getDenominator() * y.getDenominator());
    }

    // 빼기
    public static Rational sub(Rational x, Rational y) {
        return new Rational(
                x.getNumerator() * y.getDenominator() - y.getNumerator() * x.getDenominator(),
                x.getDenominator() * y.getDenominator());
    }

    // 곱하기
    public static Rational multiply(Rational x, Rational y) {
        return new Rational(
                x.getNumerator() * y.getNumerator(),
                x.getDenominator() * y.getDenominator());
    }

    // 나누기
    public static Rational divide(Rational x, Rational y) { // ad / bc
        return new Rational(
                x.getNumerator() * y.getDenominator(),
                x.getDenominator() * y.getNumerator());
        // return new Rational(x, y.inverse())
    }

    public Rational inverse() {
        return new Rational(-getNumerator(), getDenominator());
    }

    public Rational reciprocal(int n) {
        if ((long) Math.pow(Math.abs(getNumerator()), n) > Integer.MAX_VALUE
                || (long) Math.pow(Math.abs(getDenominator()), n) > Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        return new Rational(
                (int) Math.pow(getNumerator(), n),
                (int) Math.pow(getDenominator(), n));
    }
}