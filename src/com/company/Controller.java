package com.company;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller {
    @FXML
    private GridPane puzzle;

    public void initialize() {
        // select a puzzle from file

        // build the game board
        drawBoard();
    }

    private void drawBoard() {
        // Create 25 rectangles and add to pane
        double side = 50; // side of rectangle

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = new Tile(side, j, i);
                puzzle.add(tile.draw(), j, i);
            }
        }
    }
}
