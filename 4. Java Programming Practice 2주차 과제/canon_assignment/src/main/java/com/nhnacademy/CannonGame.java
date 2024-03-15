package com.nhnacademy;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CannonGame extends JFrame implements ComponentListener {
    static final int FRAME_WIDTH = 1500;
    static final int FRAME_HEIGHT = 700;
    static final int MIN_RADIUS = 10;
    static final int MAX_RADIUS = 50;
    static final int MIN_WIDTH = 10;
    static final int MAX_WIDTH = 50;
    static final int MIN_HEIGHT = 10;
    static final int MAX_HEIGHT = 50;
    static final int FIXED_BALL_COUNT = 0;
    static final int FIXED_BOX_COUNT = 3;
    static final int BOUNDED_BALL_COUNT = 5;
    static final int MIN_DELTA = 5;
    static final int MAX_DELTA = 7;
    static final int MAX_MOVE_COUNT = 0;
    static final int DT = 50;
    static final int BLOCK_WIDTH = 80;
    static final Color[] COLOR_TABLE = {
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.YELLOW
    };

    Logger logger = LogManager.getLogger();

    CannonWorld world;

    public CannonGame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(this);

        setLayout(null);

        world = new CannonWorld(300, 0, FRAME_WIDTH - 300, FRAME_HEIGHT - 200);
        world.setDT(DT);
        world.setBackground(Color.WHITE);
        add(world);

        // ----------------------------- Fire 버튼 (구현 완료) -----------------------------
        JButton fireButton = new JButton();

        fireButton.setBounds(20, 590, 100, 60);
        fireButton.setText("Fire!");
        fireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.fire();
            }
        });

        add(fireButton);

        // ----------------------------- Clear 버튼 (구현 완료) -----------------------------
        // 구현 완료
        JButton clearButton = new JButton();

        clearButton.setBounds(140, 590, 100, 60);
        clearButton.setText("Clear!");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.clear();
            }
        });

        add(clearButton);

        // ----------------------------- 속도 슬라이더 (구현은 된 것 같은데 다른 요소가 작용하는 듯) -----------------------------
        // TODO 1. 속도 구현하기
        // 1. 텍스트 띄우기
        JLabel ballSpeedTexEditorPane = new JLabel();
        ballSpeedTexEditorPane.setBounds(30, 30, 30, 30);
        ballSpeedTexEditorPane.setText("속도");
        add(ballSpeedTexEditorPane);

        // 2. 슬라이더 보여주기
        JSlider ballSpeedControSlider = new JSlider(0, 100, 0);
        ballSpeedControSlider.setBounds(50, 50, 200, 100);

        ballSpeedControSlider.setPaintTrack(true);
        ballSpeedControSlider.setPaintTicks(true);
        ballSpeedControSlider.setPaintLabels(true);

        ballSpeedControSlider.setMajorTickSpacing(20);
        ballSpeedControSlider.setMinorTickSpacing(10);

        ballSpeedControSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                world.setBallSpeed(ballSpeedControSlider.getValue());
                logger.info(ballSpeedControSlider.getValue());
            }
        });

        add(ballSpeedControSlider);

        // ----------------------------- 각도 슬라이더 -----------------------------
        // 했다 치자

        // 1. 텍스트 띄우기
        JLabel angleControlJLabel = new JLabel();
        angleControlJLabel.setBounds(30, 140, 30, 30);
        angleControlJLabel.setText("각도");
        add(angleControlJLabel);

        // 2. 슬라이더 보여주기
        JSlider angleControlSlider = new JSlider(0, 90, 45);

        angleControlSlider.setBounds(50, 140, 200, 100);

        angleControlSlider.setPaintTrack(true);
        angleControlSlider.setPaintTicks(true);
        angleControlSlider.setPaintLabels(true);

        angleControlSlider.setMajorTickSpacing(20);
        angleControlSlider.setMinorTickSpacing(5);

        angleControlSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                world.setAngle(angleControlSlider.getValue());
                logger.info(5 * Math.cos(Math.toRadians(angleControlSlider.getValue())));
                logger.info(5 * Math.sin(Math.toRadians(angleControlSlider.getValue())));
            }

        });

        add(angleControlSlider);

        // ----------------------------- 중력 슬라이더 -----------------------------

        // 1. 텍스트 띄우기
        JLabel gravityControlJLabel = new JLabel();
        gravityControlJLabel.setBounds(30, 230, 30, 30);
        gravityControlJLabel.setText("중력");
        add(gravityControlJLabel);

        // 2. 슬라이더 보여주기
        JSlider gravityControSlider = new JSlider(0, 10, 1);

        gravityControSlider.setBounds(50, 240, 200, 100);

        gravityControSlider.setPaintTrack(true);
        gravityControSlider.setPaintTicks(true);
        gravityControSlider.setPaintLabels(true);

        gravityControSlider.setMajorTickSpacing(2);

        gravityControSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                world.setGravity(gravityControSlider.getValue());
            }

        });

        add(gravityControSlider);

        // ----------------------------- 바람 슬라이더 -----------------------------

        // 1. 텍스트 띄우기
        JLabel windSpeedControlJLabel = new JLabel();
        windSpeedControlJLabel.setBounds(30, 340, 30, 30);
        windSpeedControlJLabel.setText("바람");
        add(windSpeedControlJLabel);

        // 2. 슬라이더 보여주기
        JSlider windSpeedSlider = new JSlider(-10, 10, 0);

        windSpeedSlider.setBounds(50, 350, 200, 100);

        windSpeedSlider.setPaintTrack(true);
        windSpeedSlider.setPaintTicks(true);
        windSpeedSlider.setPaintLabels(true);

        windSpeedSlider.setMajorTickSpacing(2);

        windSpeedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                world.setWindSpeed(windSpeedSlider.getValue());
                logger.info(windSpeedSlider.getValue());
            }
        });
        add(windSpeedSlider);
    }

    public void start() {
        setVisible(true);
        setEnabled(true);

        world.run();
    }

    public static void main(String[] args) {
        CannonGame frame = new CannonGame();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        frame.start();
    }

    public void componentHidden(ComponentEvent event) {
        //
    }

    @Override
    public void componentMoved(ComponentEvent event) {
        //
    }

    @Override
    public void componentResized(ComponentEvent event) {
        if (getWidth() % BLOCK_WIDTH != 0) {
            setSize(getWidth() / BLOCK_WIDTH * BLOCK_WIDTH, getHeight());
        }
    }

    @Override
    public void componentShown(ComponentEvent event) {
        //
    }
}