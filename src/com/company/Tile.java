package com.company;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends Rectangle {

    int[] tilePosition;
    char value;
    char guess;

    public Tile(double side, int xPos, int yPos){
        super(side,side);
        setPosition(yPos, xPos);
        this.setFill(Color.WHITE);
        this.setStyle("-fx-stroke: black; -fx-stroke-width: .5;");
    }

    public StackPane draw() {
        Text text = new Text ("" + guess);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(this, text);
        return stack;
    }

    public void setPosition(int x, int y) {
        tilePosition = new int[2];
        tilePosition[0] = y;
        tilePosition[1] = x;
    }

    public int[] getPosition() {
        return tilePosition;
    }

}
