import javax.swing.*;
import java.awt.*;
/*
 *      y축
      |
      |
      |
      |
------|------ x축
      |
      |
      |
      |
 */
public class RecursiveTriangle extends JPanel {

    // paintComponent 메서드는 화면에 그릴 내용을 지정합니다.
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[] first = new int[] { 0, getHeight() }; // 왼쪽 아래
        int[] second = new int[] { getWidth(), getHeight() }; // 오른쪽 아래
        int[] third = new int[] { getWidth() / 2, getHeight() / 3 }; // 완전 중간

        drawTriangle(g, first[0], first[1], second[0], second[1], third[0], third[1]);
    }

    // drawTriangle 메서드는 주어진 꼭짓점 좌표를 가지는 삼각형을 그립니다.
    public void drawTriangle(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3) {
        if (Math.abs(x2 - x1) < 5) {
            return;
        }

        // 꼭짓점 잇기
        g.drawLine(x1, y1, x2, y2); // 삼각형 선분 1
        g.drawLine(x1, y1, x3, y3); // 삼각형 선분 2
        g.drawLine(x2, y2, x3, y3); // 삼각형 선분 3

        // 중점 계산하여 새로운 삼각형 만들기
        drawTriangle(g, x1, y1, (x1 + x2) / 2, (y1 + y2) / 2, (x1 + x3) / 2, (y1 + y3) / 2);
        drawTriangle(g, (x1 + x2) / 2, (y1 + y2) / 2, x2, y2, (x2 + x3) / 2, (y2 + y3) / 2);
        drawTriangle(g, (x1 + x3) / 2, (y1 + y3) / 2, (x2 + x3) / 2, (y2 + y3) / 2, x3, y3);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.add(new RecursiveTriangle());
        frame.setVisible(true);
    }
}
