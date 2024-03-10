public class Rational { // 유리수
    int numerator;
    int denominator;

    public Rational plus(Rational rational) {
        return this;    
    }
    
    Rational(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        int g = gCD(numerator, denominator);
        this.numerator = this.numerator / g;
        this.denominator = this.denominator / g;
    }
    
    private static int gCD(int m, int n) {
        return (n == 0) ? m : gCD(n, m % n);
    }

    @Override
    public String toString() {
        return numerator + " / " + denominator;
    }

    public static void main(String[] args) {
        Rational r = new Rational(5000, 2000);
        System.out.println(r);
    }
}
