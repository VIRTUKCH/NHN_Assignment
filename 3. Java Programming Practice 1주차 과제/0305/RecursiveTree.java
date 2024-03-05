import java.awt.*;

public class RecursiveTree {
    static final double LEN = 0.7;
    public static class MyCanvas extends Canvas {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int x = 600;
            int y = 800;
            recursiveDraw(g, x, y, Math.PI/2, 200);
        }

        public void recursiveDraw(Graphics g, int x, int y, double angle, double length) {
            if (length < 1) {
                return;
            }

            int nextX = (int) (x + length * Math.cos(angle));
            int nextY = (int) (y - length * Math.sin(angle));

            g.drawLine(x, y, nextX, nextY);
            length *= LEN;

            recursiveDraw(g, nextX, nextY, angle - Math.PI/6, length);
            recursiveDraw(g, nextX, nextY, angle + Math.PI/6, length);
        }
    }

    public static void main(String[] args) throws Exception {
        Frame frame = new Frame("RecursiveTree");
        frame.setSize(1200, 800);
        MyCanvas canvas = new MyCanvas();
        frame.add(canvas);
        frame.setVisible(true);
    }
}