package com.flappyBird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final int PIPE_WIDTH = 80;
    static final int PIPE_GAP = 150;
    static final int GRAVITY = 2;
    static final int JUMP_STRENGTH = -15;

    private Timer timer;
    private Bird bird;
    private ArrayList<Pipe> pipes;
    private int score;
    private boolean gameOver;

    private enum GameState { START, PLAYING, GAME_OVER }
    private GameState gameState = GameState.START;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.CYAN);
        setFocusable(true);
        addKeyListener(this);

        initGame();
    }

    private void initGame() {
        bird = new Bird(WIDTH / 4, HEIGHT / 2);
        pipes = new ArrayList<>();
        score = 0;
        gameOver = false;

        timer = new Timer(20, this);
        timer.start();

        for (int i = 0; i < 3; i++) {
            pipes.add(new Pipe(WIDTH + i * 300, new Random().nextInt(HEIGHT / 2) + HEIGHT / 4));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState == GameState.START) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Press SPACE to Start", WIDTH / 2 - 180, HEIGHT / 2);
            return;
        }

        if (gameState == GameState.GAME_OVER) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("Game Over!", WIDTH / 2 - 150, HEIGHT / 2 - 50);
            g.drawString("Score: " + score, WIDTH / 2 - 100, HEIGHT / 2 + 50);
            g.drawString("Press R to Restart", WIDTH / 2 - 180, HEIGHT / 2 + 100);
            return;
        }

        bird.draw(g);

        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 10, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameState != GameState.PLAYING) return;

        bird.update();

        for (Pipe pipe : pipes) {
            pipe.update();

            if (pipe.collidesWith(bird)) {
                gameState = GameState.GAME_OVER;
            }

            if (!pipe.isScored && pipe.x + PIPE_WIDTH < bird.x) {
                score++;
                pipe.isScored = true;
            }

            if (pipe.isOffScreen()) {
                pipe.reset(WIDTH);
            }
        }

        if (bird.y > HEIGHT || bird.y < 0) {
            gameState = GameState.GAME_OVER;
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == GameState.START && e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameState = GameState.PLAYING;
        }

        if (gameState == GameState.GAME_OVER && e.getKeyCode() == KeyEvent.VK_R) {
            initGame();
            gameState = GameState.START;
        }

        if (gameState == GameState.PLAYING && e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
