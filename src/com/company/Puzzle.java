package com.company;

import java.util.LinkedList;
import java.util.Map;

public class Puzzle {

    int id;
    Map item;
    LinkedList cells;
    Object hints;
    String[] hintsAcross;
    String[] hintsDown;

    public Puzzle(int idx, LinkedList data) {
        id = idx;
        item = (Map) data.get(id);
        cells = (LinkedList) item.get("cells");
        hints = item.get("hints");
    }

}
