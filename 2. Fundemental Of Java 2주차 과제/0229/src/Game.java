package src;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Game {
    public static void main(String[] args) {
        System.out.println("게임이 시작되었습니다.");
        System.out.println("1, 2, 3 중 하나를 입력하여 세 가지 종족 중 하나를 골라 주세요.");
        System.out.print("1 : 프로토스 / 2 : 테란 / 3 : 저그 : ");

        Scanner sc = new Scanner(System.in);
        int number = 0;

        // 1. 정상값을 입력할 때까지 입력 받기.
        while (true) {
            number = sc.nextInt();
            try {
                if (number > 3 || number < 1) {
                    throw new IllegalArgumentException("1, 2, 3 중 하나를 입력해 주셔야 합니다.");
                } else {
                    break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("다시 입력하세요.");
            }
        }

        // 2. 사용자의 객체를 만든다. (사용자는 LinkedList로 유닛을 가지고 있다.)
        // List를 가지고 있는 건 Human이야.
        // List를 한 번에 처리하고 싶은 거라면 => Human에게 number을 넘겨 줘야
        // 그리고 그걸 초기화하는 건 UnitManager가 할 일이야.
        Human human = new Human(number);

        // 3. 컴퓨터의 객체를 만든다. (컴퓨터는 LinkedList로 유닛을 가지고 있다.)
        int random = (int) (Math.random() * 3 + 1);
        Computer computer = new Computer(random);

        // 4. 적군과 아군의 유닛을 표시한다.
        computer.printComputer();
        human.printHuman();

        String str;
        StringTokenizer st;

        // 5. 유저에게 공격을 실행할 유닛과 공격받을 적군 유닛을 선택하게 한다.
        while (true) {
            System.out.println("두 개의 정수를 띄어쓰기로 입력해 주세요");
            System.out.print("ex) 1 3 : ");
            st = new StringTokenizer(sc.nextLine());
            int first = Integer.parseInt(st.nextToken());
            int second = Integer.parseInt(st.nextToken());
            if (str.equals("Hello World")) { // 잘못된 입력을 주면... ex) ArrayIndexOutOfBoundsException / 
                try {
                    throw new Exception("정상적이지 않은 입력입니다.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                break;
            }
        }

        // 6. 공격하면 적의 방어력을 깎는다.
        // 적군의 방어력이 0이 되면 List에서 제외시킨다.
        // 결과 출력

        // 6. 컴퓨터는 무작위로 유저 유닛을 공격한다. + 공격하면 유저 유닛의 방어력을 깎는다.

        // 아군의 방어력이 0이 되면 Linked List에서 소멸시킨다.
        // 결과 출력

        // 7. 아군이나 적군 중 유닛이 모두 파괴되었다면, 게임을 종료한다. (while(종료 조건)))

        // 8. 게임이 종료되었다면, 결과를 출력하고, 승자를 발표한다.

        // 9. 끝났다@!~@!~~@!@!~@!~@!~
        sc.close();
    }
}