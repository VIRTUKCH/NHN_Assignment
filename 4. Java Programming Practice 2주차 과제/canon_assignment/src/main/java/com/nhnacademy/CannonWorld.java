/* 
 * TODO 1. 시작 각도도 안 맞고
 * TODO 2. 시작 속도도 좀 달라
 */

package com.nhnacademy;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CannonWorld extends MovableWorld implements MouseMotionListener, KeyListener, ComponentListener {
    static final int WALL_THICKNESS = 500;
    static final int BAR_WIDTH = 100;
    static final int BAR_THICKNESS = 20;
    static final int BAR_SPEED = 10;
    static final int MIN_HEIGHT = WALL_THICKNESS * 2 + BAR_THICKNESS; // 뭐하는 데 쓰일까?
    static final int MIN_WIDTH = WALL_THICKNESS * 2 + BAR_WIDTH; // 뭐하는 데 쓰일까?
    int blockHeight = 20;
    int blockWidth = 40;
    int angle;

    Vector gravity = new Vector(0, 1); // 더하는 게 좋겠다 (더해야 하니까)
    Vector windSpeed = new Vector(0, 0); // 더하는 게 좋겠다 (더해야 하니까)
    Vector ballSpeed = new Vector(1, 1); // 1로 둬서 곱하는 게 좋겠다 (곱해야 하니까)
    Vector angleVector = new Vector(1, 1); // 5 단위로 조절

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setAngleVector(int angle) {
        angleVector.setDX((int) (5 * Math.cos(Math.toRadians(angle))));
        angleVector.setDY((int) (5 * Math.sin(Math.toRadians(angle))));
    }

    public void setWindSpeed(int speed) {
        windSpeed.setDX(speed);
    }

    public void setGravity(int speed) {
        gravity.setDY(speed);
    }

    public void setBallSpeed(int speed) {
        this.ballSpeed.setDX(speed);
        this.ballSpeed.setDY(speed);
    }

    public void clear() {
        // 0. 쓰레드 끊기
        threadPool.shutdown();

        // 1. 볼 다 버리기
        for (Ball ball : ballList) {
            remove(ball);
        }

        // 2. 쓰레드 풀 초기화하기
        threadPool = Executors.newFixedThreadPool(10);

    }

    final Box leftWall;
    final Box rightWall;
    final Box topWall;
    final Box bottomWall;

    final BounceableBox bar; // 현재 탱크 역할
    final Canon canon;
    final EnemyCanon enemyCanon;
    final Obstacle obstacle;

    final List<Box> boxList = new LinkedList<>();
    final List<Ball> ballList = new LinkedList<>();

    ExecutorService threadPool = Executors.newFixedThreadPool(10);

    final Color[] colors = { Color.YELLOW, Color.WHITE, Color.BLUE, Color.GREEN };

    // 생성자 - 기본 세팅
    public CannonWorld(int x, int y, int width, int height) {
        super();

        setBounds(x, y, width, height);

        // ------------------------- 벽 만들기 -------------------------

        leftWall = new PaintableBox(-WALL_THICKNESS / 2 + 1, height / 2, WALL_THICKNESS, height, Color.WHITE);
        rightWall = new PaintableBox(width + WALL_THICKNESS / 2 - 50, height / 2, WALL_THICKNESS, height,
                Color.WHITE);
        topWall = new PaintableBox(width / 2, -WALL_THICKNESS / 2, width + 2 * WALL_THICKNESS, WALL_THICKNESS,
                Color.YELLOW);
        bottomWall = new PaintableBox(width / 2, height + WALL_THICKNESS / 2 - 1, width + 2 * WALL_THICKNESS,
                WALL_THICKNESS, Color.GREEN);

        add(leftWall);
        add(rightWall);
        add(topWall);
        add(bottomWall);

        // ------------------------- 바닥에 충돌하면 천천히 줄어들게 하기 -------------------------

        bottomWall.setHitListener(other -> {

            if (other instanceof Bounceable) {
                Vector motion = ((Movable) other).getMotion();

                motion.multiply(0.5);
                ((Movable) other).setMotion(motion);
            }
        });

        // ------------------------- 바 만들기 -------------------------

        bar = new BounceableBox(100, height - BAR_THICKNESS / 2, BAR_WIDTH, BAR_THICKNESS, Color.BLUE);
        add(bar);
        // bar = null;

        // ------------------------- 탱크 만들기 -------------------------

        canon = new Canon(100, 450, 100, 100, Color.black);
        add(canon);

        enemyCanon = new EnemyCanon(900, 450, 100, 100, Color.black);
        add(enemyCanon);


        // ------------------------- 장애물 만들기 -------------------------

        obstacle = new Obstacle(450, 450, 300, 300, Color.YELLOW);
        add(obstacle);

        // ------------------------- 판넬에 리스너 더하기 -------------------------

        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);

    }

    // 블럭 추가하기
    public void init() {
        int y = blockHeight / 2;
        for (int line = 0; line < 4; line++) {
            int x = blockWidth / 2;

            while (x + blockWidth / 2 <= getWidth()) {
                Box box = new BrittleBox(x, y, blockWidth, blockHeight, colors[line]);
                boxList.add(box);
                add(box);
                box.setHitListener(other -> remove(box));
                x += blockWidth;
            }
            y += blockHeight;
        }
    }

    // 공 만들기
    @Override
    public void add(Bounded object) {
        super.add(object);
        if (object instanceof Movable) {
            threadPool.execute((Movable) object);
        }
    }

    // 공 발사하기
    public void fire() {
        // 공이 포의 머리에서 시작할 수 있도록 조치했음.
        BounceableBall ball = new BounceableBall(canon.getX() + (int) (100 * Math.cos(Math.toRadians(-angle))), canon.getY() - 80 + (int) (100 * Math.sin(Math.toRadians(-angle))), 10, Color.RED);
        ballList.add(ball); // 내가 추가했음.

        ball.setDT(getDT()); // 속도 정하기 -> 작아질수록 속도가 빨라짐.

        // 얘는 그냥 코드 나누는 정도의 역할 외에는 수행하지 못하는 듯.
        ball.addStartedActionListener(() -> {
            ball.setMotion(5 * angleVector.getDX() * ballSpeed.getDX(), -5 * angleVector.getDY() * ballSpeed.getDY());
        });
        
        
        // 움직이는 데에 필요한 메서드는 여기에 구현한다.
        ball.addMovedActionListener(() -> {
            List<Bounded> removeList = new LinkedList<>();

            // * 주의 : 움직일 때마다 더하는거다.
            Vector newMotion = ball.getMotion();
            newMotion.add(gravity); // 벡터에 중력을 더해요 (덧셈)
            newMotion.add(windSpeed); // 벡터에 바람을 더해요 (덧셈)

            logger.info(ball.getMotion().getDX());
            logger.info(ball.getMotion().getDY());

            ball.setMotion(newMotion); // 계산 결과를 적용해요
            if (ball instanceof Bounceable) { // 발사된 공이 Bounceable이라면
                for (int j = 0; j < getCount(); j++) { // 지도 상에 있는 물체 다 뒤져가면서
                    Bounded other = get(j); // 다른 물체와의 교집합 바운드 있는지 확인해

                    if (ball != other && ball.isCollision(other.getBounds())) { // 겹치는 부분이 있다면
                        ((Bounceable) ball).bounce(other); // 튕기자

                        if (other instanceof HitListener) { // 힛 리스너와 겹친 거라면
                            ((HitListener) other).hit(ball); // 힛 메서드 실행. + 지금은 딱히 뭘 하진 않음.
                        }
                    }
                }
            }

            for (Bounded item : removeList) {
                remove(item);
            }
        });

        add(ball);
    }

    @Override
    public void keyPressed(KeyEvent event) { // 상하좌우 이동하기
        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            canon.move(new Vector(-BAR_SPEED, 0));
            if (canon.getMinX() < 0) {
                canon.setLocation(new Point(canon.getWidth() / 2, canon.getCenterY()));
            }
        } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            canon.move(new Vector(BAR_SPEED, 0));
            if (canon.getMaxX() > getWidth()) {
                canon.setLocation(new Point(getWidth() - canon.getWidth() / 2, canon.getCenterY()));
            }
        } else if (event.getKeyCode() == KeyEvent.VK_R) { // R키를 누르면 블록을 만들고
            init();
        } else if (event.getKeyCode() == KeyEvent.VK_S) { // S키를 누르면 공을 쏜다.
            fire();
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        //
        logger.info("{}", event.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent event) {
        //
        logger.info("{}", event.getKeyCode());
    }

    @Override
    public void componentHidden(ComponentEvent event) {
        logger.info("Hidden");
    }

    @Override
    public void componentMoved(ComponentEvent event) {
        logger.info("Moved");
    }

    // 화면 사이즈 조정하는 메서드
    @Override
    public void componentResized(ComponentEvent event) {
        if (ballList.isEmpty() && (getWidth() > BAR_WIDTH) && (getHeight() > BAR_THICKNESS)) {
            leftWall.setBounds(new Bounds(-WALL_THICKNESS, -WALL_THICKNESS,
                    WALL_THICKNESS, WALL_THICKNESS * 2 + getHeight()));
            rightWall.setBounds(new Bounds(getWidth(), -WALL_THICKNESS,
                    WALL_THICKNESS, WALL_THICKNESS * 2 + getHeight()));
            topWall.setBounds(new Bounds(-WALL_THICKNESS, -WALL_THICKNESS,
                    getWidth() + WALL_THICKNESS * 2, WALL_THICKNESS));
            bottomWall.setBounds(new Bounds(-WALL_THICKNESS, getHeight(),
                    getWidth() + WALL_THICKNESS * 2, WALL_THICKNESS));
            bar.moveTo(new Point(100, getHeight() - BAR_THICKNESS / 2));
        }
    }

    // 컴포넌트 보여주기?
    @Override
    public void componentShown(ComponentEvent event) {
        logger.info("Shown");
    }

    // 마우스 드래그 -> 바 이동하게 만들기
    @Override
    public void mouseDragged(MouseEvent event) {
        if (event.getX() > canon.getWidth() / 2 && event.getX() < getWidth() - canon.getWidth() / 2) {
            canon.setLocation(new Point(event.getX(), canon.getCenterY()));
        }
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        //
    }

    public void setDT(int dt) {
        this.dt = dt;
    }
}