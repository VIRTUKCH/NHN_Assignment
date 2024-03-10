package com.nhnacademy;

import java.awt.Color;

public class ScoreBox extends BrittleBox {
    int score;
    int life;

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    static final Color[] COLOR_TABLE = {
            Color.BLACK, // 1
            Color.RED, // 2
            Color.BLUE, // 3
            Color.YELLOW // 4
    };

    public ScoreBox(int x, int y, int width, int height) {
        super(x, y, width, height);

    }

    public ScoreBox(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        this.score = 0;

        for (int i = 0; i < COLOR_TABLE.length; i++) {
            if(this.color == COLOR_TABLE[i]) {
                life = score;
            }
        }
    }

    public int getLife() {
        return this.life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
