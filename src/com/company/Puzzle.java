package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Puzzle {

    int id;
    Map item;
    LinkedList cells;
    Map hints;
    Map across;
    Map down;

    public Puzzle(int idx, LinkedList data) {
        id = idx;
        item = (Map) data.get(id);
        cells = (LinkedList) item.get("cells");
        hints = (Map) item.get("hints");
        across = (Map) hints.get("across");
        down = (Map) hints.get("down");
    }

    public List getCells() {
        return cells;
    }

    public String getHint(Controller.PuzzleDirection direction, String key) {
        if (direction.equals(Controller.PuzzleDirection.ACROSS)) {
            return (String) across.get(key);
        } else {
            return (String) down.get(key);
        }
    }

}
