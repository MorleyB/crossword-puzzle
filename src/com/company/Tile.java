package com.company;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends Rectangle {

    int[] tilePosition;
    String value;
    String guess = "";
    Boolean active;
    Boolean focused;
    Boolean isEmpty;
    StackPane stack;
    Boolean isCorrect = false;

    public Tile(double side, int[] position, String value, Boolean active, Boolean focused, Boolean empty){
        super(side,side);
        this.tilePosition = position;
        this.value = value;
        this.active = active;
        this.focused = focused;
        this.isEmpty = empty;
        this.stack = new StackPane();
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
    }

    public void setFocused(Boolean val) {
        focused = val;
        setTileProps();
    }

    public void setActive(Boolean val) {
        active = val;
        setTileProps();
    }

    public void setGuess(String val) {
        guess = val;
        if (guess.equals(value)) {
            isCorrect = true;
        } else {
            isCorrect = false;
        }
    }

    public StackPane draw() {
        stack.getChildren().addAll(this);
        return stack;
    }

    public void redraw() {
        Text text = new Text ("" + guess);
        text.setStyle("-fx-font: 20 system;");
        stack.getChildren().setAll(this, text);
    }

    public void reveal() {
        Text text = new Text ("" + value);
        text.setStyle("-fx-font: 20 system;");
        stack.getChildren().setAll(this, text);
        setGuess(value);
    }

    public void clear() {
        guess = "";
        redraw();
    }

    public void displayError() {
        System.out.println("displaying Error");
        Text text = new Text ("" + guess);
        text.setFill(Color.RED);
        text.setStyle("-fx-font: 20 system;");
        stack.getChildren().setAll(this, text);
    }

    public int[] getPosition() {
        return tilePosition;
    }

    public Boolean getFocused() {
        return focused;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    @Override
    public String toString() {
        return String.format(
                "Tile[acrossPosition=" + getPosition()[0] + ", downPosition=" + getPosition()[1] +
                        ", value=" + value + ", isEmpty=" + isEmpty + ", active=" + active
        );
    }
}
