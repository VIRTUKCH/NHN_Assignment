import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class MazeRecursive {
    static int[][] map;
    static int mazeSizeN; // 미로의 행 크기
    static int mazeSizeM; // 미로의 열 크기
    static Stack<int[]> stack;

    public static void initRandomMap() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("ex) 3 3");
        System.out.print("Input Size of Map : ");
        StringTokenizer st = new StringTokenizer(br.readLine(), " +");
        mazeSizeN = Integer.parseInt(st.nextToken().trim());
        mazeSizeM = Integer.parseInt(st.nextToken().trim());

        map = new int[mazeSizeN][mazeSizeM];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == 0 && j == 0 || i == mazeSizeN - 1 && j == mazeSizeM - 1) {
                    map[i][j] = 0;
                } else {
                    map[i][j] = (int) (Math.random() * 2);
                }
            }
        }
    }

    public static void printMap(String msg) {
        System.out.println("=============printMap=============");
        System.out.println("<" + msg + ">");
        for (int i = 0; i < map.length; i++) {
            System.out.println(Arrays.toString(map[i]));
        }
        System.out.println("=============printMap=============");
    }

    private static boolean canGo(int[] currentPos, int[] go) {
        // 1. 배열의 밖이다
        if (currentPos[0] + go[0] < 0 || currentPos[1] + go[1] < 0 || currentPos[0] + go[0] >= mazeSizeM
                || currentPos[1] + go[1] >= mazeSizeN) {
            return false;
        }

        // 2. 1이거나 2다
        if (map[currentPos[0] + go[0]][currentPos[1] + go[1]] == 1
                || map[currentPos[0] + go[0]][currentPos[1] + go[1]] == 2) {
            return false;
        }

        return true;
    }

    public static boolean solveForRecursive(int x, int y, boolean result) {
        // 0. 종료 조건
        // 0-1) 현재 위치가 도착 위치인 경우 -> true를 return하면 된다.
        // 0-2) 그냥 탐색이 불가능한 경우 -> 마지막에 false를 return하면 된다. -> 이건 지금 할 게 아니다.

        if (x == mazeSizeN - 1 && y == mazeSizeM - 1) {
            return true;
        }

        // 1. 현재 좌표를 파라미터로 받는다. => 이미 받았다. => 뒤에서 재귀 함수를 부를 때 초기화만 잘 해주면 된다.

        // 2. 지금 밟고 있는 좌표를 2로 바꾼다.
        map[x][y] = 2;

        // 3. 그냥 풀어도 되지만, 이전에 있던 구조를 살리기 위해 currentPos를 선언해야겠다.
        int[] currentPos = new int[] { x, y };

        // 4. 상하좌우를 탐색한다.
        int[][] go = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } }; // 아래, 오른쪽, 위쪽, 왼쪽

        // 5. '탐색'이 목적인 반복문을 실행한다.
        for (int i = 0; i < 4; i++) { // 아래 -> 오른쪽 -> 왼쪽 -> 위쪽 순서대로 돌린다.
            if (canGo(currentPos, go[i])) { // 갈 수 있다면 간다.
                result = true;
                map[currentPos[0] + go[i][0]][currentPos[1] + go[i][1]] = 2; // 자리를 2로 바꾼다.
                solveForRecursive(currentPos[0] + go[i][0], currentPos[1] + go[i][1], result);
                return result;
                // printMap(); //TODO : Erase Test Code
                // break;
            }
        }
        // 갈 수 없는 경우에만 여기에 온다.
        result = false;
        return result;
    }

    public static void main(String[] args) throws IOException {
        initRandomMap();
        printMap("길을 찾기 전 미로입니다");
        if (solveForRecursive(0, 0, true)) {
            System.out.println("풀 수 있는 미로입니다.");
        } else {
            System.out.println("풀 수 없는 미로입니다.");
            System.out.println("그래도 길을 찾아 봤습니다.");
        }
        printMap("길을 찾은 후 미로입니다.");
    }
}