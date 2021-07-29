package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

public class Controller {

    LinkedList data;

    @FXML
    private GridPane gameBoard;

    @FXML
    private Label hint;

    private Puzzle puzzle;
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private int puzzleSize = 5;
    private int puzzleIdx = 0;

    public enum PuzzleDirection {
        ACROSS,
        DOWN;
    }

    private PuzzleDirection direction = PuzzleDirection.ACROSS;

    public void initialize() throws IOException, ParseException {
        data = new JsonFileReader().read();

        // select a puzzle from file
        generatePuzzle(puzzleIdx);

        // build the game board
        drawBoard();

        // set first hint
        hint.setText(puzzle.getHint(direction, "0"));
    }


    private Puzzle generatePuzzle(int sampleId)  throws IOException, ParseException {
        puzzle = new Puzzle(sampleId, data);
        return puzzle;
    }

    @FXML private void handleBoardClicked(MouseEvent event) {
        try {
            //  Block of code to try (
            Tile selectedTile = (Tile) event.getTarget();
            System.out.println("You clicked rectangle: " + "" + selectedTile.toString());

            // assess row orientation
            changeRowDirection(selectedTile);

            // remove active and focused properties
            resetCurrentRow();

            // set active and focused for new row
            setNextRow(selectedTile);
        } catch(Exception e) {
            return;
        }

    }

    @FXML private void handleTilePress(KeyEvent event) {
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

    @FXML private void handleNextAction(ActionEvent event) throws IOException, ParseException {
        puzzleIdx++;
        if (puzzleIdx >= data.size()) {
            puzzleIdx = 0;
        }

        // create new puzzle
        generatePuzzle(puzzleIdx);

        // rebuild the game board
        drawBoard();

        // set first hint
        hint.setText(puzzle.getHint(direction, "0"));

    }

    @FXML private void handleClearAction(ActionEvent event) {
        for (Tile tile : tiles) {
            tile.clear();
        }
    }

    @FXML private void handleAutocheckAction(ActionEvent event) {
        ArrayList<Tile> incorrectTiles = tiles.stream()
                .filter(tile -> !tile.getIsCorrect())
                .collect(Collectors.toCollection(ArrayList::new));

        for (Tile tile : incorrectTiles) {
            tile.displayError();
        }
    }

    private void changeRowDirection(Tile tile) {
        if(tile.focused && direction == PuzzleDirection.ACROSS){
            direction = PuzzleDirection.DOWN;
            hint.setText(
                    puzzle.getHint(
                            direction, String.valueOf(tile.getPosition()[1]
                            )
                    )
            );
        } else {
            direction = PuzzleDirection.ACROSS;
            hint.setText(
                    puzzle.getHint(
                            direction, String.valueOf(tile.getPosition()[0]
                            )
                    )
            );
        }
    }

    private void resetCurrentRow() {
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

    private void setNextRow(Tile tile) {
        int y = tile.getPosition()[0];
        int x = tile.getPosition()[1];
        ArrayList<Tile> nextRow;
        tile.setFocused(true);

        // bilateral direction
        if (direction == PuzzleDirection.ACROSS) {
            nextRow = tiles.stream()
                    .filter(node -> node.getPosition()[0] == y)
                    .collect(Collectors.toCollection(ArrayList::new));
        } else {
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

    private void drawBoard() {
        // setup board for keypress events
        gameBoard.setFocusTraversable(true);
        gameBoard.requestFocus();

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
                gameBoard.add(tile.draw(), j, i);
                cellIdx++;
            }
        }
    }
}
