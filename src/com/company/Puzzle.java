package com.company;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Puzzle {

    int id = 0;
    Map item;
    Map hints;
    Map across;
    Map down;

    LinkedList data;
    LinkedList cells;

    @FXML
    private Label hint;

    public Puzzle(LinkedList data, Label hint) {
        this.data = data;
        item = (Map) data.get(id);
        refreshPuzzle();
        this.hint = hint;
    }

    private void refreshPuzzle() {
        cells = (LinkedList) item.get("cells");
        hints = (Map) item.get("hints");
        across = (Map) hints.get("across");
        down = (Map) hints.get("down");
    }

    public List getCells() {
        return cells;
    }

    public String getDownHint(String key) {
        System.out.println("down: " + key);
        return (String) down.get(key);
    }

    public String getAcrossHint(String key) {
        System.out.println("across: " + key);
        return (String) across.get(key);
    }

    public Puzzle getNextPuzzle() {
        if (id == data.size() - 1) {
            id = 0;
        } else {
            id++;
        }

        item = (Map) data.get(id);
        refreshPuzzle();
        return this;
    }

}
