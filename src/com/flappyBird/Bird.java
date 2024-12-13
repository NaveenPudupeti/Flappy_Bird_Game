package com.flappyBird;

import java.awt.*;

public class Bird {
    public int x, y, velocity;
    private static final int SIZE = 20;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
    }

    public void update() {
        velocity += GamePanel.GRAVITY;
        y += velocity;
    }

    public void jump() {
        velocity = GamePanel.JUMP_STRENGTH;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, SIZE, SIZE);
    }
}
