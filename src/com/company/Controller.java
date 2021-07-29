package com.company;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Controller {
    @FXML
    private GridPane gameBoard;

    // @FXML
    // private GridPane nextBtn;

    private Puzzle puzzle;
    private String orientation = "across";
    private ArrayList<Tile> tiles = new ArrayList<Tile>();


    private int puzzleSize = 5;

    public void initialize() throws IOException, ParseException {
        // select a puzzle from file
        generatePuzzle(2);

        // build the game board
        drawBoard();
    }

    private Puzzle generatePuzzle(int sampleId)  throws IOException, ParseException {
        LinkedList data = new JsonFileReader().read();
        // TODO set puzzle length ?
        puzzle = new Puzzle(sampleId, data);
        return puzzle;
    }

    private void drawBoard() {
        // create tiles and add to pane
        char cellIdx = 0;
        boolean focusFirst = false;

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                String value = (String) puzzle.cells.get(cellIdx);

                TileBuilder tileBldr = new TileBuilder()
                        .setPosition(j, i)
                        .setValue(value)
                        .setEmpty(value);

                if (i == 0) {
                    // default first row of
                    // across tiles to active
                    tileBldr.setActive(true);

                    // focus first non empty tile
                    if (!focusFirst && !tileBldr.getIsEmpty()) {
                        tileBldr.setFocused(true);
                        focusFirst = true;
                    }
                }

                // create tile
                Tile tile  = tileBldr.build();

                // store all tiles
                tiles.add(tile);

                // add tile to gameBoard
                gameBoard.add(tile.draw(), j, i);
                cellIdx++;
            }
        }
    }
}
