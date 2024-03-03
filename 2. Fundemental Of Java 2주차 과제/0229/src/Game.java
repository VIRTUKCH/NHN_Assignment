package src;

import src.Interface.FlyAttackable;
import src.Interface.Flyable;
import src.Interface.NonFlyable;
import src.abstractclass.Unit;

import java.util.*;

public class Game {


    public static void main(String[] args) {
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
        while (!isGameOver(human, computer)) {
            // 4-1. 일단 현재 상황 브리핑
            System.out.println("현재 상황입니다.");
            computer.printList();
            human.printList();

            // 4-2. 때리라고 말하기.
            System.out.println("공격을 수행할 아군 유닛과 공격할 적군 유닛을 선택해주세요.");
            System.out.print("ex) 1 3 : ");

            // 4-3. 입력 받기 : 인덱스를 벗어나거나, NonFlyable && !FlyAttackable -> Flyable을 때리는 경우 다시 받게 했음.
            int[] idx;
            idx = getInput(sc, human, computer);

            int humanIdx = idx[0];
            int computerIdx = idx[1];


            // 5. 실제로 공격하기 - 그냥 입 닫고 공격만 하면 됨.
            human.orderAttack(computer, humanIdx, computerIdx);

            // 6. 이랬는데 중간에 게임이 끝나버렸으면 끝내
            if(isGameOver(human, computer)) {
                break;
            }

            // 7. 컴퓨터 공격하기
            // 7-1 컴퓨터의 무작위 인덱스가 유효한지 검사
            idx = checkComputerRandomInput(sc, human, computer);
            humanIdx = idx[0];
            computerIdx = idx[1];

            // 7-2. 실제로 공격하기 - 예외값은 다 처리했음. 기능만 구현하면 됨.
            computer.orderAttack(human, computerIdx, humanIdx);
        }
        printWinner(human, computer);

        sc.close();
    }

    private static void printWinner(Human human, Computer computer) {
        // 1. 컴퓨터가 승리
        if(human.isListEmpty() || human.isListHasOnlyNonFlyAttackable() && computer.isListHasOnlyFlyable()) {
            System.out.println("인간이 승리하였습니다.");
        } else if (computer.isListEmpty() || human.isListHasOnlyFlyable() && computer.isListHasOnlyNonFlyAttackable()) {
            System.out.println("컴퓨터가 승리하였습니다.");
        } else {
            System.out.println("무승부입니다.");
        }
    }

    private static int[] checkComputerRandomInput(Scanner sc, Human human, Computer computer) {
        int indexOfHumanUnitList = 0;
        int indexOfComputerUnitList = 0;

        boolean isValidInput = false;
        while (!isValidInput) {
            indexOfComputerUnitList = (int) (Math.random() * computer.getList().size());
            indexOfHumanUnitList = (int) (Math.random() * human.getList().size());
            // System.out.println("indexOfComputerUnitList = " + indexOfComputerUnitList);
            // System.out.println("indexOfHumanUnitList = " + indexOfHumanUnitList);

            // 2. NonFlyable Unit, Flyattackable이 아닌 유닛이, Flyable 유닛을 때리려 했는가?
            Unit computerUnit = computer.getList().get(indexOfComputerUnitList);
            Unit humanUnit = human.getList().get(indexOfHumanUnitList);

            isValidInput = true;

            if ((computerUnit instanceof NonFlyable) && !(computerUnit instanceof FlyAttackable) && (humanUnit instanceof Flyable)) {
                isValidInput = false;
                System.out.println("다시뽑기");
            }
        }
        return new int[] {indexOfHumanUnitList, indexOfComputerUnitList};
    }

    private static int[] getInput(Scanner sc, Human human, Computer computer) {
        String input;

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
}