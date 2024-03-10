package com.nhnacademy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {
    private GamePanel panel;

    public Main() {
        panel = new GamePanel();
        add(panel);
        setTitle("벽돌 깨기 게임");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new Main();
    }

    private class GamePanel extends JPanel implements ActionListener {

        static final Color BACKGROUND_COLOR = Color.BLACK;
        static final int BACKGROUND_SIZE_X = 500;
        static final int BACKGROUND_SIZE_Y = 500;

        static final Color PADDLE_COLOR = Color.RED;
        static final int PADDLE_SIZE_X = 60;
        static final int PADDLE_SIZE_Y = 10;

        static final Color BALL_COLOR = Color.WHITE;
        static final int BALL_SIZE_X = 20;
        static final int BALL_SIZE_Y = 20;

        private Timer timer;
        private int paddleX = 200;
        private int ballX = 240, ballY = 355;
        private int ballDirX = -1, ballDirY = -2;

        private boolean[][] bricks = new boolean[2][9];

        public GamePanel() {
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    paddleX = e.getX() - 30; // 마우스 위치에서 패들의 중앙을 조정
                    // 패들이 프레임 밖으로 나가지 않도록 조정
                    if (paddleX < 0) {
                        paddleX = 0;
                    } else if (paddleX > getWidth() - 60) {
                        paddleX = getWidth() - 60;
                    }
                    repaint();
                }
            });

            setFocusable(true);
            timer = new Timer(10, this);
            timer.start();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 1. 배경을 설정한다.
            g.setColor(BACKGROUND_COLOR);
            g.fillRect(0, 0, BACKGROUND_SIZE_X, BACKGROUND_SIZE_Y); // 배경

            // 2. 패들을 설정한다.
            g.setColor(PADDLE_COLOR);
            g.fillRect(paddleX, 400, PADDLE_SIZE_X, PADDLE_SIZE_Y); // 패들

            // 3. 공을 추가한다.
            g.setColor(BALL_COLOR);
            g.fillOval(ballX, ballY, BALL_SIZE_X, BALL_SIZE_Y); // 공

            // 4. 벽돌을 추가한다.
            for (int i = 0; i < 2; i++) {
                // 4-1. 윗쪽 두 줄은 파란색으로 추가
                for (int j = 0; j < 9; j++) {
                    g.setColor(Color.BLUE); // 벽돌의 색상을 설정합니다.
                    g.fillRect(55 * j, 25 * i, 50, 20); // 벽돌을 그립니다. (x좌표, y좌표, 가로 길이, 세로 길이)
                }

                // 4-2. 가운뎃 줄은 노란색으로 추가
                for (int j = 0; j < 9; j++) {
                    g.setColor(Color.YELLOW); // 벽돌의 색상을 설정합니다.
                    g.fillRect(55 * j, 50 + 25 * i, 50, 20); // 벽돌을 그립니다. (x좌표, y좌표, 가로 길이, 세로 길이)
                }

                // 4-3. 맨 아래는 빨간색으로 추가
                for (int j = 0; j < 9; j++) {
                    g.setColor(Color.RED); // 벽돌의 색상을 설정합니다.
                    g.fillRect(55 * j, 100 + 25 * i, 50, 20); // 벽돌을 그립니다. (x좌표, y좌표, 가로 길이, 세로 길이)
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ballX += ballDirX;
            ballY += ballDirY;

            // 공과 벽돌 충돌 감지
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 9; j++) {
                    Rectangle brick = new Rectangle(55 * j, 25 * i, 50, 20);
                    if (brick.intersects(new Rectangle(ballX, ballY, BALL_SIZE_X, BALL_SIZE_Y))) {
                        // 충돌 처리
                        ballDirY = -ballDirY;
                        // 벽돌 삭제
                        bricks[i][j] = false;
                        break;
                    }
                }
            }

            // 공과 패들 충돌 감지
            if (new Rectangle(ballX, ballY, BALL_SIZE_X, BALL_SIZE_Y)
                    .intersects(new Rectangle(paddleX, 400, PADDLE_SIZE_X, PADDLE_SIZE_Y))) {
                ballDirY = -ballDirY;
            }

            // 화면 갱신
            repaint();
        }
    }
}