package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.LinkedList;

public class Controller {

    @FXML
    private GridPane gamePane;

    @FXML
    private Label hint;

    @FXML
    private Label winnerMsg;

    LinkedList data;
    private GameBoard gameBoard;

    public void initialize() throws IOException, ParseException {
        data = new JsonFileReader().read();

        // build the game board
        gameBoard = new GameBoard(gamePane, data, hint);
    }

    @FXML private void handleBoardClicked(MouseEvent event) {
        try {
            // select tile
            gameBoard.setSelectedTile(event);

            // assess row orientation
            gameBoard.changeRowDirection();

            // remove active and focused properties
            gameBoard.resetCurrentRow();

            // set active and focused for new row
            gameBoard.setNextRow();
        } catch(Exception e) {
            return;
        }

    }


    @FXML private void handleTilePress(KeyEvent event) {
        gameBoard.selectTile(event);
    }

    @FXML private void handleNextAction(ActionEvent event) throws IOException, ParseException {
        gameBoard.reloadPuzzle();
        winnerMsg.setVisible(false);
    }

    @FXML private void handleClearAction(ActionEvent event) {
        gameBoard.clearTiles(event);
        winnerMsg.setVisible(false);
    }

    @FXML private void handleRevealAction(ActionEvent event) {
        gameBoard.revealTiles(event);
        winnerMsg.setVisible(false);
    }

    @FXML private void handleAutocheckAction(ActionEvent event) {
        gameBoard.checkTiles();

        if (gameBoard.getIsWinner()) {
            winnerMsg.setVisible(true);
        } else {
            winnerMsg.setVisible(false);
        }
    }
}
