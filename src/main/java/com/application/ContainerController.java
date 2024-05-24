package com.application;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class ContainerController {
    @FXML
    private Pane containerPane;
    @FXML
    private Pane surfacePane;
    @FXML
    private Pane objectPane;

    @FXML
    public void initialize() {
        Rectangle clip = new Rectangle(containerPane.getPrefWidth(), containerPane.getPrefHeight());
        containerPane.setClip(clip);

        Surface surface = new Surface(surfacePane, 100, 50);
        surface.surfaceAnimation().start();
        createObject();
    }

    private void createObject() {

    }

    @FXML
    public void updateParams() {

    }

}
