import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

public class MazeStack {
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

	public static boolean solveForStack(int x, int y) {
		// 목적 : 자동으로 미로를 찾아 가는 프로그램 만들기
		// 마지막에 나갈 수 있는 길은 2로 표시하기. (못 나가는 길이면 => 1 안 가 본 길은 => 0)
		// 꼭 모든 길을 가 봐야 하는 건 아님.

		// 1. 스택을 만든다.
		// 2. 최초 좌표를 스택에 넣고, 자리를 2로 표시한다.
		// 3. 상하좌우를 갈 수 있는지 탐색한다. (배열 밖이거나, 1이거나, 2면 가지 않는다.)
		// 4. 갈 수 있다면 => 스택에 해당하는 자리를 넣고 + 자리를 2로 바꾸고 반복문을 종료한다.
		// 5. 갈 수 없다면 => Stack을 POP하고 반복문을 종료한다.

		// 1. 스택을 만든다.
		stack = new Stack<>();

		// 2. 초기 좌표를 기록한다.
		stack.add(new int[] { x, y });

		// 3. 현재 좌표를 초기 좌표로 초기화한다. (최초만)
		int[] currentPos = new int[2];
		currentPos = stack.peek();

		// 4. 지금 밟고 있는 좌표를 2로 바꾼다. (지금 밟고 있는 좌표는 2가 아니다. Map 초기화를 그렇게 했기 때문이다. -> 따라서,
		// 2로 표기해도 괜찮다.)
		map[currentPos[0]][currentPos[1]] = 2;

		// printMap(); //TODO : Erase Test

		// 5. 반복문을 시작하여 길을 찾는다.
		while (true) {
			// printMap(); //TODO: Erase Test Code

			// 반복문 종료 조건 1 : 스택이 비어 있다면 -> 길이 없어서 못 찾는 중이다. -> 실패를 선언하고 시스템을 종료한다.
			try {
				currentPos = stack.peek();
				// System.out.println("current Position : " + Arrays.toString(currentPos));
				// //TODO: Erase Test Code
			} catch (EmptyStackException e) {
				// System.out.println("풀 수 없는 미로입니다."); //TODO: Erase Test Code
				return false;
			}

			// 반복문 종료 조건 2 : 미로의 끝에 도달했다면 -> 탐색할 수 있는 미로다 -> 성공을 선언하고 반복문을 탈출한다.
			if (currentPos[0] == mazeSizeN - 1 && currentPos[1] == mazeSizeM - 1) {
				return true;
			}

			// 6. 위의 경우가 아니라면, 찾아야 하는 경우다. -> 탐색한다.
			int[][] go = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } }; // 아래, 오른쪽, 위쪽, 왼쪽

			for (int i = 0; i < 4; i++) { // 7. 아래 -> 오른쪽 -> 왼쪽 -> 위쪽 순서대로 돌린다.
				if (canGo(currentPos, go[i])) { // 갈 수 있다면 간다.
					stack.add(new int[] { currentPos[0] + go[i][0], currentPos[1] + go[i][1] }); // 스택에 갈 수 있는 자리를 저장하고
					map[currentPos[0] + go[i][0]][currentPos[1] + go[i][1]] = 2; // 자리를 2로 바꾸고 반복문을 종료한다.
					// printMap(); //TODO : Erase Test Code
					break;
				}
			}

			// 7. 갈 수 없다면, 실행했던 곳으로 되돌아간다. (pop)
			stack.pop();
		}
	}

	public static void main(String[] args) throws IOException {
		// initMap(); // 수동으로 맵 초기화하기
		// initMapInFile(); // 파일의 입력된 값을 미리 받아 맵 초기화하기

		initRandomMap();
		printMap("길을 찾기 전 미로입니다");
		if (solveForStack(0, 0)) {
			System.out.println("풀 수 있는 미로입니다.");
		} else {
			System.out.println("풀 수 없는 미로입니다.");
		}
		printMap("길을 찾은 후 미로입니다.");
	}
}