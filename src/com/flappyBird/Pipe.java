package com.flappyBird;

import java.awt.*;
import java.util.Random;

public class Pipe {
    public int x, gapY;
    public boolean isScored;

    public Pipe(int x, int gapY) {
        this.x = x;
        this.gapY = gapY;
        this.isScored = false;
    }

    public void update() {
        x -= 5;
    }

    public boolean isOffScreen() {
        return x + GamePanel.PIPE_WIDTH < 0;
    }

    public void reset(int startX) {
        x = startX;
        gapY = new Random().nextInt(GamePanel.HEIGHT / 2) + GamePanel.HEIGHT / 4;
        isScored = false;
    }

    public boolean collidesWith(Bird bird) {
        return bird.x + 20 > x && bird.x < x + GamePanel.PIPE_WIDTH &&
               (bird.y < gapY || bird.y + 20 > gapY + GamePanel.PIPE_GAP);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, 0, GamePanel.PIPE_WIDTH, gapY); // Top pipe
        g.fillRect(x, gapY + GamePanel.PIPE_GAP, GamePanel.PIPE_WIDTH, GamePanel.HEIGHT - gapY - GamePanel.PIPE_GAP); // Bottom pipe
    }
}
