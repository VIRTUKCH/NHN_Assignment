// public class RationalOperators {

//     public static int gCD(int m, int n) { // 최대공약수
//         return (n == 0) ? m : gCD(n, m % n);
//     }

//     public static int lCD(int a, int b) { // 최소공배수
//         int largestCommonDivisor = gCD(a, b); // 최대공약수를 구하고 -> 최소공배수를 구하자.
//         return (a / largestCommonDivisor) * b;
//     }

//     public static Rational plus(Rational left, Rational right) {
//         int leftNumerator = left.numerator;
//         int rightNumerator = right.numerator;
//         int leftDenominator = left.denominator;
//         int rightDenominator = right.denominator;
//         // int lCDOfDenominator = lCD(leftDenominator, rigthDenominator);

//         int resultNumerator = ((lCD(left.denominator, right.denominator) / leftDenominator) * leftNumerator)
//                 + (lCD(leftDenominator, rightDenominator) / rightDenominator) * rightNumerator;
//         int resultDenominator = lCD(leftDenominator, rightDenominator);

//         return new Rational(resultNumerator, resultDenominator);
//     }

//     public static Rational multiply(Rational left, Rational right) {
//         int leftNumerator = left.numerator;
//         int rightNumerator = right.numerator;
//         int leftDenominator = left.denominator;
//         int rigthDenominator = right.denominator;

//         int resultNumerator = left.numerator * right.numerator;
//         int resultDenominator = left.denominator * right.denominator;

//         return new Rational(resultNumerator, resultDenominator);
//     }

//     public static Rational divide(Rational left, Rational right) {
//         right = new Rational(right.denominator, right.numerator);
//         return multiply(left, right);
//     }
// }

// class Test {
//     public static void main(String[] args) {
//         Rational f = new Rational(2, 15); // (2 / 15)
//         Rational s = new Rational(7, 30); // (7 / 30)
//         System.out.println("Plus : " + RationalOperators.plus(f, s)); // (11 / 30)
//         System.out.println("Multiply : " + RationalOperators.multiply(f, s)); // (7 / 225)
//         System.out.println("Divide : " + RationalOperators.divide(f, s)); // (7 / 4)
//     }
// }

// /*
//  * 지금 작성되어 있는 코드는 C언어 같은 절차지향 프로그래밍 언어에서 사용하는 방법이다.
//  * 
//  * 그럼 여기서 추상화를 함 해보자.
//  * 우리는 분수를 만들기로 했고, 분자와 분모를 받았다.
//  * 분수를 표현하려면 숫자가 2개 필요하고
//  * 
//  * 분수는 더하기 곱하기 나누기를 해야 함
//  * 분수끼리 더하는 것을, 분수를 객체로 찍어 낼 수 있을 거다.
//  * 
//  * int[]를 쓰는 게 더 좋을지, Rational을 쓰는 게 좋을지는 잘 모르겠다.
//  * 근데 확실한 건, int[]는 확실히 안 좋다.
//  * 
//  * 앞에는 분자 뒤에는 분모로 고고 해야 한다.
//  * 그리고 배열의 요소가 한 개가 될지, 두 개가 될지도 컴파일러는 모른다.
//  * 
//  * 객체를 쓸 수 있다.
//  * raiton객체를 가지고 유리수를 표현한 걸 => 쉽게 표현할 수 있다.
//  * 여기서 메서드만 잘 추가하면 클래스를 늘리고 줄이고 하기가 쉽다.
//  * 
//  * 근데 배열이라면, 코드를 수정하기도 많이 힘들어진다.
//  * 객체지향 프로그래밍에서 제일 중요한 것은, 어떤 동작이나 계산을 틀 안에 넣어서
//  * 객체 간의 상호 작용으로 만들어 놓는 것. 그리고 그 안에서 돌아가게 하는 것.
//  * 하나의 템플릿으로 여러 개의 객체를 찍으니 수정도 용이해지고, 수정할 일이 있으면 객체의 추가만으로 모든 것이 해결되는 게 OOP의 장점
//  * 
//  * 그래서 클래스를 잘 만들어 줘야 함.
//  * 제일 중요한 건, 클래스를 잘 이해하는 것보다 < 객체가지고 상호작용하는 게 OOP라는 것
//  * 
//  * [오늘의 숙제]
//  * 
//  * 1. RationalOperator
//  * : 객체를 쓰지 않고 메서드 형식으로 메인 함수에 찍어 놓으면 수준이 많이 떨어진다. 체험을 해 봐라.
//  * 그리고 나서 객체를 해보고 추상화를 해봐라.
//  * 
//  * 객체는 변수하고 함수 있어야 한다
//  * 
//  * 클래스는 분모하고 함수가 있어야 함
//  * 
//  * 행동은 더하기, 곱하기, 나누기
//  * 
//  * 이걸 하려면 캡슐화가 있어야 함.
//  * -> gCD
//  * 
//  * 추가적으로 하려면 현재의 Rational을 정수로 바꾸는 거, double로 바꾸는 거, 화면에 출력하는 거(toString())
//  * 
//  * Fraction은 분자와 분모가 있어야 한다. 그리고 int를 두 개 받아야 함
//  * 내부적으로 생성될 때 약분을 해서 최소값을 만들어 내야 함.
//  * 그리고 더하기 동작이 있어야 하고
//  * 유리수끼리 빼기도 있어야 한다.
//  * 필요에 따라, 유리수하고 정수, 유리수하고 유리수 곱하기도 있으면 좋아.
//  * 객체가 가지고 있는 동작이 많으면 많을수록, 좋은 거라는 거지
//  * 
//  * 객체지향적 프로그래밍을 만들어 봐라.........
//  * 
//  * [오늘의 숙제]
//  * 1. RationalOperators에서 더하기, 곱하기 나누기
//  * 2. 추상화를 하면 동작이 나옴 => Rational 클래스를 만들어서 객체끼리 연산하는 것 구현해 봐라.
//  * 3. 수요일 아침까지 하면 된다.
//  */