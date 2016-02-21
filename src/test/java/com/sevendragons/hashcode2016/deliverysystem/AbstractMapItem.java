package com.sevendragons.hashcode2016.deliverysystem;

public abstract class AbstractMapItem implements MapItem {
    int id;
    int row;
    int col;

    AbstractMapItem(int id, int row, int col) {
        this.id = id;
        this.row = row;
        this.col = col;
    }

    public int getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
