package com.application;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Surface {
    private Pane surfacePane;

    private static final int RECTANGLE_WIDTH = 100;
    private static final int RECTANGLE_HEIGHT = 30;
    private static final int GAP = 10;

    private double velocity = 0;
    private double acceleration = 0;

    @SuppressWarnings("exports")
    public Surface(Pane surfacePane, double velocity, double acceleration) {
        this.surfacePane = surfacePane;
        this.velocity = velocity;
        this.acceleration = acceleration;
        createSurface();
    }

    private void createSurface() {
        for (int i = 0; i < 20; i++) {
            Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, RECTANGLE_HEIGHT, Color.BLUE);
            rectangle.setX(i * (RECTANGLE_WIDTH + GAP));
            surfacePane.getChildren().add(rectangle);
        }
    }

    private void moveSurface(double deltaTime) {
        velocity += acceleration * deltaTime;
        for (int i = 0; i < surfacePane.getChildren().size(); i++) {
            Rectangle rectangle = (Rectangle) surfacePane.getChildren().get(i);
            rectangle.setX(rectangle.getX() - velocity * deltaTime);

            if (rectangle.getX() + RECTANGLE_WIDTH < 0) {
                rectangle.setX(surfacePane.getChildren().size() * (RECTANGLE_WIDTH + GAP) - RECTANGLE_WIDTH);
            }
        }
    }

    @SuppressWarnings("exports")
    public AnimationTimer surfaceAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                    moveSurface(deltaTime);
                }
                lastUpdate = now;
            }
        };
        return timer;
    }
}
