package src;

import src.Interface.FlyAttackable;
import src.Interface.Flyable;
import src.Interface.NonFlyable;
import src.abstractclass.Unit;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Game {
    private static boolean isGameOver(Human human, Computer computer) {
        if(human.isListEmpty()) { // 1. 휴먼의 리스트가 모두 비었을 경우
            return true;
        } else if (computer.isListEmpty()) { // 2. 컴퓨터의 리스트가 모두 비었을 경우
            return true;
        } else if (human.isListHasOnlyFlyable() && computer.isListHasOnlyNonFlyAttackable()) { // 3. 한 쪽은 나는 것밖에 & 한 쪽은 안 나는 것밖에
            return true;
        } else if (human.isListHasOnlyNonFlyAttackable() && computer.isListHasOnlyFlyable()) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        java.lang.System.out.println();

        System.out.println("게임이 시작되었습니다.");
        System.out.println("1, 2, 3 중 하나를 입력하여 세 가지 종족 중 하나를 골라 주세요.");

        Scanner sc = new Scanner(System.in);
        int number = 0;

        // 1. 정상값을 입력할 때까지 받기
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                System.out.print("1 : 프로토스 / 2 : 테란 / 3 : 저그 : ");
                number = Integer.parseInt(sc.nextLine().trim());
                if (number >= 1 && number <= 3) { // 정상적으로 입력한 경우
                    isValidInput = true;
                } else {
                    throw new IllegalArgumentException("1 미만 3 초과의 값을 입력하셨습니다.");
                }
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
                System.out.println("다시 입력하세요");
            } catch (InputMismatchException ime) {
                System.out.println("숫자만 입력하셔야 합니다.");
                System.out.println("다시 입력하세요.");
            }
        }

        // 2. 사용자의 객체를 만든다. - 리스트에 유닛 포함 중.
        Human human = new Human(number);

        // 3. 컴퓨터의 객체를 만든다. - 리스트에 유닛 포함 중.
        Computer computer = new Computer();

        // 4. 유저에게 어떤 유닛으로, 어떤 유닛을 때릴지 물어본다.
        String input;
        StringTokenizer st;

        while (!isGameOver(human, computer)) {
            // 4-1. 일단 현재 상황 브리핑
            System.out.println("현재 상황입니다.");
            computer.printList();
            human.printList();

            // 4-2. 때리라고 말하기.
            System.out.println("공격을 수행할 아군 유닛과 공격할 적군 유닛을 선택해주세요.");
            System.out.print("ex) 1 3 : ");

            // ---- 여기까지는 정상입 ----

            // 4-3. 입력 받기
            int[] idx;
            idx = getInput(sc, human, computer);

            int humanIdx = idx[0];
            int computerIdx = idx[1];


            // 5. 실제로 공격하기. 근데 Flyable이면 다시 공격하게 만들어야 함.

            human.orderAttack(computer, humanIdx, computerIdx);

            if(isGameOver(human, computer)) {
                break;
            }
            computer.orderAttack(human);
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

    private static int[] getInput(Scanner sc, Human human, Computer computer) {
        String input;
        StringTokenizer st;

        int first = 0;
        int second = 0;

        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                input = sc.nextLine();
                String[] strings;
                strings = input.split(" +");
                first = Integer.parseInt(strings[0].trim());
                second = Integer.parseInt(strings[1].trim());

                // 1. 인덱스에 존재하는 아군과, 인덱스에 존재하는 적군을 때렸는가?
                if(first < 0 || first > human.getList().size() - 1) {
                    throw new IllegalArgumentException("인덱스에 존재하지 않는 아군입니다.");
                } else if (second < 0 || second > computer.getList().size() - 1) {
                    throw new IllegalArgumentException("인덱스에 존재하지 않는 적군입니다.");
                }

                // 2. NonFlyable Unit, Flyattackable이 아닌 유닛이, Flyable 유닛을 때리려 했는가?
                Unit ourUnit = human.getList().get(first);
                Unit enemyUnit = computer.getList().get(second);

                if((ourUnit instanceof NonFlyable) && !(ourUnit instanceof FlyAttackable) && (enemyUnit instanceof Flyable)) {
                    throw new IllegalArgumentException("공중을 공격할 수 없는 유닛입니다.");
                }

                isValidInput = true; // 여기가 매우 핵심임
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("다시 입력하세요.");
                System.out.print("ex) 1 3 : ");
            } catch (Exception e) {
                System.out.println("예상하지 못한 값이 입력되었습니다.");
                System.out.println("다시 입력하세요.");
                System.out.print("ex) 1 3 : ");
            }
        }
        return new int[] {first, second};
    }
}