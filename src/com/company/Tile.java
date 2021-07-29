package com.company;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends Rectangle {

    int[] tilePosition;
    String value;
    char guess;
    Boolean active;
    Boolean focused;
    Boolean isEmpty;

    public Tile(double side, int[] position, String value, Boolean active, Boolean focused, Boolean empty){
        super(side,side);
        tilePosition = position;
        this.value = value;
        this.active = active;
        this.focused = focused;
        this.isEmpty = empty;
        setTileProps();
    }

    public void setTileProps() {
        if (!isEmpty) {
            this.setFill(Color.WHITE);
            this.setStyle("-fx-stroke: black; -fx-stroke-width: .5;");
        } else {
            this.setStyle("-fx-stroke: white; -fx-stroke-width: .2;");
        }

        if (active && !isEmpty) {
            if (focused) {
                this.setFill(Color.LIGHTGOLDENRODYELLOW);
            } else {
                this.setFill(Color.LIGHTBLUE);
            }
        }
        System.out.println("value: " + value);
    }

    public StackPane draw() {
        Text text = new Text ("" + guess);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(this, text);
        return stack;
    }

    public int[] getPosition() {
        return tilePosition;
    }
}
