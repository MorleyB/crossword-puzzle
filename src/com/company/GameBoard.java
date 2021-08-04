package com.company;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameBoard {
    private enum PuzzleDirection {
        ACROSS,
        DOWN
    }

    @FXML
    private GridPane pane;

    @FXML
    private Label hint;

    LinkedList data;

    private Puzzle puzzle;
    private int puzzleSize = 5;
    private PuzzleDirection direction = PuzzleDirection.ACROSS;

    private Tile selectedTile;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();

    public Boolean isWinner = false;

    public GameBoard(GridPane pane, LinkedList data, Label hint) {
        this.pane = pane;
        this.hint = hint;
        this.direction = direction;

        // select a puzzle from file
        this.puzzle = new Puzzle(data, hint);

        // build the board
        draw();

        // set first hint
        hint.setText(selectHint());
    }

    private void draw() {
        // setup board for keypress events
        pane.setFocusTraversable(true);
        pane.requestFocus();

        // create tiles and add to pane
        char cellIdx = 0;
        boolean focusFirst = false;

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                String value = (String) puzzle.getCells().get(cellIdx);

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
                System.out.println(tile.toString());

                // store all tiles
                tiles.add(tile);

                // add tile to gameBoard
                pane.add(tile.draw(), j, i);
                cellIdx++;
            }
        }
    }

    public void redraw(Puzzle puzzle) {
        this.puzzle = puzzle;
        draw();
    }

    public void setSelectedTile(MouseEvent event) {
        selectedTile = (Tile) event.getTarget();
        System.out.println("You clicked rectangle: " + "" + selectedTile.toString());
    }

    public Boolean getIsWinner() {
        return isWinner;
    }

    public void changeRowDirection() {
        int y = selectedTile.getPosition()[0];
        int x = selectedTile.getPosition()[1];

        if(selectedTile.focused && direction == PuzzleDirection.ACROSS){
            direction = PuzzleDirection.DOWN;
            hint.setText(puzzle.getDownHint(String.valueOf(x)));
        } else {
            direction = PuzzleDirection.ACROSS;
            hint.setText(puzzle.getAcrossHint(String.valueOf(y)));
        }
    }

    public void resetCurrentRow() {
        ArrayList<Tile> currentRow = tiles.stream().filter(
                (node) -> node.focused == true || node.active == true)
                .collect(Collectors.toCollection(ArrayList::new));

        // loop tiles in active row
        // update properties
        for (Tile tile : currentRow) {
            tile.setActive(false);
            tile.setFocused(false);
        }
    }

    public void setNextRow() {
        int y = selectedTile.getPosition()[0];
        int x = selectedTile.getPosition()[1];
        ArrayList<Tile> nextRow;
        selectedTile.setFocused(true);

        // bilateral direction
        if (direction == PuzzleDirection.ACROSS) {
            System.out.println("across");
            nextRow = tiles.stream()
                    .filter(node -> node.getPosition()[0] == y)
                    .collect(Collectors.toCollection(ArrayList::new));
        } else {
            System.out.println("down");
            nextRow = tiles.stream()
                    .filter(node -> node.getPosition()[1] == x)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        // loop applicable tiles
        // update properties
        for (Tile node : nextRow) {
            if (node.isEmpty == false) {
                node.setActive(true);
            }
        }

    }

    public void selectTile(KeyEvent event) {
        String guess = event.getCode().toString();
        System.out.println("key pressed: " +  event.getCode() + " letter: " + event.getCode().isLetterKey());

        if (event.getCode().isLetterKey()) {
            // stream filter for active tile
            Optional<Tile> activeTile = tiles.stream()
                    .filter(Tile::getFocused)
                    .findFirst();

            Tile tile = activeTile.get();
            // call setter
            tile.setGuess(guess);
            tile.redraw();
        }
    }

    public void clearTiles(ActionEvent event) {
        for (Tile tile : tiles) {
            tile.clear();
        }
    }

    public void revealTiles(ActionEvent event) {
        for (Tile tile : tiles) {
            tile.reveal();
        }
    }

    public void checkTiles() {
        ArrayList<Tile> incorrectTiles = tiles.stream()
                .filter(tile -> tile.getIsCorrect().equals(false))
                .collect(Collectors.toCollection(ArrayList::new));

        for (Tile tile : incorrectTiles) {
            tile.displayError();
        }


        if (incorrectTiles.size() == 0) {
            ArrayList<Tile> correctTiles = tiles.stream()
                    .filter(tile -> tile.getIsCorrect().equals(true))
                    .collect(Collectors.toCollection(ArrayList::new));
            System.out.println("correct size: " + correctTiles.size() + " all size:" + tiles.size());
            if (correctTiles.size() == tiles.size()) {
                isWinner = true;
            }
        }
    }

    private String selectHint() {
        if (direction == PuzzleDirection.ACROSS) {
            return puzzle.getAcrossHint("0");
        } else {
            return puzzle.getDownHint("0");
        }
    }

    public void reloadPuzzle() {
        // create new puzzle
        puzzle.getNextPuzzle();

        // rebuild the game board
        redraw(puzzle);

        // set first hint
        hint.setText(selectHint());
    }
}
