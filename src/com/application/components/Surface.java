package com.application.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Surface extends JPanel implements ActionListener {
    private final int FRAME_START = -20;
    private final int FRAME_END = 30;
    private final int RECT_WIDTH = 100;
    private final int RECT_WIDTH_IN_METRE = 5;
    private final int RECT_HEIGHT = 30;
    private final int GAP = 10;
    private final int NUM_RECTANGLES = FRAME_END - FRAME_START;
    private final int DELAY = 1000 / 60; // 60 fps
    private final Color BACKGROUND_COLOR = new Color(246, 211, 143, 255);
    private final Color SURFACE_COLOR = new Color(226, 135, 67);
    private final float MAX_VELOCITY = 100000;
    private float velocity = 0; // m/s = velocity * (1/10 pixels/ (DELAY ms))
    private float prev_velocity;
    private float prev_acc;
    private float acceleration = 0; // m/s^2 = acceleration * (1/10 pixels/ (DELAY ms)^2)
    private boolean isPaused = false;

    private int lastIndex = NUM_RECTANGLES - 1;
    private int firstIndex = 0;

    private int[] xPositions;

    private Timer timer;

    public Surface() {
        xPositions = new int[NUM_RECTANGLES];
        for (int i = FRAME_START; i < FRAME_END; i++) {
            xPositions[i - FRAME_START] = i * (RECT_WIDTH + GAP);
        }

        setMaximumSize(new Dimension(1200, 300));
        setBackground(BACKGROUND_COLOR);

        // Each DELAY milliseconds, the actionPerformed method will be called
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void setAcceralation(float acceleration) {
        this.acceleration = acceleration;
        this.velocity = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(SURFACE_COLOR);
        for (int x : xPositions) {
            g.fillRect(x, 0, RECT_WIDTH, RECT_HEIGHT);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (acceleration == 0.0f || isPaused) {
            return;
        } else if (velocity > 0) {
            for (int i = FRAME_START; i < FRAME_END; i++) {
                int j = i - FRAME_START;
                xPositions[j] -= velocity * ((RECT_WIDTH / RECT_WIDTH_IN_METRE) / DELAY);
                // If a rectangle moves out of the left side, reposition it to the right side
                if (xPositions[j] + RECT_WIDTH < FRAME_START * (RECT_WIDTH + GAP)) {
                    xPositions[j] = xPositions[lastIndex] + RECT_WIDTH + GAP;
                    lastIndex = j; // Update the last index to the current rectangle
                }
            }
        } else {
            for (int i = FRAME_END - 1; i >= FRAME_START; i--) {
                int j = i - FRAME_START;
                xPositions[j] -= velocity * ((RECT_WIDTH / RECT_WIDTH_IN_METRE) / DELAY);
                // If a rectangle moves out of the left side, reposition it to the right side
                if (xPositions[j] > FRAME_END * (RECT_WIDTH + GAP)) {
                    xPositions[j] = xPositions[firstIndex] - RECT_WIDTH - GAP;
                    firstIndex = j; // Update the first index to the current rectangle
                }
            }
        }
        if (velocity < MAX_VELOCITY) {
            velocity += acceleration / 60;
        }
        repaint();
    }

    public void pause() {
        isPaused = true;
    }

    public boolean checkPaused() {
        return isPaused;
    }

    public void resume() {
        isPaused = false;
    }
}