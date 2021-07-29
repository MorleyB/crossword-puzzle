package com.company;

public class TileBuilder {
    // builder pattern is used for constructors
    // implemented by introducing a separate Builder class

    private int[] position;
    private String value;
    private char guess;
    private Boolean active = false;
    private Boolean focused = false;
    private Boolean isEmpty = false;
    private double side = 50; // side of rectangle

    TileBuilder setValue(String value) {
        this.value = value;
        return this;
    }

    TileBuilder setEmpty(String value) {
        this.isEmpty = value.equals("-");
        return this;
    }

    TileBuilder setActive(Boolean active) {
        this.active = active;
        return this;
    }

    TileBuilder setFocused(Boolean focused) {
        this.focused = focused;
        return this;
    }

    TileBuilder setPosition(int x, int y) {
        this.position = new int[2];
        this.position[0] = y;
        this.position[1] = x;
        return this;
    }

    public Boolean getIsEmpty() {
        return isEmpty;
    }

    Tile build() {
        return new Tile(side, position, value, active, focused, isEmpty);
    }
}